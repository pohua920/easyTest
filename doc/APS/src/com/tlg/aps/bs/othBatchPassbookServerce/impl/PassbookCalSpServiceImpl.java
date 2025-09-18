package com.tlg.aps.bs.othBatchPassbookServerce.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.othBatchPassbookServerce.AS400ToPassbookDataService;
import com.tlg.aps.bs.othBatchPassbookServerce.PassbookCalSpService;
import com.tlg.aps.bs.othBatchPassbookServerce.PassbookDataInsertService;
import com.tlg.prpins.entity.FirBatchInfo;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.entity.OthBatchPassbookNcData;
import com.tlg.prpins.service.FirBatchInfoService;
import com.tlg.prpins.service.OthBatchPassbookNcDataService;
import com.tlg.prpins.service.OthPassbookSpService;
import com.tlg.util.Mailer;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：OTH0131，處理人員：BJ085，需求單編號：OTH0131 保發中心-保單存摺各險寫入中介Table作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.NEVER, readOnly = false, rollbackFor = Exception.class)
public class PassbookCalSpServiceImpl implements PassbookCalSpService {

	private static final Logger logger = Logger.getLogger(PassbookCalSpServiceImpl.class);

	private PassbookDataInsertService passbookDataInsertService;
	private FirBatchInfoService firBatchInfoService;
	private OthPassbookSpService othPassbookSpService;
	private OthBatchPassbookNcDataService othBatchPassbookNcDataService;
	private AS400ToPassbookDataService as400ToPassbookDataService;

	@Override
	public Result excuteAndCallSp(String userId, String programId, String riskcode,
			String spName, String policyno, String type, Date undate) throws Exception {
		// 新增執行記錄檔、判斷排程是否可以執行
		StringBuilder sb = new StringBuilder();
		if (StringUtil.isSpace(userId)) {
			sb.append("執行人員無內容值。");
		}
		if (StringUtil.isSpace(programId)) {
			sb.append("程式代碼無內容值。");
		}
		String batchNo = "OTHNC_" + new SimpleDateFormat("yyMMddHHmmss").format(new Date());
		if (!StringUtil.isSpace(sb.toString())) {
			Result result = passbookDataInsertService.insertFirBatchLog(new Date(), userId, programId, "F", sb.toString(), batchNo);
			if (result.getResObject() != null) {
				FirBatchLog firBatchLog = (FirBatchLog) result.getResObject();
				String mailMsg = sendEmail(batchNo, new Date(), "F", sb.toString(), programId);
				if (!StringUtil.isSpace(mailMsg)) {
					passbookDataInsertService.updateFirBatchLog("F", mailMsg, userId, firBatchLog);
				}
			}
			return this.getReturnResult("接收參數無值，結束排程");
		}
		String status = "1";
		String msg = "";
		Map<String, String> params = new HashMap<>();
		params.put("prgId", programId + "_STATUS");
		Result result = firBatchInfoService.findFirBatchInfoByUK(params);
		if (result.getResObject() != null) {
			FirBatchInfo firBatchInfo = (FirBatchInfo) result.getResObject();
			if (firBatchInfo.getMailTo().equals("N")) {
				status = "S";
				msg = "FIR_BATCH_INFO設定檔設定為排程暫停執行。";
			}
		}
		if (result.getResObject() == null) {
			status = "F";
			msg = "未設定保發保單存摺新核心資料寫入中介排程設定(FIR_BATCH_INFO)。";
		}

		result = passbookDataInsertService.insertFirBatchLog(new Date(), userId, programId, status, msg, batchNo);
		if (status.equals("S")) {
			return this.getReturnResult(msg);
		}
		if (status.equals("F")) {
			return this.getReturnResult(msg);
		}
		
		if (result.getResObject() != null) {
			FirBatchLog firBatchLog = (FirBatchLog) result.getResObject();
			BigDecimal batchLogOid = firBatchLog.getOid();

			int resultVal = 0;
			String remark = "";
			if(undate == null) {
				undate = getYesterday();
			}
			
			Map<String, Object> returnMap = new HashMap<>();
			try {
				//開始呼叫SP，先判斷是整批執行或是單筆執行
				if("P".equals(type)) {//單筆執行
					returnMap = callSpOne(batchNo, userId, riskcode, spName, policyno, undate);
					resultVal = (int) returnMap.get("outResult");
					remark = (String) returnMap.get("remark");
					if (1 == resultVal) {
						status = "F";
						updateFirBatchLog(status, remark, userId, batchLogOid);
						String mailMsg = sendEmail(batchNo, new Date(), status, remark, programId);
						if (!StringUtil.isSpace(mailMsg)) {
							updateFirBatchLog(status, mailMsg, userId, batchLogOid);
						}
						return this.getReturnResult("執行失敗，批次序號："+batchNo+"，請查明錯誤原因。");
					}
				}else {//整批執行
					callSpBatch(batchNo, userId, undate);
				}
			}catch (Exception e) {
				logger.error("passbook callSp error",e);
				status = "F";
				updateFirBatchLog(status, e.toString(), userId, batchLogOid);
				String mailMsg = sendEmail(batchNo, new Date(), status,  e.toString(), programId);
				if (!StringUtil.isSpace(mailMsg)) {
					updateFirBatchLog(status,  e.toString()+mailMsg, userId, batchLogOid);
				}
				return this.getReturnResult("執行失敗，批次序號："+batchNo+"，請查明錯誤原因。"+e.toString());
			}
			
			status = "S";
			updateFirBatchLog(status, "", userId, batchLogOid);
			String mailMsg = sendEmail(batchNo, new Date(), status, "", programId);
			if (!StringUtil.isSpace(mailMsg)) {
				updateFirBatchLog(status, mailMsg, userId, batchLogOid);
			}
		}
		
		return this.getReturnResult("執行成功，批次序號："+batchNo+"，完整執行結果請使用查詢功能。");
	}
	
	private Map<String, Object> callSpOne(String batchNo, String userId, String riskcode, 
			String spName, String policyno, Date undate) throws Exception {
		String produceType = spName.substring(20);
		String batchSerial =  batchNo + riskcode + produceType;
		String spCode = spName.substring(16);
		
		Result result = passbookDataInsertService.insertOthBatchPassbookNcData(batchNo,
				riskcode, produceType, spName, userId);
		String status = "N";
		int outResult = 1;
		int as400Count = 0;
		String remark = "";
		Map<String, Object> returnMap = new HashMap<>();
		if(result.getResObject()!=null) {
			Map<String, Object> params = new HashMap<>();
			params.put("inBatchNoNc", batchSerial);
			params.put("inUndate", undate);
			params.put("inUser", userId);
			params.put("inRiskcode",riskcode);
			if(!StringUtil.isSpace(policyno)) {
				params.put("inPolicyno",policyno);
			}
			try {
				if("MAR_P".equals(spCode)) {
					outResult = othPassbookSpService.runSpOthPassbookMarP(params);
				}
				if("MAR_E".equals(spCode)) {
					outResult = othPassbookSpService.runSpOthPassbookMarE(params);
				}
				if("FIR_P".equals(spCode)) {
					outResult = othPassbookSpService.runSpOthPassbookFirP(params);
				}
				if("FIR_E".equals(spCode)) {
					outResult = othPassbookSpService.runSpOthPassbookFirE(params);
				}
				if("CAL_P".equals(spCode)) {
					outResult = othPassbookSpService.runSpOthPassbookCalP(params);
				}
				if("CAL_E".equals(spCode)) {
					outResult = othPassbookSpService.runSpOthPassbookCalE(params);
				}
				if("CAR_P".equals(spCode)) {
					outResult = othPassbookSpService.runSpOthPassbookCarP(params);
				}
				if("CAR_E".equals(spCode)) {
					outResult = othPassbookSpService.runSpOthPassbookCarE(params);
				}
				if("LOP_P".equals(spCode)) {
					outResult = othPassbookSpService.runSpOthPassbookLopP(params);
				}
				if("LOP_E".equals(spCode)) {
					outResult = othPassbookSpService.runSpOthPassbookLopE(params);
				}
				if("400_A".equals(spCode)) {
					as400Count = as400ToPassbookDataService.insertOthBatchPassbookListFromAs400(batchSerial, userId);
					outResult = 0;
				}
			}catch (Exception e) {
				logger.error("passbook callSp error",e);
				remark = e.toString();
			}
			
			if (outResult == 0) {
				status = "Y";
			}
			
			OthBatchPassbookNcData othBatchPassbookNcData = (OthBatchPassbookNcData) result.getResObject();
			othBatchPassbookNcData.setStatus(status);
			othBatchPassbookNcData.setDupdate(new Date());
			if(remark.length()>500) {
				remark = remark.substring(0,500);
			}
			othBatchPassbookNcData.setRemark(remark);
			if("400_A".equals(spCode) && "Y".equals(status)) {
				othBatchPassbookNcData.setQty(Long.valueOf(as400Count));
			}
			passbookDataInsertService.updateOthBatchPassbookNcData(othBatchPassbookNcData);
		}
		
		returnMap.put("outResult", outResult);
		returnMap.put("remark", remark);
		return returnMap;
	}
	
	private String getSpName(String classcode, String produceType) {
		if("M".equals(classcode)) {
			return "SP_OTH_PASSBOOK_MAR_"+produceType;
		}
		if("F".equals(classcode)) {
			return "SP_OTH_PASSBOOK_FIR_"+produceType;
		}
		if("B".equals(classcode)) {
			return "SP_OTH_PASSBOOK_CAL_"+produceType;
		}
		if("A".equals(classcode)) {
			return "SP_OTH_PASSBOOK_CAR_"+produceType;
		}
		if("C".equals(classcode)) {
			return "SP_OTH_PASSBOOK_LOP_"+produceType;
		}
		
		return produceType;
	}
	
	@SuppressWarnings("unchecked")
	private void callSpBatch(String batchNo, String userId, Date undate) throws Exception {//執行類型為固定接受參數1
		String spNameP = "";
		String spNameE = "";
		Map<String, Object> params = new HashMap<>();
		params.put("prgIdLike", "OTH_PASSBOOK_NC_DATA_RISK_");
		Result result = firBatchInfoService.findFirBatchInfoByParams(params);
		if(result.getResObject()!=null) {
			List<FirBatchInfo> riskList = (List<FirBatchInfo>) result.getResObject();
			
			Map<String, Object> returnMap = new HashMap<>();
			for(FirBatchInfo firBatchInfo : riskList) {
				String classcode = firBatchInfo.getPrgId().substring(26);//大險種分類
				String[] riskArr = firBatchInfo.getMailTo().split(",");//險種以,切割
				for(int i=0;i<riskArr.length;i++) {
					if("AS400".equals(classcode)) {
						callSpOne(batchNo, userId, riskArr[i], "OTH_PASSBOOK_TO_400_A", "", undate);
						continue;
					}
					spNameP = getSpName(classcode,"P");
					returnMap = callSpOne(batchNo, userId, riskArr[i], spNameP, "", undate);
					if((Integer)returnMap.get("outResult") == 0) {
						spNameE = getSpName(classcode,"E");
						callSpOne(batchNo, userId, riskArr[i], spNameE, "", undate);
					}
				}
			}
		}
	}
	
	private Date getYesterday() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date sysdate = sdf.parse(sdf.format(new Date()));
		Calendar cl = Calendar.getInstance();
		cl.setTime(sysdate);
		cl.add(Calendar.DATE, -1);// 取前一天
		return cl.getTime();
	}
	
	@SuppressWarnings("unchecked")
	private String sendEmail(String batchNo, Date excuteTime, String logStatus, String errMsg, String programId) {
		Mailer mailer = new Mailer();
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("prgId", programId);
			Result result = this.firBatchInfoService.findFirBatchInfoByUK(params);
			if (result.getResObject() == null) {
				return "無法取得FIR_BATCH_INFO資料，無法寄送MAIL";
			}
			
			FirBatchInfo firBatchInfo = (FirBatchInfo) result.getResObject();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String subject = firBatchInfo.getMailSubject() + "-" + sdf.format(new Date());
			String mailTo = firBatchInfo.getMailTo();
			String mailCc = firBatchInfo.getMailCc();

			StringBuilder sb = new StringBuilder();
			sb.append("<p>批次號碼：" + batchNo + "</p>");
			sb.append("<p>轉檔時間：" + sdf.format(excuteTime) + "</p>");
			if (logStatus.equals("S")) {
				sb.append("<p>執行狀態：完成</p>");
				params.clear();
				params.put("batchNo", batchNo);
				Result mainResult = othBatchPassbookNcDataService.findOthBatchPassbookNcDataByParams(params);

				if (mainResult.getResObject() == null) {
					sb.append("OTH_BATCH_PASSBOOK批次送保發保單存摺新核心資料寫入中介排程主檔查無資料，請洽系統人員。");
				} else {
					sb.append("<table border=1 style='border-collapse: collapse;'>");
					sb.append("<tr bgcolor='#70bbd9'>");
					sb.append("<td width='50px'>序號</td>");
					sb.append("<td width='100px'>狀態</td>");
					sb.append("<td>批次序號</td>");
					sb.append("<td width='50px'>險種別</td>");
					sb.append("<td width='50px'>報送保發筆數</td>");
					sb.append("<td>檔案名稱</td>");
					sb.append("<td>備註</td>");
					sb.append("</tr>");
					List<OthBatchPassbookNcData> othBatchPassbookNcDataList = (List<OthBatchPassbookNcData>) mainResult.getResObject();
					int serial = 1;
					for (OthBatchPassbookNcData othBatchPassbookNcData : othBatchPassbookNcDataList) {
						sb.append("<tr>");
						String status = othBatchPassbookNcData.getStatus();
						if ("Y".equals(status)) {
							status = "傳送成功";
						} else if ("N".equals(status)) {
							status = "傳送失敗";
						}
						String qty = othBatchPassbookNcData.getQty()==null?"":othBatchPassbookNcData.getQty().toString();
						
						String remark = othBatchPassbookNcData.getRemark()==null?"":othBatchPassbookNcData.getRemark();
						sb.append("<td>" + serial + "</td>");
						sb.append("<td>" + status + "</td>");
						sb.append("<td>" + othBatchPassbookNcData.getBatchSerial() + "</td>");
						sb.append("<td>" + othBatchPassbookNcData.getRiskcode() + "</td>");
						sb.append("<td>" + qty + "</td>");
						sb.append("<td>" + othBatchPassbookNcData.getFilename() + "</td>");
						sb.append("<td>" + remark + "</td>");
						sb.append("</tr>");
						serial++;
					}
					sb.append("</table>");
				}
			} else if (logStatus.equals("N")) {
				sb.append("<p>執行狀態：無資料 </p>");
			} else {
				sb.append("<p>執行狀態：" + logStatus + "</p>");
				sb.append("<p>異常訊息：" + StringUtil.nullToSpace(errMsg) + "</p>");
			}
			mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", "", mailTo,
					"", mailCc, "", "", "", sb.toString(), "smtp", "newims", "2012newims");
		} catch (Exception e) {
			logger.error("passbookCallSp sendEmail error", e);
		}
		return "";
	}
	
	private void updateFirBatchLog(String status, String outMsg, String userId, BigDecimal oid) throws Exception {
		FirBatchLog firBatchLog = new FirBatchLog();
		firBatchLog.setOid(oid);
		passbookDataInsertService.updateFirBatchLog(status, outMsg, userId, firBatchLog);
	}

	private Result getReturnResult(String msg) {
		Result result = new Result();
		Message message = new Message();
		message.setMessageString(msg);
		result.setMessage(message);
		return result;
	}

	public PassbookDataInsertService getPassbookDataInsertService() {
		return passbookDataInsertService;
	}

	public void setPassbookDataInsertService(PassbookDataInsertService passbookDataInsertService) {
		this.passbookDataInsertService = passbookDataInsertService;
	}

	public FirBatchInfoService getFirBatchInfoService() {
		return firBatchInfoService;
	}

	public void setFirBatchInfoService(FirBatchInfoService firBatchInfoService) {
		this.firBatchInfoService = firBatchInfoService;
	}

	public OthPassbookSpService getOthPassbookSpService() {
		return othPassbookSpService;
	}

	public void setOthPassbookSpService(OthPassbookSpService othPassbookSpService) {
		this.othPassbookSpService = othPassbookSpService;
	}

	public OthBatchPassbookNcDataService getOthBatchPassbookNcDataService() {
		return othBatchPassbookNcDataService;
	}

	public void setOthBatchPassbookNcDataService(OthBatchPassbookNcDataService othBatchPassbookNcDataService) {
		this.othBatchPassbookNcDataService = othBatchPassbookNcDataService;
	}

	public AS400ToPassbookDataService getAs400ToPassbookDataService() {
		return as400ToPassbookDataService;
	}

	public void setAs400ToPassbookDataService(AS400ToPassbookDataService as400ToPassbookDataService) {
		this.as400ToPassbookDataService = as400ToPassbookDataService;
	}

}
