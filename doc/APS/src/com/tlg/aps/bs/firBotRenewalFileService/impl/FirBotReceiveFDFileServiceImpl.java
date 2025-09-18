package com.tlg.aps.bs.firBotRenewalFileService.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firBotRenewalFileService.BotReceiveFileService;
import com.tlg.aps.bs.firBotRenewalFileService.BotRenewalFileService;
import com.tlg.aps.bs.firBotRenewalFileService.FirBotReceiveFDFileService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtBatchMain;
import com.tlg.prpins.entity.FirAgtBotFd;
import com.tlg.prpins.entity.FirBatchInfo;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.service.FirAgtBatchMainService;
import com.tlg.prpins.service.FirAgtBotFdService;
import com.tlg.prpins.service.FirBatchInfoService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Mailer;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.SftpUtil;
import com.tlg.util.StringUtil;

/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程 **/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirBotReceiveFDFileServiceImpl implements FirBotReceiveFDFileService {

	private static final Logger logger = Logger.getLogger(FirBotReceiveFDFileServiceImpl.class);
	private ConfigUtil configUtil;
	private FirBatchInfoService firBatchInfoService;
	private FirAgtBatchMainService firAgtBatchMainService;
	private BotReceiveFileService botReceiveFileService;
	private FirAgtBotFdService firAgtBotFdService;
	

	private static final String ROOTDIRECTORY = "D:" + File.separator + "BOT_FD" + File.separator;
	private static final String OUTMSG = "msg";
	private static final String OUTSTATUS = "status";

	private BotRenewalFileService botRenewalFileService;

	@Override
	public Result runToReceiveData(String userId, Date excuteTime, String programId) throws Exception {
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

		FirBatchLog firBatchLog;
		String batchNo = "BOT_FD_" + new SimpleDateFormat("yyMMddHHmmss").format(excuteTime);
		if (!StringUtil.isSpace(sb.toString())) {
			Result result = botRenewalFileService.insertFirBatchLog(excuteTime, userId, programId, "F", sb.toString(),
					batchNo);
			if (result.getResObject() != null) {
				firBatchLog = (FirBatchLog) result.getResObject();
				String mailMsg = sendEmail(firBatchLog.getBatchNo(), excuteTime, "F", sb.toString(), programId);
				if (!StringUtil.isSpace(mailMsg)) {
					botRenewalFileService.updateFirBatchLog("F", mailMsg, userId, firBatchLog);
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
			if ("N".equals(firBatchInfo.getMailTo())) {
				status = "S";
				msg = "FIR_BATCH_INFO設定檔設定為排程暫停執行。";
			}
		} else {
			return this.getReturnResult("查無此排程代號，請詢問技術人員");
		}

		result = botRenewalFileService.insertFirBatchLog(excuteTime, userId, programId, status, msg, batchNo);
		firBatchLog = (FirBatchLog) result.getResObject();
		if (status.equals("S")) {
			return this.getReturnResult("查詢狀態為N，不執行排程");
		}
		if (result.getResObject() != null) {
			BigDecimal batchLogOid = firBatchLog.getOid();
			Map<String, String> returnData = null;

			returnData = writeData(batchNo, excuteTime, userId);

			if ("Y".equals(returnData.get(OUTSTATUS))) {
				updateFirBatchLog("S", returnData.get(OUTMSG), userId, batchLogOid);
				sendEmail(batchNo, excuteTime,"S", returnData.get(OUTMSG), programId);
			} else if ("0".equals(returnData.get(OUTSTATUS))) {
				updateFirBatchLog("N", "", userId, batchLogOid);
				sendEmail(batchNo, excuteTime, "N", returnData.get(OUTMSG), programId);
				return this.getReturnResult("無資料");
			} else {
				updateFirBatchLog("F", returnData.get(OUTMSG), userId, batchLogOid);
				sendEmail(batchNo, excuteTime, "F", returnData.get(OUTMSG), programId);
				return this.getReturnResult("執行失敗");
			}

		}

		return this.getReturnResult("執行完成");
	}

	public Map<String, String> writeData(String batchNo, Date excuteTime, String userId)
			throws SystemException, Exception {
		Map<String, String> returnData = new HashMap<>();
		Map<String, String> fileReturn = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		if (StringUtil.isSpace(batchNo)) {
			sb.append("執行人員無內容值。");
		}
		if (excuteTime == null) {
			sb.append("轉檔時間無內容值。");
		}
		if (StringUtil.isSpace(userId)) {
			sb.append("程式代碼無內容值。");
		}
		if (!StringUtil.isSpace(sb.toString())) {
			returnData.put(OUTSTATUS, "N");
			returnData.put(OUTMSG, sb.toString());
			return returnData;
		}

		fileReturn = getFileFromSftp();
		
		if (fileReturn != null) {
			if ("fail".equals(fileReturn.get("result"))) {
				returnData.put(OUTSTATUS, "F");
				returnData.put(OUTMSG, "連線sftp異常");
				return returnData;
			} else {
				if ("0".equals(fileReturn.get("unhandleNum"))) {
					returnData.put(OUTSTATUS, "0"); // 檔案無資料
					returnData.put(OUTMSG, "");
					return returnData;
				} else if ("2".equals(fileReturn.get("unhandleNum"))) {
					sb.append("排程一次僅能處理一個檔案，FTP有多個檔案未處理，請通知系統人員手動執行 ");
				}

				try {
					Result result = botReceiveFileService.insertFirAgtBatchMain(batchNo, "I99004", "02",
							fileReturn.get("filename"), "N", "N", userId);
					if (result.getResObject() != null) {
						int fileQty = 0;// 資料總筆數
						int filePqty = 0;// 資料處理筆數
						String fileStatus = "";
						String remark = "";
						String filePath = fileReturn.get("filePath");
						List<String> dataList = readFile(filePath);
						fileQty = dataList.size();
						if (fileQty == 0) {
							// 檔案內無資料
							fileStatus = "Z";
							remark = sb.toString();
							try {
								botReceiveFileService.updateFirAgtBatchMain(batchNo, userId, fileQty, filePqty,
										fileStatus, remark);
								returnData.put(OUTSTATUS, "0");
								returnData.put(OUTMSG, "");
								return returnData;
							} catch (Exception e) {
								returnData.put(OUTSTATUS, "N");
								returnData.put(OUTMSG, e.toString());
							}

						} else {
							List<FirAgtBotFd> firAgtBotFdList =new ArrayList<>();
							try {
								for (int j = 0; j < dataList.size(); j++) {
									String[] rawData = dataList.get(j).split("\t");
									FirAgtBotFd firAgtBotFd = setFirAgtBotFd(rawData, userId, batchNo);
									firAgtBotFdList.add(firAgtBotFd);
									filePqty++;
								}
								botReceiveFileService.insertBotFdAndUpdateFirAgtBatchMain(firAgtBotFdList, batchNo,userId);
							} catch (Exception e) {
								e.printStackTrace();
								fileStatus = "E";
								 remark=e.toString();
								if(remark.length()>1000){
									remark=remark.substring(remark.length()-1000);
								}
								sb.append(remark);
								botReceiveFileService.updateFirAgtBatchMain(batchNo, userId, dataList.size(), filePqty,fileStatus,
										remark);
								returnData.put(OUTSTATUS, "F"); 
								returnData.put(OUTMSG, sb.toString());
								return returnData;
							}
							returnData.put(OUTSTATUS, "Y"); 
							returnData.put(OUTMSG, sb.toString());
						}
					}
				} catch (Exception e) {
					returnData.put(OUTSTATUS, "F");
					sb.append("新增FIR_AGT_BATCH_MAIN火險保經代資料交換批次主檔失敗");
					returnData.put(OUTMSG, sb.toString());
					return returnData;
				}

			}

		}
		return returnData;
	}

	// 取得sftp檔案
	private Map<String, String> getFileFromSftp() {
		String strResult = "";
		String sftpHost = configUtil.getString("botFTP");
		String sftpUser = configUtil.getString("botFtpUserPut");
		String sftpPwd = configUtil.getString("botFptPwdPut");
		int sftpPort = Integer.parseInt(configUtil.getString("botFtpPort"));
		String remoteDir = configUtil.getString("botRemotePath");
		Map<String, String> returnResult = new HashMap<>();
		try {
//			正式要開 start
//			// 取得SFTP上面檔案名稱
			SftpUtil sftpUtil = new SftpUtil(sftpHost, sftpPort, sftpUser, sftpPwd);
//			List<String> fileList = sftpUtil.getFileListFromSftp(remoteDir, 1);
//			if (null == fileList) {
//				returnResult.put("result", "fail");
//				return returnResult;
//			}
//			正式要開 end

			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			Date datePeriod = calendar.getTime();

			Map<String, Object> params = new HashMap<>();
			params.put("DELETE_FLAG", "N");
			params.put("BUSINESSNATURE", "I99004");
			params.put("BATCH_TYPE", "02");
			params.put("datePeriod", datePeriod);
			Result result = firAgtBatchMainService.findFirAgtBatchMainByParams(params);

			List<FirAgtBatchMain> handleFileList = new ArrayList<>();// 已處理檔案
			List<String> handleNameList = new ArrayList<>();// 已處理檔案名稱
			List<String> downloadFileList = new ArrayList<>();// 本次下載檔案
			List<String> unHandleFileList = new ArrayList<>();// 未處理檔案
			if (result.getResObject() != null) {
				handleFileList = (List<FirAgtBatchMain>) result.getResObject();
				if (handleFileList.size() > 0) {
					for (FirAgtBatchMain main : handleFileList) {
						handleNameList.add(main.getFileName());
					}
				}
			}
			//正式要關 start
			List<String> fileList=new ArrayList<>();
			String folderPath ="D:\\BotFd";
			File folderT = new File(folderPath);
			String []listFiles = folderT.list();
			for(String file:listFiles){
				fileList.add(file);
			}
			//正式要關 end
			for (String fileName : fileList) {
				if (fileName.contains("FDX") && !handleNameList.contains(fileName)) {
					unHandleFileList.add(fileName);
				}
			}
			if (!unHandleFileList.isEmpty()) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
				String folder = sdf.format(new Date());
				String root = ROOTDIRECTORY + folder + File.separator;
				root="D:\\BotFd\\";//正式要關
				File rootPath = new File(root);
				if (!rootPath.exists()) {
					rootPath.mkdirs();
				}
				downloadFileList.add(unHandleFileList.get(0));
				
//				strResult = sftpUtil.getFileFromSftp(remoteDir, root, downloadFileList);//正式要開 
				strResult="success";//正式要關
				returnResult.put("result", strResult);
				returnResult.put("unhandleNum", "1");
				returnResult.put("filename", downloadFileList.get(0));
				returnResult.put("filePath", root + downloadFileList.get(0));
				if (unHandleFileList.size() > 1) {
					returnResult.put("unhandleNum", "2");
				}
			} else {
				returnResult.put("result", "sucess");
				returnResult.put("unhandleNum", "0");
			}

		} catch (Exception e) {
			logger.error("BOTFD getFileFromSftp Exception", e);
			returnResult.put("result", "fail");
			return returnResult;
		}
		return returnResult;
	}

	// 讀取檔案
	private List<String> readFile(String filepath) throws IOException {
		List<String> fileDataList = new ArrayList<>();
		File file = new File(filepath);
		if (file.length() == 0) {
			return new ArrayList<>();
		}
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), "UTF-8"))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.trim().length() > 0) {
					fileDataList.add(line);
				}
			}
		}
		return fileDataList;
	}

	private FirAgtBotFd setFirAgtBotFd(String[] rawData, String userId, String batchNo) throws Exception {
		FirAgtBotFd firAgtBotFd = new FirAgtBotFd();
		firAgtBotFd.setBatchNo(batchNo);// 批次號
		firAgtBotFd.setFdType(rawData[0]);
		firAgtBotFd.setOrderseq(rawData[1]);
		firAgtBotFd.setFdDate(dateFormat(rawData[2]));
		firAgtBotFd.setStartdateF(dateFormat(rawData[3]));
		firAgtBotFd.setStartdateQ(dateFormat(rawData[4]));
		firAgtBotFd.setAccountNo(rawData[5]);
		firAgtBotFd.setBranchNo(rawData[6]);
		firAgtBotFd.setBankSales(rawData[7]);
		firAgtBotFd.setIsApproval(rawData[8]);
		firAgtBotFd.setIsAutoRenew(rawData[9]);
		firAgtBotFd.setStartdateA(dateFormat(rawData[10]));
		firAgtBotFd.setAmountF("".equals(rawData[11])?null:Long.parseLong(rawData[11]));
		firAgtBotFd.setAmountQ("".equals(rawData[12])?null:Long.parseLong(rawData[12]));
		firAgtBotFd.setAmountA("".equals(rawData[13])?null:Integer.parseInt(rawData[13]));
		firAgtBotFd.setLoanaccount(rawData[14]);
		firAgtBotFd.setReplaceFlag("1");
		firAgtBotFd.setIcreate(userId);
		firAgtBotFd.setDcreate(new Date());
		firAgtBotFd.setCompareFlag("N");
		
		return  firAgtBotFd;
	}

	private Date dateFormat(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			return null;
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
//			 mailTo = "cd094@ctbcins.com,bj085@ctbcins.com";
			String mailCc = firBatchInfo.getMailCc();

			StringBuilder sb = new StringBuilder();
			sb.append("<p>批次號碼：" + batchNo + "</p>");
			sb.append("<p>轉檔時間：" + sdf.format(excuteTime) + "</p>");
			if (status.equals("S")) {
				sb.append("<p>執行狀態：完成</p>");
				sb.append("<p>錯誤訊息："+errMsg+"</p>");
				params.clear();
				params.put("batchNo", batchNo);
				Result mainResult = firAgtBatchMainService.findFirAgtBatchMainByParams(params);

				params.clear();
				params.put("batchNo", batchNo);
				params.put("sortBy", "ORDERSEQ");
				Result agtBotFdResult = firAgtBotFdService.findFirAgtBotFdByParams(params);
				if (mainResult.getResObject() == null) {
					tmpMsg.append("FIR_AGT_BATCH_MAIN批次主檔查無資料，請洽系統人員。");
				}else {
					sb.append("<p>本次處理明細如下：</p>");
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
						String fileStatus = firAgtBatchMain.getFileStatus();
						if ("N".equals(fileStatus)) {
							fileStatus = "未處理";
						} else if ("Y".equals(fileStatus)) {
							fileStatus = "已處理";
						} else if ("Z".equals(fileStatus)) {
							fileStatus = "檔案無資料";
						} else if ("E".equals(fileStatus)) {
							fileStatus = "檔案/執行異常";
						} else {
							fileStatus = "未定義";
						}
						sb.append("<td>" + fileStatus + "</td>");
						sb.append("<td>" + firAgtBatchMain.getFileQty() + "</td>");
						sb.append("<td>" + firAgtBatchMain.getFilePqty() + "</td>");
						sb.append("<td>" + StringUtil.nullToSpace(firAgtBatchMain.getRemark()) + "</td>");
						sb.append("</table>");
					
					if(agtBotFdResult.getResObject()!=null){
						sb.append("<p>本次處理受理編號如下：</p>");
						sb.append("<table border=1 style='border-collapse: collapse;'>");
						sb.append("<tr bgcolor='#70bbd9'>");
						sb.append("<td>受理編號</td>");
						sb.append("<td>類型</td>");
						sb.append("<td>檢核結果</td>");
						sb.append("</tr>");
						List<FirAgtBotFd> firAgtBotFdList = (List<FirAgtBotFd>) agtBotFdResult.getResObject();
						for (FirAgtBotFd firAgtBotFd : firAgtBotFdList) {
							sb.append("<tr>");
							sb.append("<td>" + firAgtBotFd.getOrderseq() + "</td>");
							sb.append("<td>" + firAgtBotFd.getFdType() + "</td>");
							if(firAgtBotFd.getCompareResult()==null){
								sb.append("<td></td>");
							}else{
								sb.append("<td>" + firAgtBotFd.getCompareResult() + "</td>");
							}
							sb.append("</tr>");
						}
						sb.append("</table>");
					}else if(agtBotFdResult.getResObject() == null && firAgtBatchMain.getFileQty() != 0){
						tmpMsg.append("FIR_AGT_BOT_FD 火險保經代臺銀簽署檔FD查無資料，請洽系統人員。");
						
					}
				}

			} else if (status.equals("N")) {
				sb.append("<p>執行狀態：無檔案 </p>");
			} else {// status = "F"
				sb.append("<p>執行狀態：失敗</p>");
				sb.append("<p>異常訊息：" + StringUtil.nullToSpace(errMsg) + "</p>");
			}
			mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", "", mailTo,
					"", mailCc, "", "", "", sb.toString(), "smtp", "newims", "2012newims");
		} catch (Exception e) {
			logger.error("BOTFD sendEmail Exception", e);
		}
		return tmpMsg.toString();
	}

	private void updateFirBatchLog(String status, String outMsg, String userId, BigDecimal oid) throws Exception {
		FirBatchLog firBatchLog = new FirBatchLog();
		firBatchLog.setOid(oid);
		botRenewalFileService.updateFirBatchLog(status, outMsg, userId, firBatchLog);
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

	public BotRenewalFileService getBotRenewalFileService() {
		return botRenewalFileService;
	}

	public void setBotRenewalFileService(BotRenewalFileService botRenewalFileService) {
		this.botRenewalFileService = botRenewalFileService;
	}

	public FirAgtBatchMainService getFirAgtBatchMainService() {
		return firAgtBatchMainService;
	}

	public void setFirAgtBatchMainService(FirAgtBatchMainService firAgtBatchMainService) {
		this.firAgtBatchMainService = firAgtBatchMainService;
	}

	public BotReceiveFileService getBotReceiveFileService() {
		return botReceiveFileService;
	}

	public void setBotReceiveFileService(BotReceiveFileService botReceiveFileService) {
		this.botReceiveFileService = botReceiveFileService;
	}

	public FirAgtBotFdService getFirAgtBotFdService() {
		return firAgtBotFdService;
	}

	public void setFirAgtBotFdService(FirAgtBotFdService firAgtBotFdService) {
		this.firAgtBotFdService = firAgtBotFdService;
	}
	

}
