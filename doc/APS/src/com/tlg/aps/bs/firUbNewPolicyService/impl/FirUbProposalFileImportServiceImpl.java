package com.tlg.aps.bs.firUbNewPolicyService.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
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

import com.tlg.aps.bs.firUbNewPolicyService.FirUbProposalFileImportService;
import com.tlg.aps.bs.firUbNewPolicyService.UbNewPolicyProcessService;
import com.tlg.aps.vo.FileUploadResponseVo;
import com.tlg.aps.vo.FirUbProcessVo;
import com.tlg.prpins.entity.FirAgtBatchDtl;
import com.tlg.prpins.entity.FirAgtBatchMain;
import com.tlg.prpins.entity.FirAgtUb01;
import com.tlg.prpins.entity.FirBatchInfo;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.service.FirAgtBatchDtlService;
import com.tlg.prpins.service.FirAgtBatchMainService;
import com.tlg.prpins.service.FirAgtUb01Service;
import com.tlg.prpins.service.FirBatchInfoService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.FtsUtil;
import com.tlg.util.Mailer;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.SftpUtil;
import com.tlg.util.StringUtil;
import com.tlg.util.ZipUtil;

/** mantis：FIR0545，處理人員：BJ085，需求單編號：FIR0545 火險_聯邦新件_APS要保資料接收排程 **/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirUbProposalFileImportServiceImpl implements FirUbProposalFileImportService {

	private static final Logger logger = Logger.getLogger(FirUbProposalFileImportServiceImpl.class);
	private ConfigUtil configUtil;
	private FirBatchInfoService firBatchInfoService;
	private UbNewPolicyProcessService ubNewPolicyProcessService;
	private FirAgtBatchMainService firAgtBatchMainService;
	private FirAgtUb01Service firAgtUb01Service;
	private FirAgtBatchDtlService firAgtBatchDtlService;

	private List<String> downloadFileList = new ArrayList<>(); // SFTP上需處理的檔案清單
	private List<String> earlyFileList = new ArrayList<>(); // 本次處理的檔案清單，只會有一個
	private List<String> orderseqList = new ArrayList<>();// 受理編號清單檔案
	private String unZipDir;
	private String txtname = "";


	public Result runToImportFile(String userId, Date excuteTime, String programId) throws Exception {
		clearFieldList();
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

		// programId = FIR_AGT_UB_01
		String batchNo = programId.substring(8, 10) + "01_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		if (!StringUtil.isSpace(sb.toString())) {
			Result result = ubNewPolicyProcessService.insertFirBatchLog(excuteTime, userId, programId, "F",
					sb.toString(), batchNo);
			if (result.getResObject() != null) {
				FirBatchLog firBatchLog = (FirBatchLog) result.getResObject();
				String mailMsg = sendEmail(firBatchLog.getBatchNo(), excuteTime, "F", sb.toString(), programId);
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
			}
		}
		result = ubNewPolicyProcessService.insertFirBatchLog(excuteTime, userId, programId, status, msg, batchNo);
		if (status.equals("S")) {
			return this.getReturnResult(msg);
		}

		if (result.getResObject() != null) {
			FirBatchLog firBatchLog = (FirBatchLog) result.getResObject();
			BigDecimal batchLogOid = firBatchLog.getOid();
			// 取得SFTP檔案
			String strResult = getFileFromSftp();

			// 無需處理檔案
			if (downloadFileList.isEmpty()) {
				msg = "SFTP上無需處理的檔案";
				status = "N";
				updateFirBatchLog(status, msg, userId, batchLogOid);
				sendEmail(batchNo, excuteTime, status, msg, programId);
				return this.getReturnResult(msg);
			}
			
			if ("fail".equals(strResult) || StringUtil.isSpace(strResult)) {
				msg = "自SFTP取得檔案失敗";
				status = "F";
				updateFirBatchLog(status, msg, userId, batchLogOid);
				sendEmail(batchNo, excuteTime, status, msg, programId);
				return this.getReturnResult(msg);
			}
			
			// 解壓縮檔案，會包含一個txt檔、0~多個pdf檔案
			String fileForder = configUtil.getString("ubRootDirectory");
			List<String> fileList = unzipFile(fileForder, earlyFileList.get(0));

			try {
				ubNewPolicyProcessService.insertFirAgtBatchMain(batchNo, txtname, userId);
			} catch (Exception e) {
				status = "F";
				msg = "UB01 新增火險保經代資料交換批次主檔失敗";
				logger.error(msg, e);
				updateFirBatchLog(status, msg, userId, batchLogOid);
				return this.getReturnResult(msg);
			}

			// 開始處理檔案
			int fileQty = 0;// 資料總筆數
			int filePqty = 0;// 資料處理筆數
			String fileStatus = "";
			List<String> pdfList = new ArrayList<>();
			for (String filename : fileList) {
				// txt檔案處理
				if (filename.contains(".txt") || filename.contains(".TXT")) {
					List<String> dataList = readFile(unZipDir + File.separator + txtname);
					fileQty = dataList.size() - 1;// 扣掉表頭行
					if (fileQty == 0) {
						fileStatus = "Z";
						msg = "檔案無資料";
						status = "S";
						this.updateFirAgtBatchMain(batchNo, userId, fileStatus, fileQty, filePqty, "");
						sendEmail(batchNo, excuteTime, status, msg, programId);
						updateFirBatchLog(status, msg, userId, batchLogOid);
						moveFile();
						return this.getReturnResult(msg);
					}

					try {
						// 兩個Table資料必須同時新增成功，否則rollback
						for (int j = 1; j < dataList.size(); j++) {
							String[] rawData = dataList.get(j).split(",");
							insertUb01AndDtl(rawData, userId, batchNo, fileList);
							filePqty++;
						}
					} catch (Exception e) {
						e.printStackTrace();
						fileStatus = "E";
						this.updateFirAgtBatchMain(batchNo, userId, fileStatus, fileQty, filePqty, e.toString());
					}
				} else if (filename.contains(".pdf") || filename.contains(".PDF")) {
					pdfList.add(filename);
				}
			}
			fileStatus = "Y";
			
			// 重複受理編號處理
			if (!orderseqList.isEmpty()) {
				for (int i = 0; i < orderseqList.size(); i++) {
					params.clear();
					params.put("orderseq", orderseqList.get(i));
					params.put("notBatchNo", batchNo);
					result = firAgtBatchDtlService.findFirAgtBatchDtlByParams(params);
					if (result.getResObject() != null) {
						@SuppressWarnings("unchecked")
						List<FirAgtBatchDtl> repeatList = (List<FirAgtBatchDtl>) result.getResObject();
						for (int j = 0; j < repeatList.size(); j++) {
							FirAgtBatchDtl repeatDtl = repeatList.get(j);
							repeatDtl.setRemark(repeatDtl.getRemark() + "受理編號重複，以批次號" + batchNo + "資料為主;");
							ubNewPolicyProcessService.updateFirAgtBatchDtl(repeatDtl, userId);
						}
					}
				}
			}

			// 上傳TXT檔案
			File txtFile = new File(unZipDir + File.separator + txtname);
			FileUploadResponseVo fileUploadResponseVo = uploadFile(txtFile, batchNo, userId, "F");
			if ("N".equals(fileUploadResponseVo.getStatus())) {// 上傳txt失敗
				this.updateFirAgtBatchMain(batchNo, userId, fileStatus, fileQty, filePqty, "上傳txt至檔案伺服器失敗");
			}

			// 上傳PDF要保檔案
			for (int i = 0; i < pdfList.size(); i++) {
				File pdfFile = new File(unZipDir + File.separator + pdfList.get(i));
				fileUploadResponseVo = uploadFile(pdfFile, pdfList.get(i).substring(0, pdfList.get(i).indexOf(".")),
						userId, "F");
				if ("N".equals(fileUploadResponseVo.getStatus())) {// 上傳失敗
					params.clear();
					params.put("batchNo", batchNo);
					params.put("orderseq", pdfList.get(i));
					params.put("coreNo", pdfList.get(i));
					result = firAgtBatchDtlService.findFirAgtBatchDtlByUK(params);
					FirAgtBatchDtl firAgtBatchDtl = (FirAgtBatchDtl) result.getResObject();
					firAgtBatchDtl.setRemark(
							firAgtBatchDtl.getRemark() == null ? "" : firAgtBatchDtl.getRemark() + "上傳PDF檔案至檔案伺服器失敗;");
					// 更新Dtl檔
					ubNewPolicyProcessService.updateFirAgtBatchDtl(firAgtBatchDtl, userId);
				}
			}
			status = "S";
			// 回寫FIR_AGT_ABTCH_MAIN
			this.updateFirAgtBatchMain(batchNo, userId, fileStatus, fileQty, filePqty, "");
			sendEmail(batchNo, excuteTime, status, msg, programId);
			updateFirBatchLog(status, msg, userId, batchLogOid);
			moveFile();
		}
		return this.getReturnResult("執行完成");
	}

	private void updateFirAgtBatchMain(String batchNo, String userId, String fileStatus, int fileQty, int filePqty,
			String remark) throws Exception {
		FirAgtBatchMain firAgtBatchMain = new FirAgtBatchMain();
		firAgtBatchMain.setBatchNo(batchNo);
		firAgtBatchMain.setFileStatus(fileStatus);
		firAgtBatchMain.setFileQty(fileQty);
		firAgtBatchMain.setFilePqty(filePqty);
		if (!StringUtil.isSpace(remark) && remark.length() > 300) {
			remark = remark.substring(remark.length() - 300 < 0 ? 0 : remark.length() - 300, remark.length());
		}
		firAgtBatchMain.setRemark(remark);
		ubNewPolicyProcessService.updateFirAgtBatchMain(firAgtBatchMain, remark, userId);
	}

	private void insertUb01AndDtl(String[] rawData, String userId, String batchNo, List<String> fileList) throws Exception {
		StringBuilder remarkSb = new StringBuilder();
		FirAgtUb01 firAgtUb01 = new FirAgtUb01();
		firAgtUb01.setBatchNo(batchNo);// 批次號
		// 險種
		String riskcode = "";
		if ("商火".equals(rawData[8])) {
			riskcode = "F01";
		} else if ("住火".equals(rawData[8])) {
			riskcode = "F02";
		}
		firAgtUb01.setRiskcode(riskcode);

		// 是否有要保書檔
		if (fileList.contains(rawData[3] + ".pdf")) {
			firAgtUb01.setIsPdf("Y");
		} else {
			firAgtUb01.setIsPdf("N");
			remarkSb.append("此受理編號無對應的受理要保書檔;");
		}

		// 保代寄件日
		Date ubDate01 = dateFormat(rawData[0]);
		if (ubDate01 == null) {
			remarkSb.append("保代寄件日" + rawData[0] + "日期格式錯誤;");
		}
		firAgtUb01.setUbDate01(ubDate01);

		// 保代部受理日
		Date ubDate02 = dateFormat(rawData[1]);
		if (ubDate02 == null) {
			remarkSb.append("保代部受理日" + rawData[1] + "日期格式錯誤;");
		}
		firAgtUb01.setUbDate01(ubDate02);

		firAgtUb01.setInsName(rawData[2]);// 保險公司
		firAgtUb01.setOrderseq(rawData[3]);// 受理編號
		orderseqList.add(rawData[3]);
		firAgtUb01.setUbType(rawData[4]);// 歸屬
		firAgtUb01.setBranchName(rawData[5]);// 建檔分行名稱
		firAgtUb01.setBranchNo(rawData[6]);// 建檔分行代號

		// 保險起日
		Date startdate = dateFormat(rawData[7]);
		if (startdate == null) {
			remarkSb.append("保險起日" + rawData[7] + "日期格式錯誤;");
		}
		firAgtUb01.setStartdate(startdate);

		firAgtUb01.setUbKindcode(rawData[8]);// 險種
		firAgtUb01.setApplyName(rawData[9]);// 要保人姓名
		firAgtUb01.setApplyId(rawData[10]);// 要保人ID
		firAgtUb01.setInsuredName(rawData[11]);// 被保險人姓名
		firAgtUb01.setInsuredId(rawData[12]);// 被保險人ID
		firAgtUb01.setUbSource(rawData[13]);// 案件類型
		firAgtUb01.setSalesEid(rawData[14]);// 業務員員工編號
		firAgtUb01.setSalesName(rawData[15]);// 業務員姓名
		firAgtUb01.setSalesNo(rawData[16]);// 業務員登錄字號
		firAgtUb01.setUbStatus(rawData[17]);// 受理狀況
		firAgtUb01.setDeleteFlag("N");// 刪除註記
		firAgtUb01.setIcreate(userId);
		firAgtUb01.setDcreate(new Date());

		FirAgtBatchDtl firAgtBatchDtl = new FirAgtBatchDtl();
		firAgtBatchDtl.setBatchNo(batchNo);
		firAgtBatchDtl.setBusinessnature("I99080");
		firAgtBatchDtl.setOrderseq(rawData[3]);
		firAgtBatchDtl.setOrderseqStatus("11");
		firAgtBatchDtl.setRiskcode(riskcode);
		firAgtBatchDtl.setDataType("4");
		firAgtBatchDtl.setDeleteFlag("N");
		firAgtBatchDtl.setDataSource("1");
		firAgtBatchDtl.setCoreNo(rawData[3]);// 先放受理編號
		if (remarkSb.length() != 0) {
			firAgtBatchDtl.setRemark(remarkSb.toString());
		}
		firAgtBatchDtl.setIcreate(userId);
		firAgtBatchDtl.setDcreate(new Date());
		ubNewPolicyProcessService.insertUb01AndDtl(firAgtUb01, firAgtBatchDtl);
	}

	// 將zip檔解壓縮，回傳解壓縮後的檔案名稱list
	private List<String> unzipFile(String workDir, String zipFilename) throws Exception {
		ZipUtil zipUtil = new ZipUtil();
		// 將解壓縮檔案放置檔案名稱資料夾，並在排程結束後刪除
		unZipDir = workDir + File.separator + zipFilename.substring(0, zipFilename.indexOf("."));
		File unZipPath = new File(unZipDir);
		if (!unZipPath.exists()) {
			unZipPath.mkdirs();
		}
		if (zipFilename.contains(".zip") || zipFilename.contains(".ZIP")) {
			zipUtil.unzip(workDir + zipFilename, unZipDir, zipFilename.substring(6, 14));
		}

		List<String> fileList = new ArrayList<>();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(unZipDir))) {
			for (Path file : stream) {
				fileList.add(file.getFileName().toString());
				if (file.getFileName().toString().contains(".TXT") || file.getFileName().toString().contains(".txt")) {
					txtname = file.getFileName().toString();
				}
			}
		}
		return fileList;
	}

	// 取得sftp檔案
	private String getFileFromSftp() {
		String strResult = "";
		String sftpHost = configUtil.getString("ubSFTP");
		String sftpUser = configUtil.getString("ubSftpUserGet");
		String sftpPwd = configUtil.getString("ubSfptPwdGet");
		int sftpPort = Integer.parseInt(configUtil.getString("ubSftpPort"));
		String remoteDir = configUtil.getString("ubUploadPath");
		String rootDirectory = configUtil.getString("ubRootDirectory");
		File rootPath = new File(rootDirectory);
		if (!rootPath.exists()) {
			rootPath.mkdirs();
		}
		SftpUtil sftpUtil = new SftpUtil(sftpHost, sftpPort, sftpUser, sftpPwd);
		try {
			List<String> sftpList = sftpUtil.getFileListFromSftp(remoteDir);
			if (null == sftpList) {
				strResult = "fail";
				return strResult;
			}

			Result result = null;
			// 取得未處理過檔案清單
			String fileName = "";
			String fileNameLike = "";
			for (int i = 0; i < sftpList.size(); i++) {
				fileName = sftpList.get(i);
				if (fileName.length() == 22) {
					fileNameLike = fileName.substring(0, 18);
				}
				Map<String, String> params = new HashMap<>();
				params.put("fileNameLike", fileNameLike);
				result = firAgtBatchMainService.findFirAgtBatchMainByParams(params);
				if (null == result.getResObject() && fileName.length() == 22) {// 查詢不到資料且長度符合檔案命名規則才加入未處理檔案清單
					downloadFileList.add(fileName);
				}
			}
		} catch (Exception e) {
			logger.error("UB getInsureFileFromSftp Exception", e);
			strResult = "fail";
			return strResult;
		}

		if (!downloadFileList.isEmpty()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
			Date earlyDate = null;
			int fileIndex = 0;
			// 取得最早日期未處理過的檔案
			for (int j = 0; j < downloadFileList.size(); j++) {
				Date fileDate;
				try {
					fileDate = sdf.parse(downloadFileList.get(j).substring(6, 18));
					if (null == earlyDate) {
						earlyDate = fileDate;
						fileIndex = j;
						continue;
					}
					if (fileDate.before(earlyDate)) {
						earlyDate = fileDate;
						fileIndex = j;
					}
				} catch (ParseException e) {
					// 若有錯應是抓到不是受理檔的檔案，或檔案名稱傳送有誤，印出錯誤訊息並繼續執行程式
					e.printStackTrace();
				}
			}

			earlyFileList.add(downloadFileList.get(fileIndex));
			strResult = sftpUtil.getFileFromSftp(remoteDir, rootDirectory, earlyFileList);
		}
		return strResult;
	}

	// 讀取檔案
	private List<String> readFile(String filepath) throws IOException {
		List<String> dataList = new ArrayList<>();
		File file = new File(filepath);
		if (file.length() == 0) {
			return new ArrayList<>();
		}
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(filepath), StandardCharsets.UTF_8))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.trim().length() > 0) {
					dataList.add(line);
				}
			}
		}
		return dataList;
	}

	private Date dateFormat(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	// 將檔案上傳至fts
	private FileUploadResponseVo uploadFile(File file, String businessNo, String userId, String riskcode) {
		FileUploadResponseVo fileUploadResponseVo = null;
		try {
			FtsUtil ftsutil = new FtsUtil(configUtil.getString("ftsUrl"));
			fileUploadResponseVo = ftsutil.uploadFile(file.getAbsolutePath(), userId, riskcode, businessNo);
		} catch (Exception e) {
			e.printStackTrace();
			return fileUploadResponseVo;
		}
		return fileUploadResponseVo;
	}

	private void moveFile() throws IOException {
		File filePath = new File(unZipDir);
		if (filePath.exists()) {
			FileUtils.forceDelete(filePath);
		}
	}

	@SuppressWarnings("unchecked")
	private String sendEmail(String batchNo, Date excuteTime, String status, String errMsg, String programId) {
		Mailer mailer = new Mailer();
		StringBuilder tmpMsg = new StringBuilder();
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
				sb.append("<p>SFTP上待處理檔案清單:</p>");
				sb.append("<table border=1 style='border-collapse: collapse;'>");
				sb.append("<tr bgcolor='#70bbd9'>");
				sb.append("<td>檔案名稱</td>");
				sb.append("</tr>");
				for (String fileName : downloadFileList) {
					sb.append("<tr>");
					sb.append("<td>" + fileName + "</td>");
					sb.append("</tr>");
				}
				sb.append("</table>");
				sb.append("<p>此次處理的檔案(每次只處理一個檔案):</p>");

				params.clear();
				params.put("batchNo", batchNo);
				Result mainResult = firAgtBatchMainService.findFirAgtBatchMainByParams(params);

				params.clear();
				params.put("batchNo", batchNo);
				Result dtlResult = firAgtBatchDtlService.findForUbProposalEmail(params);
				if (mainResult.getResObject() == null) {
					tmpMsg.append("FIR_AGTRN_BATCH_MAIN批次主檔查無資料，請洽系統人員。");
				} else {
					sb.append("<table border=1 style='border-collapse: collapse;'>");
					sb.append("<tr bgcolor='#70bbd9'>");
					sb.append("<td>檔案名稱</td>");
					sb.append("<td>檔案狀態</td>");
					sb.append("<td>檔案筆數</td>");
					sb.append("<td>處理筆數</td>");
					sb.append("<td>備註</td>");
					sb.append("</tr>");
					List<FirAgtBatchMain> firAgtBatchMainList = (List<FirAgtBatchMain>) mainResult.getResObject();
					FirAgtBatchMain firAgtBatchMain = firAgtBatchMainList.get(0);
					sb.append("<tr>");
					sb.append("<td>" + firAgtBatchMain.getFileName() + "</td>");
					String fileStatus = "";
					if ("N".equals(firAgtBatchMain.getFileStatus())) {
						fileStatus = "未處理";
					} else if ("Y".equals(firAgtBatchMain.getFileStatus())) {
						fileStatus = "已處理";
					} else if ("Z".equals(firAgtBatchMain.getFileStatus())) {
						fileStatus = "檔案無資料";
					} else if ("E".equals(firAgtBatchMain.getFileStatus())) {
						fileStatus = "檔案/執行異常";
					} else {
						fileStatus = "未定義";
					}
					sb.append("<td>" + fileStatus + "</td>");
					sb.append("<td>" + firAgtBatchMain.getFileQty() + "</td>");
					sb.append("<td>" + firAgtBatchMain.getFilePqty() + "</td>");
					sb.append("<td>" + StringUtil.nullToSpace(firAgtBatchMain.getRemark()) + "</td>");
					sb.append("</tr>");
					sb.append("</table>");
					
					if (dtlResult.getResObject() != null) {
						sb.append("<p>本次處理明細如下：</p>");
						sb.append("<table border=1 style='border-collapse: collapse;'>");
						sb.append("<tr bgcolor='#70bbd9'>");
						sb.append("<td>險別</td>");
						sb.append("<td>受理編號</td>");
						sb.append("<td>是否有要保影像檔</td>");
						sb.append("<td>備註</td>");
						sb.append("</tr>");
						List<FirUbProcessVo> dtlList = (List<FirUbProcessVo>) dtlResult.getResObject();
						for (FirUbProcessVo vo : dtlList) {
							sb.append("<tr>");
							sb.append("<td>" + vo.getRiskcode() + "</td>");
							sb.append("<td>" + vo.getOrderseq() + "</td>");
							sb.append("<td>" + vo.getIsPdf() + "</td>");
							sb.append("<td>" + StringUtil.nullToSpace(vo.getRemark()) + "</td>");
							sb.append("</tr>");
						}
						sb.append("</table>");
					}else if(dtlResult.getResObject() == null && firAgtBatchMain.getFileQty() != 0) {
						tmpMsg.append("FIR_AGTRN_BATCH_DTL批次明細檔查無資料，請洽系統人員。");
					}
				}
			} else if (status.equals("N")) {
				sb.append("<p>執行狀態：無檔案 </p>");
			} else {
				sb.append("<p>執行狀態：失敗</p>");
				sb.append("<p>異常訊息：" + StringUtil.nullToSpace(errMsg) + "</p>");
			}
			mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", "", mailTo,
					"", mailCc, "", "", "", sb.toString(), "smtp", "newims", "2012newims");
		} catch (Exception e) {
			logger.error(batchNo + " sendEmail Exception", e);
		}
		return tmpMsg.toString();
	}

	private void updateFirBatchLog(String status, String outMsg, String userId, BigDecimal oid) throws Exception {
		FirBatchLog firBatchLog = new FirBatchLog();
		firBatchLog.setOid(oid);
		ubNewPolicyProcessService.updateFirBatchLog(status, outMsg, userId, firBatchLog);
	}
	
	private void clearFieldList() {
		//避免重複執行不會重新new全域變數，所以排程一開始先將所有list清空。
		downloadFileList.clear();
		earlyFileList.clear();
		orderseqList.clear();
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

	public FirAgtBatchMainService getFirAgtBatchMainService() {
		return firAgtBatchMainService;
	}

	public void setFirAgtBatchMainService(FirAgtBatchMainService firAgtBatchMainService) {
		this.firAgtBatchMainService = firAgtBatchMainService;
	}

	public FirAgtUb01Service getFirAgtUb01Service() {
		return firAgtUb01Service;
	}

	public void setFirAgtUb01Service(FirAgtUb01Service firAgtUb01Service) {
		this.firAgtUb01Service = firAgtUb01Service;
	}

	public FirAgtBatchDtlService getFirAgtBatchDtlService() {
		return firAgtBatchDtlService;
	}

	public void setFirAgtBatchDtlService(FirAgtBatchDtlService firAgtBatchDtlService) {
		this.firAgtBatchDtlService = firAgtBatchDtlService;
	}
}
