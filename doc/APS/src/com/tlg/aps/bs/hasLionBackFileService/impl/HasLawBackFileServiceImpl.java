package com.tlg.aps.bs.hasLionBackFileService.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import org.apache.log4j.Logger;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.hasLionBackFileService.HasLawBackFileService;
import com.tlg.aps.bs.hasLionBackFileService.LionBackFileService;
//import com.tlg.aps.vo.FileUploadResponseVo;

import com.tlg.exception.SystemException;

import com.tlg.prpins.entity.HasAgtBatchDtl;
import com.tlg.prpins.entity.HasAgtBatchMain;
import com.tlg.prpins.entity.HasAgtLawPol;
import com.tlg.prpins.entity.HasAgtLawPolDetail;
import com.tlg.prpins.entity.HasBatchInfo;
import com.tlg.prpins.entity.HasBatchLog;
import com.tlg.prpins.service.HasAgtBatchDtlService;
import com.tlg.prpins.service.HasAgtBatchMainService;
import com.tlg.prpins.service.HasAgtLawPolDetailService;
import com.tlg.prpins.service.HasAgtLawPolService;
import com.tlg.prpins.service.HasBatchInfoService;
import com.tlg.prpins.service.HasSpService;

import com.tlg.util.ConfigUtil;
import com.tlg.util.DateUtils;
//import com.tlg.util.FtsUtil;
import com.tlg.util.Mailer;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.SftpUtil;
import com.tlg.util.StringUtil;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class HasLawBackFileServiceImpl implements HasLawBackFileService {

	private static final Logger logger = Logger.getLogger(HasLawBackFileServiceImpl.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private ConfigUtil configUtil;
	private HasSpService hasSpService;
	private HasBatchInfoService hasBatchInfoService;
	private LionBackFileService lionBackFileService;
	private HasAgtBatchMainService hasAgtBatchMainService;
	private HasAgtBatchDtlService hasAgtBatchDtlService;
	private HasAgtLawPolService hasAgtLawPolService;
	private HasAgtLawPolDetailService hasAgtLawPolDetailService;

	@Override
	public Result runToGenerateFile(Date excuteTime, String userId, Date dataDate) throws SystemException, Exception {
		String tmpStatus = "";
		String tmpMsg = "";
		String tmpBatchNo = "";
		String mailMsg = "";
		String fileQty = "0";
		String filePqty = "0";
		String programId = "HAS_AGT_LAW";

		StringBuilder sb2 = new StringBuilder();
		String tmpBatchTime = new SimpleDateFormat("yyMMddHHmmss").format(excuteTime);
		sb2.append("LAW01");
		sb2.append("_" + tmpBatchTime);
		tmpBatchNo = sb2.toString();

		Map<String, String> params = new HashMap<>();
		params.put("prgId", "HAS_AGT_LAW_STATUS");
		Result result0 = hasBatchInfoService.findHasBatchInfoByUK(params);
		if (result0.getResObject() != null && "N".equals(((HasBatchInfo) result0.getResObject()).getMailTo())) {
			tmpStatus = "S";
			tmpMsg = "HAS_BATCH_INFO 設定檔設定為排程暫停執行。";
		} else {
			tmpStatus = "1"; // 可執行
		}
		logger.info("tmpBatchNo =" + tmpBatchNo);
		logger.info("tmpStatus =" + tmpStatus);
		logger.info("tmpMsg =" + tmpMsg);

		// 新增Has_BATCH_LOG批次程式執行記錄檔
		Result result = lionBackFileService.insertHasBatchLog(excuteTime, userId, programId, tmpStatus, tmpMsg,	tmpBatchNo);

		HasBatchLog hasBatchLog = null;
		if (result.getResObject() != null) {
			hasBatchLog = (HasBatchLog) result.getResObject();
		}

		if ("1".equals(tmpStatus)) {
			Map<String, String> returnData = this.callSp(tmpBatchNo, userId, dataDate);
			tmpStatus = returnData.get("status");
			tmpMsg = returnData.get("msg");
			fileQty = returnData.get("fileQty");
			filePqty = returnData.get("filePqty");
			lionBackFileService.updateHasBatchLog(tmpStatus, tmpMsg, userId, hasBatchLog);
			File zipFile = new File(returnData.get("outputFile") + ".zip");
			if ("S".equals(tmpStatus)) {
				if (zipFile.exists()) {
					String[] zipFilePath = { zipFile.getPath() };
					String[] zipFileName = { zipFile.getName() };
					mailMsg = sendEmail(tmpBatchNo, fileQty, filePqty, excuteTime, tmpStatus, tmpMsg, programId,
							zipFilePath, zipFileName);
					this.deleteFile(returnData.get("outputFile"));
				}
			} else {
				mailMsg = sendEmail(tmpBatchNo, fileQty, filePqty, excuteTime, tmpStatus, tmpMsg, programId);
			}
		} else if ("F".equals(tmpStatus)) {
			mailMsg = sendEmail(tmpBatchNo, fileQty, filePqty, excuteTime, tmpStatus, tmpMsg, programId);
		}

		logger.info("mailMsg =" + mailMsg);
		if (!StringUtil.isSpace(mailMsg)) {
			lionBackFileService.updateHasBatchLog("F", mailMsg, userId, hasBatchLog);
		}

		return this.getReturnResult(StringUtil.isSpace(mailMsg) ? "執行完成" : mailMsg);
	}

	/**
	 * 產生HAS_AGT_LAW_POL(主檔) + HAS_AGT_LAW_POL_DETAIL(明細檔) 兩份csv檔案，壓縮成一個zip，並FTP給對方
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> generateFile(String batchNo, String userId, String executeType)
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
		if (!StringUtil.isSpace(sb.toString())) {
			returnData.put("status", "F");
			returnData.put("msg", "傳入參數異常:" + sb.toString());
			return returnData;
		}

		params.put("batchNo", batchNo);
		params.put("businessnature", "LAW");
		Result result = hasAgtBatchMainService.findHasAgtBatchMainByParams(params);
		List<HasAgtBatchMain> list = new ArrayList<HasAgtBatchMain>();
		HasAgtBatchMain hasAgtBatchMain = null;
		if (result.getResObject() != null) {
			list = (List<HasAgtBatchMain>) result.getResObject();
			if (list.size() > 0) {
				hasAgtBatchMain = list.get(0);
				if (hasAgtBatchMain.getFilePqty() == null || hasAgtBatchMain.getFilePqty() == 0) {
					returnData.put("status", "N");
					returnData.put("msg", "無資料");
					this.updateHasAgtBatch(hasAgtBatchMain, "Z", null, null, userId);
					return returnData;
				}
			}
		} else {
			returnData.put("status", "F");
			returnData.put("msg", batchNo + "批號查無對應資料。");
			return returnData;
		}

		//若接收參數.執行類型 = '2'重新執行，需將前次的執行結果寫入REMARK欄位，避免執行完成後資料覆蓋。
		if("2".equals(executeType)) {
			String remark=StringUtil.nullToSpace(hasAgtBatchMain.getRemark()) + ";" +
					"上次執行資訊：" + hasAgtBatchMain.getIupdate() + "-" + 
					DateUtils.format(hasAgtBatchMain.getDupdate(), "yyyy/MM/dd HH:mm:ss") + "-檔名：" + hasAgtBatchMain.getFileName();
			lionBackFileService.updateHasAgtBatchMain(hasAgtBatchMain, remark, userId);
		}

		String tempFolder = configUtil.getString("tempFolder");
		String sftpFlag = "Y";
		ArrayList<File> toZipFiles = new ArrayList<File>(); // 要一起打包壓縮的檔案
		String zipFileName = this.getFileName(sdf.format(new Date()));
		String zipFilePath = "";

		try {

			File filePath = new File(tempFolder);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}

			// mantis：OTH0182，處理人員：DP0714，錠嵂非專案回饋檔新增業務來源和商品 -- start
			Map<String, String> map = new HashMap<String, String>();
			map.put("J00090", "online"); // 網投
			map.put("J00133", "law"); // 非網投
			map.put("J00115", "UIB"); // 智匯
			map.put("J00103", "JIB"); // 聯众
			map.put("J00141", "LLB"); // 隆龍

			for (String key : map.keySet()) {

				// 產生主檔.csv
				File csvFile1 = new File(tempFolder + File.separator + zipFileName + "_主檔_" + map.get(key) + ".csv");
				String csvFileContent1 = this.genLawPolData(batchNo, key);
				FileUtils.write(csvFile1, csvFileContent1, "UTF-8");
				toZipFiles.add(csvFile1);

				// 產生明細檔.csv
				File csvFile2 = new File(tempFolder + File.separator + zipFileName + "_明細檔_" + map.get(key) + ".csv");
				String csvFileContent2 = this.genLawPolDetailData(batchNo, key);
				FileUtils.write(csvFile2, csvFileContent2, "UTF-8");
				toZipFiles.add(csvFile2);
			}
			// mantis：OTH0182，處理人員：DP0714，錠嵂非專案回饋檔新增業務來源和商品 -- end

			// 壓縮檔案
			zipFilePath = tempFolder + File.separator + zipFileName;
			String pswd = "";
			params.clear();
			returnData.put("outputFile", zipFilePath);
			this.zipFile(zipFilePath, toZipFiles, pswd);
			returnData.put("status", "S");
			returnData.put("msg", "");
			this.updateHasAgtBatch(hasAgtBatchMain, "Y", zipFileName, null, userId);
			return returnData;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			this.deleteFile(zipFilePath);
			returnData.put("status", "F");
			returnData.put("msg", "zip產生失敗:" + e.getMessage());
			this.updateHasAgtBatch(hasAgtBatchMain, "E", null, "zip產生失敗:" + e.getMessage(), userId);
			return returnData;
		}

		// 上傳檔案至FTS
		/*
		try {
			FtsUtil ftsutil = new FtsUtil(configUtil.getString("ftsUrl"));
			FileUploadResponseVo fileUploadResponseVo = ftsutil.uploadFile(zipFilePath + ".zip", "錠嵂保經全險種回饋檔", "TA", batchNo);
			if ("N".equals(fileUploadResponseVo.getStatus())) {
				throw new Exception(fileUploadResponseVo.getMessage());
			}
		} catch (Exception e) {
			logger.error(e.toString());
			this.deleteFile(zipFilePath);
			returnData.put("status", "F");
			returnData.put("msg", "上傳FTS失敗:" + e.toString());
			this.updateHasAgtBatch(hasAgtBatchMain, "E", null, "上傳FTS失敗:" + e.toString(), userId);
			return returnData;
		}
		*/

		// 上傳檔案至SFTP
		/*
		try {
			if ("Y".equals(sftpFlag)) {
				if (!uploadZipFileToSftp(zipFilePath + ".zip")) {
					throw new Exception("傳送SFTP失敗");
				}
			}

			returnData.put("status", "S");
			returnData.put("msg", "");
			// this.deleteFile(file.getPath(), file.getPath() + ".zip");
			this.updateHasAgtBatch(hasAgtBatchMain, "Y", zipFileName, null, userId);
			return returnData;

		} catch (Exception e) {
			this.deleteFile(zipFilePath);
			returnData.put("status", "F");
			returnData.put("msg", e.toString());
			this.updateHasAgtBatch(hasAgtBatchMain, "E", zipFileName, e.toString(), userId);
			return returnData;
		}
		*/
	}

	private Map<String, String> callSp(String batchNo, String userId, Date dataDate)
			throws SystemException, Exception {

		Map<String, String> returnData = new HashMap<>();

		Map<String, Object> params = new HashMap<>();
		params.put("inBatchNo", batchNo);
		params.put("inUser", userId);
		params.put("inDate", dataDate);
		params.put("outResult", null);
		int returnValue = hasSpService.runSpHasAgtLawPol(params);
		logger.info(">>> returnValue: " + returnValue);

		if (returnValue == 0) {
			params.clear();
			params.put("batchNo", batchNo);
			Result result = hasAgtBatchMainService.findHasAgtBatchMainByUk(params);
			if (result.getResObject() != null) {
				HasAgtBatchMain hasAgtBatchMain = (HasAgtBatchMain) result.getResObject();
				if (hasAgtBatchMain.getFileStatus().equals("Z")) {
					returnData.put("status", "N"); // 檔案無資料
					returnData.put("msg", "檔案無資料");
				} else {
					returnData = this.generateFile(batchNo, userId, "1");
					returnData.put("fileQty", hasAgtBatchMain.getFileQty().toString());
					returnData.put("filePqty", hasAgtBatchMain.getFilePqty().toString());
				}
			}
		} else {
			returnData.put("status", "F");
			returnData.put("msg", "執行SP失敗(呼叫SP_HAS_AGT_LAW_POL)");
		}

		return returnData;
	}

	private String getFileName(String today) throws Exception {
		String companyCode = "CO35_";
		Map<String, Object> params = new HashMap<>();
		int number = 1;
		String filename = companyCode + number + "-" + today;
		params.put("fileName", filename);
		if (hasAgtBatchMainService.countByParams(params) > 0) {
			number++;
			filename = companyCode + number + "-" + today;
		}
		return filename;
	}

	@SuppressWarnings("unchecked")
	// mantis：OTH0182，處理人員：DP0714，錠嵂非專案回饋檔新增業務來源和商品
	private String genLawPolData(String batchNo, String businessNature) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("batchNo", batchNo);
		// mantis：OTH0182，處理人員：DP0714，錠嵂非專案回饋檔新增業務來源和商品
		params.put("businessNature", businessNature);
		Result result = hasAgtLawPolService.selectForGenFile(params);
		StringBuilder sb = new StringBuilder();
		if (result.getResObject() != null) {
			List<HasAgtLawPol> list = (List<HasAgtLawPol>) result.getResObject();
			for (HasAgtLawPol m : list) {
				sb.append(StringUtil.nullToSpace(m.getProposalno())).append(","); // 要保書編號
				sb.append(StringUtil.nullToSpace(m.getPolicyno())).append(","); // 保單號碼
				sb.append(StringUtil.nullToSpace(m.getAgentcode())).append(","); // 經代/旅行社
				sb.append(StringUtil.nullToSpace(m.getHandleridentifynumber())).append(","); // 電傳人
				sb.append(StringUtil.nullToSpace(m.getRiskcname())).append(","); // 投保類別
				sb.append(StringUtil.nullToSpace(m.getAppliname())).append(","); // 主要保人姓名
				sb.append(StringUtil.nullToSpace(m.getBirthday())).append(","); // 主要保人生日
				sb.append(StringUtil.nullToSpace(m.getApplicode())).append(","); // 要保人ID/統一編號
				sb.append(StringUtil.nullToSpace(m.getStartdate())).append(","); // 保單起日
				sb.append(StringUtil.nullToSpace(m.getStarthour())).append(","); // 保單起日時間
				sb.append(StringUtil.nullToSpace(m.getEnddate())).append(","); // 保單迄日
				sb.append(m.getInsuredtotaldays() == null ? "" : m.getInsuredtotaldays()).append(","); // 投保天數
				sb.append(StringUtil.nullToSpace(m.getTotalinsuredno())).append(","); // 被保險人數
				sb.append(m.getSumpremium() == null ? "" : m.getSumpremium()).append(","); // 總保險費
				sb.append(StringUtil.nullToSpace(m.getTravelpalce())).append(","); // 目的地
				sb.append(StringUtil.nullToSpace(m.getRoomtelenumber())).append(","); // 要保人電話
				sb.append(StringUtil.nullToSpace(m.getRoompostcode())).append(","); // 要保人郵遞區號
				sb.append(StringUtil.nullToSpace(m.getRoomaddress())).append(","); // 要保人地址
				sb.append(StringUtil.nullToSpace(m.getUnderwriteenddate())).append(","); // 要保日期(簽單日)
				sb.append(StringUtil.nullToSpace(m.getRiskcode())).append(","); // 險種代號
				sb.append(StringUtil.nullToSpace(m.getCarLicenseno())).append(","); // 車牌號碼
				sb.append(StringUtil.nullToSpace(m.getPeSpecies())).append(","); // 寵物種類
				sb.append(StringUtil.nullToSpace(m.getPeIdentifynumber())).append(","); // 晶片號碼
				sb.append(StringUtil.nullToSpace(m.getEndhour())); // 保單迄日時間
 				sb.append("\r\n");
			}
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	// mantis：OTH0182，處理人員：DP0714，錠嵂非專案回饋檔新增業務來源和商品
	private String genLawPolDetailData(String batchNo, String businessNature) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("batchNo", batchNo);
		// mantis：OTH0182，處理人員：DP0714，錠嵂非專案回饋檔新增業務來源和商品
		params.put("businessNature", businessNature);
		Result result = hasAgtLawPolDetailService.selectForGenFile(params);
		StringBuilder sb = new StringBuilder();
		if (result.getResObject() != null) {
			List<HasAgtLawPolDetail> list = (List<HasAgtLawPolDetail>) result.getResObject();
			for (HasAgtLawPolDetail m : list) {
				sb.append(StringUtil.nullToSpace(m.getProposalno())).append(","); // 要保書編號
				sb.append(StringUtil.nullToSpace(m.getPolicyno())).append(","); // 保單號碼
				sb.append(StringUtil.nullToSpace(m.getAppliname())).append(","); // 主要保人姓名
				sb.append(StringUtil.nullToSpace(m.getInsuredname())).append(","); // 主被保人姓名
				sb.append(StringUtil.nullToSpace(m.getIdentifynumber())).append(","); // 被要保人ID
				sb.append(StringUtil.nullToSpace(m.getBirthday())).append(","); // 主被保人生日
				sb.append(StringUtil.nullToSpace(m.getInsuredidentity())).append(","); // 要/被保險人關係
				sb.append(StringUtil.nullToSpace(m.getStartdate())).append(","); // 保單起日
				sb.append(StringUtil.nullToSpace(m.getEnddate())).append(","); // 保單迄日
				sb.append(m.getTa00ta0d() == null ? "" : m.getTa00ta0d()).append(","); // 身故及殘廢保險金額
				sb.append(m.getTa01ta0e() == null ? "" : m.getTa01ta0e()).append(","); // 傷害醫療保險金額
				sb.append(m.getTr45() == null ? "" : m.getTr45()).append(","); // 海外突發疾病健康保險金額
				sb.append(m.getInsuredtotaldays() == null ? "" : m.getInsuredtotaldays()).append(","); // 投保天數
				sb.append(m.getSumpremium() == null ? "" : m.getSumpremium()).append(","); // 總保險費
				sb.append(StringUtil.nullToSpace(m.getTravelpalce())).append(","); // 目的地
				sb.append(StringUtil.nullToSpace(m.getUnderwriteenddate())).append(","); // 要保日期
				sb.append(StringUtil.nullToSpace(m.getBenefitInsuredname())).append(","); // 受益人姓名
				sb.append(StringUtil.nullToSpace(m.getBenefitInsuredidentity())).append(","); // 受益人與被保人關係
				sb.append(StringUtil.nullToSpace(m.getStarthour())).append(","); // 保單起日時間
				sb.append(StringUtil.nullToSpace(m.getTotalinsuredno())).append(","); // 被保險人數
				sb.append(StringUtil.nullToSpace(m.getHandleridentifynumber())).append(","); // 電傳人
				sb.append(StringUtil.nullToSpace(m.getChannelcode())); // 通路代號
				sb.append("\r\n");
			}
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	private String sendEmail(String batchNo, String fileQty, String filePqty, Date excuteTime, String logStatus,
			String errMsg, String programId) throws Exception {
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
		String subject = hasBatchInfo.getMailSubject() + "-" + sdf.format(new Date());

		String mailTo = hasBatchInfo.getMailTo();
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
					String dataSource = main.getDataSource().equals("1") ? "新核心" : "AS400";
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
		try {
			mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", "", mailTo,
					"", mailCc, "", "", "", sb.toString(), "smtp", "newims", "2012newims");
		} catch (Exception e) {
			return "寄送信件發生異常";
		}
		return tmpMsg.toString();
	}

	@SuppressWarnings("unchecked")
	private String sendEmail(String batchNo, String fileQty, String filePqty, Date excuteTime, String logStatus,
			String errMsg, String programId, String[] filepath, String[] filename) throws Exception {
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
		String subject = hasBatchInfo.getMailSubject() + "-" + sdf.format(new Date());
		String mailTo = hasBatchInfo.getMailTo();
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
					String dataSource = main.getDataSource().equals("1") ? "新核心" : "AS400";
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
		try {
			mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", "", mailTo,
					"", mailCc, "", "", "", sb.toString(), "smtp", "newims", "2012newims", filepath, filename);

		} catch (Exception e) {
			return "寄送信件發生異常";
		}
		return tmpMsg.toString();
	}

	private void updateHasAgtBatch(HasAgtBatchMain oldMain, String fileStatus, String filename,
			String remark,	String userId) throws Exception {
		// HasAgtBatchMain處理
		HasAgtBatchMain main = new HasAgtBatchMain();
		main.setBatchNo(oldMain.getBatchNo());
		if (filename != null)
			main.setFileName(filename);
		if (remark != null) {
			remark = StringUtil.isSpace(oldMain.getRemark()) ? remark : oldMain.getRemark() + ";" + remark;
			if (remark != null && remark.length() > 300) {
				remark = remark.substring(remark.length() - 300 < 0 ? 0 : remark.length() - 300, remark.length());
			}
			main.setRemark(remark);
		}
		main.setFileStatus(fileStatus);
		main.setIupdate(userId);
		main.setDupdate(new Date());
		// HasAgtBatchDtl處理
		Map<String, Object> params = null;
		if ("Y".equals(fileStatus)) {
			params = new HashMap<String, Object>();
			params.put("batchNo", main.getBatchNo());
			params.put("orderseqStatus", "02");
			params.put("iupdate", userId);
			params.put("dupdate", new Date());
		}
		lionBackFileService.updateHasAgtBatch(main, params);
	}

	private void zipFile(String fileName, ArrayList<File> files, String pswd) throws Exception {
		ZipFile zipFile = new ZipFile(fileName + ".zip");
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
		if (!StringUtil.isSpace(pswd)) {
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
			parameters.setPassword(pswd);
		}
		zipFile.addFiles(files, parameters);
	}

	public void deleteFile(String zipFilePath) {
		File csvFile1 = new File(zipFilePath + "_主檔.csv");
		File csvFile2 = new File(zipFilePath + "_明細檔.csv");
		File zipFile = new File(zipFilePath + ".zip");
		try {
	        FileUtils.forceDeleteOnExit(csvFile1);
	        FileUtils.forceDeleteOnExit(csvFile2);
	        FileUtils.forceDeleteOnExit(zipFile);
		} catch (IOException ioe) {
			logger.error(ioe.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	private boolean uploadZipFileToSftp(String filePath) throws Exception {
		boolean result = false;

		Map<String, String> params = new HashMap<>();
		String sftpHost = configUtil.getString("lionBackFileFTP");
		String sftpPort = configUtil.getString("lionBackFileFtpPort");
		String sftpUser = configUtil.getString("lionBackFileFtpUserPut");
		String sftpPwd = configUtil.getString("lionBackFileFtpPwdPut");
		String remoteDir = configUtil.getString("lionBackFileRemotePath");

		Map<String, String> map = new HashMap<>();
		Result response = hasBatchInfoService.findHasBatchInfoByParams(params);
		if (response != null) {
			List<HasBatchInfo> list = (List<HasBatchInfo>) response.getResObject();
			for (HasBatchInfo a : list) {
				map.put(a.getPrgId(), a.getMailTo());
			}
		}
		sftpHost = map.get(sftpHost);
		sftpPort = map.get(sftpPort);
		sftpUser = map.get(sftpUser);
		sftpPwd = map.get(sftpPwd);
		remoteDir = map.get(remoteDir);

		try {

			SftpUtil sftpUtil = new SftpUtil(sftpHost, Integer.valueOf(sftpPort), sftpUser, sftpPwd);

			String strResult = sftpUtil.putFileToSftp2(remoteDir, filePath);
			if ("success".equals(strResult)) {
				result = true;
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
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

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public void setHasBatchInfoService(HasBatchInfoService hasBatchInfoService) {
		this.hasBatchInfoService = hasBatchInfoService;
	}

	public void setLionBackFileService(LionBackFileService lionBackFileService) {
		this.lionBackFileService = lionBackFileService;
	}

	public void setHasSpService(HasSpService hasSpService) {
		this.hasSpService = hasSpService;
	}

	public void setHasAgtBatchMainService(HasAgtBatchMainService hasAgtBatchMainService) {
		this.hasAgtBatchMainService = hasAgtBatchMainService;
	}

	public void setHasAgtLawPolService(HasAgtLawPolService hasAgtLawPolService) {
		this.hasAgtLawPolService = hasAgtLawPolService;
	}

	public void setHasAgtLawPolDetailService(HasAgtLawPolDetailService hasAgtLawPolDetailService) {
		this.hasAgtLawPolDetailService = hasAgtLawPolDetailService;
	}

	public void setHasAgtBatchDtlService(HasAgtBatchDtlService hasAgtBatchDtlService) {
		this.hasAgtBatchDtlService = hasAgtBatchDtlService;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public HasSpService getHasSpService() {
		return hasSpService;
	}

	public HasBatchInfoService getHasBatchInfoService() {
		return hasBatchInfoService;
	}

	public LionBackFileService getLionBackFileService() {
		return lionBackFileService;
	}

	public HasAgtBatchMainService getHasAgtBatchMainService() {
		return hasAgtBatchMainService;
	}

	public HasAgtBatchDtlService getHasAgtBatchDtlService() {
		return hasAgtBatchDtlService;
	}

	public HasAgtLawPolService getHasAgtLawPolService() {
		return hasAgtLawPolService;
	}

	public HasAgtLawPolDetailService getHasAgtLawPolDetailService() {
		return hasAgtLawPolDetailService;
	}

	public static void main(String args[]) throws Exception {
		// 測試取得檔案list----start
		// String riskCode = "TA";
		// String businessNo = "LION04_240109175923";
		// FtsUtil ftsUtil = new
		// FtsUtil("http://192.168.112.122:8880/FTS/rf/fileHandler/");
		// FileListResponseVo vo = ftsUtil.getFtsFileList(riskCode, businessNo);
		// ArrayList<FileVo> list = vo.getFileList();
		// for(FileVo fv : list) {
		// System.out.println("----------");
		// System.out.println("oid : " + fv.getOid());
		// System.out.println("name : " + fv.getName());
		// System.out.println("downloadPath : " + fv.getDownloadPath());
		// System.out.println("----------");
		// System.out.println("start to download file : " + fv.getName());
		// ftsUtil.downloadFile(businessNo, fv.getOid(), "D:/temp/",
		// fv.getName());
		// System.out.println("download file : " + fv.getName() + " finished");
		// }
		// 測試取得檔案list----end

	}
}
