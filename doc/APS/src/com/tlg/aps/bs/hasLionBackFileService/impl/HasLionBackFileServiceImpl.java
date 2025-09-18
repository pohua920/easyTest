package com.tlg.aps.bs.hasLionBackFileService.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.hasLionBackFileService.HasLionBackFileService;
import com.tlg.aps.bs.hasLionBackFileService.LionBackFileService;
import com.tlg.aps.vo.FileUploadResponseVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.HasAgtBatchDtl;
import com.tlg.prpins.entity.HasAgtBatchMain;
import com.tlg.prpins.entity.HasAgtLionAC;
import com.tlg.prpins.entity.HasAgtLionCH;
import com.tlg.prpins.entity.HasAgtLionCL;
import com.tlg.prpins.entity.HasAgtLionCM;
import com.tlg.prpins.entity.HasAgtLionOP;
import com.tlg.prpins.entity.HasBatchInfo;
import com.tlg.prpins.entity.HasBatchLog;
import com.tlg.prpins.service.HasAgtBatchDtlService;
import com.tlg.prpins.service.HasAgtBatchMainService;
import com.tlg.prpins.service.HasAgtLionACService;
import com.tlg.prpins.service.HasAgtLionCHService;
import com.tlg.prpins.service.HasAgtLionCLService;
import com.tlg.prpins.service.HasAgtLionCMService;
import com.tlg.prpins.service.HasAgtLionOPService;
import com.tlg.prpins.service.HasBatchInfoService;
import com.tlg.prpins.service.HasSpService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.DateUtils;
import com.tlg.util.FtsUtil;
import com.tlg.util.Mailer;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.SftpUtil;
import com.tlg.util.StringUtil;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class HasLionBackFileServiceImpl  implements HasLionBackFileService{
	
	private static final Logger logger = Logger.getLogger(HasLionBackFileServiceImpl.class);
	private ConfigUtil configUtil;
	private HasSpService hasSpService;
	private HasBatchInfoService hasBatchInfoService;
	private LionBackFileService lionBackFileService;
	private HasAgtBatchMainService hasAgtBatchMainService;
	private HasAgtBatchDtlService hasAgtBatchDtlService;
	private HasAgtLionOPService hasAgtLionOPService;
	private HasAgtLionACService hasAgtLionACService;
	private HasAgtLionCLService hasAgtLionCLService;
	private HasAgtLionCMService hasAgtLionCMService;
	private HasAgtLionCHService hasAgtLionCHService;
	
	
	
	@Override
	public Result runToGenerateFile(String type, Date excuteTime, String userId, String programId,Date dataDate)
			throws SystemException, Exception {
		StringBuilder sb = new StringBuilder();
		String tmpStatus = "";
		String tmpMsg = "";
		String tmpBatchNo = "";
		String mailMsg = "";
		String fileQty = "0";
		String filePqty = "0";
		HashSet<String> typeSet= new HashSet<>();
		typeSet.add("1");
		typeSet.add("2");
		typeSet.add("3");
		typeSet.add("4");
		typeSet.add("5");
		StringBuilder sb2=new StringBuilder();
		String tmpBatchTime=new SimpleDateFormat("yyMMddHHmmss").format(excuteTime);
		
		// 檢核傳入參數
		if (StringUtil.isSpace(type)) {
			sb.append("回饋檔類型無內容值。");
		}
		if (StringUtil.isSpace(userId)) {
			sb.append("執行人員無內容值。");
		}
		if (StringUtil.isSpace(programId)) {
			sb.append("程式代碼無內容值。");
		}
		
		// 判斷排程是否可以執行
		if (StringUtil.isSpace(sb.toString()) && (typeSet.contains(type)) ) {
			sb2.append("LION0");
			sb2.append(type);
			sb2.append("_"+tmpBatchTime);
			tmpBatchNo=sb2.toString();
			Map<String, String> params = new HashMap<>();
			params.put("prgId", "HAS_AGT_LION_STATUS");
			Result result = hasBatchInfoService.findHasBatchInfoByUK(params);
			if (result.getResObject() != null && "N".equals(((HasBatchInfo) result.getResObject()).getMailTo())) {
				tmpStatus = "S";
				tmpMsg = "HAS_BATCH_INFO設定檔設定為排程暫停執行。";
			} else {
				tmpStatus = "1";
			}
		} else {
			tmpBatchNo = "LIONER_"+ tmpBatchTime;
			tmpStatus = "F";
			tmpMsg = "接收參數." + sb.toString();
		}
		
		logger.info("tmpBatchNo =" + tmpBatchNo);
		logger.info("tmpStatus =" + tmpStatus);
		logger.info("tmpMsg =" + tmpMsg);
		
		// 新增Has_BATCH_LOG批次程式執行記錄檔
		
		Result result = lionBackFileService.insertHasBatchLog(excuteTime, userId, programId, tmpStatus, tmpMsg, tmpBatchNo);			
		
		HasBatchLog hasBatchLog = null;
		if(result.getResObject()!=null) {
			hasBatchLog  = (HasBatchLog ) result.getResObject();
		}
		
		if("1".equals(tmpStatus)) {
			Map<String,String> returnData = this.callSp(tmpBatchNo, userId, programId, type,dataDate);
			tmpStatus = returnData.get("status");
			tmpMsg = returnData.get("msg");
			fileQty = returnData.get("fileQty");
			filePqty = returnData.get("filePqty");
			lionBackFileService.updateHasBatchLog(tmpStatus, tmpMsg, userId, hasBatchLog);
			File zipFile = new File(returnData.get("outputFile")+".zip");
			if("S".equals(tmpStatus)){
				if(zipFile.exists()){
					String[] zipFilePath = { zipFile.getPath() };
					String[] zipFileName = { zipFile.getName() };
					mailMsg = sendEmail(tmpBatchNo, type, fileQty, filePqty, excuteTime, tmpStatus, tmpMsg, programId,zipFilePath,zipFileName);
					this.deleteFile(returnData.get("outputFile"), returnData.get("outputFile")+".zip");
				}
			}else{
				//mantis：HAS0272，處理人員：DP0706，處理人員：DP0706，TA雄獅回饋檔新增欄位START
				if(zipFile.exists()){
					String[] zipFilePath = { zipFile.getPath() };
					String[] zipFileName = { zipFile.getName() };
					mailMsg = sendEmail(tmpBatchNo, type, fileQty, filePqty, excuteTime, tmpStatus, tmpMsg, programId,zipFilePath,zipFileName);
					this.deleteFile(returnData.get("outputFile"), returnData.get("outputFile")+".zip");
				} else {
					mailMsg = sendEmail(tmpBatchNo, type, fileQty, filePqty, excuteTime, tmpStatus, tmpMsg, programId);
				}
				//mantis：HAS0272，處理人員：DP0706，處理人員：DP0706，TA雄獅回饋檔新增欄位END
			}
		} else if("F".equals(tmpStatus)) {
			mailMsg = sendEmail(tmpBatchNo, type, fileQty, filePqty, excuteTime, tmpStatus, tmpMsg, programId);
		}
		
		logger.info("mailMsg =" + mailMsg);
		if (!StringUtil.isSpace(mailMsg)) {
			lionBackFileService.updateHasBatchLog("F", mailMsg, userId, hasBatchLog);
		}
		
		return this.getReturnResult(StringUtil.isSpace(mailMsg)?"執行完成":mailMsg);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,String> generateFile(String batchNo, String userId, String programId, String type) throws SystemException, Exception {
		StringBuilder sb = new StringBuilder();
		Map<String,Object> params = new HashMap<>();
		Map<String,String> returnData = new HashMap<>();
		
		// 檢核傳入參數
		if (StringUtil.isSpace(batchNo)) {
			sb.append("批次號碼無內容值。");
		}
		if (StringUtil.isSpace(userId)) {
			sb.append("執行人員無內容值。");
		}
		if (StringUtil.isSpace(programId)) {
			sb.append("程式代碼無內容值。");
		}
		if (StringUtil.isSpace(type)) {
			sb.append("執行類型無內容值。");
		}
		if (!StringUtil.isSpace(sb.toString())){
			returnData.put("status", "F");
			returnData.put("msg", "傳入參數異常:"+sb.toString());
			return returnData;
		}
		
		params.put("batchNo", batchNo);
		params.put("businessnature", "J00127");
		Result result = hasAgtBatchMainService.findHasAgtBatchMainByParams(params);
		List<HasAgtBatchMain> list = new ArrayList<HasAgtBatchMain>();
		HasAgtBatchMain hasAgtBatchMain = null;
		if(result.getResObject() != null) {
			list = (List<HasAgtBatchMain>)result.getResObject();
			if(list.size() > 0) {
				hasAgtBatchMain = list.get(0);
				if(hasAgtBatchMain.getFilePqty()==null || hasAgtBatchMain.getFilePqty() == 0) {
					returnData.put("status", "N");
					returnData.put("msg", "無資料");
					this.updateHasAgtBatch(hasAgtBatchMain, "Z", null, null, userId);
					return returnData;
				}
			}
		}else {
			returnData.put("status", "F");
			returnData.put("msg", batchNo+ "批號查無對應資料。");
			return returnData;
		}
		
		//若接收參數.執行類型 = '2'重新執行，需將前次的執行結果寫入REMARK欄位，避免執行完成後資料覆蓋。
		if("2".equals(type)) {
			String remark=StringUtil.nullToSpace(hasAgtBatchMain.getRemark()) + ";" +
					"上次執行資訊：" + hasAgtBatchMain.getIupdate() + "-" + 
					DateUtils.format(hasAgtBatchMain.getDupdate(), "yyyy/MM/dd HH:mm:ss") + "-檔名：" + hasAgtBatchMain.getFileName();
		lionBackFileService.updateHasAgtBatchMain(hasAgtBatchMain, remark, userId);
		}
		
		String tmpBno = batchNo.substring(0, 6);
		String fileContent = "";
		String fileName = "";
		String source = "";
		File file = new File("");
		String sftpFlag="Y";
//		HashSet tmpBnoSet =new HashSet();
		
		try {
			//產生txt資料
			fileName = this.getFileName(batchNo);
			switch(tmpBno){
				case "LION01":
					fileContent = genLionOPDate(batchNo);
					source = "雄獅受理檔";
					break;
				case "LION02":
					fileContent = genLionCHDate(batchNo);
					source = "雄獅保批檔";
					break;
				case "LION03":
					fileContent = genLionACDate(batchNo);
					source = "雄獅銷帳檔";
					break;
				case "LION04":
					fileContent = genLionCMDate(batchNo);
					source = "雄獅佣金檔";
					sftpFlag="N";//佣金檔不上傳給雄獅
					break;
				case "LION05":
					fileContent = genLionCLDate(batchNo);
					source = "雄獅理賠檔";
					break;
				default:
					returnData.put("status", "F");
					returnData.put("msg", "批次號碼錯誤。");
					this.updateHasAgtBatch(hasAgtBatchMain, "E", null, "批次號碼錯誤。", userId);
					return returnData;
					
			
			}
			//產生txt
			File filePath = new File(configUtil.getString("tempFolder"));
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			String outputFile = filePath + File.separator + fileName;
			file = new File(outputFile);
			String msg = this.genFile(fileContent, file);
			if(!StringUtil.isSpace(msg)) {
				throw new Exception(msg);
			}
			
			//壓縮檔案
			String pswd = "";
			params.clear();
//			params.put("prgId", "FIR_AGT_BOP_FB_PSWD");
//			result = hasBatchInfoService.findHasBatchInfoByUK(params);
//			if(result.getResObject() != null) {
//				pswd = ((HasBatchInfo)result.getResObject()).getMailTo();
//			}
			returnData.put("outputFile", outputFile);
			this.writeZip(file.getPath(), file, pswd);
		} catch (Exception e) {
			logger.info(e.toString());
			//mantis：HAS0272，處理人員：DP0706，處理人員：DP0706，TA雄獅回饋檔新增欄位
//			this.deleteFile(file.getPath(), file.getPath() + ".zip");
			returnData.put("status", "F");
			returnData.put("msg", "txt產生失敗:" + e.getMessage());
			this.updateHasAgtBatch(hasAgtBatchMain, "E", null, "txt產生失敗:" + e.getMessage(), userId);
			return returnData;
		}
		//----------------------
		//上傳檔案至FTS
		try {
			FtsUtil ftsutil = new FtsUtil(configUtil.getString("ftsUrl"));
			FileUploadResponseVo fileUploadResponseVo = ftsutil.uploadFile(file.getPath() + ".zip", source, "TA", batchNo);
			if("N".equals(fileUploadResponseVo.getStatus())) {
				throw new Exception(fileUploadResponseVo.getMessage());
			}
		} catch (Exception e) {
			logger.info(e.toString());
			//mantis：HAS0272，處理人員：DP0706，處理人員：DP0706，TA雄獅回饋檔新增欄位
//			this.deleteFile(file.getPath(), file.getPath() + ".zip");
			returnData.put("status", "F");
			returnData.put("msg", "上傳FTS失敗:" + e.getMessage());
			this.updateHasAgtBatch(hasAgtBatchMain, "E", null, "上傳FTS失敗:" + e.getMessage(), userId);
			return returnData;
		}
		
		//上傳檔案至SFTP
		try {
			if("Y".equals(sftpFlag)){		
				if(!uploadZipFileToSftp(file.getPath() + ".zip")) {
					throw new Exception("傳送SFTP失敗;");
				}
			}
			
			returnData.put("status", "S");
			returnData.put("msg", "");
//			this.deleteFile(file.getPath(), file.getPath() + ".zip");
			this.updateHasAgtBatch(hasAgtBatchMain, "Y", fileName, null, userId);
			return returnData;
			
		} catch (Exception e) {
			logger.info(e.toString());
			//mantis：HAS0272，處理人員：DP0706，處理人員：DP0706，TA雄獅回饋檔新增欄位
//			this.deleteFile(file.getPath(), file.getPath() + ".zip");
			returnData.put("status", "F");
			returnData.put("msg", "傳送SFTP失敗;" + e.getMessage());
			this.updateHasAgtBatch(hasAgtBatchMain, "E", fileName, "傳送SFTP失敗:" + e.getMessage(), userId);
			return returnData;
		}
		
	}
	
	private Map<String,String> callSp(String batchNo, String userId, String programId, String type,Date dataDate) throws SystemException, Exception {
		Map<String,Object> params = new HashMap<>();
		Map<String,String> returnData = new HashMap<>();
		Calendar calendar;
		Date inDateS;
		Date inDateE;
		int returnValue = 0;
		params.put("inBatchNo", batchNo);
		params.put("inUser", userId);
		Date inDate=dataDate;
			
		params.put("inDate", inDate);
		params.put("outResult", null);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		switch(type){
			case "1":
				returnValue = hasSpService.runSpHasAgtLionOP(params);
				break;
			case "2":
				returnValue = hasSpService.runSpHasAgtLionCH(params);
				break;
			case "3":
				returnValue = hasSpService.runSpHasAgtLionAC(params);
				break;
			case "4":
				//拿到上一個月的第一天跟最後一天
				 calendar = Calendar.getInstance();
			    calendar.setTime(inDate);
			    calendar.add(Calendar.MONTH, -1);
			    calendar.set(Calendar.DAY_OF_MONTH, 1);
			     inDateS = calendar.getTime();
			    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			     inDateE = calendar.getTime();
					inDateS = sdf.parse(sdf.format(inDateS));
					inDateE = sdf.parse(sdf.format(inDateE));
			    params.put("inDateS", inDateS);
				params.put("inDateE", inDateE);
				returnValue = hasSpService.runSpHasAgtLionCM(params);
				break;
			case "5":
				 calendar = Calendar.getInstance();
			    calendar.setTime(inDate);
			    calendar.add(Calendar.MONTH, -1);
			    calendar.set(Calendar.DAY_OF_MONTH, 1);
			     inDateS = calendar.getTime();
			    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			     inDateE = calendar.getTime();
					inDateS = sdf.parse(sdf.format(inDateS));
					inDateE = sdf.parse(sdf.format(inDateE));
			    params.put("inDateS", inDateS);
				params.put("inDateE", inDateE);
				returnValue = hasSpService.runSpHasAgtLionCL(params);
				break;
		
		}
		
		if(returnValue == 0) {
			params.clear();
			params.put("batchNo", batchNo);
			Result result = hasAgtBatchMainService.findHasAgtBatchMainByUk(params);
			if(result.getResObject()!=null) {
				HasAgtBatchMain hasAgtBatchMain = (HasAgtBatchMain) result.getResObject();
				if(hasAgtBatchMain.getFileStatus().equals("Z")) {
					returnData.put("status", "N"); //檔案無資料
					returnData.put("msg", "檔案無資料");
				}else {
					returnData = this.generateFile(batchNo, userId, programId, "1");
					returnData.put("fileQty", hasAgtBatchMain.getFileQty().toString());
					returnData.put("filePqty", hasAgtBatchMain.getFilePqty().toString());
				}
			}
		} else {
			returnData.put("status", "F");
			switch(type){
			case "1":
				returnData.put("msg", "執行SP失敗(呼叫SP_HAS_AGT_LION_OP)");
				break;
			case "2":
				returnData.put("msg", "執行SP失敗(呼叫SP_HAS_AGT_LION_CH)");
				break;
			case "3":
				returnData.put("msg", "執行SP失敗(呼叫SP_HAS_AGT_LION_AC)");
				break;
			case "4":
				returnData.put("msg", "執行SP失敗(呼叫SP_HAS_AGT_LION_CM)");
				break;
			case "5":
				returnData.put("msg", "執行SP失敗(呼叫SP_HAS_AGT_LION_CL)");
				break;
		
			}
		}
		
		return returnData;
	}
	
	private String getFileName( String batchNo) throws Exception {
		String typeCode = "";
		String companyCode="218";
		String tmpBno = batchNo.substring(0, 6);
		Map<String,Object> params =new HashMap<>();
		int number = 1;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String today = sdf.format(new Date());
//		if(!StringUtil.isSpace(tmpMainFile) && tmpMainFile.substring(5,13).equals(today)) {
//			number = Integer.valueOf(tmpMainFile.substring(13, 16));
//			number++;
//		}
		switch(tmpBno){
			case"LION01":
				typeCode="OP";
				break;
			case"LION02":
				typeCode="CH";
				break;
			case"LION03":
				typeCode="AC";
				break;
			case"LION04":
				typeCode="CM";
				break;
			case"LION05":
				typeCode="CL";
				break;
		
		}
		String filename = companyCode + typeCode + today + String.format("%03d", number);
		params.put("fileName",filename );
		while(hasAgtBatchMainService.countByParams(params)>0){
			number++;
			filename = companyCode + typeCode + today + String.format("%03d", number);
			params.put("fileName",filename );
		};

		return filename;
	}
	
	@SuppressWarnings("unchecked")
	private String genLionOPDate(String batchNo) throws Exception {
		Map<String,String> params = new HashMap<String, String>();
		params.put("batchNo", batchNo);
		Result result = hasAgtLionOPService.selectForGenFile(params);
		StringBuilder sb = new StringBuilder();
		if(result.getResObject() != null) {
			List<HasAgtLionOP> list = (List<HasAgtLionOP>) result.getResObject();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			for(HasAgtLionOP m:list) {
//				sb.append(m.getStartdate()==null?"":sdf.format(m.getStartdate())).append("\t");
				sb.append(StringUtil.nullToSpace(m.getCompanyno())).append("\t");	            //保險公司代號:固定值：218
				sb.append(StringUtil.nullToSpace(m.getPectype())).append("\t");                  //新/續保件代碼:
				sb.append(StringUtil.nullToSpace(m.getRiskcode())).append("\t");                 //商品代碼:
				sb.append(StringUtil.nullToSpace(m.getAgentorderseq())).append("\t");            //受理編號:
				sb.append(StringUtil.nullToSpace(m.getPolicyno())).append("\t");                 //保單號碼:
				sb.append(StringUtil.nullToSpace(m.getEndorseno())).append("\t");                //批單號碼:
				sb.append(StringUtil.nullToSpace(m.getOperatedate())).append("\t");              //簽單日期:
				sb.append(StringUtil.nullToSpace(m.getStartdate())).append("\t");                //保單起日:
				sb.append(StringUtil.nullToSpace(m.getEnddate())).append("\t");                  //保單止日:
//				sb.append(StringUtil.nullToSpace(m.getBatchNo())).append("\t");                  //批次號碼: 
               	sb.append(StringUtil.nullToSpace(m.getAppliname())).append("\t");                //要保人姓名:
               	sb.append(StringUtil.nullToSpace(m.getApplicode())).append("\t");                //要保人id:
               	sb.append(StringUtil.nullToSpace(m.getInsuredname())).append("\t");              //被保人姓名:
               	sb.append(StringUtil.nullToSpace(m.getInsuredcode())).append("\t");              //被保人id:
               	sb.append(StringUtil.nullToSpace(m.getSubbusinessnature())).append("\t");        //分行代碼:
               	sb.append(StringUtil.nullToSpace(m.getHandler1code())).append("\t");             //保險業務員代碼:
               	sb.append(m.getSumpremium()==null?"":m.getSumpremium()).append("\t");            //保險費:
               	sb.append(m.getSumdiscount()==null?"":m.getSumdiscount()).append("\t");          //折扣金額:
               	sb.append(m.getFycfee()==null?"":m.getFycfee()).append("\t");                    //佣金:
               	sb.append(m.getServicefee()==null?"":m.getServicefee()).append("\t");            //服務費:
               	sb.append(StringUtil.nullToSpace(m.getRemark())).append("\t");                   //註記:
               	sb.append(StringUtil.nullToSpace(m.getIntroducerid())).append("\t");             //推薦人id:
               	sb.append(StringUtil.nullToSpace(m.getPaymentNo())).append("\t");                //銷帳編號:
               	sb.append(m.getPaymentTotal()==null?"":m.getPaymentTotal()).append("\t");        //繳費金額:
               	sb.append(StringUtil.nullToSpace(m.getSeriescode())).append("\t");               //專案代號:
               	sb.append(StringUtil.nullToSpace(m.getLicenseno())).append("\t");                //車號:
               	sb.append(StringUtil.nullToSpace(m.getAccount1())).append("\t");                 //放款帳號:
               	sb.append(StringUtil.nullToSpace(m.getDebitaccount())).append("\t");             //扣款帳號:
               	sb.append(StringUtil.nullToSpace(m.getSitename())).append("\t");                 //船班:
               	sb.append(StringUtil.nullToSpace(m.getStartsite())).append("\t");                //出發埠:
               	sb.append(StringUtil.nullToSpace(m.getEndsite())).append("\t");                  //目地埠:
               	sb.append(StringUtil.nullToSpace(m.getInvoiceno())).append("\t");                //發票號碼:
               	sb.append(m.getNegotiateRate()==null?"":m.getNegotiateRate()).append("\t");      //議價佣率:
               	sb.append(StringUtil.nullToSpace(m.getNegotiateReason())).append("\t");          //議價原因:
               	sb.append(StringUtil.nullToSpace(m.getOriginalEnddate())).append("\t");          //原保單到期月份:
               	sb.append(StringUtil.nullToSpace(m.getRenewflag())).append("\t");                //約定續保:
               	sb.append(StringUtil.nullToSpace(m.getOldPolicyno())).append("\t");              //前保單號碼:
               	//mantis：HAS0272，處理人員：DP0706，處理人員：DP0706，TA雄獅回饋檔新增欄位START
               	sb.append(m.getAmount()==null?"":m.getAmount()).append("\t");                    //保險金額:
            	sb.append(StringUtil.nullToSpace(m.getApplipostaddress())).append("\t");         //要保人通訊地址:
            	sb.append(StringUtil.nullToSpace(m.getInsuredpostaddress())).append("\t");		 //被保人通訊地址:
            	sb.append(StringUtil.nullToSpace(m.getTotalinsuredno()));                        //總承保人數:
            	//mantis：HAS0272，處理人員：DP0706，處理人員：DP0706，TA雄獅回饋檔新增欄位END
				sb.append("\r\n");
			}
		}
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	private String genLionACDate(String batchNo) throws Exception {
		Map<String,String> params = new HashMap<String, String>();
		params.put("batchNo", batchNo);
		Result result = hasAgtLionACService.selectForGenFile(params);
		StringBuilder sb = new StringBuilder();
		if(result.getResObject() != null) {
			List<HasAgtLionAC> list = (List<HasAgtLionAC>) result.getResObject();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			for(HasAgtLionAC m:list) {
				sb.append(StringUtil.nullToSpace(m.getCompanyno())).append("\t");	            //保險公司代號:
				sb.append(StringUtil.nullToSpace(m.getPectype())).append("\t");                  //新/續保件代碼:
//				sb.append(StringUtil.nullToSpace(m.getBatchNo())).append("\t");                  //批次號碼: 
				sb.append(StringUtil.nullToSpace(m.getRiskcode())).append("\t");                 //商品代碼:
				sb.append(StringUtil.nullToSpace(m.getAgentorderseq())).append("\t");            //受理編號:
				sb.append(StringUtil.nullToSpace(m.getPolicyno())).append("\t");                 //保單號碼:
               	sb.append(StringUtil.nullToSpace(m.getEndorseno())).append("\t");                //批單號碼:
               	sb.append(StringUtil.nullToSpace(m.getOperatedate())).append("\t");              //簽單日期:
               	sb.append(StringUtil.nullToSpace(m.getStartdate())).append("\t");                //保單起日:
               	sb.append(StringUtil.nullToSpace(m.getEnddate())).append("\t");                  //保單止日:
               	sb.append(StringUtil.nullToSpace(m.getAppliname())).append("\t");                //要保人姓名:
               	sb.append(StringUtil.nullToSpace(m.getApplicode())).append("\t");                //要保人id:
               	sb.append(StringUtil.nullToSpace(m.getInsuredname())).append("\t");              //被保人姓名:
               	sb.append(StringUtil.nullToSpace(m.getInsuredcode())).append("\t");              //被保人id:
               	sb.append(StringUtil.nullToSpace(m.getSubbusinessnature())).append("\t");        //分行代碼:
               	sb.append(StringUtil.nullToSpace(m.getHandler1code())).append("\t");             //保險業務員代碼:
               	sb.append(m.getSumpremium()==null?"":m.getSumpremium()).append("\t");            //保險費:
               	sb.append(m.getSumdiscount()==null?"":m.getSumdiscount()).append("\t");          //折扣金額:
               	sb.append(m.getFycfee()==null?"":m.getFycfee()).append("\t");                    //佣金:
               	sb.append(m.getServicefee()==null?"":m.getServicefee()).append("\t");            //服務費:
               	sb.append(StringUtil.nullToSpace(m.getRemark())).append("\t");                   //註記:
               	sb.append(StringUtil.nullToSpace(m.getInfc15())).append("\t");                   //入金日:
               	sb.append(m.getInfc10()==null?"":m.getInfc10()).append("\t");                    //入金金額:
               	sb.append(StringUtil.nullToSpace(m.getSeriescode()));                           //專案代號:
				sb.append("\r\n");
			}
		}
		return sb.toString();
	}
	@SuppressWarnings("unchecked")
	private String genLionCMDate(String batchNo) throws Exception {
		Map<String,String> params = new HashMap<String, String>();
		params.put("batchNo", batchNo);
		Result result = hasAgtLionCMService.selectForGenFile(params);
		StringBuilder sb = new StringBuilder();
		if(result.getResObject() != null) {
			List<HasAgtLionCM> list = (List<HasAgtLionCM>) result.getResObject();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			for(HasAgtLionCM m:list) {
//				sb.append(StringUtil.nullToSpace(m.getBatchNo())).append("\t");                  //批次號碼: 
				sb.append(StringUtil.nullToSpace(m.getCompanyno())).append("\t");	            //保險公司代號:
				sb.append(StringUtil.nullToSpace(m.getPectype())).append("\t");                  //新/續保件代碼:
				sb.append(StringUtil.nullToSpace(m.getRiskcode())).append("\t");                 //商品代碼:
				sb.append(StringUtil.nullToSpace(m.getAgentorderseq())).append("\t");            //受理編號:
				sb.append(StringUtil.nullToSpace(m.getPolicyno())).append("\t");                 //保單號碼:
               	sb.append(StringUtil.nullToSpace(m.getEndorseno())).append("\t");                //批單號碼:
               	sb.append(StringUtil.nullToSpace(m.getOperatedate())).append("\t");              //簽單日期:
               	sb.append(StringUtil.nullToSpace(m.getStartdate())).append("\t");                //保單起日:
               	sb.append(StringUtil.nullToSpace(m.getEnddate())).append("\t");                  //保單止日:
               	sb.append(StringUtil.nullToSpace(m.getAppliname())).append("\t");                //要保人姓名:
               	sb.append(StringUtil.nullToSpace(m.getApplicode())).append("\t");                //要保人id:
               	sb.append(StringUtil.nullToSpace(m.getInsuredname())).append("\t");              //被保人姓名:
               	sb.append(StringUtil.nullToSpace(m.getInsuredcode())).append("\t");              //被保人id:
               	sb.append(StringUtil.nullToSpace(m.getSubbusinessnature())).append("\t");        //分行代碼:
               	sb.append(StringUtil.nullToSpace(m.getSubbusinessnaturename())).append("\t");    //分行名稱:
               	sb.append(StringUtil.nullToSpace(m.getHandler1code())).append("\t");             //保險業務員代碼:
               	sb.append(StringUtil.nullToSpace(m.getHandler1name())).append("\t");             //保險業務員姓名:
               	sb.append(m.getSumpremium()==null?"":m.getSumpremium()).append("\t");            //保險費:
               	sb.append(m.getRate()==null?"":m.getRate()).append("\t");                        //佣金(率):  
               	sb.append(m.getSumdiscount()==null?"":m.getSumdiscount()).append("\t");          //折扣金額:
               	sb.append(m.getFycfee()==null?"":m.getFycfee()).append("\t");                    //佣金:
               	sb.append(m.getServicefee()==null?"":m.getServicefee()).append("\t");            //服務費:
               	sb.append(StringUtil.nullToSpace(m.getRemark())).append("\t");                   //註記:
               	sb.append(StringUtil.nullToSpace(m.getInfc15())).append("\t");                   //入金日:
               	sb.append(StringUtil.nullToSpace(m.getFycmonth())).append("\t");                 //佣金月份:
               	sb.append(StringUtil.nullToSpace(m.getCreditcardno()));                         //信用卡卡號:
				sb.append("\r\n");
			}
		}
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	private String genLionCLDate(String batchNo) throws Exception {
		Map<String,String> params = new HashMap<String, String>();
		params.put("batchNo", batchNo);
		Result result = hasAgtLionCLService.selectForGenFile(params);
		StringBuilder sb = new StringBuilder();
		if(result.getResObject() != null) {
			List<HasAgtLionCL> list = (List<HasAgtLionCL>) result.getResObject();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			for(HasAgtLionCL m:list) {
//				sb.append(StringUtil.nullToSpace(m.getBatchNo())).append("\t");                  //批次號碼:     
				sb.append(StringUtil.nullToSpace(m.getPolicyno())).append("\t");                 //保單號碼:
				sb.append(StringUtil.nullToSpace(m.getClaimno())).append("\t");                  //理賠案號:
				sb.append(StringUtil.nullToSpace(m.getInsuredcode())).append("\t");              //被保人id:
				sb.append(StringUtil.nullToSpace(m.getApplicode())).append("\t");                //要保人id:
				sb.append(StringUtil.nullToSpace(m.getInsuredidentity())).append("\t");          //身分證字號:
				sb.append(StringUtil.nullToSpace(m.getKindcode())).append("\t");                 //理賠型態:
				sb.append(StringUtil.nullToSpace(m.getDamagename())).append("\t");               //理賠事故原因:
				sb.append(m.getSumthispaid()==null?"":m.getSumthispaid()).append("\t");          //理賠金額:
				sb.append(StringUtil.nullToSpace(m.getCurrency())).append("\t");                 //幣別:
               	sb.append(StringUtil.nullToSpace(m.getOwnership())).append("\t");                //付款方式:
               	sb.append(StringUtil.nullToSpace(m.getReceiptdate())).append("\t");              //收件日期:
               	sb.append(StringUtil.nullToSpace(m.getDamagestartdate())).append("\t");          //事故日期:
               	sb.append(StringUtil.nullToSpace(m.getClaimdate())).append("\t");                //申請日期:
               	sb.append(StringUtil.nullToSpace(m.getUnderwriteenddate())).append("\t");        //結案日期:
               	sb.append(StringUtil.nullToSpace(m.getPaydone())).append("\t");                  //是否需給付:
               	sb.append(StringUtil.nullToSpace(m.getPaycomment())).append("\t");               //給付說明:
               	sb.append(StringUtil.nullToSpace(m.getAgentcode())).append("\t");                //保經代代碼:
               	sb.append(StringUtil.nullToSpace(m.getCompanyno())).append("\t");	            //保險公司代碼:
               	sb.append(StringUtil.nullToSpace(m.getAgentorderseq())).append("\t");            //受理編號:
               	sb.append(StringUtil.nullToSpace(m.getClaimtimes()));                           //理賠次數:
				sb.append("\r\n");
			}
		}
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	private String genLionCHDate(String batchNo) throws Exception {
		Map<String,String> params = new HashMap<String, String>();
		params.put("batchNo", batchNo);
		Result result = hasAgtLionCHService.selectForGenFile(params);
		StringBuilder sb = new StringBuilder();
		if(result.getResObject() != null) {
			List<HasAgtLionCH> list = (List<HasAgtLionCH>) result.getResObject();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			for(HasAgtLionCH m:list) {
				sb.append(StringUtil.nullToSpace(m.getCompanyno())).append("\t");	            //保險公司代號:
				sb.append(StringUtil.nullToSpace(m.getPectype())).append("\t");                  //新/續保件代碼:
				sb.append(StringUtil.nullToSpace(m.getRiskcode())).append("\t");                 //商品代碼:
				sb.append(StringUtil.nullToSpace(m.getAgentorderseq())).append("\t");            //受理編號:
//				sb.append(StringUtil.nullToSpace(m.getBatchNo())).append("\t");                  //批次號碼: 
				sb.append(StringUtil.nullToSpace(m.getPolicyno())).append("\t");                 //保單號碼:
               	sb.append(StringUtil.nullToSpace(m.getEndorseno())).append("\t");                //批單號碼:
               	sb.append(StringUtil.nullToSpace(m.getOperatedate())).append("\t");              //簽單日期:
               	sb.append(StringUtil.nullToSpace(m.getStartdate())).append("\t");                //保單起日:
               	sb.append(StringUtil.nullToSpace(m.getEnddate())).append("\t");                  //保單止日:
               	sb.append(StringUtil.nullToSpace(m.getAppliname())).append("\t");                //要保人姓名:
               	sb.append(StringUtil.nullToSpace(m.getApplicode())).append("\t");                //要保人id:
               	sb.append(StringUtil.nullToSpace(m.getInsuredname())).append("\t");              //被保人姓名:
               	sb.append(StringUtil.nullToSpace(m.getInsuredcode())).append("\t");              //被保人id:
               	sb.append(StringUtil.nullToSpace(m.getSubbusinessnature())).append("\t");        //分行代碼:
               	sb.append(StringUtil.nullToSpace(m.getHandler1code())).append("\t");             //保險業務員代碼:
               	sb.append(m.getSumpremium()==null?"":m.getSumpremium()).append("\t");            //保險費:
               	sb.append(m.getSumdiscount()==null?"":m.getSumdiscount()).append("\t");          //折扣金額:
               	sb.append(m.getFycfee()==null?"":m.getFycfee()).append("\t");                    //佣金:
               	sb.append(m.getServicefee()==null?"":m.getServicefee()).append("\t");            //服務費:
               	sb.append(StringUtil.nullToSpace(m.getRemark())).append("\t");                   //註記:
               	sb.append(StringUtil.nullToSpace(m.getIntroducerid())).append("\t");             //推薦人id:
               	sb.append(StringUtil.nullToSpace(m.getPaymentNo())).append("\t");                //銷帳編號:
               	sb.append(m.getPaymentTotal()==null?"":m.getPaymentTotal()).append("\t");        //繳費金額:
               	sb.append(StringUtil.nullToSpace(m.getSeriescode())).append("\t");               //專案代號:
               	sb.append(StringUtil.nullToSpace(m.getLicenseno())).append("\t");                //車號:
               	sb.append(StringUtil.nullToSpace(m.getAccount1())).append("\t");                 //放款帳號:
               	sb.append(StringUtil.nullToSpace(m.getDebitaccount())).append("\t");             //扣款帳號:
               	sb.append(StringUtil.nullToSpace(m.getSitename())).append("\t");                 //船班:
               	sb.append(StringUtil.nullToSpace(m.getStartsite())).append("\t");                //出發埠:
               	sb.append(StringUtil.nullToSpace(m.getEndsite())).append("\t");                  //目地埠:
               	sb.append(StringUtil.nullToSpace(m.getInvoiceno())).append("\t");                //發票號碼:
               	sb.append(StringUtil.nullToSpace(m.getBusinessnature())).append("\t");           //業務來源:
               	sb.append(StringUtil.nullToSpace(m.getRenewflag())).append("\t");                //約定續保:
               	sb.append(StringUtil.nullToSpace(m.getOldPolicyno())).append("\t");              //前保單號碼:
               	sb.append(StringUtil.nullToSpace(m.getBirthday())).append("\t");                 //被保險人生日:
               	sb.append(StringUtil.nullToSpace(m.getEmail())).append("\t");                    //要保人E-mail:
               	sb.append(StringUtil.nullToSpace(m.getFromtype())).append("\t");                 //件別:
               	sb.append(StringUtil.nullToSpace(m.getPostaddress())).append("\t");              //要保人通訊地址:
               	sb.append(StringUtil.nullToSpace(m.getPhonetelenumber())).append("\t");          //要保人市話:
               	sb.append(StringUtil.nullToSpace(m.getMobile())).append("\t");                   //要保人手機:
               	sb.append(m.getAmount()==null?"":m.getAmount()).append("\t");                    //保額:
               	sb.append(StringUtil.nullToSpace(m.getAmountUnit())).append("\t");               //保額單位:
               	//mantis：HAS0272，處理人員：DP0706，處理人員：DP0706，TA雄獅回饋檔新增欄位START
               	sb.append(StringUtil.nullToSpace(m.getOthflag())).append("\t");                  //保單狀態:
            	sb.append(StringUtil.nullToSpace(m.getTotalinsuredno()));                        //總承保人數:
            	//mantis：HAS0272，處理人員：DP0706，處理人員：DP0706，TA雄獅回饋檔新增欄位END
				sb.append("\r\n");
			}
		}
		return sb.toString();
	}
	
	
	@SuppressWarnings("unchecked")
	private String sendEmail(String batchNo,String type,String fileQty,String filePqty,Date excuteTime,
			String logStatus,String errMsg,String programId) throws Exception {
		Mailer mailer = new Mailer();
		StringBuilder tmpMsg = new StringBuilder();
		Map<String, Object> params = new HashMap<>();
		params.put("prgId", programId);           
		Result result = this.hasBatchInfoService.findHasBatchInfoByUK(params);
		if (result.getResObject() == null) {
			return "無法取得HAS_BATCH_INFO資料，無法寄送MAIL";
		}
		
		HasBatchInfo hasBatchInfo = (HasBatchInfo) result.getResObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String subject;
		switch(type){
			case"1":
				subject = hasBatchInfo.getMailSubject() + "受理-" + sdf.format(new Date());
				break;
			case"2":
				subject = hasBatchInfo.getMailSubject() + "保批-" + sdf.format(new Date());
				break;
			case"3":
				subject = hasBatchInfo.getMailSubject() + "銷帳-" + sdf.format(new Date());
				break;
			case"4":
				subject = hasBatchInfo.getMailSubject() + "佣金-" + sdf.format(new Date());
				break;
			case"5":
				subject = hasBatchInfo.getMailSubject() + "理賠-" + sdf.format(new Date());
				break;
			default:
				subject = hasBatchInfo.getMailSubject() + "-" + sdf.format(new Date());
		}
		
		String mailTo = hasBatchInfo.getMailTo();
//		String mailTo = "cd094@ctbcins.com";
		String mailCc = hasBatchInfo.getMailCc();

		StringBuilder sb = new StringBuilder();
		sb.append("<p>批次號碼：" + batchNo + "</p>");
		sb.append("<p>轉檔時間：" + sdf.format(excuteTime) + "</p>");
		if (logStatus.equals("S")) {
			sb.append("<p>執行狀態：完成</p>");
			sb.append("<p>預計筆數：" + fileQty + "</p>");
			sb.append("<p>處理筆數：" + filePqty + "</p>");
			params.clear();
			params.put("batchNo", batchNo);
			params.put("sortBy", "RISKCODE, ORDERSEQ");
			params.put("sortType", "asc");
			Result mainResult = hasAgtBatchDtlService.findHasAgtBatchDtlByParams(params);

			if (mainResult.getResObject() == null) {
				sb.append("<p>OTH_BATCH_PASSBOOK批次主檔查無資料，請洽系統人員。</p>");
				tmpMsg.append("OTH_BATCH_PASSBOOK批次主檔查無資料，請洽系統人員。");
			} else {
				sb.append("<table border=1 style='border-collapse: collapse;'>");
				sb.append("<tr bgcolor='#70bbd9'>");
				sb.append("<td>受理編號</td>");
				sb.append("<td>狀態</td>");
				sb.append("<td>對應核心單號</td>");
				sb.append("<td>險別</td>");
				sb.append("<td>資料來源</td>");
				sb.append("</tr>");
				List<HasAgtBatchDtl> hasAgtBatchDtlList = (List<HasAgtBatchDtl>) mainResult.getResObject();
				for (HasAgtBatchDtl main : hasAgtBatchDtlList) {
					String dataSource = main.getDataSource().equals("1")?"新核心":"AS400";
					String status = "";
					switch (main.getOrderseqStatus()) {
					case "00":
						status = "未處理";
						break;
					case "01":
						status = "資料產生成功";
						break;
					case "02":
						status = "檔案產生成功";
						break;
					default:
						status = "未定義";
						break;
					}
					sb.append("<tr>");
					sb.append("<td>" + main.getOrderseq() + "</td>");
					sb.append("<td>" + status + "</td>");
					sb.append("<td>" + main.getCoreNo() + "</td>");
					sb.append("<td>" + main.getRiskcode() + "</td>");
					sb.append("<td>" + dataSource + "</td>");
					sb.append("</tr>");
				}
				sb.append("</table>");
			}
		} else if (logStatus.equals("N")) {
			sb.append("<p>執行狀態：無資料 </p>");
			tmpMsg.append("無資料");
		} else {// status = "F"
			sb.append("<p>執行狀態：失敗</p>");
			sb.append("<p>異常訊息：" + StringUtil.nullToSpace(errMsg) + "</p>");
			tmpMsg.append(errMsg);
		}
		try{
			mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", "", mailTo, "",
					mailCc, "", "", "", sb.toString(), "smtp", "newims", "2012newims");	
		}
		catch(Exception e){
			return "寄送信件發生異常";
		}
		return tmpMsg.toString();
	 }
	
	private String sendEmail(String batchNo,String type,String fileQty,String filePqty,Date excuteTime,
			String logStatus,String errMsg,String programId,String[]filepath,String[]filename) throws Exception {
		Mailer mailer = new Mailer();
		StringBuilder tmpMsg = new StringBuilder();
		Map<String, Object> params = new HashMap<>();
		params.put("prgId", programId);           
		Result result = this.hasBatchInfoService.findHasBatchInfoByUK(params);
		if (result.getResObject() == null) {
			return "無法取得HAS_BATCH_INFO資料，無法寄送MAIL";
		}
		HasBatchInfo hasBatchInfo = (HasBatchInfo) result.getResObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String subject;
		switch(type){
			case"1":
				subject = hasBatchInfo.getMailSubject() + "受理-" + sdf.format(new Date());
				break;
			case"2":
				subject = hasBatchInfo.getMailSubject() + "保批-" + sdf.format(new Date());
				break;
			case"3":
				subject = hasBatchInfo.getMailSubject() + "銷帳-" + sdf.format(new Date());
				break;
			case"4":
				subject = hasBatchInfo.getMailSubject() + "佣金-" + sdf.format(new Date());
				break;
			case"5":
				subject = hasBatchInfo.getMailSubject() + "理賠-" + sdf.format(new Date());
				break;
			default:
				subject = hasBatchInfo.getMailSubject() + "-" + sdf.format(new Date());
		}
		
		String mailTo = hasBatchInfo.getMailTo();
//		String mailTo = "cd094@ctbcins.com";
		String mailCc = hasBatchInfo.getMailCc();

		StringBuilder sb = new StringBuilder();
		sb.append("<p>批次號碼：" + batchNo + "</p>");
		sb.append("<p>轉檔時間：" + sdf.format(excuteTime) + "</p>");
		if (logStatus.equals("S")) {
			sb.append("<p>執行狀態：完成</p>");
			sb.append("<p>預計筆數：" + fileQty + "</p>");
			sb.append("<p>處理筆數：" + filePqty + "</p>");
			params.clear();
			params.put("batchNo", batchNo);
			params.put("sortBy", "RISKCODE, ORDERSEQ");
			params.put("sortType", "asc");
			Result mainResult = hasAgtBatchDtlService.findHasAgtBatchDtlByParams(params);

			if (mainResult.getResObject() == null) {
				sb.append("<p>OTH_BATCH_PASSBOOK批次主檔查無資料，請洽系統人員。</p>");
				tmpMsg.append("OTH_BATCH_PASSBOOK批次主檔查無資料，請洽系統人員。");
			} else {
				sb.append("<table border=1 style='border-collapse: collapse;'>");
				sb.append("<tr bgcolor='#70bbd9'>");
				sb.append("<td>受理編號</td>");
				sb.append("<td>狀態</td>");
				sb.append("<td>對應核心單號</td>");
				sb.append("<td>險別</td>");
				sb.append("<td>資料來源</td>");
				sb.append("</tr>");
				List<HasAgtBatchDtl> hasAgtBatchDtlList = (List<HasAgtBatchDtl>) mainResult.getResObject();
				for (HasAgtBatchDtl main : hasAgtBatchDtlList) {
					String dataSource = main.getDataSource().equals("1")?"新核心":"AS400";
					String status = "";
					switch (main.getOrderseqStatus()) {
					case "00":
						status = "未處理";
						break;
					case "01":
						status = "資料產生成功";
						break;
					case "02":
						status = "檔案產生成功";
						break;
					default:
						status = "未定義";
						break;
					}
					sb.append("<tr>");
					sb.append("<td>" + main.getOrderseq() + "</td>");
					sb.append("<td>" + status + "</td>");
					sb.append("<td>" + main.getCoreNo() + "</td>");
					sb.append("<td>" + main.getRiskcode() + "</td>");
					sb.append("<td>" + dataSource + "</td>");
					sb.append("</tr>");
				}
				sb.append("</table>");
			}
		} else if (logStatus.equals("N")) {
			sb.append("<p>執行狀態：無資料 </p>");
			tmpMsg.append("無資料");
		} else {// status = "F"
			sb.append("<p>執行狀態：失敗</p>");
			sb.append("<p>異常訊息：" + StringUtil.nullToSpace(errMsg) + "</p>");
			tmpMsg.append(errMsg);
		}
		try{
			mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", "", mailTo, "",
					mailCc, "", "", "", sb.toString(), "smtp", "newims", "2012newims",filepath,filename);
		
		}
		catch(Exception e ){
			return "寄送信件發生異常";
		}
		return tmpMsg.toString();
	 }
	
	private void updateHasAgtBatch(HasAgtBatchMain oldMain, String fileStatus, String filename, String remark, String userId) throws Exception {
		// HasAgtBatchMain處理
		HasAgtBatchMain main = new HasAgtBatchMain();
		main.setBatchNo(oldMain.getBatchNo());
		if(filename != null)
			main.setFileName(filename);
		if(remark != null) {
			remark = StringUtil.isSpace(oldMain.getRemark())?remark:oldMain.getRemark()+";"+remark;
			if(remark != null && remark.length() > 300) {
				remark = remark.substring(remark.length()-300 <0?0:remark.length()-300, remark.length());
			}
			main.setRemark(remark);
		}
		main.setFileStatus(fileStatus);
		main.setIupdate(userId);
		main.setDupdate(new Date());
		// HasAgtBatchDtl處理
		Map<String,Object> params = null;
		if("Y".equals(fileStatus)) {
			params = new HashMap<String, Object>();
			params.put("batchNo", main.getBatchNo());
			params.put("orderseqStatus", "02");
			params.put("iupdate", userId);
			params.put("dupdate", new Date());
		}
		lionBackFileService.updateHasAgtBatch(main, params);
	}
	
	public String genFile(String fileContent, File file){
		FileOutputStream fileOutputStream = null;
		BufferedWriter bufWriter = null;
		try {
			fileOutputStream = new FileOutputStream(file, false);
			bufWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
			bufWriter.write(fileContent);
			bufWriter.close();
		} catch (Exception e) {
			logger.info(e.toString());
			return e.toString();
		} finally {
			try {
				if(bufWriter!=null){
					bufWriter.close();									
				}if(fileOutputStream!=null){					
					fileOutputStream.close();
				}
			} catch (IOException e) {
				logger.info(e.toString());
			}
		}
		return "";
	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	private void writeZip(String fileName,File file,String pswd) throws Exception {
		ZipFile zipFile = null;
		zipFile = new ZipFile(fileName + ".zip");
		ArrayList filesToAdd = new ArrayList();
		filesToAdd.add(file);
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
		if (!StringUtil.isSpace(pswd)) {
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
			parameters.setPassword(pswd);
		}
		zipFile.addFiles(filesToAdd, parameters);
	}
	
	public  void deleteFile(String txtFilePath, String zipFilePath) {
		File txtFile = new File(txtFilePath);
		File zipFile = new File(zipFilePath);
		if(txtFile.exists())
			txtFile.delete();
		if(zipFile.exists())
			zipFile.delete();
	}
	
	
	
	private boolean uploadZipFileToSftp(String filePath) throws Exception{
		boolean result = false;
		Result response;
		Map<String,String> params = new HashMap<>();
		String sftpHost = configUtil.getString("lionBackFileFTP");
		String sftpUser = configUtil.getString("lionBackFileFtpUserPut");
		String sftpPwd = configUtil.getString("lionBackFileFtpPwdPut");
		String sftpPort = configUtil.getString("lionBackFileFtpPort");
		String remoteDir = configUtil.getString("lionBackFileRemotePath");
//		params.put("prgId",sftpHost);
//		 response = hasBatchInfoService.findHasBatchInfoByUK(params);
//		sftpHost=((HasBatchInfo)response.getResObject()).getMailTo();
//		list = (List<HasAgtBatchMain>)result.getResObject();
		Map<String, String> map =new HashMap<>();
		response=hasBatchInfoService.findHasBatchInfoByParams(params);
		if(response!=null){
			List<HasBatchInfo> list= (List<HasBatchInfo>)response.getResObject();
			for(HasBatchInfo a:list ){
				map.put(a.getPrgId(), a.getMailTo());
			}	
		}
		sftpHost=map.get(sftpHost);
		sftpPort=map.get(sftpPort);
		sftpUser=map.get(sftpUser);
		sftpPwd=map.get(sftpPwd);
		remoteDir=map.get(remoteDir);
//		params.put("prgId",sftpUser);
//	     response = hasBatchInfoService.findHasBatchInfoByUK(params);
//		sftpUser=((HasBatchInfo)response.getResObject()).getMailTo();
//		params.put("prgId",sftpPwd);
//		 response = hasBatchInfoService.findHasBatchInfoByUK(params);
//		sftpPwd=((HasBatchInfo)response.getResObject()).getMailTo();
//	
		
		SftpUtil sftpUtil = new SftpUtil(sftpHost, Integer.valueOf(sftpPort), sftpUser, sftpPwd);

		String strResult = sftpUtil.putFileToSftp2(remoteDir, filePath);
		if("success".equals(strResult)) {
			result = true;
		}
		
		return result;
	}
	
	
	private Result getReturnResult(String msg) {
		Result result = new Result();
		Message message = new Message();
		message.setMessageString(msg);
		result.setMessage(message);
		return result;
	}

	
	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}


	public HasBatchInfoService getHasBatchInfoService() {
		return hasBatchInfoService;
	}

	public void setHasBatchInfoService(HasBatchInfoService hasBatchInfoService) {
		this.hasBatchInfoService = hasBatchInfoService;
	}



	public LionBackFileService getLionBackFileService() {
		return lionBackFileService;
	}

	public void setLionBackFileService(LionBackFileService lionBackFileService) {
		this.lionBackFileService = lionBackFileService;
	}

	public HasSpService getHasSpService() {
		return hasSpService;
	}

	public void setHasSpService(HasSpService hasSpService) {
		this.hasSpService = hasSpService;
	}

	public HasAgtBatchMainService getHasAgtBatchMainService() {
		return hasAgtBatchMainService;
	}

	public void setHasAgtBatchMainService(HasAgtBatchMainService hasAgtBatchMainService) {
		this.hasAgtBatchMainService = hasAgtBatchMainService;
	}


	

	public HasAgtLionOPService getHasAgtLionOPService() {
		return hasAgtLionOPService;
	}

	public void setHasAgtLionOPService(HasAgtLionOPService hasAgtLionOPService) {
		this.hasAgtLionOPService = hasAgtLionOPService;
	}
	
	public HasAgtLionACService getHasAgtLionACService() {
		return hasAgtLionACService;
	}

	public void setHasAgtLionACService(HasAgtLionACService hasAgtLionACService) {
		this.hasAgtLionACService = hasAgtLionACService;
	}

	public HasAgtLionCLService getHasAgtLionCLService() {
		return hasAgtLionCLService;
	}

	public void setHasAgtLionCLService(HasAgtLionCLService hasAgtLionCLService) {
		this.hasAgtLionCLService = hasAgtLionCLService;
	}

	public HasAgtLionCMService getHasAgtLionCMService() {
		return hasAgtLionCMService;
	}

	public void setHasAgtLionCMService(HasAgtLionCMService hasAgtLionCMService) {
		this.hasAgtLionCMService = hasAgtLionCMService;
	}

	public HasAgtLionCHService getHasAgtLionCHService() {
		return hasAgtLionCHService;
	}

	public void setHasAgtLionCHService(HasAgtLionCHService hasAgtLionCHService) {
		this.hasAgtLionCHService = hasAgtLionCHService;
	}

	public HasAgtBatchDtlService getHasAgtBatchDtlService() {
		return hasAgtBatchDtlService;
	}

	public void setHasAgtBatchDtlService(HasAgtBatchDtlService hasAgtBatchDtlService) {
		this.hasAgtBatchDtlService = hasAgtBatchDtlService;
	}

	public static void main(String args[]) throws Exception{
		//測試取得檔案list----start
//		String riskCode = "TA";
//		String businessNo = "LION04_240109175923";
//		FtsUtil ftsUtil = new FtsUtil("http://192.168.112.122:8880/FTS/rf/fileHandler/");
//		FileListResponseVo vo = ftsUtil.getFtsFileList(riskCode, businessNo);
//        ArrayList<FileVo> list = vo.getFileList();
//        for(FileVo fv : list) {
//        	System.out.println("----------");
//        	System.out.println("oid : " + fv.getOid());
//        	System.out.println("name : " + fv.getName());
//        	System.out.println("downloadPath : " + fv.getDownloadPath());
//        	System.out.println("----------");
//        	System.out.println("start to download file : " + fv.getName());
//        	ftsUtil.downloadFile(businessNo, fv.getOid(), "D:/temp/", fv.getName());
//        	System.out.println("download file : " + fv.getName() + " finished");
//        }
		//測試取得檔案list----end
		
	}
}
