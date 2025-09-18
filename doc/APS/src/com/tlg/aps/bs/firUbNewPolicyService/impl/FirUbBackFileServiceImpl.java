package com.tlg.aps.bs.firUbNewPolicyService.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
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

import com.tlg.aps.bs.firUbNewPolicyService.FirUbBackFileService;
import com.tlg.aps.bs.firUbNewPolicyService.UbNewPolicyProcessService;
import com.tlg.aps.vo.FileUploadResponseVo;
import com.tlg.aps.vo.FirUbProcessVo;
import com.tlg.dms.entity.PrpdNewCode;
import com.tlg.dms.service.PrpdNewCodeService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtBatchMain;
import com.tlg.prpins.entity.FirAgtUb02;
import com.tlg.prpins.entity.FirBatchInfo;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.entity.Prpcmainprop;
import com.tlg.prpins.service.FirAgtBatchDtlService;
import com.tlg.prpins.service.FirAgtBatchMainService;
import com.tlg.prpins.service.FirBatchInfoService;
import com.tlg.prpins.service.FirSpService;
import com.tlg.prpins.service.PrpcmainpropService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.DateUtils;
import com.tlg.util.FtsUtil;
import com.tlg.util.Mailer;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.SftpUtil;
import com.tlg.util.StringUtil;
import com.tlg.xchg.entity.Rfrcode;
import com.tlg.xchg.service.RfrcodeService;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/** mantis：FIR0546，處理人員：BJ085，需求單編號：FIR0546 火險_聯邦新件_APS回饋檔產生排程 **/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirUbBackFileServiceImpl implements FirUbBackFileService {

	private static final Logger logger = Logger.getLogger(FirUbBackFileServiceImpl.class);
	private ConfigUtil configUtil;
	private FirBatchInfoService firBatchInfoService;

	private UbNewPolicyProcessService ubNewPolicyProcessService;
	private FirSpService firSpService;
	private FirAgtBatchMainService firAgtBatchMainService;
	private FirAgtBatchDtlService firAgtBatchDtlService;
	private RfrcodeService rfrcodeService;
	private PrpcmainpropService prpcmainpropService;
	private PrpdNewCodeService prpdNewCodeService;

	@Override
	public Result runToReceiveData(String userId, Date excuteTime, String programId, String type)
			throws SystemException, Exception {
		// 新增執行記錄檔、判斷排程是否可以執行
		StringBuilder sb = new StringBuilder();
		if (StringUtil.isSpace(userId)) {
			sb.append("執行人員無內容值。");
		}
		if (excuteTime == null) {
			sb.append("轉檔時間無內容值。");
		}
		if (StringUtil.isSpace(programId)) {
			sb.append("程式代碼無內容值。");
		}
		String mailMsg = "";
		String batchNo = programId.substring(8, 10) + "02_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		if (!StringUtil.isSpace(sb.toString())) {
			Result result = ubNewPolicyProcessService.insertFirBatchLog(excuteTime, userId, programId, "F",
					sb.toString(), batchNo);
			if (result.getResObject() != null) {
				FirBatchLog firBatchLog = (FirBatchLog) result.getResObject();
				mailMsg = sendEmail(firBatchLog.getBatchNo(), excuteTime, "F", sb.toString(), programId, "", "");
				if (!StringUtil.isSpace(mailMsg)) {
					ubNewPolicyProcessService.updateFirBatchLog("F", mailMsg, userId, firBatchLog);
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
			/*mantis：FIR0627，處理人員：BJ085，需求單編號：FIR0627 火險_聯邦新件_APS回饋檔產生排程_排程執行判斷是否為假日 start*/
			}else {
				String sysdate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
				params.clear();
				params.put("codetype", "Workday");
				params.put("codecode", sysdate);
				result = prpdNewCodeService.findPrpdNewCodeByParams(params);
				if(result.getResObject()!=null) {//查詢到資料表示為假日，不執行排程
					status = "S";
					msg = "設定檔查詢結果為假日不傳送回饋檔。";
				}
				/*mantis：FIR0627，處理人員：BJ085，需求單編號：FIR0627 火險_聯邦新件_APS回饋檔產生排程_排程執行判斷是否為假日 end*/
			}
		}
		result = ubNewPolicyProcessService.insertFirBatchLog(excuteTime, userId, programId, status, msg, batchNo);
		
		if (status.equals("S")) {
			//mantis：FIR0627，處理人員：BJ085，需求單編號：FIR0627 火險_聯邦新件_APS回饋檔產生排程_排程執行判斷是否為假日
			return this.getReturnResult(msg);
		}
		if (status.equals("F")) {
			return this.getReturnResult(msg);
		}


		String fileQty = "";
		String filePqty = "";
		FirBatchLog firBatchLog = null;
		if (result.getResObject() != null) {
			firBatchLog = (FirBatchLog) result.getResObject();
			// 呼叫sp
			Map<String, String> returnData = callSp(firBatchLog.getBatchNo(), userId, programId, type);
			status = returnData.get("status");
			msg = returnData.get("msg");
			if("S".equals(status)) {
				fileQty = returnData.get("fileQty");
				filePqty = returnData.get("filePqty");
			}
			updateFirBatchLog(status, msg, userId, firBatchLog.getOid());

			mailMsg = sendEmail(batchNo, excuteTime, status, msg, programId, fileQty, filePqty);
			if (!StringUtil.isSpace(mailMsg)) {
				ubNewPolicyProcessService.updateFirBatchLog(status, msg + mailMsg, userId, firBatchLog);
			}
		}
		return this.getReturnResult("執行完成");
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, String> generateFile(String batchNo, String userId, String programId, String type)
			throws SystemException, Exception {
		StringBuilder sb = new StringBuilder();
		Map<String, Object> params = new HashMap<>();
		Map<String, String> returnData = new HashMap<>();
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
		if (!StringUtil.isSpace(sb.toString())) {
			returnData.put("status", "F");
			returnData.put("msg", "傳入參數異常:" + sb.toString());
			return returnData;
		}

		String fileStatus = "Y";
		params.put("batchNo", batchNo);
		params.put("businessnature", "I99080");
		Result result = firAgtBatchMainService.findFirAgtBatchMainByParams(params);
		List<FirAgtBatchMain> list = new ArrayList<>();
		FirAgtBatchMain firAgtBatchMain = null;
		if (result.getResObject() != null) {
			list = (List<FirAgtBatchMain>) result.getResObject();
			if (!list.isEmpty()) {
				firAgtBatchMain = list.get(0);
				if (firAgtBatchMain.getFilePqty() == null || firAgtBatchMain.getFilePqty() == 0) {
					fileStatus = "Z";
					returnData.put("status", "N");
					returnData.put("msg", "");
					this.updateFirAgtBatch(firAgtBatchMain, fileStatus, null, null, userId);
					return returnData;
				}
			}
		} else {
			returnData.put("status", "F");
			returnData.put("msg", batchNo + "批號查無對應資料。");
			return returnData;
		}

		// 若接收參數.執行類型 = '2'重新執行，需將前次的執行結果寫入REMARK欄位，避免執行完成後資料覆蓋。
		if ("2".equals(type)) {
			String remark = StringUtil.nullToSpace(firAgtBatchMain.getRemark()) + ";" + "上次執行資訊："
					+ firAgtBatchMain.getIupdate() + "-"
					+ DateUtils.format(firAgtBatchMain.getDupdate(), "yyyy/MM/dd HH:mm:ss") + "-檔名："
					+ firAgtBatchMain.getFileName();
			ubNewPolicyProcessService.updateFirAgtBatchMain(firAgtBatchMain, remark, userId);
		}

		String fileContent = "";
		String fileName = "";
		File file = new File("");

		// 產生txt資料
		fileName = "0045CH" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".txt";
		fileContent = genTxtData(batchNo);

		// 產生txt
		File filePath = new File(configUtil.getString("tempFolder"));
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		String outputFile = filePath + File.separator + fileName;
		file = new File(outputFile);
		String msg = this.genTxtFile(file, fileContent);
		if (!StringUtil.isSpace(msg)) {
			fileStatus = "E";
			this.deleteFile(file.getPath(), "");
			returnData.put("status", "F");
			returnData.put("msg", "txt產生失敗:" + msg);
			this.updateFirAgtBatch(firAgtBatchMain, fileStatus, null, "txt產生失敗:" + msg, userId);
			return returnData;
		}

		// 壓縮檔案
		String pswd = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String zipFileName = fileName.substring(0, fileName.indexOf(".")) + ".zip";
		String zipFilePath = filePath + File.separator + zipFileName;
		msg = this.writeZip(zipFilePath, file, pswd);
		if (!StringUtil.isSpace(msg)) {
			fileStatus = "E";
			this.deleteFile(file.getPath(), zipFilePath);
			returnData.put("status", "F");
			returnData.put("msg", "壓縮檔案失敗:" + msg);
			this.updateFirAgtBatch(firAgtBatchMain, fileStatus, null, "壓縮檔案失敗::" + msg, userId);
			return returnData;
		}

		// 上傳檔案至FTS
		FtsUtil ftsutil = new FtsUtil(configUtil.getString("ftsUrl"));
		FileUploadResponseVo fileUploadResponseVo = ftsutil.uploadFile(zipFilePath, userId, "F", batchNo);
		if (fileUploadResponseVo.getStatus().equals("N")) {
			fileStatus = "E";
			this.deleteFile(file.getPath(), zipFilePath);
			returnData.put("status", "F");
			returnData.put("msg", "上傳FTS失敗:" + fileUploadResponseVo.getMessage());
			this.updateFirAgtBatch(firAgtBatchMain, "E", null, "上傳FTS失敗:" + fileUploadResponseVo.getMessage(), userId);
			return returnData;
		}

		// 上傳檔案至SFTP
		try {
			returnData.put("status", "S");
			returnData.put("msg", "");
			if (!uploadZipFileToSftp(zipFilePath)) {
				returnData.put("status", "F");
				returnData.put("msg", "傳送SFTP失敗:");
				fileStatus = "E";
			}

			this.deleteFile(file.getPath(), zipFilePath);
			this.updateFirAgtBatch(firAgtBatchMain, fileStatus, zipFileName, null, userId);
			return returnData;
		} catch (Exception e) {
			fileStatus = "E";
			logger.info(e.toString());
			this.deleteFile(file.getPath(), zipFilePath);
			returnData.put("status", "F");
			returnData.put("msg", "傳送SFTP失敗;" + e.getMessage());
			this.updateFirAgtBatch(firAgtBatchMain, fileStatus, zipFileName, "傳送SFTP失敗:" + e.getMessage(), userId);
			return returnData;
		}
	}

	private Map<String, String> callSp(String batchNo, String userId, String programId, String type) {
		FirAgtBatchMain firAgtBatchMain = new FirAgtBatchMain();
		Map<String, String> returnData = new HashMap<>();
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("inBatchNo", batchNo);
			params.put("inUser", userId);
			params.put("outResult", null);
			int returnValue = firSpService.runSpFirAgtUb02(params);
			if (returnValue != 0) {
				returnData.put("status", "F");
				returnData.put("msg", "執行SP失敗(SP_FIR_AGT_UB_02)");
				return returnData;
			}

			params.clear();
			params.put("batchNo", batchNo);
			Result result = firAgtBatchMainService.findFirAgtBatchMainByUk(params);
			if (result.getResObject() != null) {
				firAgtBatchMain = (FirAgtBatchMain) result.getResObject();
				if (firAgtBatchMain.getFileStatus().equals("Z")) {
					returnData.put("status", "N");
				} else {
					// 需求項目2.實體檔案產生
					returnData = generateFile(batchNo, userId, programId, type);
					returnData.put("fileQty", firAgtBatchMain.getFileQty().toString());
					returnData.put("filePqty", firAgtBatchMain.getFilePqty().toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}

	private String genTxtData(String batchNo) throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("batchNo", batchNo);
		Result result = firAgtBatchDtlService.findForUbBackFile(params);
		StringBuilder sb = new StringBuilder();
		//先插入表頭行
		sb.append("保險公司代號\t").append("保單號碼\t").append("受理編號\t").append("批單號碼\t").append("險種代號\t")
		.append("資料日\t").append("生效日\t").append("到期日\t").append("出單日\t").append("受理狀況\t").append("保單狀態\t")
		.append("批改內容\t").append("是否續保件\t").append("保費(地震)\t").append("保費(火險)\t").append("附加險保費\t").append("總保費\t")
		.append("保額(地震)\t").append("保額(火險)\t").append("佣金率(地震)\t").append("佣金率(火險)\t").append("佣金(地震)\t").append("佣金(火險)\t")
		.append("總佣金\t").append("服務費\t").append("銷售行員登錄字號\t").append("是否自動續保\t").append("前一保單號碼\t")
		.append("前一保單到期日\t").append("建築物總樓層數\t").append("主要建材代號\t").append("主要建材代號其它內容\t").append("屋頂材料代號\t")
		.append("屋頂材料代號其它內容\t").append("標的物地址/營業處所\t").append("抵押權人代號(金資代碼)\t").append("業務屬性\t").append("要保人姓名\t")
		.append("要保人ID\t").append("被保險人姓名\t").append("被保險人ID\t").append("\r\n");
		
		if (result.getResObject() != null) {
			@SuppressWarnings("unchecked")
			List<FirAgtUb02> list = (List<FirAgtUb02>) result.getResObject();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			for (FirAgtUb02 ub : list) {
				sb.append("0045").append("\t");// 01 保險公司代號:固定值：0045
				sb.append(ub.getPolicyno()).append("\t");// 02 保單號碼
				sb.append(ub.getOrderseq()).append("\t");// 03 受理編號
				sb.append(StringUtil.nullToSpace(ub.getEndorseno())).append("\t");// 04 批單號碼
				sb.append(ub.getRiskcodeUb()).append("\t");// 05 險種代碼
				sb.append(sdf.format(ub.getDataDate())).append("\t");// 06 資料日
				sb.append(sdf.format(ub.getStartdate())).append("\t");// 07 生效日
				sb.append(sdf.format(ub.getEnddate())).append("\t");// 08 到期日
				sb.append(sdf.format(ub.getUnderwriteenddate())).append("\t");// 09 簽單日
				sb.append(ub.getOrderType()).append("\t");// 10 受理狀況
				sb.append(ub.getPolicyStatus()).append("\t");// 11 保單狀況
				sb.append(changeEndortext(ub.getEndorsetext())).append("\t");// 12 批改內容
				sb.append(ub.getIsRenew()).append("\t");// 13 是否續保件
				sb.append(ub.getPremiumQ() == null ? "" : ub.getPremiumQ()).append("\t");// 14 保費(地震)
				sb.append(ub.getPremiumF() == null ? "" : ub.getPremiumF()).append("\t");// 15 保費(火險)
				sb.append(ub.getPremiumA() == null ? "" : ub.getPremiumA()).append("\t");// 16 保費(附加險)
				sb.append(ub.getPremiumT() == null ? "" : ub.getPremiumT()).append("\t");// 17 總保費
				sb.append(ub.getAmountQ() == null ? "" : ub.getAmountQ()).append("\t");// 18 保額(地震)
				sb.append(ub.getAmountF() == null ? "" : ub.getAmountF()).append("\t");// 19 保額(火險)
				sb.append(ub.getCommRateQ() == null ? "" : ub.getCommRateQ()).append("\t");// 20 佣金率(地震)
				sb.append(ub.getCommRateF() == null ? "" : ub.getCommRateF()).append("\t");// 21 佣金率(火險)
				sb.append(ub.getCommissionQ() == null ? "" : ub.getCommissionQ()).append("\t");// 22 佣金(地震)
				sb.append(ub.getCommissionF() == null ? "" : ub.getCommissionF()).append("\t");// 23 佣金(火險)
				sb.append(ub.getCommission() == null ? "" : ub.getCommission()).append("\t");// 24 總佣金
				sb.append(ub.getServiceCharge() == null ? "" : ub.getServiceCharge()).append("\t");// 25 服務費
				sb.append(StringUtil.nullToSpace(ub.getSalesNo())).append("\t");// 26 銷售行員登錄字號
				sb.append(StringUtil.nullToSpace(ub.getIsAutoRenew())).append("\t");// 27 是否自動續保
				sb.append(StringUtil.nullToSpace(ub.getOldPolicyno())).append("\t");// 28 前一保單號碼
				sb.append(ub.getOldEnddate() == null ? "" : sdf.format(ub.getOldEnddate())).append("\t");// 29 前一保單到期日
				sb.append(ub.getSumfloors() == null ? "" : ub.getSumfloors()).append("\t");// 30 總樓層數
				
				Map<String,String> strutsMap = getStruts(ub.getWallno(), ub.getPolicyno(), "Wall");
				String ubCode = strutsMap.get("ubCode");
				String buildInfo = strutsMap.get("buildInfo");
				sb.append(StringUtil.nullToSpace(ubCode)).append("\t");// 31 外牆
				sb.append(StringUtil.nullToSpace(buildInfo)).append("\t");// 32 外牆其他說明
				
				strutsMap = getStruts(ub.getRoofno(), ub.getPolicyno(), "Roof");
				ubCode = strutsMap.get("ubCode");
				buildInfo = strutsMap.get("buildInfo");
				sb.append(StringUtil.nullToSpace(ubCode)).append("\t");// 33 屋頂
				sb.append(StringUtil.nullToSpace(buildInfo)).append("\t");// 34 屋頂其他說明
				
				sb.append(StringUtil.nullToSpace(ub.getAddress())).append("\t");// 35 標的物地址/營業處所
				sb.append(StringUtil.nullToSpace(ub.getMortgageepcode1())).append("\t");// 36 抵押權人
				sb.append(StringUtil.nullToSpace(ub.getUbSource())).append("\t");// 37 業務屬性
				if (!StringUtil.isSpace(ub.getApplyName()) && ub.getApplyName().length() > 50) {// 38 要保人姓名
					sb.append(ub.getApplyName().substring(0, 50)).append("\t");
				} else {
					sb.append(StringUtil.nullToSpace(ub.getApplyName())).append("\t");
				}
				sb.append(ub.getApplyId()).append("\t");// 39 要保人ID
				if (!StringUtil.isSpace(ub.getInsuredName()) && ub.getInsuredName().length() > 50) {// 40 被保險人姓名
					sb.append(ub.getInsuredName().substring(0, 50)).append("\t");
				} else {
					sb.append(StringUtil.nullToSpace(ub.getInsuredName())).append("\t");
				}
				sb.append(ub.getInsuredId()).append("\t");// 41 被保險人ID
				sb.append("\r\n");
			}
		}
		return sb.toString();
	}
	
	/**取得聯邦建材對應代碼，若為Z其他需一併取得建材中文說明*
	 * @param strutsno 核心建築代碼
	 * @param policyno 核心保單號
	 * @param type 外牆Wall 屋頂Roof
	 * @return strutsMap 
	 * @throws SystemException
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private Map<String,String> getStruts(String strutsno, String policyno, String type) throws SystemException, Exception {
		String rfrCodeType = type + "No_UB";
		String coreCodeType = type + "Material";
		Map<String,String> returnMap = new HashMap<>();
		
		Map<String,String> params = new HashMap<>();
		params.put("codetype", rfrCodeType);
		Result result = rfrcodeService.findRfrcodeByParams(params);
		String ubCode = "";
		List<Rfrcode> ubCodeList =  (List<Rfrcode>)result.getResObject();
		for(Rfrcode rfrcode : ubCodeList) {
			if(strutsno.equals(rfrcode.getMappedcode())) {
				ubCode = rfrcode.getCodecode();
				break;
			}
		}
		
		if(StringUtil.isSpace(ubCode)) {
			returnMap.put("ubCode", "Z");
			params.clear();
			params.put("codetype", coreCodeType);
			params.put("codecode", strutsno);
			
			result = prpdNewCodeService.findPrpdNewCodeByParams(params);
			if(result.getResObject() != null) {
				List<PrpdNewCode> newCodeList =  (List<PrpdNewCode>)result.getResObject();
				returnMap.put("buildInfo", newCodeList.get(0).getCodecname());
			}
			return returnMap;
		}
		
		returnMap.put("ubCode", ubCode);
		if("Z".equals(ubCode)) {
			params.clear();
			params.put("policyno", policyno);
			result = prpcmainpropService.findPrpcmainpropByParams(params);
			if(result.getResObject()!=null) {
				List<Prpcmainprop> propList =  (List<Prpcmainprop>) result.getResObject();
				String buildInfo = propList.get(0).getBuilddetailinfo();
				if(!StringUtil.isSpace(buildInfo)) {
					if(buildInfo.contains("-")) {
						buildInfo = buildInfo.substring(0,buildInfo.indexOf("-"));
					}					
					returnMap.put("buildInfo", buildInfo.trim());
				}
			}
		}
		return returnMap;
	}

	private String changeEndortext(String endortext) throws Exception {
		if(StringUtil.isSpace(endortext)) {
			return "";
		}
		endortext = endortext.replace("\r\n", "");
		if(endortext.length() > 200) {
			endortext = endortext.substring(0, 200);
		}
		
		return endortext;
	}
	
	private String genTxtFile(File file, String content) {
		try (BufferedWriter bufWriter = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8))) {
			bufWriter.write(content);
		} catch (IOException e) {
			e.printStackTrace();
			return e.toString();
		}
		return "";
	}

	private String writeZip(String filePath, File file, String pswd) {
		ZipFile zipFile = null;
		try {
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
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
		return "";
	}

	private boolean uploadZipFileToSftp(String filePath) throws Exception {
		boolean result = false;
		String sftpHost = configUtil.getString("ubSFTP");
		String sftpUser = configUtil.getString("ubSftpUserGet");
		String sftpPwd = configUtil.getString("ubSfptPwdGet");
		String sftpPort = configUtil.getString("ubSftpPort");
		String remoteDir = configUtil.getString("ubDowloadPath");
		SftpUtil sftpUtil = new SftpUtil(sftpHost, Integer.valueOf(sftpPort), sftpUser, sftpPwd);

		String strResult = sftpUtil.putFileToSftp2(remoteDir, filePath);
		if ("success".equals(strResult)) {
			result = true;
		}
		return result;
	}

	private void deleteFile(String txtFilePath, String zipFilePath) throws IOException {
		File txtFile = new File(txtFilePath);
		File zipFile = new File(zipFilePath);
		if (txtFile.exists())
			FileUtils.forceDelete(txtFile);
		if (zipFile.exists())
			FileUtils.forceDelete(zipFile);
	}

	private void updateFirAgtBatch(FirAgtBatchMain firAgtBatchMain, String fileStatus, String filename, String remark,
			String userId) throws Exception {
		// FirAgtBatchMain處理
		if (filename != null)
			firAgtBatchMain.setFileName(filename);
		if (!StringUtil.isSpace(remark)) {
			remark = StringUtil.isSpace(firAgtBatchMain.getRemark()) ? remark
					: firAgtBatchMain.getRemark() + ";" + remark;
			if (!StringUtil.isSpace(remark) && remark.length() > 300) {
				remark = remark.substring(remark.length() - 300 < 0 ? 0 : remark.length() - 300, remark.length());
			}
			firAgtBatchMain.setRemark(remark);
		}
		firAgtBatchMain.setFileStatus(fileStatus);
		firAgtBatchMain.setIupdate(userId);
		firAgtBatchMain.setDupdate(new Date());

		// FirAgtBatchDtl處理
		Map<String, Object> params = null;
		if ("Y".equals(fileStatus)) {
			params = new HashMap<>();
			params.put("batchNo", firAgtBatchMain.getBatchNo());
			params.put("orderseqStatus", "02");
			params.put("iupdate", userId);
			params.put("dupdate", new Date());
		}
		ubNewPolicyProcessService.updateFirAgtBatch(firAgtBatchMain, params);
	}

	private void updateFirBatchLog(String status, String outMsg, String userId, BigDecimal oid) throws Exception {
		FirBatchLog firBatchLog = new FirBatchLog();
		firBatchLog.setOid(oid);
		ubNewPolicyProcessService.updateFirBatchLog(status, outMsg, userId, firBatchLog);
	}

	@SuppressWarnings("unchecked")
	private String sendEmail(String batchNo, Date excuteTime, String status, String errMsg, String programId,
			String fileQty, String filePqty) {
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
			if (status.equals("S")) {
				sb.append("<p>執行狀態：完成</p>");
				sb.append("<p>預計筆數：" + fileQty + "</p>");
				sb.append("<p>處理筆數：" + filePqty + "</p>");

				params.clear();
				params.put("batchNo", batchNo);
				Result dtlResult = firAgtBatchDtlService.findForUbBackFileEmail(params);
				if (dtlResult.getResObject() == null) {
					sb.append("FIR_AGT_BATCH_DTL批次明細檔查無資料，請洽系統人員。");
				} else {
					sb.append("<table border=1 style='border-collapse: collapse;'>");
					sb.append("<tr bgcolor='#70bbd9'>");
					sb.append("<td>受理編號</td>");
					sb.append("<td>狀態</td>");
					sb.append("<td>對應核心單號</td>");
					sb.append("<td>險別</td>");
					sb.append("<td>類型</td>");
					sb.append("</tr>");
					List<FirUbProcessVo> dtlList = (List<FirUbProcessVo>) dtlResult.getResObject();
					for (FirUbProcessVo dtl : dtlList) {
						sb.append("<tr>");
						sb.append("<td>" + dtl.getOrderseq() + "</td>");
						sb.append("<td>" + dtl.getOrderseqStatus() + "</td>");
						sb.append("<td>" + dtl.getCoreNo() + "</td>");
						sb.append("<td>" + dtl.getRiskcode() + "</td>");
						sb.append("<td>" + dtl.getDataType() + "</td>");
						sb.append("</tr>");
					}
					sb.append("</table>");
				}
			} else if (status.equals("N")) {
				sb.append("<p>執行狀態：無資料</p>");
			} else {
				sb.append("<p>執行狀態：" + status + "</p>");
				sb.append("<p>異常訊息：" + StringUtil.nullToSpace(errMsg) + "</p>");
			}
			mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", "", mailTo,
					"", mailCc, "", "", "", sb.toString(), "smtp", "newims", "2012newims");
		} catch (Exception e) {
			logger.error("UB backfile sendEmail Exception", e);
		}
		return null;
	}

	private Result getReturnResult(String msg) {
		Result result = new Result();
		Message message = new Message();
		message.setMessageString(msg);
		result.setMessage(message);
		return result;
	}

	public FirAgtBatchDtlService getFirAgtBatchDtlService() {
		return firAgtBatchDtlService;
	}

	public void setFirAgtBatchDtlService(FirAgtBatchDtlService firAgtBatchDtlService) {
		this.firAgtBatchDtlService = firAgtBatchDtlService;
	}

	public FirBatchInfoService getFirBatchInfoService() {
		return firBatchInfoService;
	}

	public void setFirBatchInfoService(FirBatchInfoService firBatchInfoService) {
		this.firBatchInfoService = firBatchInfoService;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public UbNewPolicyProcessService getUbNewPolicyProcessService() {
		return ubNewPolicyProcessService;
	}

	public void setUbNewPolicyProcessService(UbNewPolicyProcessService ubNewPolicyProcessService) {
		this.ubNewPolicyProcessService = ubNewPolicyProcessService;
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

	public RfrcodeService getRfrcodeService() {
		return rfrcodeService;
	}

	public void setRfrcodeService(RfrcodeService rfrcodeService) {
		this.rfrcodeService = rfrcodeService;
	}

	public PrpcmainpropService getPrpcmainpropService() {
		return prpcmainpropService;
	}

	public void setPrpcmainpropService(PrpcmainpropService prpcmainpropService) {
		this.prpcmainpropService = prpcmainpropService;
	}

	public PrpdNewCodeService getPrpdNewCodeService() {
		return prpdNewCodeService;
	}

	public void setPrpdNewCodeService(PrpdNewCodeService prpdNewCodeService) {
		this.prpdNewCodeService = prpdNewCodeService;
	}

}
