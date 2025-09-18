package com.tlg.aps.bs.firProcFpolicyRerunService.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firProcFpolicyRerunService.FirProcFpolicyRerunService;
import com.tlg.aps.bs.firProcFpolicyRerunService.FpolicyRerunService;
import com.tlg.prpins.entity.FirBatchInfo;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.entity.Prpcmain;
import com.tlg.prpins.service.FirBatchInfoService;
import com.tlg.prpins.service.FirSpService;
import com.tlg.prpins.service.PrpcmainService;
import com.tlg.util.Mailer;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：FIR0635，處理人員：CD094，需求單編號：FIR0635_住火_新核心保單轉入中介檔異常處理排程  **/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirProcFpolicyRerunServiceImpl implements FirProcFpolicyRerunService {

	private static final Logger logger = Logger.getLogger(FirProcFpolicyRerunServiceImpl.class);
	
	private FpolicyRerunService fpolicyRerunService;
	private FirBatchInfoService firBatchInfoService;
	private PrpcmainService prpcmainService;
	private FirSpService firSpService;


	@Override
	public Result rerunFpolicy(Date excuteTime,String underwriteenddate,String userId,String programId) throws Exception {
		// 新增執行記錄檔、判斷排程是否可以執行
		StringBuilder sb = new StringBuilder();
		
		if (excuteTime == null) {
			sb.append("執行時間無內容值或內容值錯誤。");
		}
		if (StringUtil.isSpace(underwriteenddate)) {
			sb.append("簽單日無內容值或內容值錯誤。");
		}
		if (StringUtil.isSpace(userId)) {
			sb.append("執行人員無內容值或內容值錯誤。");
		}
		if (StringUtil.isSpace(programId)) {
			sb.append("程式代碼無內容值或內容值錯誤。");
		}
		FirBatchLog firBatchLog;
		String batchNo ="PROCP" + new SimpleDateFormat("yyMMddHHmmss").format(excuteTime);
		if (!StringUtil.isSpace(sb.toString())) {
			Result result = fpolicyRerunService.insertFirBatchLog(excuteTime, userId, programId, "F", sb.toString(),
					batchNo);
			return this.getReturnResult("接收參數無值，結束排程");
		}
		String status = "1";
		String msg = "";
		Map<String, Object> params = new HashMap<>();
		params.put("prgId", programId + "_STATUS");
		Result result = firBatchInfoService.findFirBatchInfoByUK(params);
		if (result.getResObject() != null) {
			FirBatchInfo firBatchInfo = (FirBatchInfo) result.getResObject();
			if ("N".equals(firBatchInfo.getMailTo())) {
				status = "S";
				msg = "FIR_BATCH_INFO設定檔設定為排程暫停執行。";
			}
		} else {
			return this.getReturnResult("查無此排程代號，請詢問技術人員");
		}

		result = fpolicyRerunService.insertFirBatchLog(excuteTime, userId, programId, status, msg, batchNo);
		firBatchLog = (FirBatchLog) result.getResObject();
		if (status.equals("S")) {
			return this.getReturnResult("查詢狀態為N，不執行排程");
		}
		if (result.getResObject() != null) {
			params.clear();
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd");
			params.put("underwriteenddate", sdf.parse(underwriteenddate));
			result = prpcmainService.findForRerunFpolicy(params);
			params.clear();
				List<Prpcmain>searchResult= (List<Prpcmain>)result.getResObject();
				if(!searchResult.isEmpty()&&searchResult!=null){
					for(Prpcmain main :searchResult){
						params.put("batchno", main.getPolicyno());
						try{
							firSpService.runSpProcFpolicyInterinfo(params);		
							status="S";
						}catch(Exception e){
							msg="執行保單轉AS400中介SP出現異常:"+ e.getMessage().toString();
							status="F";
						}
					}
					msg = sendEmail(batchNo,excuteTime,status,msg,programId,searchResult);
				}
			else{
				status="S";
				msg="查無轉入中介檔異常資料，無需處理";
			}
			fpolicyRerunService.updateFirBatchLog(status, msg, userId, firBatchLog);

		}

		return this.getReturnResult(msg.isEmpty()?"執行完成":msg);
	}
	@SuppressWarnings("unchecked")
	private String sendEmail(String batchNo, Date excuteTime, String status, String msg, String programId,List<Prpcmain>searchResult) {
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
			String subject = firBatchInfo.getMailSubject() ;
			String mailTo = firBatchInfo.getMailTo();
			String mailCc = firBatchInfo.getMailCc();
//			String mailCc = "";
//			String mailTo = "cd094@ctbcins.com";

			StringBuilder sb = new StringBuilder();
			sb.append("<p>批次號碼：" + batchNo + "</p>");
			sb.append("<p>轉檔時間：" + sdf.format(excuteTime) + "</p>");
			if ("S".equals(status)) {
				sb.append("<p>執行狀態：完成</p>");
			}else if("F".equals(status)){
				sb.append("<p>執行狀態：異常</p>");
				sb.append(msg);
			}
					sb.append("<table border=1 style='border-collapse: collapse;'>");
					sb.append("<tr bgcolor='#70bbd9'>");
					sb.append("<td>保單號</td>");
					sb.append("<td>簽單日</td>");
					sb.append("</tr>");
					for (Prpcmain main : searchResult) {
						sb.append("<tr>");
						sb.append("<td>" + main.getPolicyno() + "</td>");
						sb.append("<td>" + sdf.format(main.getUnderwriteenddate()) + "</td>");
						sb.append("</tr>");
					}
					sb.append("</table>");

			mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", "", mailTo,
					"", mailCc, "", "", "", sb.toString(), "smtp", "newims", "2012newims");
		} catch (Exception e) {
			logger.error("FBRN sendEmail Exception", e);
		}
		return msg;
	}

	private Result getReturnResult(String msg) {
		Result result = new Result();
		Message message = new Message();
		message.setMessageString(msg);
		result.setMessage(message);
		return result;
	}


	public FpolicyRerunService getFpolicyRerunService() {
		return fpolicyRerunService;
	}
	public void setFpolicyRerunService(FpolicyRerunService fpolicyRerunService) {
		this.fpolicyRerunService = fpolicyRerunService;
	}
	public FirBatchInfoService getFirBatchInfoService() {
		return firBatchInfoService;
	}
	public void setFirBatchInfoService(FirBatchInfoService firBatchInfoService) {
		this.firBatchInfoService = firBatchInfoService;
	}
	public PrpcmainService getPrpcmainService() {
		return prpcmainService;
	}
	public void setPrpcmainService(PrpcmainService prpcmainService) {
		this.prpcmainService = prpcmainService;
	}
	public FirSpService getFirSpService() {
		return firSpService;
	}
	public void setFirSpService(FirSpService firSpService) {
		this.firSpService = firSpService;
	}

}
