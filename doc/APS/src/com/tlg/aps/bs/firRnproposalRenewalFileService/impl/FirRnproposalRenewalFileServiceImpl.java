package com.tlg.aps.bs.firRnproposalRenewalFileService.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firRnproposalRenewalFileService.FirRnproposalRenewalFileService;
import com.tlg.aps.bs.firRnproposalRenewalFileService.RnproposalRenewalFileService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchInfo;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.entity.FirCtbcRnproposalDtl;
import com.tlg.prpins.entity.FirCtbcRnproposalMain;
import com.tlg.prpins.service.FirBatchInfoService;
import com.tlg.prpins.service.FirCtbcRnproposalDtlService;
import com.tlg.prpins.service.FirCtbcRnproposalMainService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.DateUtils;
import com.tlg.util.Mailer;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.SftpUtil;
import com.tlg.util.StringUtil;
import com.tlg.util.ZipUtil;

/* mantis：FIR0458，處理人員：CC009，需求單編號：FIR0458 住火-APS中信續件要保書-資料接收排程 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirRnproposalRenewalFileServiceImpl  implements FirRnproposalRenewalFileService{
	
	private static final Logger logger = Logger.getLogger(FirRnproposalRenewalFileServiceImpl.class);
	private ConfigUtil configUtil;
	private FirBatchInfoService firBatchInfoService;
	private RnproposalRenewalFileService rnproposalRenewalFileService;
	private FirCtbcRnproposalMainService firCtbcRnproposalMainService;
	private FirCtbcRnproposalDtlService firCtbcRnproposalDtlService;
	
	private static final String OUTMSG = "outMsg";
	private static final String OUTSTATUS = "outStatus";
	
	@Override
	public Result runToProcessFile(String userId, Date excuteTime, String programId) throws SystemException, Exception {
		//檢核傳入參數
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
		String batchNo = "CTBC03_" + new SimpleDateFormat("yyMMddHHmmss").format(excuteTime);
		logger.info("batchNo ="+batchNo);
		if(!StringUtil.isSpace(sb.toString())) {
			Result result = rnproposalRenewalFileService.insertFirBatchLog(excuteTime, userId, programId,"F", sb.toString(),batchNo);
			if(result.getResObject()!=null) {
				FirBatchLog firBatchLog = (FirBatchLog) result.getResObject();
				String mailMsg = sendEmail(firBatchLog.getBatchNo(), excuteTime,"F", sb.toString(), programId);
				if(!StringUtil.isSpace(mailMsg)){
					rnproposalRenewalFileService.updateFirBatchLog("F",mailMsg, userId, firBatchLog);
				}
			} return this.getReturnResult("接收參數無值，結束排程");
			//結束排程
		}
		
		//判斷排程是否可以執行
		String status = "1";
		String msg="";
		Map<String,String> params = new HashMap<>();
		params.put("prgId",programId+"_STATUS");
		Result result = firBatchInfoService.findFirBatchInfoByUK(params);
		if(result.getResObject()!=null) {
			FirBatchInfo firBatchInfo = (FirBatchInfo)result.getResObject();
			if(firBatchInfo.getMailTo().equals("N")){
				status = "S";
				msg = "FIR_BATCH_INFO設定檔設定為排程暫停執行。";
			}
		}
		result = rnproposalRenewalFileService.insertFirBatchLog(excuteTime, userId, programId,status, msg, batchNo);
		if(status.equals("S")){
			return this.getReturnResult("查詢狀態為N，不執行排程");
		}
		
		//排程可執行，呼叫程式
		if(result.getResObject()!=null){
			FirBatchLog firBatchLog = (FirBatchLog) result.getResObject();
			BigDecimal batchLogOid = firBatchLog.getOid();
		    //取得檔案、原始資料暫存、明細檢核
			Map<String,String> returnData = null;
			returnData = temporaryDataStorage(batchNo, userId, excuteTime);
			if("N".equals(returnData.get(OUTSTATUS))) {
				updateFirBatchLog("F",returnData.get(OUTMSG),userId,batchLogOid);
				sendEmail(batchNo, excuteTime, "F", returnData.get(OUTMSG), programId);
				return this.getReturnResult("資料暫存執行失敗");
			}else if("0".equals(returnData.get(OUTSTATUS))){
				updateFirBatchLog("N",returnData.get(OUTMSG),userId,batchLogOid);
				sendEmail(batchNo, excuteTime, "N", returnData.get(OUTMSG), programId);
				return this.getReturnResult("本次沒有檔案要處理");
			}
			updateFirBatchLog("S",returnData.get(OUTMSG),userId,batchLogOid);
			sendEmail(batchNo, excuteTime, "S", returnData.get(OUTMSG), programId);
			
		}
		
		return this.getReturnResult("執行完成");
	}
	
	@SuppressWarnings("unchecked")
	private Map<String,String> temporaryDataStorage(String batchNo, String userId, Date executeTime) throws Exception {
		Map<String,String> returnData = new HashMap<>();
		if(StringUtil.isSpace(batchNo) ) {
			returnData.put(OUTSTATUS, "N");
			returnData.put(OUTMSG, "批次號碼未輸入，無法執行。");
			return returnData;
		}
		if(StringUtil.isSpace(userId) ) {
			returnData.put(OUTSTATUS, "N");
			returnData.put(OUTMSG, "執行人員未輸入，無法執行。");
			return returnData;
		}
		
		//執行時先在根目錄找是否有檔案，若無檔案，才在SFTP上抓檔案放在根目錄上，執行成功後才把檔案移到對應月份資料夾中
		List<String> downloadFileList = new ArrayList<String>();
		File rootPath = new File(configUtil.getString("localRnproposalFilePath"));
		boolean loaclPathFileCheck = false;
		String localFileName;
		if (!rootPath.exists()) {
			rootPath.mkdirs();
		}
		
		for(File file:rootPath.listFiles()) {
			localFileName = file.getName().toUpperCase();
			if(localFileName.startsWith("FKRENEW") && localFileName.endsWith(".ZIP")) {
				Map<String,String> params = new HashMap<>();
				params.put("filenameZip", localFileName);
				params.put("deleteFlag", "N");
				int dataCount = firCtbcRnproposalMainService.countFirCtbcRnproposalMain(params);
				if(dataCount <= 0) {
					downloadFileList.add(localFileName);
					loaclPathFileCheck = true;
				}
			}
		}
		
		//連線SFTP取得檔案清單，並下載檔案
		if(!loaclPathFileCheck) {
			try {
				//取sftp檔案
				downloadFileList = getZipFileFromSftp();
				//sftp無檔案
				if(downloadFileList.size() <= 0) {
					returnData.put(OUTSTATUS, "0");
					returnData.put(OUTMSG, "");
					return returnData;
				}
			} catch (Exception e) {
				e.printStackTrace();
				returnData.put(OUTSTATUS, "0");
				returnData.put(OUTMSG, "連線sftp異常");
				return returnData;			
			}
		}
		
		// 取得檔案後新增FIR_CTBC_RNROPOSAL_MAIN 中信續保要保書接收主檔
		try {
			rnproposalRenewalFileService.insertFirCtbcRnproposalMainData(batchNo, downloadFileList, userId, executeTime);
		} catch (Exception e) {
			returnData.put(OUTSTATUS, "N");
			returnData.put(OUTMSG, "FIR_CTBC_RNPROPOSAL_MAIN中信續保要保書接收主檔失敗");
			e.printStackTrace();
			return returnData;
		}
		
		
		//ZIP檔處理
		Result result = new Result();
		ZipUtil zipUtil = new ZipUtil();
		String unzipFileFolder;
		String source;
		File folder;
		String fileName;
		String workingDirPath = configUtil.getString("localRnproposalFilePath");
		String pwd = configUtil.getString("ctbcZipPwd");
		Map<String, Object> params = new HashMap<>();
		params.put("batchNo", batchNo);
		result = firCtbcRnproposalMainService.findFirCtbcRnproposalMainByParams(params);
		List<FirCtbcRnproposalMain> searchResult = (List<FirCtbcRnproposalMain>) result.getResObject();
		FirCtbcRnproposalMain firCtbcRnproposalMain;
		
		for(String zipFileName : downloadFileList) {
			firCtbcRnproposalMain = this.getFirCtbcRnproposalMain(zipFileName, searchResult);
			if(firCtbcRnproposalMain == null) {
				continue;
			}
			String batchSeq = firCtbcRnproposalMain.getBatchSeq();
			unzipFileFolder = workingDirPath+zipFileName.toUpperCase().replaceAll(".ZIP", "");
			source = workingDirPath + zipFileName;
			try {
				zipUtil.unzip(source, unzipFileFolder, pwd);
			} catch (Exception e) {
				//解壓縮失敗，回寫FirCtbcRnproposalMain錯誤狀態E：ZIP檔案異常。
				this.updateFirCtbcRnproposalMain(firCtbcRnproposalMain, "E", "ZIP檔案異常",  userId);
				continue;
			}
			
			//檢查解壓縮後資料夾是否存在
			folder = new File(unzipFileFolder);
			if(!folder.exists() || folder.list().length <= 0) {
				//回寫FirCtbcRnproposalMain錯誤狀態L：缺檔
				this.updateFirCtbcRnproposalMain(firCtbcRnproposalMain, "L", "找無解壓縮後的資料夾",  userId);
				continue;
			}
			
			boolean dtlCheck = false;
			Integer dataQty = 0;
			Integer pdfQty = 0;
			for (File fileEntry : folder.listFiles()) {
				if (fileEntry.isFile()) {
					logger.info("fileName : " + fileEntry.getName());
					fileName = fileEntry.getName().toUpperCase();
					if(fileName.startsWith("FKRENEW") && fileName.endsWith(".180")) {
						List<String> fileDataList = readFileReader(fileEntry);
						int countData = fileDataList.size();
						if(countData == 0) {
							//檔案內無資料
							this.updateFirCtbcRnproposalMain(firCtbcRnproposalMain, "Z", "檔案無資料",  userId);
							continue;
						}
						try {
							dataQty = rnproposalRenewalFileService.insertFirCtbcRnproposalDtlList(batchNo, batchSeq, fileName, userId, fileDataList);
							dtlCheck = true;
						} catch (Exception e) {
							e.printStackTrace();
							//回寫FirCtbcRnproposalMain錯誤狀態A：新增錯誤
							this.updateFirCtbcRnproposalMain(firCtbcRnproposalMain, "A", "新增錯誤",  userId);
						}
					}else if(fileName.startsWith("FKRENEW") && fileName.endsWith(".PDF")) {
						pdfQty ++;
					}
				}
			}
			
			if(!dtlCheck) {
				deleteFile(new File(source));
				deleteFile(folder);
				continue;
			}
			
			//rawData檢核
			rawDataVerify(batchNo, batchSeq, executeTime);
			
			//處理完成，刪除解壓縮檔與資料夾
			deleteFile(new File(source));
			deleteFile(folder);
			
			//zip檔處理完成，回寫FirCtbcRnproposalMain
			firCtbcRnproposalMain.setDataQty(new BigDecimal(dataQty));
			firCtbcRnproposalMain.setPdfQty(new BigDecimal(pdfQty));
			firCtbcRnproposalMain.setFileStatus("S");
			firCtbcRnproposalMain.setIupdate(userId);
			firCtbcRnproposalMain.setDupdate(new Date());
			rnproposalRenewalFileService.updateFirCtbcRnproposalMain(firCtbcRnproposalMain);
		}
		returnData.put(OUTSTATUS, "Y");
		returnData.put(OUTMSG, "");
		return returnData;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	private List<String> getZipFileFromSftp() throws Exception{
		String sftpHost = configUtil.getString("ctbcFTP");
		String sftpUser = configUtil.getString("ctbcFtpUserGet");
		String sftpPwd = configUtil.getString("ctbcFptPwdGet");
		int sftpPort = 22;
		String remoteDir = configUtil.getString("ctbcRemotePath");
		String workingDirPath = configUtil.getString("localRnproposalFilePath");
		SftpUtil sftpUtil = new SftpUtil(sftpHost, sftpPort, sftpUser, sftpPwd);
		List<String> fileList = sftpUtil.getFileListFromSftp(remoteDir);
		List<String> downloadFileList = new ArrayList<String>();
		int dataCount;
		Map params;
		if(fileList != null && fileList.size() > 0) {
			for(String fileName : fileList) {
				if(fileName.toUpperCase().startsWith("FKRENEW")) {
					params = new HashMap();
					params.put("filenameZip", fileName);
					params.put("deleteFlag", "N");
					dataCount = firCtbcRnproposalMainService.countFirCtbcRnproposalMain(params);
					if(dataCount <= 0) {
						downloadFileList.add(fileName);
					}
				}
			}
		}
		
		//下載檔案到指定目錄
		if(downloadFileList.size() > 0) {
			sftpUtil.getFileFromSftp(remoteDir, workingDirPath, downloadFileList);
		}
		return downloadFileList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Integer rawDataVerify(String batchNo, String batchSeq, Date executeTime) throws Exception{
		int pdfQty = 0;
		String rawdata;
		//pdf轉移用
		String workingDirPath = configUtil.getString("localRnproposalFilePath");
		String path = DateUtils.formatDate(executeTime);
		File localRnproposalFile = new File(workingDirPath + path.substring(0, 6) + File.separator + path);
		if(!localRnproposalFile.exists()) {
			localRnproposalFile.mkdirs();
		}
		//日期檢核用
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd");
		dFormat.setLenient(false);
		Map params = new HashMap<>();
		
		params.put("batchNo", batchNo);
		params.put("batchSeq", batchSeq);
		Result result = firCtbcRnproposalDtlService.findFirCtbcRnproposalDtlByParams(params);
		List<FirCtbcRnproposalDtl> searchResult = (List<FirCtbcRnproposalDtl>) result.getResObject();
		if(searchResult == null || searchResult.size() <= 0) {
			return 0;
		}
		for(FirCtbcRnproposalDtl dtl:searchResult) {
			StringBuilder memo = new StringBuilder();
			rawdata = dtl.getRawdata();
			if(StringUtil.isSpace(rawdata) || rawdata.split("\\|").length <=0) {
				memo.append("RAWDATA資料格式異常");
			}
			String[] ary = rawdata.split("\\|");
			if(ary.length >= 6) {
				//01資料產生日
				try {
					String dataDateStr = ary[0];
					Date dataDate = dFormat.parse(dataDateStr);
					dtl.setDataDate(dataDate);
				}catch (Exception e) {
					memo.append("資料產生日異常;");
				}
				//02保單號碼
				if(!StringUtil.isSpace(ary[1]) && ary[1].startsWith("18")) {
					dtl.setPolicyno(ary[1]);
					String pdfName = "FKRENEW" + ary[1] + ".PDF";
					try {
						File pdf = new File(workingDirPath + dtl.getFilename() + File.separator + pdfName);
						dtl.setPdfName(pdfName);
						if(pdf.exists()) {
							//複製檔案至指定路徑
							File dest = new File(localRnproposalFile.getPath() + File.separator + pdfName);
							logger.info("copy前來源檔案path :" + pdf.toPath() + " ,copy前來源檔案是否存在 :" + pdf.exists());
							logger.info("copy前目的檔案path :" + dest.toPath() + " ,copy前目的檔案是否存在 :" + dest.exists());
							if(dest.exists()) {
								dest.delete(); 
							}
							Files.copy(pdf.toPath(), dest.toPath());
							logger.info("copy後目的檔案path :" + dest.toPath() + " ,copy後目的檔案是否存在 :" + dest.exists());
							pdfQty++;
						} else {
							memo.append("無對應要保書PDF;");
						}
					} catch (IOException e) {
						logger.info(pdfName + " copy error : "+e.toString());
						memo.append("要保書PDF COPY失敗;");
					}
				}else {
					memo.append("保單號空白或非本公司之保單號;");
				}
				//03保單生效日
				try {
					String startdateStr = ary[2];
					Date startdate = dFormat.parse(startdateStr);
					String now = DateUtils.formatDate(executeTime);
					dtl.setStartdate(startdate);
					if(!startdateStr.equals(now) && !startdate.after(executeTime)) {
						memo.append("保單生效日小於系統執行日;");
					}
				} catch (Exception e) {
					memo.append("保單生效日異常;");
				}
				//04總保費
				try {
					BigDecimal sumPremium = new BigDecimal(ary[3]);
					dtl.setSumPremium(sumPremium);
					if(sumPremium.intValue() < 0 ) {
						memo.append("總保費異常;");
					}
				} catch (Exception e) {
					memo.append("總保費異常;");
				}
				//05主要保人姓名
				String nameA = ary[4];
				dtl.setNameA(nameA.trim());
				if(StringUtil.isSpace(nameA) || nameA.length() > 300) {
					memo.append("主要保人姓名異常;");
				}
				//06主被保人姓名
				String nameI = ary[5];
				dtl.setNameI(nameI.trim());
				if(StringUtil.isSpace(nameI) || nameI.length() > 300) {
					memo.append("主被保人姓名異常;");
				}
			} else {
				memo.append("RAWDATA資料格式異常");
			}
			dtl.setpMemo(memo.toString());
			dtl.setpStatus("Y");
			dtl.setIupdate("SYSTEM");
			dtl.setDupdate(new Date());
			rnproposalRenewalFileService.updateFirCtbcRnproposalDtl(dtl);
		}
		return Integer.valueOf(pdfQty);
	}
	
	private List<String> readFileReader(File file) throws IOException {
		List<String> rawDataList = new ArrayList<String>();
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String line;
        System.out.println("Reading text file using FileReader");
        while((line = br.readLine()) != null){
            //process the line
        	if(line.trim().length() > 0) {
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
	
	private void updateFirCtbcRnproposalMain(FirCtbcRnproposalMain main, String status, 
			String remark, String userId) throws Exception {
		if(remark.length() > 300) {
			remark = remark.substring(0, 300);
		}
		main.setFileStatus(status);
		main.setRemark(remark);
		main.setIupdate(userId);
		main.setDupdate(new Date());
		rnproposalRenewalFileService.updateFirCtbcRnproposalMain(main);
	}
	
	private Result getReturnResult(String msg) {
		Result result = new Result();
		Message message = new Message();
		message.setMessageString(msg);
		result.setMessage(message);
		return result;
	}
	
	private FirCtbcRnproposalMain getFirCtbcRnproposalMain(String zipFileName, List<FirCtbcRnproposalMain> list){
		if(list != null && list.size() > 0) {
			for(FirCtbcRnproposalMain entity : list) {
				if(zipFileName.equals(entity.getFilenameZip()))
					return entity;
			}
		}
		return null;
	}
	
	private void updateFirBatchLog(String status, String outMsg, String userId,BigDecimal oid) throws Exception {
		FirBatchLog firBatchLog = new FirBatchLog();
		firBatchLog.setOid(oid);
		rnproposalRenewalFileService.updateFirBatchLog(status, outMsg, userId, firBatchLog);
	 }
	
	public void deleteFile(File file) {
		if(file.exists()) {
			if(file.isFile()){ 
				file.delete();
			}else{
				File[] listFiles = file.listFiles();
				for (File file2 : listFiles) {
					deleteFile(file2);
				}
			}
			file.delete();
		}
	}
	
	@SuppressWarnings("unchecked")
	private String sendEmail(String batchNo,Date excuteTime,String status,String errMsg,String programId) {
	 Mailer mailer = new Mailer();
	 StringBuilder tmpMsg = new StringBuilder();
	 try {
		 Map<String,Object> params = new HashMap<>();
		 params.put("prgId", programId);
		 Result result = this.firBatchInfoService.findFirBatchInfoByUK(params);
		 if(result.getResObject() == null) {
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
		 if(status.equals("S")) {
			 sb.append("<p>執行狀態：完成</p>");
			 sb.append("<p>本次處理明細如下：</p>");
			 params.clear();
			 params.put("batchNo", batchNo);
			 Result mainResult = firCtbcRnproposalMainService.findFirCtbcRnproposalMainByParams(params);
			 
			 if(mainResult.getResObject()==null) {
				 sb.append("<p>FIR_CTBC_RNPROPOSAL_MAIN批次主檔查無資料，請洽系統人員。</p>");
				 tmpMsg.append("FIR_CTBC_RNPROPOSAL_MAIN批次主檔查無資料，請洽系統人員。");
			 }else {
				 sb.append("<table border=1 style='border-collapse: collapse;'>");
				 sb.append("<tr bgcolor='#70bbd9'>");
				 sb.append("<td>批次序號</td>");
				 sb.append("<td>ZIP檔案名稱</td>");
				 sb.append("<td>檔案狀態</td>");
				 sb.append("<td>資料筆數</td>");
				 sb.append("<td>PDF個數</td>");
				 sb.append("</tr>");
				 List<FirCtbcRnproposalMain> firCtbcRnproposalMainList = (List<FirCtbcRnproposalMain>) mainResult.getResObject();
				 for(FirCtbcRnproposalMain main:firCtbcRnproposalMainList) {
					 sb.append("<tr>");
					 sb.append("<td>" + main.getBatchSeq() + "</td>");
					 sb.append("<td>" + main.getFilenameZip() + "</td>");
					 sb.append("<td>" + main.getDataQty() + "</td>");
					 sb.append("<td>" + main.getPdfQty() + "</td>");
					 String fileStatus = "";
					 switch(main.getFileStatus()) { 
						 case "S" :
							 fileStatus = "正常";
							 break;
						 case "L" :
							 fileStatus = "缺檔";
							 break;
						 case "E" :
							 fileStatus = "檔案異常";
							 break;
						 case "A" :
							 fileStatus = "新增錯誤";
							 break;
						 case "Z" :
							 fileStatus = "檔案無資料";
							 break;
					 }
					 sb.append("<td>" + fileStatus + "</td>");
				 }
				 sb.append("</table>");
			 }
		 }else if (status.equals("N")) {
			 sb.append("<p>執行狀態：無檔案 </p>");
		 }else {//status = "F"
			 sb.append("<p>執行狀態：失敗</p>");
			 sb.append("<p>異常訊息：" + StringUtil.nullToSpace(errMsg) + "</p>");
		 }
		 mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", 
				 "", mailTo, "", mailCc, "", "", "", sb.toString(), "smtp","newims", "2012newims");
	 } catch (Exception e) {
		 e.printStackTrace();
	 }
	 	return tmpMsg.toString();
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

	public RnproposalRenewalFileService getRnproposalRenewalFileService() {
		return rnproposalRenewalFileService;
	}

	public void setRnproposalRenewalFileService(RnproposalRenewalFileService rnproposalRenewalFileService) {
		this.rnproposalRenewalFileService = rnproposalRenewalFileService;
	}

	public FirCtbcRnproposalMainService getFirCtbcRnproposalMainService() {
		return firCtbcRnproposalMainService;
	}

	public void setFirCtbcRnproposalMainService(FirCtbcRnproposalMainService firCtbcRnproposalMainService) {
		this.firCtbcRnproposalMainService = firCtbcRnproposalMainService;
	}

	public FirCtbcRnproposalDtlService getFirCtbcRnproposalDtlService() {
		return firCtbcRnproposalDtlService;
	}

	public void setFirCtbcRnproposalDtlService(FirCtbcRnproposalDtlService firCtbcRnproposalDtlService) {
		this.firCtbcRnproposalDtlService = firCtbcRnproposalDtlService;
	}

}
