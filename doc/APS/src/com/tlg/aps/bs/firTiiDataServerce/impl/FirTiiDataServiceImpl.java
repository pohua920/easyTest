package com.tlg.aps.bs.firTiiDataServerce.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firTiiDataServerce.AS400ToTiiDataService;
import com.tlg.aps.bs.firTiiDataServerce.FirTiiDataService;
import com.tlg.aps.bs.firTiiDataServerce.TiiDataProcessService;
import com.tlg.aps.vo.FirTiiDataVo;
import com.tlg.prpins.entity.FirBatchInfo;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.entity.FirBatchTii;
import com.tlg.prpins.entity.FirBatchTiiAddr;
import com.tlg.prpins.entity.FirBatchTiiList;
import com.tlg.prpins.service.FirBatchInfoService;
import com.tlg.prpins.service.FirBatchTiiAddrService;
import com.tlg.prpins.service.FirBatchTiiListService;
import com.tlg.prpins.service.FirBatchTiiService;
import com.tlg.prpins.service.FirSpService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Mailer;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.SftpUtil;
import com.tlg.util.StringUtil;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/** mantis：FIR0579，處理人員：BJ085，需求單編號：FIR0579 保發中心-住火保批資料產生排程規格 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.NEVER, readOnly = false, rollbackFor = Exception.class)
public class FirTiiDataServiceImpl implements FirTiiDataService {

	private static final Logger logger = Logger.getLogger(FirTiiDataServiceImpl.class);

	private FirBatchInfoService firBatchInfoService;
	private TiiDataProcessService tiiDataProcessService;
	private FirSpService firSpService;
	private FirBatchTiiListService firBatchTiiListService;
	private FirBatchTiiService firBatchTiiService;
	private FirBatchTiiAddrService firBatchTiiAddrService;
	private ConfigUtil configUtil;
	private AS400ToTiiDataService aS400ToTiiDataService;
	private SimpleDateFormat loggerSdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss SSS");

	/**
	 * 依傳入參數呼叫SP產生住火保單以及批單資料
	 * @param type 執行類型
	 * @param businessNo 單號(保單或批單號)
	 * @param undate 簽單日期
	 * @param userId 執行人員
	 * @param programId 程式代碼  FIR_BATCH_TII_01
	 * */
	@Override
	public Result callSpToGenData(String type, String businessNo, Date undate, 
			String userId, String programId) throws Exception {
		// 新增執行記錄檔、判斷排程是否可以執行
		StringBuilder sb = new StringBuilder();
		//手動執行排程檢核所有參數必填
		if(!"A".equals(type)) {
			if (StringUtil.isSpace(type)) {
				sb.append("執行類型無內容值。");
			}
			//AS400不需檢查簽單日，會寫入所有未執行過的資料
			if (!"AS400".equals(type) && null == undate) {
				sb.append("簽單日期無內容值。");
			}
			if("D".equals(type) && StringUtil.isSpace(businessNo)) {
				sb.append("執行保單刪除時，需傳入保單號。");
			}
		}
		
		String batchNo = "TII01_" + new SimpleDateFormat("yyMMddHHmmss").format(new Date());
		if (!StringUtil.isSpace(sb.toString())) {
			Result result = tiiDataProcessService.insertFirBatchLog(new Date(), userId, programId, "F", sb.toString(), batchNo);
			if (result.getResObject() != null) {
				FirBatchLog firBatchLog = (FirBatchLog) result.getResObject();
				String mailMsg = sendEmail(batchNo, "F", sb.toString(), programId);
				if (!StringUtil.isSpace(mailMsg)) {
					tiiDataProcessService.updateFirBatchLog("F", mailMsg, userId, firBatchLog);
				}
			}
			return this.getReturnResult(sb.toString());
		}
		
		String logStatus = "1";
		String tmpMsg = "";
		Map<String, String> params = new HashMap<>();
		params.put("prgId", programId + "_STATUS");
		Result result = firBatchInfoService.findFirBatchInfoByUK(params);
		if (result.getResObject() != null) {
			FirBatchInfo firBatchInfo = (FirBatchInfo) result.getResObject();
			if (firBatchInfo.getMailTo().equals("N")) {
				logStatus = "S";
				tmpMsg = "FIR_BATCH_INFO設定檔設定為排程暫停執行。";
			}
		}
		
		result = tiiDataProcessService.insertFirBatchLog(new Date(), userId, programId, logStatus, tmpMsg, batchNo);
		if (logStatus.equals("S")) {
			return this.getReturnResult(tmpMsg);
		}
		
		//呼叫SP產生新核心住火保批資料
		if (result.getResObject() != null) {
			FirBatchLog firBatchLog = (FirBatchLog) result.getResObject();
			BigDecimal batchLogOid = firBatchLog.getOid();

			int resultVal = 0;
			
			try {
				//呼叫產生保單資料SP
				if("A".equals(type) || "P".equals(type)) {
					resultVal = callSp(type, batchNo, userId,
							businessNo, undate, "policy");
					
					if (1 == resultVal) {
						logStatus = "F";
						tmpMsg = "執行SP_FIR_BATCH_TII_P產生保單失敗。";
						updateFirBatchLog(logStatus, "", userId, batchLogOid);
						String mailMsg = sendEmail(batchNo, logStatus, tmpMsg, programId);
						if (!StringUtil.isSpace(mailMsg)) {
							updateFirBatchLog(logStatus, tmpMsg+mailMsg, userId, batchLogOid);
						}
						return this.getReturnResult("執行SP_FIR_BATCH_TII_P產生保單失敗。");
					}
				}
				
				if("A".equals(type) || "E".equals(type)) {
					resultVal = callSp(type, batchNo, userId,
							businessNo, undate, "endorse");
					
					if (1 == resultVal) {
						logStatus = "F";
						tmpMsg = "執行SP_FIR_BATCH_TII_E產生批單失敗。";
						updateFirBatchLog(logStatus, tmpMsg, userId, batchLogOid);
						String mailMsg = sendEmail(batchNo, logStatus, tmpMsg, programId);
						if (!StringUtil.isSpace(mailMsg)) {
							updateFirBatchLog(logStatus, tmpMsg+mailMsg, userId, batchLogOid);
						}
						return this.getReturnResult("執行SP_FIR_BATCH_TII_E產生批單失敗。");
					}
				}
				
				if("D".equals(type)) {
					resultVal = callSp(type, batchNo, userId,
							businessNo, undate, "delete");
					if (1 == resultVal) {
						logStatus = "F";
						tmpMsg = "執行SP_FIR_BATCH_TII_D 失敗";
						updateFirBatchLog(logStatus, tmpMsg, userId, batchLogOid);
						String mailMsg = sendEmail(batchNo, logStatus, tmpMsg, programId);
						if (!StringUtil.isSpace(mailMsg)) {
							updateFirBatchLog(logStatus, tmpMsg+mailMsg, userId, batchLogOid);
						}
						return this.getReturnResult("執行SP_FIR_BATCH_TII_D 失敗。");
					}
				}
				
			}catch (Exception e) {
				logger.error("FirTiiData callSp error",e);
				logStatus = "F";
				updateFirBatchLog(logStatus, e.toString(), userId, batchLogOid);
				String mailMsg = sendEmail(batchNo, logStatus,  e.toString(), programId);
				if (!StringUtil.isSpace(mailMsg)) {
					updateFirBatchLog(logStatus, e.toString()+mailMsg, userId, batchLogOid);
				}
				return this.getReturnResult("執行失敗，批次序號："+batchNo+"，請查明錯誤原因。"+e.toString());
			}
			
			try {
				//呼叫AS400Service寫入400資料，傳入批次號、使用者ID，不需傳入簽單日
				if("A".equals(type) || "AS400".equals(type)) {
					aS400ToTiiDataService.insertFirBatchTiiListFromAs400(batchNo, userId);
				}
			}catch(Exception e) {
				logger.error("FirTiiData excuteAs400Service error", e);
				logStatus = "F";
				tmpMsg = "執行呼叫AS400資料寫入Service失敗";
				updateFirBatchLog(logStatus, tmpMsg+e.toString(), userId, batchLogOid);
				String mailMsg = sendEmail(batchNo, logStatus,  tmpMsg + e.toString(), programId);
				if (!StringUtil.isSpace(mailMsg)) {
					updateFirBatchLog(logStatus, e.toString()+mailMsg, userId, batchLogOid);
				}
				return this.getReturnResult(tmpMsg+":"+e.toString());
			}
			
			//確認本次執行結果
			params.clear();
			params.put("batchNoSp", batchNo);
			int count = firBatchTiiListService.countFirBatchTiiList(params);
			if(count == 0) {
				logStatus = "N";
			}else {
				logStatus = "S";
			}
			updateFirBatchLog(logStatus, "", userId, batchLogOid);
			String mailMsg = sendEmail(batchNo, logStatus, "", programId);
			if (!StringUtil.isSpace(mailMsg)) {
				updateFirBatchLog(logStatus, mailMsg, userId, batchLogOid);
			}
		}
		
		return this.getReturnResult("執行完成，批次序號："+batchNo+"。");
	}
	
	/**
	 * 依傳入參數產生實體檔案並傳送至保發
	 * @param userId 執行人員
	 * @param batchNo 批次號
	 * @param type 執行類型
	 * @param programId 程式代碼  FIR_BATCH_TII_02
	 * */

	@Override
	public Result generateFileAndUpload(String userId, String batchNo, String type, String programId) throws Exception{
		StringBuilder sb = new StringBuilder();
		// 手動執行需檢核傳入參數必填
		if("2".equals(type)) {
			if (StringUtil.isSpace(userId)) {
				sb.append("執行人員無內容值。");
			}
			if (StringUtil.isSpace(batchNo)) {
				sb.append("批次號碼無內容值。");
			}
		}else {
			batchNo = "TII02_" + new SimpleDateFormat("yyMMddHHmmss").format(new Date());
		}
		
		if (!StringUtil.isSpace(sb.toString())) {
			Result result = tiiDataProcessService.insertFirBatchLog(new Date(), userId, programId, "F", sb.toString(), batchNo);
			if (result.getResObject() != null) {
				FirBatchLog firBatchLog = (FirBatchLog) result.getResObject();
				String mailMsg = sendEmail(batchNo, "F", sb.toString(), programId);
				if (!StringUtil.isSpace(mailMsg)) {
					tiiDataProcessService.updateFirBatchLog("F", mailMsg, userId, firBatchLog);
				}
			}
			return this.getReturnResult(sb.toString());
		}
		
		String logStatus = "1";
		String tmpMsg = "";
		String remark = "";
		Map<String, String> params = new HashMap<>();
		params.put("prgId", programId + "_STATUS");
		Result result = firBatchInfoService.findFirBatchInfoByUK(params);
		if (result.getResObject() != null) {
			FirBatchInfo firBatchInfo = (FirBatchInfo) result.getResObject();
			if (firBatchInfo.getMailTo().equals("N")) {
				logStatus = "S";
				tmpMsg = "FIR_BATCH_INFO設定檔設定為排程暫停執行。";
			}
		}
		
		if("2".equals(type) && !"".equals(tmpMsg)) {
			tmpMsg = "上次BATCH_NO" + batchNo + ":" + tmpMsg;
		}
		
		result = tiiDataProcessService.insertFirBatchLog(new Date(), userId, programId, logStatus, tmpMsg, batchNo);
		
		FirBatchLog firBatchLog = (FirBatchLog) result.getResObject();
		BigDecimal batchLogOid = firBatchLog.getOid();
		
		if (logStatus.equals("S")) {
			return this.getReturnResult(tmpMsg);
		}
		
		//處理批次主檔
		FirBatchTii firBatchTii = new FirBatchTii();
		
		if("1".equals(type)) {//正常執行
			result = tiiDataProcessService.insertFirBatchTii(batchNo, userId);
			firBatchTii = (FirBatchTii)result.getResObject();
		}else if("2".equals(type)) {//重新執行
			params.clear();
			params.put("batchNo", batchNo);
			result = firBatchTiiService.findFirBatchTiiByUk(batchNo);
			if(null == result.getResObject()) {
				logStatus = "F";
				tmpMsg = "查無手動執行輸入之批次號碼資料!";
				updateFirBatchLog(logStatus, tmpMsg, userId, batchLogOid);
				return this.getReturnResult(tmpMsg);
			}
			firBatchTii = (FirBatchTii) result.getResObject();
			firBatchTii.setStatus("0");
			firBatchTii.setQtyTii(0);
			StringBuilder remarkSb = new StringBuilder();
			remarkSb.append(firBatchTii.getRemark() == null?"" : firBatchTii.getRemark()+ ";上次執行資訊:") ;
			remarkSb.append(firBatchTii.getIupdate() == null? "":firBatchTii.getIupdate() + "-");
			remarkSb.append(firBatchTii.getDupdate() == null? "":  new SimpleDateFormat("yyyy/MM/dd").format(firBatchTii.getDupdate()) + "-");
			remarkSb.append(firBatchTii.getQtyTii() == null? "": firBatchTii.getQtyTii() + "筆");
			remarkSb.append(firBatchTii.getFilenameTii() == null? "": "-檔名"+firBatchTii.getFilenameTii());
			remark = remarkSb.toString();
			firBatchTii.setRemark(remark);
			tiiDataProcessService.updateFirBatchTii(firBatchTii);
		}
		
		//產生檔案
		logger.info("FIR_BATCH_TII_02 genTxt data startTime:"+loggerSdf.format(new Date()));
		if("1".equals(type)) {
			params.clear();
			params.put("batchNoIsNull", "batchNoIsNull");
			logger.info("FIR_BATCH_TII_02 select FIR_BATCH_TII_LIST startTime:"+loggerSdf.format(new Date()));
			result = firBatchTiiListService.findFirBatchTiiListByParams(params);
			logger.info("FIR_BATCH_TII_02 select FIR_BATCH_TII_LIST endTime:"+loggerSdf.format(new Date()));
			if(result.getResObject() != null) {
				List<FirBatchTiiList> batchList = (List<FirBatchTiiList>) result.getResObject();
				for(FirBatchTiiList firBatchTiiList : batchList) {
					firBatchTiiList.setBatchNo(batchNo);
					tiiDataProcessService.updateFirBatchTiiList(firBatchTiiList);
				}
			}
		}
		String tmpStatus = "";
		int qty = 0;
		String filename = ""; 
		tmpMsg = "";
		
		params.clear();
		params.put("batchNo", batchNo);
		params.put("sortBy", "PROC_TYPE DESC, OID");
		result = firBatchTiiListService.findFirBatchTiiListByParams(params);
		if(result.getResObject() == null) {
			logStatus = "N";
			tmpStatus = "4";
			updateFirBatchTii(firBatchTii, tmpStatus, qty, filename, remark + tmpMsg, userId);
			updateFirBatchLog(logStatus, tmpMsg, userId, batchLogOid);
			String mailMsg = sendEmail(batchNo, logStatus, tmpMsg, programId);
			if (!StringUtil.isSpace(mailMsg)) {
				updateFirBatchLog(logStatus, mailMsg, userId, batchLogOid);
			}
			return this.getReturnResult("執行完成，FIR_BATCH_TII_LIST住火送保發保批資料中介檔無資料。");
		}
		
		File file = null;
		File filePath = new File(configUtil.getString("localFirTiiTmpPath"));
		List<FirBatchTiiList> batchList = (List<FirBatchTiiList>) result.getResObject();
		try {
			qty = batchList.size();
			filename = "18_" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "_tii_p5.txt";
			
			// 產生txt資料
			String fileContent = genTxtContent(batchList);
			
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			String outputFile = filePath + File.separator + filename;
			file = new File(outputFile);
			this.genTxtFile(file, fileContent);
		}catch (Exception e) {
			logger.error(e);
			tmpStatus = "3";
			logStatus = "F";
			tmpMsg = e.toString();
			if(file!=null) {
				this.deleteFile(file.getPath(), "", "");
			}
			//產生TXT失敗則將此次排程執行的批次號清空
			if("1".equals(type)) {
				for(FirBatchTiiList firBatchTiiList : batchList) {
					firBatchTiiList.setBatchNo(null);
					tiiDataProcessService.updateFirBatchTiiList(firBatchTiiList);
				}
			}
			updateFirBatchTii(firBatchTii, tmpStatus, qty, filename,  remark + tmpMsg, userId);
			updateFirBatchLog(logStatus, tmpMsg, userId, batchLogOid);
			String mailMsg = sendEmail(batchNo, logStatus, tmpMsg, programId);
			if (!StringUtil.isSpace(mailMsg)) {
				updateFirBatchLog(logStatus, tmpMsg + mailMsg, userId, batchLogOid);
			}
			return this.getReturnResult("txt產生失敗:" + tmpMsg);
		}
		logger.info("FIR_BATCH_TII_02 genTxt data endTime:"+loggerSdf.format(new Date()));
		
		tmpStatus = "1";
		Date sysdate = new Date();
		for(FirBatchTiiList firBatchTiiList : batchList) {
			firBatchTiiList.setIupdate(userId);
			firBatchTiiList.setDupdate(sysdate);
			tiiDataProcessService.updateFirBatchTiiList(firBatchTiiList);
		}
		updateFirBatchTii(firBatchTii, tmpStatus, qty, filename,  remark + tmpMsg, userId);
		
		// 壓縮檔案
		params.clear();
		params.put("prgId", "FIR_BATCH_TII_02_PSWD");
		result = firBatchInfoService.findFirBatchInfoByParams(params);
		List<FirBatchInfo> infoList = (List<FirBatchInfo>) result.getResObject();
		String pswd = infoList.get(0).getMailTo(); 
		
		params.put("prgId", "FIR_BATCH_TII_02_DELFILE");
		result = firBatchInfoService.findFirBatchInfoByParams(params);
		infoList = (List<FirBatchInfo>) result.getResObject();
		String delfile = infoList.get(0).getMailTo(); 
		
		String zipFilename = filename.substring(0, filename.indexOf(".")) + ".zip";
		String zipFilePath = filePath + File.separator + zipFilename;
		
		
		// 壓縮檔案並上傳至SFTP
		tmpStatus = "3";
		logStatus = "F";
		tmpMsg = "檔案上傳保發SFTP失敗!";
		try {
			this.writeZip(zipFilePath, file, pswd);
			if (uploadZipFileToSftp(zipFilePath)) {
				tmpStatus = "2";
				logStatus = "S";
				tmpMsg = "";
			}
		} catch (Exception e) {
			logger.error(e);
			//若上傳失敗應將檔案都刪除，重新執行批次號重送
			delfile = "Y";
			tmpMsg = tmpMsg + e.getMessage();
		}
		
		this.deleteFile(file.getPath(), zipFilePath, delfile);
		
		this.updateFirBatchTii(firBatchTii, tmpStatus, qty, filename,  remark + tmpMsg, userId);
		this.updateFirBatchLog(logStatus, tmpMsg, userId, batchLogOid);
		String mailMsg = sendEmail(batchNo, logStatus, tmpMsg, programId);
		if (!StringUtil.isSpace(mailMsg)) {
			updateFirBatchLog(logStatus, tmpMsg + mailMsg, userId, batchLogOid);
		}
		return this.getReturnResult("執行完成!");
	}
	
	private int callSp(String type, String batchNo, String userId,
			String businessNo, Date undate, String businessType) throws Exception {
		int outResult = 1;
		Map<String, Object> params = new HashMap<>();
		params.put("inBatchNoSp", batchNo);
		params.put("inUndate", undate);
		params.put("inUser", userId);
		
		if("P".equals(type)) {
			params.put("inPolicyno",businessNo);
		}
		
		if("E".equals(type)) {
			params.put("inEndorseno",businessNo);
		}
		
		if("D".equals(type)) {
			params.put("inPolicyno",businessNo);
			params.remove("inUndate");
		}
		
		if("policy".equals(businessType)) {
			outResult = firSpService.runSpFirBatchTiiP(params);
		}
		
		if("endorse".equals(businessType)) {
			outResult = firSpService.runSpFirBatchTiiE(params);
		}
		
		if("delete".equals(businessType)) {
			outResult = firSpService.runSpFirBatchTiiD(params);
		}
		return outResult;
	}
	
	private String genTxtContent(List<FirBatchTiiList> batchList) throws Exception {
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date sysdate = new Date();
		for (FirBatchTiiList tiiData : batchList) {
			String procType = tiiData.getProcType();
			String dataStatus = tiiData.getDataStatus();
			sb.append(procType).append("||");// 01保單種類
			sb.append(tiiData.getInscoCode()).append("||");// 02 保險公司代碼
			sb.append(tiiData.getBranchCode()).append("||");// 03 分支機構代號
			sb.append(StringUtil.nullToSpace(tiiData.getInscoCodeE())).append("||");// 04 批單公司代號
			sb.append(StringUtil.nullToSpace(tiiData.getBranchCodeE())).append("||");// 05 批單分支機構
			sb.append(StringUtil.nullToSpace(tiiData.getInsNo())).append("||");// 06 商品代碼
			sb.append(StringUtil.nullToSpace(tiiData.getInsName())).append("||");// 07 商品名稱
			sb.append(StringUtil.nullToSpace(tiiData.getPolicyno())).append("||");// 08 保單號碼
			String endorseno = "";
			if("E".equals(procType)) {
				endorseno = StringUtil.nullToSpace(tiiData.getEndorseno());
			}
			sb.append(endorseno).append("||");// 09 批單號碼
			sb.append(StringUtil.nullToSpace(tiiData.getOldpolicyno())).append("||");// 10 前期保單號碼
			sb.append(StringUtil.nullToSpace(tiiData.getInsuredIdOld())).append("||");// 11 被保險人證號(原)
			sb.append(tiiData.getStartdateOld() == null? "" : sdf.format(tiiData.getStartdateOld())).append("||");// 12 保單生效日(原)
			sb.append(tiiData.getSumamount()==null ? "0" : tiiData.getSumamount()).append("||");// 13 總保險金額
			sb.append(tiiData.getSumpremium()==null ? "0" : tiiData.getSumpremium()).append("||");// 14 總保險費
			sb.append(tiiData.getStartdate() == null? "" : sdf.format(tiiData.getStartdate())).append("||");// 15 保單生效日(新)
			sb.append(tiiData.getEnddate() == null? "" : sdf.format(tiiData.getEnddate())).append("||");// 16 保單到期日(新)
			sb.append(StringUtil.nullToSpace(tiiData.getPolicynoStatus())).append("||");// 17 保單狀態
			sb.append(tiiData.getAppliCount() == null ? "" : tiiData.getAppliCount()).append("||");// 18 要保人數量(新)
			sb.append(StringUtil.nullToSpace(tiiData.getApplicantId())).append("||");// 19 要保人證號(新)
			sb.append(StringUtil.nullToSpace(tiiData.getApplicantName())).append("||");// 20 要保人姓名(新)
			sb.append(StringUtil.nullToSpace(tiiData.getApplicantAddr())).append("||");// 21 要保人地址(新)
			sb.append(tiiData.getInsuredCount() == null ? "" : tiiData.getInsuredCount()).append("||");// 22 被保險人數量(新)
			sb.append(StringUtil.nullToSpace(tiiData.getInsuredId())).append("||");// 23 被保險人證號(新)
			sb.append(StringUtil.nullToSpace(tiiData.getInsuredName())).append("||");// 24 被保險人姓名(新)
			sb.append(StringUtil.nullToSpace(tiiData.getInsuredAddr())).append("||");// 25 被保險人地址(新)
			sb.append(tiiData.getBankCount() == null ? "" : tiiData.getBankCount()).append("||");// 26 抵押權銀行家數
			sb.append(StringUtil.nullToSpace(tiiData.getBankData())).append("||");// 27 抵押權銀行
			sb.append(StringUtil.nullToSpace(tiiData.getRemark()).replace("\r", "").replace("\n", "").trim()).append("||");// 28 備註
			sb.append(tiiData.getStartdateE() == null? "" : sdf.format(tiiData.getStartdateE())).append("||");// 29 批單生效日
			sb.append(tiiData.getEnddateE() == null? "" : sdf.format(tiiData.getEnddateE())).append("||");// 30 批單到期日
			
			String endorText = StringUtil.nullToSpace(tiiData.getEndorText());
			if(!StringUtil.isSpace(endorText)) {
				endorText = endorText.replace("\"", "").replace("\r", "").replace("\n", "").trim();
				if(endorText.length() > 490 ) {
					endorText = endorText.substring(0, 490);
				}
			}
			sb.append(endorText).append("||");// 31 批文
			sb.append(sdf.format(sysdate)).append("||");// 32 資料製送日期
			sb.append(tiiData.getDataStatus()).append("||");// 33 處理方式註記
			sb.append(tiiData.getOid()).append("||");// 34 識別碼
			sb.append(null == tiiData.getAddrCount()?"":tiiData.getAddrCount()).append("||");// 35 標的物筆數
			
			if("D".equals(dataStatus)) {
				sb.append("||||||||||||||||||||||||||");
			}else {
				Map<String,Object> params = new HashMap<>();
				if("1".equals(tiiData.getDataSource())) {//資料來源為1.新核心
					params.put("listOid", tiiData.getOid());
				}else {//資料來源為2.AS400
					params.put("as400Oid", tiiData.getAs400Oid());
				}
				params.put("sortBy", "OID");
				Result result = firBatchTiiAddrService.findFirBatchTiiAddrByParams(params);
				if(result.getResObject() != null) {
					List<FirBatchTiiAddr> addrList = (List<FirBatchTiiAddr>) result.getResObject();
					for(int i=0; i<addrList.size(); i++) {
						FirBatchTiiAddr addr = addrList.get(i);
						sb.append(StringUtil.nullToSpace(addr.getAddrDetail())).append("||");// 36 標的物地址
						sb.append(StringUtil.nullToSpace(addr.getWallNo())).append("||");// 37結構類別代號
						sb.append(transLength(StringUtil.nullToSpace(addr.getBuildyears()),3)).append("||");// 38 建築年份
						sb.append(transLength(addr.getBuildarea()==null?"":addr.getBuildarea().setScale(2, RoundingMode.HALF_UP)
								.toString().replace(".", ""),6)).append("||");// 39 面積(坪)
						sb.append(transLength(addr.getSumfloors()==null?"":addr.getSumfloors().toString(),3)).append("||");// 40 總樓層數
						sb.append(StringUtil.nullToSpace(addr.getStructure())).append("||");// 41 建築等級代號
						sb.append(StringUtil.nullToSpace(addr.getUsingnature())).append("||");// 42 使用性質代號
						sb.append(addr.getAmountF1() == null? "0" : addr.getAmountF1()).append("||");// 43 火險保額-建物
						sb.append(addr.getAmountF2() == null? "0" : addr.getAmountF2()).append("||");// 44 火險保額-建物及其動產
						sb.append(addr.getAmountOth() == null? "0" : addr.getAmountOth()).append("||");// 45 火險保額-建物以外
						sb.append(addr.getAmountQ() == null? "0" : addr.getAmountQ()).append("||");// 46 地震保額
						sb.append(addr.getPremF1() == null? "0" : addr.getPremF1()).append("||");// 47 火險保費
						sb.append(addr.getPremQ() == null? "0" : addr.getPremQ()).append("||");// 48 地震保費
						sb.append(addr.getPremOth() == null? "0" : addr.getPremOth());// 49 附加險保費
						if(i < addrList.size()-1) {
							sb.append("||");
						}
					}
				}
			}
			sb.append("\r\n");
		}
		return sb.toString();
	}
	
	public static String transLength(String str, int length) throws Exception {
		StringBuilder sb = new StringBuilder();
		int strLength = str.length();
		if(strLength > 0) {
			//數字長度不足左邊補0
			while(strLength<length) {
				sb.append("0");
				strLength ++ ;
			}
			sb.append(str);
		}
		return sb.toString();
	}
	
	private void genTxtFile(File file, String content) throws IOException {
		try (BufferedWriter bufWriter = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8))) {
			bufWriter.write(content);
		}
	}
	
	private void deleteFile(String txtFilePath, String zipFilePath, String delfile) throws IOException {
		File txtFile = new File(txtFilePath);
		File zipFile = new File(zipFilePath);
		if (txtFile.exists())
			FileUtils.forceDelete(txtFile);
		//依據設定檔設定值，判斷是否刪除Server上的zip檔案
		if ("Y".equals(delfile) && zipFile.exists())
			FileUtils.forceDelete(zipFile);
	}
	
	private void writeZip(String filePath, File file, String pswd) throws Exception {
		ZipFile zipFile = null;
		zipFile = new ZipFile(filePath);
		ArrayList<File> filesToAdd = new ArrayList<>();
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
	
	private boolean uploadZipFileToSftp(String filePath) throws Exception {
		boolean result = false;
		String sftpHost = configUtil.getString("tvrfisSftpHost");
		String sftpUser = configUtil.getString("tvrfisSftpUser");
		String sftpPwd = configUtil.getString("tvrfisSftpPwd");
		String sftpPort = configUtil.getString("tvrfisSftpPort");
		String remoteDir = configUtil.getString("tvrfisFileStartDir");
		SftpUtil sftpUtil = new SftpUtil(sftpHost, Integer.valueOf(sftpPort), sftpUser, sftpPwd);

		String strResult = sftpUtil.putFileToSftp2(remoteDir, filePath);
		if ("success".equals(strResult)) {
			result = true;
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private String sendEmail(String batchNo, String logStatus, String tmpMsg, String programId) {
		//依傳入程式代碼區分寄送不同EMAIL
		String program = programId.substring(14);
		
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
			if ("S".equals(logStatus) && "01".equals(program)) {
				sb.append("<p>執行狀態：完成</p>");
				params.clear();
				params.put("batchNoSp", batchNo);
				result = firBatchTiiListService.findForCountProcTypeByParams(params);

				if (result.getResObject() != null) {
					sb.append("<table border=1 style='border-collapse: collapse;'>");
					sb.append("<tr bgcolor='#70bbd9'>");
					sb.append("<td width='300px'>類型</td>");
					sb.append("<td width='300px'>筆數</td>");
					sb.append("</tr>");
					List<FirTiiDataVo> procTypeList = (List<FirTiiDataVo>) result.getResObject();
					for (FirTiiDataVo procTypeVo : procTypeList) {
						sb.append("<tr>");
						String procType = "";
						if("P".equals(procTypeVo.getProcType())){
							procType = "保單";
						}else if("E".equals(procTypeVo.getProcType())) {
							procType = "批單";
						}
						sb.append("<td>" + procType + "</td>");
						sb.append("<td>" + procTypeVo.getNrec() + "</td>");
						sb.append("</tr>");
					}
					sb.append("</table>");
				}
			} else if("S".equals(logStatus) && "02".equals(program)) {
				sb.append("<p>執行狀態：完成</p>");
				result = firBatchTiiService.findFirBatchTiiByUk(batchNo);
				if (result.getResObject() != null) {
					sb.append("<table border=1 style='border-collapse: collapse;'>");
					sb.append("<tr bgcolor='#70bbd9'>");
					sb.append("<td width='300px'>狀態</td>");
					sb.append("<td width='300px'>筆數</td>");
					sb.append("<td width='300px'>檔名</td>");
					sb.append("</tr>");
					FirBatchTii firBatchTii = (FirBatchTii) result.getResObject();
					sb.append("<tr>");
					String status = "";
					switch (firBatchTii.getStatus()) {
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
					sb.append("<td>" + status + "</td>");
					sb.append("<td>" + firBatchTii.getQtyTii() + "</td>");
					sb.append("<td>" + firBatchTii.getFilenameTii() + "</td>");
					sb.append("</tr>");
					sb.append("</table>");
				}
			} else if ("N".equals(logStatus)) {
				sb.append("<p>執行狀態：無資料 </p>");
			} else {
				sb.append("<p>執行狀態：" + logStatus + "</p>");
				sb.append("<p>異常訊息：" + StringUtil.nullToSpace(tmpMsg) + "</p>");
			}
			mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", "", mailTo,
					"", mailCc, "", "", "", sb.toString(), "smtp", "newims", "2012newims");
		} catch (Exception e) {
			logger.error("FirTiiDataService sendEmail error", e);
		}
		return "";
	}
	
	//更新執行紀錄檔
	private void updateFirBatchLog(String logStatus, String tmpMsg, String userId, BigDecimal oid) throws Exception {
		FirBatchLog firBatchLog = new FirBatchLog();
		firBatchLog.setOid(oid);
		tiiDataProcessService.updateFirBatchLog(logStatus, tmpMsg, userId, firBatchLog);
	}
	
	//更新批次主檔
	private void updateFirBatchTii(FirBatchTii firBatchTii, String tmpStatus, int qty, String filename, String tmpMsg,
			String userId) throws Exception {
		firBatchTii.setStatus(tmpStatus);
		firBatchTii.setQtyTii(qty);
		firBatchTii.setFilenameTii(filename);
		firBatchTii.setRemark(tmpMsg);
		firBatchTii.setIupdate(userId);
		firBatchTii.setDupdate(new Date());
		tiiDataProcessService.updateFirBatchTii(firBatchTii);
	}

	private Result getReturnResult(String msg) {
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

	public TiiDataProcessService getTiiDataProcessService() {
		return tiiDataProcessService;
	}

	public void setTiiDataProcessService(TiiDataProcessService tiiDataProcessService) {
		this.tiiDataProcessService = tiiDataProcessService;
	}

	public FirSpService getFirSpService() {
		return firSpService;
	}

	public void setFirSpService(FirSpService firSpService) {
		this.firSpService = firSpService;
	}

	public FirBatchTiiListService getFirBatchTiiListService() {
		return firBatchTiiListService;
	}

	public void setFirBatchTiiListService(FirBatchTiiListService firBatchTiiListService) {
		this.firBatchTiiListService = firBatchTiiListService;
	}

	public FirBatchTiiService getFirBatchTiiService() {
		return firBatchTiiService;
	}

	public void setFirBatchTiiService(FirBatchTiiService firBatchTiiService) {
		this.firBatchTiiService = firBatchTiiService;
	}

	public FirBatchTiiAddrService getFirBatchTiiAddrService() {
		return firBatchTiiAddrService;
	}

	public void setFirBatchTiiAddrService(FirBatchTiiAddrService firBatchTiiAddrService) {
		this.firBatchTiiAddrService = firBatchTiiAddrService;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public AS400ToTiiDataService getaS400ToTiiDataService() {
		return aS400ToTiiDataService;
	}

	public void setaS400ToTiiDataService(AS400ToTiiDataService aS400ToTiiDataService) {
		this.aS400ToTiiDataService = aS400ToTiiDataService;
	}
}
