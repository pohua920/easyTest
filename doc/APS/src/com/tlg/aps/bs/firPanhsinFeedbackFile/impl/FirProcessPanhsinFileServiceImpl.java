package com.tlg.aps.bs.firPanhsinFeedbackFile.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firPanhsinFeedbackFile.FirProcessPanhsinFileService;
import com.tlg.aps.bs.firPanhsinFeedbackFile.ProcessPanhsinFileService;
import com.tlg.aps.vo.FileUploadResponseVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtBatchDtl;
import com.tlg.prpins.entity.FirAgtBatchGenfile;
import com.tlg.prpins.entity.FirAgtBatchMain;
import com.tlg.prpins.entity.FirAgtBop01;
import com.tlg.prpins.entity.FirAgtBop02;
import com.tlg.prpins.entity.FirBatchInfo;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.service.FirAgtBatchDtlService;
import com.tlg.prpins.service.FirAgtBatchGenfileService;
import com.tlg.prpins.service.FirAgtBatchMainService;
import com.tlg.prpins.service.FirAgtBop01Service;
import com.tlg.prpins.service.FirAgtBop02Service;
import com.tlg.prpins.service.FirBatchInfoService;
import com.tlg.prpins.service.FirSpService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.FtsUtil;
import com.tlg.util.Mailer;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.NEVER, readOnly = false, rollbackFor = Exception.class)
public class FirProcessPanhsinFileServiceImpl implements FirProcessPanhsinFileService {
	/* mantis：FIR0265，處理人員：BJ085，需求單編號：FIR0265 板信受理檔產生排程
	   mantis：FIR0266，處理人員：BJ085，需求單編號：FIR0266 板信資料交換處理作業
	   mantis：FIR0271，處理人員：BJ085，需求單編號：FIR0271 板信保單檔產生作業-排程作業  start */

	private ProcessPanhsinFileService processPanhsinFileService;
	private FirBatchInfoService firBatchInfoService;
	private FirSpService firSpService;
	private FirAgtBatchMainService firAgtBatchMainService;
	private FirAgtBatchDtlService firAgtBatchDtlService;
	private FirAgtBop01Service firAgtBop01Service;
	private FirAgtBop02Service firAgtBop02Service;
	private FirAgtBatchGenfileService firAgtBatchGenfileService;
	
	private ConfigUtil configUtil;

	@Override
	public Result RunToGenerateFiles(String userId,Date excuteTime,String programId) throws Exception {
		//新增執行記錄檔、判斷排程是否可以執行
		StringBuilder sb = new StringBuilder();
		if(StringUtil.isSpace(userId)){
			sb.append("執行人員無內容值。");
		} 
		if(excuteTime==null) {
			sb.append("轉檔時間無內容值。");
		} 
		if(StringUtil.isSpace(programId)){
			sb.append("程式代碼無內容值。");
		}
		String batchNo = "BOP"+programId.substring(12,14)+"_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		if(!StringUtil.isSpace(sb.toString())) {
			Result result = processPanhsinFileService.insertFirBatchLog(excuteTime, userId, programId, "F", sb.toString(),batchNo);
			if(result.getResObject()!=null) {
				FirBatchLog firBatchLog = (FirBatchLog) result.getResObject();
				if(!sendEmail(firBatchLog.getBatchNo(),excuteTime,"F",sb.toString(),null,programId)) {
					processPanhsinFileService.updateFirBatchLog("F", "無法取得FIR_BATCH_INFO資料，無法寄送MAIL", userId, firBatchLog);
				}
			}
			return this.getReturnResult("接收參數無值，結束排程");
			//結束排程
		}
		String status = "1";
		String msg="";
		Map<String,String> params = new HashMap<>();
		params.put("prgId", programId+"_STATUS");
		Result result = firBatchInfoService.findFirBatchInfoByUK(params);
		
		if(result.getResObject()!=null) {
			FirBatchInfo firBatchInfo = (FirBatchInfo) result.getResObject();
			if(firBatchInfo.getMailTo().equals("N")) {
				status = "S";
				msg = "FIR_BATCH_INFO設定檔設定為排程暫停執行。";
			}
		}
		result = processPanhsinFileService.insertFirBatchLog(excuteTime, userId, programId, status, msg, batchNo);
		if(status.equals("S")) {
			return this.getReturnResult("查詢狀態為N，不執行排程");
		}
		if(result.getResObject()!=null) {
			//呼叫sp
			FirBatchLog firBatchLog = (FirBatchLog) result.getResObject();
			FirAgtBatchMain firAgtBatchMain = callSp(firBatchLog.getBatchNo(),userId,programId);
			processPanhsinFileService.updateFirBatchLog(firAgtBatchMain.getTempStatus(), firAgtBatchMain.getTempMsg(), userId, firBatchLog);
			if(!sendEmail(firBatchLog.getBatchNo(), excuteTime, firAgtBatchMain.getTempStatus(), firAgtBatchMain.getTempMsg(),firAgtBatchMain,programId)) {
				processPanhsinFileService.updateFirBatchLog(firAgtBatchMain.getTempStatus(), "無法取得FIR_BATCH_INFO資料，無法寄送MAIL", userId, firBatchLog);
			}	
		}
		return result;
	}
	
	private FirAgtBatchMain callSp(String batchNo, String userId, String programId) {
		FirAgtBatchMain firAgtBatchMain = new FirAgtBatchMain();
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("inBatchNo", batchNo);
			params.put("inUser", userId);
			params.put("outResult", null);
			int returnValue = 0;
			if(programId.equals("FIR_AGT_BOP_01")) {
				returnValue = firSpService.runSpFirAgtBop01(params);				
			}else if(programId.equals("FIR_AGT_BOP_02")) {
				returnValue = firSpService.runSpFirAgtBop02(params);	
			}
			if(returnValue != 0) {
				firAgtBatchMain.setTempStatus("F");
				firAgtBatchMain.setTempMsg("執行SP失敗(SP_"+programId+")");
				return firAgtBatchMain;
			}
			params.clear();
			params.put("batchNo", batchNo);
			Result result = firAgtBatchMainService.findFirAgtBatchMainByUk(params);
			if(result.getResObject()!=null) {
				firAgtBatchMain = (FirAgtBatchMain) result.getResObject();
				if(firAgtBatchMain.getFileStatus().equals("Z")) {
					firAgtBatchMain.setTempStatus("N");//檔案無資料
				}else {
					firAgtBatchMain.setTempStatus("S");
				}
				firAgtBatchMain.setTempMsg("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return firAgtBatchMain;
	}

	@SuppressWarnings("unchecked")
	private boolean sendEmail(String batchNo,Date excuteTime,String status,String errMsg,FirAgtBatchMain firAgtBatchMain,String programId) {
		Mailer mailer = new Mailer();
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("prgId", programId);
			Result result = this.firBatchInfoService.findFirBatchInfoByUK(params);
			if(result.getResObject() == null) {
				return false;
			}
			FirBatchInfo firBatchInfo = (FirBatchInfo) result.getResObject();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String subject = firBatchInfo.getMailSubject() + "-" + sdf.format(new Date());
			String mailTo = firBatchInfo.getMailTo();
			String mailCc = firBatchInfo.getMailCc();
			StringBuilder sb = new StringBuilder();
			sb.append("<p>批次號碼：" + batchNo + "</p>");
			sb.append("<p>轉檔時間：" + sdf.format(excuteTime) + "</p>");
			if(status.equals("S")) {
				sb.append("<p>執行狀態：完成</p>");
				sb.append("<p>核心預計筆數：" + firAgtBatchMain.getFileQty() + "</p>");
				sb.append("<p>核心處理筆數：" + firAgtBatchMain.getFilePqty() + "</p>");
				sb.append("<p>AS400筆數：" + firAgtBatchMain.getAs400Qty() + "</p>");
				params.clear();
				params.put("batchNo", batchNo);
				
				result = firAgtBatchDtlService.findFirAgtBatchDtlByParams(params);
				if(result.getResObject()!=null) {
					List<FirAgtBatchDtl> firAgtBatchDtlList = (List<FirAgtBatchDtl>) result.getResObject();
					sb.append("<table border=1 style='border-collapse: collapse;'>");
					sb.append("<tr bgcolor='#70bbd9'>");
					sb.append("<td>受理編號</td>");
					sb.append("<td>狀態</td>");
					sb.append("<td>對應核心單號</td>");
					sb.append("<td>險別</td>");
					sb.append("<td>資料來源</td>");
					sb.append("</tr>");
					for(FirAgtBatchDtl firAgtBatchDtl:firAgtBatchDtlList) {
						sb.append("<tr>");
						sb.append("<td>" + StringUtil.nullToSpace(firAgtBatchDtl.getOrderseq()) + "</td>");
						String orderseqStatus = "";
						if(firAgtBatchDtl.getOrderseqStatus().equals("00")) {
							orderseqStatus = "未處理";
						}else if(firAgtBatchDtl.getOrderseqStatus().equals("01")) {
							orderseqStatus = "資料產生成功";
						}else {
							orderseqStatus = "未定義";
						}
						sb.append("<td>" + orderseqStatus + "</td>");
						sb.append("<td>" + StringUtil.nullToSpace(firAgtBatchDtl.getCoreNo()) + "</td>");
						sb.append("<td>" + StringUtil.nullToSpace(firAgtBatchDtl.getRiskcode()) + "</td>");
						sb.append("<td>" + StringUtil.nullToSpace(firAgtBatchDtl.getDataSource().equals("1")?"新核心":"AS400") + "</td>");
						sb.append("</tr>");
					}
					sb.append("</table>");
				}else {
					sb.append("<p> FIR_AGT_BATCH_DTL批次明細檔查無資料，請洽系統人員。</p>");
				}
				
			}else if (status.equals("N")) {
				sb.append("<p>執行狀態：無資料 </p>");
			}else {//status = "F"
				sb.append("<p>執行狀態：" +status+ "</p>");
				sb.append("<p>異常訊息：" +errMsg+ "</p>");
			}
			mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, 
					"newims@ctbcins.com", "",mailTo, "", mailCc, "", "", "", sb.toString(),
					"smtp","newims", "2012newims");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public Result generateFile(Map<String, String> params, String userId) throws SystemException, Exception {
		FileOutputStream fileOutputStream = null;
		File file = null;
		File filetest = null;
		String fileTitle = "";
		String fileContent = "";
		String source = "";
		try {
			if(params.get("batchNo").contains("BOP01")) {
				fileTitle = "PNFSETL";
				fileContent = genBop01File(params);
				source = "板信要保受理檔";
			}else if(params.get("batchNo").contains("BOP02")) {
				fileTitle = "PNFPOLY";
				fileContent = genBop02File(params);
				source = "板信保單檔";
			}
			String fileName = getFilename(params, fileTitle);
			File filePath = new File(configUtil.getString("tempFolder"));
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			
			String outputFile = filePath + File.separator + fileName;
			BufferedWriter bufWriter = null;
			file = new File(outputFile);
			filetest = File.createTempFile("tmp",null);
			fileOutputStream = new FileOutputStream(filetest, false);
			bufWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "UTF-8"));
			bufWriter.write(fileContent);
			bufWriter.close();
			FtsUtil ftsutil = new FtsUtil(configUtil.getString("ftsUrl"));
			FileUploadResponseVo fileUploadResponseVo = ftsutil.uploadFile(filetest.getAbsolutePath(), source, "F", params.get("batchNo")+fileName);
			if(fileUploadResponseVo.getStatus().equals("N")) {
				return this.getReturnResult("檔案產生失敗!"+fileUploadResponseVo.getMessage());
			}
			processPanhsinFileService.insertBatchGenFileAndUpdateMain(params.get("batchNo"), userId, fileName);
			
		} catch (IOException e) {
			e.printStackTrace();
			return this.getReturnResult("作業失敗："+e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return this.getReturnResult("作業失敗："+e.toString());
		}finally{
            try {
                if(fileOutputStream != null){
                	fileOutputStream.close();
                }
                file.delete();
                filetest.deleteOnExit();
            } catch (IOException e) {
                e.printStackTrace();
            }
		}
		return this.getReturnResult("檔案產生完成");
	}

	@SuppressWarnings("unchecked")
	private String getFilename(Map<String, String> params, String fileTitle) throws Exception {
		String fileName = fileTitle + new SimpleDateFormat("yyyyMMddHH").format(new Date())+"001.118";
		params.put("sortBy", "OID");
		params.put("sortType", "ASC");
		Result result = firAgtBatchGenfileService.findFirAgtBatchGenfileByParams(params);
		if(result.getResObject() !=null ) {
			List<FirAgtBatchGenfile> fileList = (List<FirAgtBatchGenfile>) result.getResObject();
			fileList.get(0);
			for(FirAgtBatchGenfile firAgtBatchGenfile : fileList) {
				String preFileName = firAgtBatchGenfile.getFileName();
				if((preFileName.substring(7,17)).equals(fileName.substring(7,17))) {
					fileName = fileTitle+new SimpleDateFormat("yyyyMMddHH").format(new Date())
							+String.format("%03d",(Integer.valueOf(preFileName.substring(18,20))+1))+".118";						
				}
			}
		}
		return fileName;
	}
	
	@SuppressWarnings("unchecked")
	public String genBop01File(Map<String,String> params) throws Exception {
		Result result = firAgtBop01Service.findFirAgtBop01ByParams(params);
		StringBuilder sb = new StringBuilder();
		if(result.getResObject() != null) {
			List<FirAgtBop01> firAgtBop01List = (List<FirAgtBop01>) result.getResObject();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			for(FirAgtBop01 firAgtBop01 : firAgtBop01List) {
				sb.append("118|");//1.保險公司代號
				sb.append(StringUtil.nullToSpace(firAgtBop01.getOrderseq())).append("||");//2.受理編號、3.保單號不處理(空白)
				sb.append(StringUtil.nullToSpace(firAgtBop01.getRiskcodeBop())).append("|");//4.險種代碼
				sb.append(StringUtil.nullToSpace(firAgtBop01.getKindcodeBop())).append("|");//5.細險種代碼
				sb.append(sdf.format(new Date())).append("|");//6.資料日--系統時間
				sb.append(firAgtBop01.getApplyDate()==null?"":sdf.format(firAgtBop01.getApplyDate())).append("|");//7.要保書申請日
				sb.append(firAgtBop01.getStartdate()==null?"":sdf.format(firAgtBop01.getStartdate())).append("|");//8.生效日
				sb.append(firAgtBop01.getEnddate()==null?"":sdf.format(firAgtBop01.getEnddate())).append("|");//9.到期日
				sb.append(StringUtil.nullToSpace(firAgtBop01.getOrderType())).append("|");//10.受理狀況
				sb.append(StringUtil.nullToSpace(firAgtBop01.getPolicyStatus())).append("||");//11.保單狀況、12.批改內容(不處理)
				sb.append(StringUtil.nullToSpace(firAgtBop01.getIsRenew())).append("|");//13.是否續保件
				sb.append(StringUtil.nullToSpace(firAgtBop01.getApplyName())).append("|");//14.要保人姓名
				sb.append(StringUtil.nullToSpace(firAgtBop01.getApplyId())).append("|");//15.要保人ID
				sb.append(StringUtil.nullToSpace(firAgtBop01.getApplyPostcode())).append("|");//16.要保人郵遞區號
				sb.append(StringUtil.nullToSpace(firAgtBop01.getApplyAddr())).append("|");//17.要保人地址
				sb.append(StringUtil.nullToSpace(firAgtBop01.getInsuredName())).append("|");//18.被保險人姓名
				sb.append(StringUtil.nullToSpace(firAgtBop01.getInsuredId())).append("|");//19.被保險人ID
				sb.append(firAgtBop01.getInsuredBirthday()==null?"":sdf.format(firAgtBop01.getInsuredBirthday())).append("|");//20.被保險人生日
				sb.append(StringUtil.nullToSpace(firAgtBop01.getInsuredAge())).append("|");//21.被保險人投保年齡
				sb.append(StringUtil.nullToSpace(firAgtBop01.getPayType())).append("|");//22.繳別
				sb.append(StringUtil.nullToSpace(firAgtBop01.getPremium().toString())).append("|");//23.保費
				sb.append(StringUtil.nullToSpace(firAgtBop01.getAmount().toString())).append("|");//24.保額
				sb.append(StringUtil.nullToSpace(firAgtBop01.getInquiryType())).append("|");//25.保經或分行詢價
				sb.append(StringUtil.nullToSpace(firAgtBop01.getCommRate().toString())).append("|");//26.佣金率
				sb.append(StringUtil.nullToSpace(firAgtBop01.getSalesSource())).append("|");//27.業績歸屬通路
				sb.append(StringUtil.nullToSpace(firAgtBop01.getSalesBranch())).append("|");//28.業績歸屬分行代號
				sb.append(StringUtil.nullToSpace(firAgtBop01.getIsAutoRenew())).append("|");//29.是否自動續保
				sb.append(StringUtil.nullToSpace(firAgtBop01.getAmountCheck())).append("|");//30.不足額/超額
				sb.append(StringUtil.nullToSpace(firAgtBop01.getLicensePlate())).append("|");//31.汽機車-車號(引擎號碼)
				sb.append(StringUtil.nullToSpace(firAgtBop01.getOldPolicyno())).append("|");//32.前一保單號碼
				sb.append(firAgtBop01.getOldEnddate()==null?"":sdf.format(firAgtBop01.getOldEnddate())).append("|");//33.前一保單到期日
				sb.append(StringUtil.nullToSpace(firAgtBop01.getProjNo())).append("|");//34.專案代碼
				sb.append(StringUtil.nullToSpace(firAgtBop01.getProjName())).append("|");//35.專案名稱
				sb.append(StringUtil.nullToSpace(firAgtBop01.getSalesId())).append("|");//36.銷售行員ID
				sb.append(StringUtil.nullToSpace(firAgtBop01.getSalesName())).append("|");//37.銷售行員姓名
				sb.append(StringUtil.nullToSpace(firAgtBop01.getSalesNo())).append("|");//38.銷售行員登錄字號
				sb.append(StringUtil.nullToSpace(firAgtBop01.getCarBrand())).append("|");//39.汽機車-品牌
				sb.append(StringUtil.nullToSpace(firAgtBop01.getCarType())).append("|");//40.汽機車-車種
				sb.append(StringUtil.nullToSpace(firAgtBop01.getCarYear())).append("|");//41.汽機車-年份
				sb.append(StringUtil.nullToSpace(firAgtBop01.getCarCc())).append("|");//42.汽機車-排氣量
				sb.append(StringUtil.nullToSpace(firAgtBop01.getSalesType())).append("|");//43.業務屬性 
				sb.append(StringUtil.nullToSpace(firAgtBop01.getPayWay())).append("|").append("\r\n");//44.繳費方式
			}
		}
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String genBop02File(Map<String, String> params) throws SystemException, Exception {
		Result result = firAgtBop02Service.findFirAgtBop02ByParams(params);
		StringBuilder sb = new StringBuilder();
		if(result.getResObject() != null) {
			List<FirAgtBop02> firAgtBop02List = (List<FirAgtBop02>) result.getResObject();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			//BigDecimal 若為空值該如何處理?
			for(FirAgtBop02 firAgtBop02 : firAgtBop02List) {
				sb.append("118|");//1.保險公司代號
				sb.append(StringUtil.nullToSpace(firAgtBop02.getPolicyno())).append("|");//2.保單號
				sb.append(StringUtil.nullToSpace(firAgtBop02.getOrderseq())).append("|");//3.受理編號
				sb.append(StringUtil.nullToSpace(firAgtBop02.getEndorseno())).append("|");//4.批單號碼
				sb.append(StringUtil.nullToSpace(firAgtBop02.getRiskcodeBop())).append("|");//5.險種代碼
				sb.append(StringUtil.nullToSpace(firAgtBop02.getKindcodeBop())).append("|");//6.細險種代碼
				sb.append(sdf.format(new Date())).append("|");//7.資料日--系統時間
				sb.append(firAgtBop02.getStartdate()==null?"":sdf.format(firAgtBop02.getStartdate())).append("|");//8.生效日
				sb.append(firAgtBop02.getEnddate()==null?"":sdf.format(firAgtBop02.getEnddate())).append("|");//9.到期日
				sb.append(firAgtBop02.getUnderwriteenddate()==null?"":sdf.format(firAgtBop02.getUnderwriteenddate())).append("|");//10.簽單日
				sb.append(StringUtil.nullToSpace(firAgtBop02.getOrderType())).append("|");//11.受理狀況
				sb.append(StringUtil.nullToSpace(firAgtBop02.getPolicyStatus())).append("||");//12.保單狀況、13.批改內容(不處理)
				sb.append(StringUtil.nullToSpace(firAgtBop02.getIsRenew())).append("|");//14.是否續保件
				sb.append(StringUtil.nullToSpace(firAgtBop02.getApplyName())).append("|");//15.要保人姓名
				sb.append(StringUtil.nullToSpace(firAgtBop02.getApplyId())).append("|");//16.要保人ID
				sb.append(StringUtil.nullToSpace(firAgtBop02.getApplyPostcode())).append("|");//17.要保人郵遞區號
				sb.append(StringUtil.nullToSpace(firAgtBop02.getApplyAddr())).append("|");//18.要保人地址
				sb.append(StringUtil.nullToSpace(firAgtBop02.getInsuredName())).append("|");//19.被保險人姓名
				sb.append(StringUtil.nullToSpace(firAgtBop02.getInsuredId())).append("|");//20.被保險人ID
				sb.append(firAgtBop02.getInsuredBirthday()==null?"":sdf.format(firAgtBop02.getInsuredBirthday())).append("|");//21.被保險人生日
				sb.append(StringUtil.nullToSpace(firAgtBop02.getInsuredAge())).append("|");//22.被保險人投保年齡
				sb.append(StringUtil.nullToSpace(firAgtBop02.getPayType())).append("|");//23.繳別
				sb.append(StringUtil.nullToSpace(firAgtBop02.getPremium().toString())).append("|");//24.保費
				sb.append(StringUtil.nullToSpace(firAgtBop02.getAmount().toString())).append("|");//25.保額
				sb.append(firAgtBop02.getCommRate()==null?"":firAgtBop02.getCommRate()).append("|");//26.佣金率
				sb.append(firAgtBop02.getCommission()==null?"":firAgtBop02.getCommission()).append("|");//27.佣金
				sb.append(firAgtBop02.getServiceCharge()==null?"":firAgtBop02.getServiceCharge()).append("|");//28.服務費
				sb.append(StringUtil.nullToSpace(firAgtBop02.getSalesSource())).append("|");//29.業績歸屬通路
				sb.append(StringUtil.nullToSpace(firAgtBop02.getSalesBranch())).append("|");//30.業績歸屬分行代號
				sb.append(StringUtil.nullToSpace(firAgtBop02.getSalesId())).append("|");//31.銷售行員代號
				sb.append(StringUtil.nullToSpace(firAgtBop02.getSalesNo())).append("|");//32.銷售行員登錄字號
				sb.append(StringUtil.nullToSpace(firAgtBop02.getIsAutoRenew())).append("|");//33.是否自動續保
				sb.append(StringUtil.nullToSpace(firAgtBop02.getAmountCheck())).append("|");//34.不足額/超額
				sb.append(StringUtil.nullToSpace(firAgtBop02.getLicensePlate())).append("|");//35.汽機車-車號(引擎號碼)
				sb.append(StringUtil.nullToSpace(firAgtBop02.getOldPolicyno())).append("|");//36.前一保單號碼
				sb.append(firAgtBop02.getOldEnddate()==null?"":sdf.format(firAgtBop02.getOldEnddate())).append("|");//37.前一保單到期日
				sb.append(StringUtil.nullToSpace(firAgtBop02.getProjNo())).append("|");//38.專案代碼
				sb.append(StringUtil.nullToSpace(firAgtBop02.getProjName())).append("|");//39.專案名稱
				sb.append(StringUtil.nullToSpace(firAgtBop02.getAcceptWay())).append("|");//40.客戶受理途徑
				sb.append(firAgtBop02.getStarttime()==null?"":sdf.format(firAgtBop02.getStarttime())).append("|");//41.生效時間
				sb.append(firAgtBop02.getEndtime()==null?"":sdf.format(firAgtBop02.getEndtime())).append("|");//42.到期時間
				sb.append(StringUtil.nullToSpace(firAgtBop02.getAddress())).append("|");//43.標的物地址/營業處所
				sb.append(StringUtil.nullToSpace(firAgtBop02.getPhonenumber())).append("|");//44.聯絡電話
				sb.append(StringUtil.nullToSpace(firAgtBop02.getDeleteFlag())).append("|").append("\r\n");//45.刪除註記
			}
		}
		return sb.toString();
	}

	private Result getReturnResult(String msg){
		Result result = new Result();
		Message message = new Message();
		message.setMessageString(msg);
		result.setMessage(message);
		return result;
	}

	public FirBatchInfoService getFirBatchInfoService() {
		return firBatchInfoService;
	}

	public void setFirBatchInfoService(FirBatchInfoService firBatchInfoService) {
		this.firBatchInfoService = firBatchInfoService;
	}

	public FirSpService getFirSpService() {
		return firSpService;
	}

	public void setFirSpService(FirSpService firSpService) {
		this.firSpService = firSpService;
	}

	public FirAgtBatchMainService getFirAgtBatchMainService() {
		return firAgtBatchMainService;
	}

	public void setFirAgtBatchMainService(FirAgtBatchMainService firAgtBatchMainService) {
		this.firAgtBatchMainService = firAgtBatchMainService;
	}

	public FirAgtBatchDtlService getFirAgtBatchDtlService() {
		return firAgtBatchDtlService;
	}

	public void setFirAgtBatchDtlService(FirAgtBatchDtlService firAgtBatchDtlService) {
		this.firAgtBatchDtlService = firAgtBatchDtlService;
	}

	public FirAgtBop01Service getFirAgtBop01Service() {
		return firAgtBop01Service;
	}

	public void setFirAgtBop01Service(FirAgtBop01Service firAgtBop01Service) {
		this.firAgtBop01Service = firAgtBop01Service;
	}

	public FirAgtBop02Service getFirAgtBop02Service() {
		return firAgtBop02Service;
	}
	
	public void setFirAgtBop02Service(FirAgtBop02Service firAgtBop02Service) {
		this.firAgtBop02Service = firAgtBop02Service;
	}

	public FirAgtBatchGenfileService getFirAgtBatchGenfileService() {
		return firAgtBatchGenfileService;
	}

	public void setFirAgtBatchGenfileService(FirAgtBatchGenfileService firAgtBatchGenfileService) {
		this.firAgtBatchGenfileService = firAgtBatchGenfileService;
	}
	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public ProcessPanhsinFileService getProcessPanhsinFileService() {
		return processPanhsinFileService;
	}

	public void setProcessPanhsinFileService(ProcessPanhsinFileService processPanhsinFileService) {
		this.processPanhsinFileService = processPanhsinFileService;
	}
}
