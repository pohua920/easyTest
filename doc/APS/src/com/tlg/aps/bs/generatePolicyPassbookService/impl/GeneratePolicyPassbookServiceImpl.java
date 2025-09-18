package com.tlg.aps.bs.generatePolicyPassbookService.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.generatePolicyPassbookService.GeneratePolicyPassbookService;
import com.tlg.aps.bs.generatePolicyPassbookService.PolicyPassbookService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchInfo;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.entity.OthBatchPassbook;
import com.tlg.prpins.entity.OthBatchPassbookKind;
import com.tlg.prpins.entity.OthBatchPassbookList;
import com.tlg.prpins.service.FirBatchInfoService;
import com.tlg.prpins.service.OthBatchPassbookKindService;
import com.tlg.prpins.service.OthBatchPassbookListService;
import com.tlg.prpins.service.OthBatchPassbookService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.DateUtils;
import com.tlg.util.Mailer;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.SftpUtil;
import com.tlg.util.StringUtil;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/*  mantis：OTH0127，處理人員：CC009，需求單編號：OTH0127 保發中心_保單存摺產生排程規格 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class GeneratePolicyPassbookServiceImpl  implements GeneratePolicyPassbookService{
	
	private static final Logger logger = Logger.getLogger(GeneratePolicyPassbookServiceImpl.class);
	private ConfigUtil configUtil;
	private FirBatchInfoService firBatchInfoService;
	private PolicyPassbookService policyPassbookService;
	private OthBatchPassbookService othBatchPassbookService;
	private OthBatchPassbookListService othBatchPassbookListService;
	private OthBatchPassbookKindService othBatchPassbookKindService;
	
	@Override
	public Result runToGeneratePolicyPassbook(String userId, String programId) throws SystemException, Exception {
		Date excuteTime = new Date();
		StringBuilder sb = new StringBuilder();
		String tmpLogStatus = "";
		String tmpMsg = "";
		String mailMsg = "";
		String tmpResult = "";
		
		// 檢核傳入參數
		if (StringUtil.isSpace(userId)) {
			sb.append("執行人員無內容值。");
		}
		if (StringUtil.isSpace(programId)) {
			sb.append("程式代碼無內容值。");
		}
		String tmpBatchNo = "OTHPB_" + new SimpleDateFormat("yyMMddHHmmss").format(excuteTime);
		logger.info("tmpBatchNo =" + tmpBatchNo);

		if (StringUtil.isSpace(sb.toString())) {
			// 判斷排程是否可以執行
			Map<String, String> params = new HashMap<>();
			params.put("prgId", programId + "_STATUS");
			Result result = firBatchInfoService.findFirBatchInfoByUK(params);
			if (result.getResObject() != null && ((FirBatchInfo) result.getResObject()).getMailTo().equals("N")) {
				tmpLogStatus = "S";
				tmpMsg = "FIR_BATCH_INFO設定檔設定為排程暫停執行。";
			} else {
				params.put("prgId", programId + "_PSWD");
				result = firBatchInfoService.findFirBatchInfoByUK(params);
				if (result.getResObject() != null) {
					tmpLogStatus = "1";
				} else {
					tmpLogStatus = "F";
					tmpMsg = "未設定保發中心保單存摺加密資訊(FIR_BATCH_INFO)";
				}
			}
		} else {
			tmpLogStatus = "F";
			tmpMsg = "=接收參數." + sb.toString();
		}

		logger.info("tmpLogStatus =" + tmpLogStatus);
		logger.info("tmpMsg =" + tmpMsg);

		// 新增FIR_BATCH_LOG批次程式執行記錄檔
		Result result = policyPassbookService.insertFirBatchLog(excuteTime, userId, programId, tmpLogStatus, tmpMsg,
				tmpBatchNo);
		FirBatchLog firBatchLog = (FirBatchLog) result.getResObject();
		switch (tmpLogStatus) {
		case "1":
			tmpResult = this.generatePolicyPassbook(userId, tmpBatchNo, "1");
			logger.info("tmpResult =" + tmpResult);
			if (tmpResult.equals("0")) {
				tmpLogStatus = "N";
			} else if (tmpResult.equals("1")) {
				tmpLogStatus = "S";
			} else if (tmpResult.equals("2")) {
				tmpLogStatus = "F";
			}
			updateFirBatchLog(tmpLogStatus, tmpMsg, userId, firBatchLog.getOid());
			mailMsg = sendEmail(firBatchLog.getBatchNo(), excuteTime, tmpLogStatus, sb.toString(), programId);
			break;
		case "S":
			updateFirBatchLog(tmpLogStatus, tmpMsg, userId, firBatchLog.getOid());
			break;
		case "F":
			updateFirBatchLog(tmpLogStatus, tmpMsg, userId, firBatchLog.getOid());
			mailMsg = sendEmail(firBatchLog.getBatchNo(), excuteTime, tmpLogStatus, sb.toString(), programId);
			break;
		}
		logger.info("mailMsg =" + mailMsg);
		if (!StringUtil.isSpace(mailMsg)) {
			updateFirBatchLog("F", mailMsg, userId, firBatchLog.getOid());
		}
			
		return this.getReturnResult("執行完成");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String generatePolicyPassbook(String userId, String batchNo, String type) throws SystemException, Exception {
		Date excuteTime = new Date();
		Result result;
		String tmpReturn;
		String tmpBno = "";
		Map params = new HashMap<>();
		OthBatchPassbook entity = new OthBatchPassbook();
		if("1".equals(type)) {
			//新增OTH_BATCH_PASSBOOK批次送保發產險保單存摺主檔
			result = policyPassbookService.insertOthBatchPassbook(batchNo, "0", userId, excuteTime);
			entity = (OthBatchPassbook) result.getResObject();
			//更新資料表：OTH_BATCH_PASSBOOK_LIST批次送保發產險保單存摺中介
			tmpBno = "TMP_" + DateUtils.format(excuteTime, "yyMMddHHmmss");
			params.put("batchNo", tmpBno);
			policyPassbookService.updateOthBatchPassbookListByTmpBno(params);
		}else if("2".equals(type)) {
			//更新OTH_BATCH_PASSBOOK批次送保發產險保單存摺主檔
			params.put("batchNo", batchNo);
			result = othBatchPassbookService.findOthBatchPassbookByUK(params);
			if(result.getResObject() == null) {
				return "0";
			}
			entity = (OthBatchPassbook) result.getResObject();
			String remark = (StringUtil.isSpace(entity.getRemark())?"":entity.getRemark()+";")  + "上次執行資訊：" + userId + "-"
					+ DateUtils.format(entity.getDupdate(), "yyyy/MM/dd HH:mm:ss") + "-" + entity.getQtyTii() + "筆-檔名" + entity.getFilename();
			//長度超過1200則擷取後面1200
			remark = remark.substring(remark.length()-1200 <0?0:remark.length()-1200, remark.length());
			logger.info("remark ="+remark);
			entity.setRemark(remark);
			entity.setStatus("0");
			entity.setQtyTii(new BigDecimal(0));
			entity.setFilename("");
			policyPassbookService.updateOthBatchPassbook(entity);
			//更新資料表：OTH_BATCH_PASSBOOK_LIST批次送保發產險保單存摺中介
			tmpBno = batchNo;
		}
		
		//讀取中介資料
		params.put("batchNo", tmpBno);
		params.put("sortBy", "RISKCODE, PROC_TYPE, POLICYNO, OID");
		result = othBatchPassbookListService.findOthBatchPassbookListByParams(params);
		List<OthBatchPassbookList> othBatchPassbookLists = (List<OthBatchPassbookList>) result.getResObject();
		if(othBatchPassbookLists == null || othBatchPassbookLists.size() <= 0) {
			this.updateOthBatchPassbook(entity, "4", 0, "", "", userId);
			return "0";
		}
		logger.info("othBatchPassbookLists.size ="+othBatchPassbookLists.size());
		
		//產生TXT
		List<File> fileList = new ArrayList<File>();
		try {
			fileList = this.genFileData(entity,othBatchPassbookLists,userId);
		} catch (Exception e) {
			logger.error("error msg =" + e.toString());
			return "2";
		}
		
		//更新中介檔回寫批號、執行人員、執行時間。
		params.clear();
		params.put("tmpBno", tmpBno);
		params.put("batchNo", batchNo);
		params.put("iupdate", userId);
		params.put("dupdate", new Date());
		policyPassbookService.updateOthBatchPassbookListByTmpBno(params);
		//更新批次主檔。
		StringBuilder sb = new StringBuilder();
		for(File file:fileList) {
			sb.append(file.getName()).append(";");
		}
		this.updateOthBatchPassbook(entity, "1", othBatchPassbookLists.size(), sb.toString(), null, userId);
		
		params.clear();
		params.put("prgId", "OTH_PASSBOOK_01_PSWD");
		result = firBatchInfoService.findFirBatchInfoByUK(params);
		String pswd = ((FirBatchInfo)result.getResObject()).getMailTo();
		params.put("prgId", "OTH_PASSBOOK_01_DELFILE");
		result = firBatchInfoService.findFirBatchInfoByUK(params);
		String delfile = ((FirBatchInfo)result.getResObject()).getMailTo();
		//產生zip檔並上傳FTP
		boolean uploadResult = true;
		for(File file:fileList) {
			writeZip(file.getPath().replaceAll(".txt", ""),file,pswd);
			//上傳檔案
			if(!this.uploadZipFileToSftp(file.getPath().replaceAll(".txt", "") + ".zip")) {
				uploadResult = false;
				break;
			}
		}
		
		if(uploadResult) {
			this.updateOthBatchPassbook(entity, "2", null, null, null, userId);
			tmpReturn = "1";
			
		}else {
			this.updateOthBatchPassbook(entity, "3", null, null, null, userId);
			tmpReturn = "2";
		}
		
		if(delfile.equals("Y")) {
			for(File file:fileList) {
				if(file.exists()){ 
					file.delete();
				}
				File zipFile = new File(file.getPath().replaceAll(".txt", "") + ".zip");
				if(zipFile.exists()){ 
					zipFile.delete();
				}
			}
		}
		
		return tmpReturn;
	}
	
	public String genFile(String fileContent,String userId, File file){
		FileOutputStream fileOutputStream = null;
		BufferedWriter bufWriter = null;
		try {
			fileOutputStream = new FileOutputStream(file, false);
			bufWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "UTF-8"));
			bufWriter.write(fileContent);
			bufWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			return e.toString();
		}finally {
			try {
				bufWriter.close();				
				fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<File> genFileData(OthBatchPassbook entity, List<OthBatchPassbookList> list,String userId) throws Exception {
		List<File> fileList = new ArrayList<File>();
		int count = 0;
		int seq = 0;
		int maxCount = 49000;
		String fileName;
		String outputFile;
		File filePath = new File(configUtil.getString("localOthPassbookTmpPath"));
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		StringBuilder sb = new StringBuilder();
		
		for(;seq<list.size();seq++) {
			OthBatchPassbookList model = list.get(seq);
			sb.append(StringUtil.isSpace(model.getSendCode())?"":model.getSendCode()).append("||");				//傳輸種類
			sb.append(StringUtil.isSpace(model.getInscoCode())?"":model.getInscoCode()).append("||");			//保險公司代碼
			sb.append(StringUtil.isSpace(model.getPolicyno())?"":model.getPolicyno()).append("||");				//保單號碼
			sb.append(StringUtil.isSpace(model.getInsuredIdOld())?"":model.getInsuredIdOld()).append("||");		//被保險人證號(原)
			sb.append(model.getStartdateOld()==null?"":sdf.format(model.getStartdateOld())).append("||");		//保單生效日(原)
			sb.append(StringUtil.isSpace(model.getApplicantId())?"":model.getApplicantId()).append("||");		//要保人證號(新)
			sb.append(StringUtil.isSpace(model.getApplicantName())?"":model.getApplicantName()).append("||");	//要保人姓名(新)
			sb.append(StringUtil.isSpace(model.getInsuredId())?"":model.getInsuredId()).append("||");			//被保險人證號(新)
			sb.append(StringUtil.isSpace(model.getInsuredName())?"":model.getInsuredName()).append("||");		//被保險人姓名(新)
			sb.append(model.getStartdate()==null?"":sdf.format(model.getStartdate())).append("||");				//保單生效日(新)
			sb.append(model.getEnddate()==null?"":sdf.format(model.getEnddate())).append("||");					//保單生效日(原)
			sb.append(StringUtil.isSpace(model.getDataStatus())?"":model.getDataStatus()).append("||");			//處理方式註記
			sb.append(StringUtil.isSpace(model.getRiskcodeTii())?"":model.getRiskcodeTii()).append("||");		//險種分類代碼
			String target = StringUtil.isSpace(model.getTarget())?"":model.getTarget();
			if(target.length() > 250)
				target = target.substring(0, 250);
			sb.append(target).append("||");																		//保險標的
			sb.append(model.getUnderwriteenddate()==null?"":sdf.format(model.getUnderwriteenddate())).append("||");//簽單日期/輸入日期
			sb.append(StringUtil.isSpace(model.getTiiTmp())?"":model.getTiiTmp()).append("||");					//資料產製時間
			String remark = StringUtil.isSpace(model.getRemark())?"":model.getRemark();
			if(remark.length() > 250)
				remark = remark.substring(0, 250);
			sb.append(remark).append("||");																		//備註
			sb.append("||");																					//批改事由(暫不處理)
			sb.append("||");																					//批改生效日(暫不處理)
			sb.append(model.getKindCount()==null?"":model.getKindCount());										//保險種類迴圈數
			
			Map params = new HashMap();
			if("1".equals(model.getDataSource())) {
				params.put("listOid", model.getOid());
			}else if ("2".equals(model.getDataSource())) {
				params.put("as400Oid", model.getAs400Oid());
			}
			params.put("sortBy", "KINDSORT,OID");
			Result result = othBatchPassbookKindService.findOthBatchPassbookKindByParams(params);
			if(null != result.getResObject()) {
				List<OthBatchPassbookKind> othBatchPassbookKinds = (List<OthBatchPassbookKind>) result.getResObject();
				int kindCount = 1;
				for(OthBatchPassbookKind kind:othBatchPassbookKinds) {
					sb.append("||").append(kindCount).append("||");
					sb.append(StringUtil.isSpace(kind.getKindname())?"":kind.getKindname()).append("||");
					sb.append(StringUtil.isSpace(kind.getAmountText())?"":kind.getAmountText());
					kindCount++;
				}
			}
			
			sb.append("\r\n");
			count++;
			if(count>=maxCount) {
				String PolicyNo = StringUtil.isSpace(model.getPolicyno())?"":model.getPolicyno();
				String LastPolicyNo = StringUtil.isSpace(list.get(seq+1).getPolicyno())?"":list.get(seq+1).getPolicyno();
				if(!PolicyNo.equals("") && !PolicyNo.equals(LastPolicyNo)) {
					fileName = "18_" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "_tii_p4.txt";
					outputFile = filePath + File.separator + fileName;
					File file = new File(outputFile);
					String msg = genFile(sb.toString(),userId, file);
					if(!StringUtil.isSpace(msg)) {
						msg = "產生檔案失敗-" + msg;
						this.updateOthBatchPassbook(entity, "3", list.size(), fileName, msg, userId);
						throw new Exception(msg);
					}
					fileList.add(file);
					sb.setLength(0);
					count = 0;
				}
			}
		}
		fileName = "18_" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "_tii_p4.txt";
		outputFile = filePath + File.separator + fileName;
		File file = new File(outputFile);
		String msg = genFile(sb.toString(),userId, file);
		if(!StringUtil.isSpace(msg)) {
			msg = "產生檔案失敗-" + msg;
			this.updateOthBatchPassbook(entity, "3", list.size(), fileName, msg, userId);
			throw new Exception(msg);
		}
		fileList.add(file);
		return fileList;
	}

	private void updateFirBatchLog(String status, String outMsg, String userId,BigDecimal oid) throws Exception {
		FirBatchLog firBatchLog = new FirBatchLog();
		firBatchLog.setOid(oid);
		policyPassbookService.updateFirBatchLog(status, outMsg, userId, firBatchLog);
	}
	
	@SuppressWarnings("unchecked")
	private String sendEmail(String batchNo,Date excuteTime,String logStatus,String errMsg,String programId) throws Exception {
		Mailer mailer = new Mailer();
		StringBuilder tmpMsg = new StringBuilder();
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
			sb.append("<p>本次處理明細如下：</p>");
			params.clear();
			params.put("batchNo", batchNo);
			Result mainResult = othBatchPassbookService.findOthBatchPassbookByParams(params);

			if (mainResult.getResObject() == null) {
				sb.append("<p>OTH_BATCH_PASSBOOK批次主檔查無資料，請洽系統人員。</p>");
				tmpMsg.append("OTH_BATCH_PASSBOOK批次主檔查無資料，請洽系統人員。");
			} else {
				sb.append("<table border=1 style='border-collapse: collapse;'>");
				sb.append("<tr bgcolor='#70bbd9'>");
				sb.append("<td>狀態</td>");
				sb.append("<td>報送保發筆數</td>");
				sb.append("<td>檔案名稱</td>");
				sb.append("<td>備註</td>");
				sb.append("</tr>");
				List<OthBatchPassbook> othBatchPassbookList = (List<OthBatchPassbook>) mainResult.getResObject();
				for (OthBatchPassbook main : othBatchPassbookList) {
					String status = "";
					switch (main.getStatus()) {
					case "0":
						status = "開始執行";
						break;
					case "1":
						status = "產生檔案成功";
						break;
					case "2":
						status = "傳送成功";
						break;
					case "3":
						status = "傳送失敗";
						break;
					case "4":
						status = "無資料";
						break;
					}
					sb.append("<tr>");
					sb.append("<td>" + status + "</td>");
					sb.append("<td>" + main.getQtyTii() + "</td>");
					sb.append("<td>" + main.getFilename() + "</td>");
					sb.append("<td>" + (StringUtil.isSpace(main.getRemark())?"":main.getRemark()) + "</td>");
					sb.append("</tr>");
				}
				sb.append("</table>");
			}
		} else if (logStatus.equals("N")) {
			sb.append("<p>執行狀態：無資料 </p>");
		} else {// status = "F"
			sb.append("<p>執行狀態：失敗</p>");
			sb.append("<p>異常訊息：" + StringUtil.nullToSpace(errMsg) + "</p>");
		}
		mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", "", mailTo, "",
				mailCc, "", "", "", sb.toString(), "smtp", "newims", "2012newims");
		return tmpMsg.toString();
	 }
	
	
	private void updateOthBatchPassbook(OthBatchPassbook entity, String status, Integer qtyTii, String filename,
			String remark, String userId) throws Exception {
		if(remark != null && remark.length() > 250) {
			remark = remark.substring(0, 250);
		}
		entity.setStatus(status==null?null:status);
		entity.setQtyTii(qtyTii==null?null:new BigDecimal(qtyTii));
		entity.setFilename(filename==null?null:filename);
		entity.setRemark(remark==null?null:entity.getRemark() + ";" + remark);
		entity.setIupdate(userId);
		entity.setDupdate(new Date());
		policyPassbookService.updateOthBatchPassbook(entity);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	private void  writeZip(String fileName,File file,String pswd) throws IOException {
		ZipFile zipFile = null;
		
		try {
			zipFile = new ZipFile(fileName + ".zip");
		} catch (ZipException e) {
			e.printStackTrace();
		}		
		ArrayList filesToAdd = new ArrayList();
		filesToAdd.add(file);
		ZipParameters parameters = new ZipParameters();
		parameters.setEncryptFiles(true);
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); 					
		parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
		parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
		parameters.setPassword(pswd);
		try {
			zipFile.addFiles(filesToAdd, parameters);
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}
	
	private boolean uploadZipFileToSftp(String filePath) throws Exception{
		boolean result = false;
		String sftpHost = configUtil.getString("tvisbkSftpHost");
		String sftpUser = configUtil.getString("tvisbkSftpUser");
		String sftpPwd = configUtil.getString("tvisbkSftpPwd");
		String sftpPort = configUtil.getString("tvisbkSftpPort");
		String remoteDir = configUtil.getString("tvisbkFileStartDir");
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

	public FirBatchInfoService getFirBatchInfoService() {
		return firBatchInfoService;
	}

	public void setFirBatchInfoService(FirBatchInfoService firBatchInfoService) {
		this.firBatchInfoService = firBatchInfoService;
	}

	public PolicyPassbookService getPolicyPassbookService() {
		return policyPassbookService;
	}

	public void setPolicyPassbookService(PolicyPassbookService policyPassbookService) {
		this.policyPassbookService = policyPassbookService;
	}

	public OthBatchPassbookService getOthBatchPassbookService() {
		return othBatchPassbookService;
	}

	public void setOthBatchPassbookService(OthBatchPassbookService othBatchPassbookService) {
		this.othBatchPassbookService = othBatchPassbookService;
	}

	public OthBatchPassbookListService getOthBatchPassbookListService() {
		return othBatchPassbookListService;
	}

	public void setOthBatchPassbookListService(OthBatchPassbookListService othBatchPassbookListService) {
		this.othBatchPassbookListService = othBatchPassbookListService;
	}

	public OthBatchPassbookKindService getOthBatchPassbookKindService() {
		return othBatchPassbookKindService;
	}

	public void setOthBatchPassbookKindService(OthBatchPassbookKindService othBatchPassbookKindService) {
		this.othBatchPassbookKindService = othBatchPassbookKindService;
	}
	
}
