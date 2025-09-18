package com.tlg.aps.bs.passbookLogDownloadService.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.passbookLogDownloadService.PassbookReturnLogDownloadService;
import com.tlg.aps.bs.passbookLogDownloadService.PassbookReturnLogRenewalFileService;
import com.tlg.prpins.entity.FirBatchInfo;
import com.tlg.prpins.entity.OthBatchPassbookReturnLog;
import com.tlg.prpins.service.FirBatchInfoService;
import com.tlg.prpins.service.OthBatchPassbookReturnLogService;
import com.tlg.util.AppContext;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Mailer;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.SftpUtil;

/* mantis：OTH0138，處理人員：CC009，需求單編號：OTH0138 保發中心_保單存摺回饋檔回存資料庫規格 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PassbookReturnLogDownloadServiceImpl implements PassbookReturnLogDownloadService {

	private static final Logger logger = Logger.getLogger(PassbookReturnLogDownloadServiceImpl.class);

	private ConfigUtil configUtil;
	private PassbookReturnLogRenewalFileService passbookReturnLogRenewalFileService;
	private OthBatchPassbookReturnLogService othBatchPassbookReturnLogService;
	private FirBatchInfoService firBatchInfoService;

	@SuppressWarnings("unchecked")
	@Override
	public Result runToReceiveData() throws Exception {
		String userId = "SYSTEM";
		String workingDirPath = configUtil.getString("localOthPassbookReturnPath");
		File workingDir = new File(workingDirPath);
		if (!workingDir.exists()) {
			workingDir.mkdirs();
		}
		List<String> downloadFileList = getFileFromSftp();
		List<String> mailResultDownloadFileList = new ArrayList<String>(); 
		for (String fileName : downloadFileList) {
			logger.info("檔名 :" + fileName + " 開始處理。");
			File file = new File(workingDirPath + File.separator + fileName);
			if (!file.exists()) {
				logger.error("檔名 :" + file.getPath() + " 檔案不存在。");
				continue;
			}else {
				mailResultDownloadFileList.add(fileName);
			}
			try {
				List<String> fileDataList = readFileReader(file);
				passbookReturnLogRenewalFileService.insertOthBatchPassbookReturnLogList(fileName, userId, fileDataList);
				deleteFileFromSftp(fileName);
				logger.info("檔名 :" + fileName + " 處理完成。");
			} catch (Exception e) {
				logger.error("檔名 :" + fileName + " 處理失敗。");
				logger.error(e.toString());
			}
		}

		/**
		 * 20221215:BJ016:新增EMAIL通知----START
		 * */
		logger.info("保單存摺回饋檔回存資料庫EMAIL通知作業----START");
		Result result = null;
		Result errorDataResult = null;
		List<OthBatchPassbookReturnLog> zipSearchResult = null;
		OthBatchPassbookReturnLog zipFile = null;
		List<OthBatchPassbookReturnLog> errorDataSearchResult = null;
		StringBuilder sb = new StringBuilder();
		Map<String, String> filter = null;
		Map<String, String> errorfilter = null;
		String[] filenameSplit = null;
		String databaseFilename = "";
		for(String filename : mailResultDownloadFileList) {
			databaseFilename = "";
			if(filename != null) {
				filenameSplit = filename.split("\\.");
				if(filenameSplit != null) {
					databaseFilename = filenameSplit[0];
				}
			}
				
			filter = new HashMap<String,String>();
			filter.put("fileName", databaseFilename);
			filter.put("dataType", "ZIP");
			result = othBatchPassbookReturnLogService.findOthBatchPassbookReturnLogByParams(filter);
			if(result != null && result.getResObject() != null) {
				zipSearchResult = (List<OthBatchPassbookReturnLog>)result.getResObject();
				if(zipSearchResult != null && zipSearchResult.size() > 0) {
					zipFile = zipSearchResult.get(0);
					sb.append("<p>檔案名稱：" + filename + "</p>");
					sb.append("<p>執行結果：" + zipFile.getStatusCode() + zipFile.getStatusMsg() + "</p>");
					if("S0000".equals(zipFile.getStatusCode())) {
						sb.append("<p>--------------------------------------------------</p>");
					} else {
						errorfilter = new HashMap<String,String>();
						errorfilter.put("fileName", databaseFilename);
						errorfilter.put("dataType", "DATA");
						errorfilter.put("notStatusCode", "S0000");
						
						errorDataResult = othBatchPassbookReturnLogService.findOthBatchPassbookReturnLogByParams(errorfilter);
						if(errorDataResult != null && errorDataResult.getResObject() != null) {
							sb.append("<table border=1 style='border-collapse: collapse;'>");
							sb.append("<tr bgcolor='#70bbd9'>");
							sb.append("<td>ID單號</td>");
							sb.append("<td>處理方式註記</td>");
							sb.append("<td>識別碼</td>");
							sb.append("<td>處理結果代碼</td>");
							sb.append("<td>處理結果訊息</td>");
							sb.append("<td>異常訊息</td>");
							sb.append("</tr>");
							errorDataSearchResult = (List<OthBatchPassbookReturnLog>)errorDataResult.getResObject();
							for(OthBatchPassbookReturnLog errorData : errorDataSearchResult) {
								 sb.append("<tr>");
								 sb.append("<td>" + errorData.getIdNo() + "</td>");
								 sb.append("<td>" + errorData.getRemark() + "</td>");
								 sb.append("<td>" + errorData.getIdentifier() + "</td>");
								 sb.append("<td>" + errorData.getStatusCode() + "</td>");
								 sb.append("<td>" + errorData.getStatusMsg() + "</td>");
								 sb.append("<td>" + errorData.getErrorMsg() + "</td>");
								 sb.append("</tr>");
							}
							sb.append("</table>");
						}
						sb.append("<p>--------------------------------------------------</p>");
					}
				}
			}
		}
		logger.info("保單存摺回饋檔回存資料庫EMAIL通知作業：信件內容長度：sb.length()=" + sb.length() + " ; sb.toString().trim().length() =" +sb.toString().trim().length());
		if(sb.toString().trim().length() > 0) {
			Map<String,Object> params = new HashMap<>();
			params.put("prgId", "OTH_BATCH_PASSBOOK_RETURN_LOG");
			Result resultBatch = this.firBatchInfoService.findFirBatchInfoByUK(params);
			if(resultBatch.getResObject() == null) {
				return getReturnResult("無法取得FIR_BATCH_INFO資料，無法寄送MAIL，但作業已執行完畢");
			} else {
				FirBatchInfo firBatchInfo = (FirBatchInfo) resultBatch.getResObject();
				ConfigUtil configUtil = (ConfigUtil) AppContext.getBean("configUtil");
				Mailer mailer = new Mailer();
				String smtpServer = configUtil.getString("smtp_host");
				String userName = configUtil.getString("smtp_username");
				String password = configUtil.getString("smtp_pwd");
				String contentType = "text/html; charset=utf-8";
				String auth = "smtp";
				String subject =  firBatchInfo.getMailSubject();
				String from = configUtil.getString("mail_from_address");
				String to = firBatchInfo.getMailTo();
				String cc = firBatchInfo.getMailCc();
				String mailBody = sb.toString();
				mailer.sendmail(smtpServer, contentType, subject, from,	"", to, "", cc, "", "", "", mailBody, auth, userName, password);
			}
		}
		logger.info("保單存摺回饋檔回存資料庫EMAIL通知作業----END");
		/**
		 * 20221215:BJ016:新增EMAIL通知----END
		 * */

		return getReturnResult("執行完成");
	}

	private List<String> readFileReader(File file) throws IOException {
		List<String> rawDataList = new ArrayList<String>();
		InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
		BufferedReader br = new BufferedReader(isr);
		String line;
		logger.info("Reading text file using FileReader");
		while ((line = br.readLine()) != null) {
			// process the line
			if (line.trim().length() > 0) {
				System.out.println(line);
				rawDataList.add(line);
			}
		}
		br.close();
		isr.close();
		br = null;
		isr = null;
		return rawDataList;
	}

	private List<String> getFileFromSftp() throws Exception {
		String sftpHost = configUtil.getString("tvisbkSftpHost");
		String sftpUser = configUtil.getString("tvisbkSftpUser");
		String sftpPwd = configUtil.getString("tvisbkSftpPwd");
		String sftpPort = configUtil.getString("tvisbkSftpPort");
		String remoteDir = configUtil.getString("tvisbkFileEndDir");
		String workingDirPath = configUtil.getString("localOthPassbookReturnPath");
		SftpUtil sftpUtil = new SftpUtil(sftpHost, Integer.valueOf(sftpPort), sftpUser, sftpPwd);
		List<String> fileList = sftpUtil.getFileListFromSftp(remoteDir);
		List<String> downloadFileList = new ArrayList<String>();
		if (fileList != null && fileList.size() > 0) {
			for (String fileName : fileList) {
				if (fileName.endsWith(".log")) {
					downloadFileList.add(fileName);
				}
			}
		}
		logger.info("sftp downloadFileList size =" + fileList.size());

		// 下載檔案到指定目錄
		if (downloadFileList.size() > 0) {
			sftpUtil.getFileFromSftp(remoteDir, workingDirPath, downloadFileList);
		}
		return downloadFileList;
	}

	private void deleteFileFromSftp(String fileName) throws Exception {
		String sftpHost = configUtil.getString("tvisbkSftpHost");
		String sftpUser = configUtil.getString("tvisbkSftpUser");
		String sftpPwd = configUtil.getString("tvisbkSftpPwd");
		String sftpPort = configUtil.getString("tvisbkSftpPort");
		String remoteDir = configUtil.getString("tvisbkFileEndDir");
		SftpUtil sftpUtil = new SftpUtil(sftpHost, Integer.valueOf(sftpPort), sftpUser, sftpPwd);
		String returnValue = sftpUtil.deleteFileToSftp(remoteDir, fileName);
		if (!returnValue.equals("success")) {
			logger.error("檔案 :" + fileName + " 刪除失敗。");
			throw new Exception("delete sftp file fail");
		}
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

	public PassbookReturnLogRenewalFileService getPassbookReturnLogRenewalFileService() {
		return passbookReturnLogRenewalFileService;
	}

	public void setPassbookReturnLogRenewalFileService(
			PassbookReturnLogRenewalFileService passbookReturnLogRenewalFileService) {
		this.passbookReturnLogRenewalFileService = passbookReturnLogRenewalFileService;
	}

	public OthBatchPassbookReturnLogService getOthBatchPassbookReturnLogService() {
		return othBatchPassbookReturnLogService;
	}

	public void setOthBatchPassbookReturnLogService(OthBatchPassbookReturnLogService othBatchPassbookReturnLogService) {
		this.othBatchPassbookReturnLogService = othBatchPassbookReturnLogService;
	}

	public FirBatchInfoService getFirBatchInfoService() {
		return firBatchInfoService;
	}

	public void setFirBatchInfoService(FirBatchInfoService firBatchInfoService) {
		this.firBatchInfoService = firBatchInfoService;
	}

}
