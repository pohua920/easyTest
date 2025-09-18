package com.tlg.aps.bs.stockholderSynchronizeServerce.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.stockholderSynchronizeServerce.RunStockholderSynchronizeService;
import com.tlg.aps.bs.stockholderSynchronizeServerce.StockholderSynchronizeService;
import com.tlg.msSqlSh.entity.CtbcStockholderTemp;
import com.tlg.msSqlSh.service.MsSqlSHSpService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.FtpClientUtil;
import com.tlg.util.Mailer;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;
import com.tlg.util.ZipUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/* mantis：OTH0101，處理人員：BJ085，需求單編號：OTH0101 取得金控利關人資料同步排程 */
@Transactional(value="msSqlShTransactionManager", propagation = Propagation.NEVER, readOnly = false, rollbackFor = Exception.class)
public class RunStockholderSynchronizeServiceImpl implements RunStockholderSynchronizeService {
	
	private static final Logger logger = Logger.getLogger(RunStockholderSynchronizeServiceImpl.class);
	private ConfigUtil configUtil;
	private StockholderSynchronizeService stockholderSynchronizeService;
	private MsSqlSHSpService msSqlSHSpService;
	private final SimpleDateFormat sysdateSDF = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	private static final String ERRFILENAME = "例外訊息.txt";

	public Result stockholderDataSynchronize(Date executeTime) throws Exception{
		String tempDir = configUtil.getString("shTempPath");
		String backupDir = configUtil.getString("shBakeupPath");
		
		List<String> fileList = new ArrayList<>();
		
		//FTP取檔案暫存到temp資料夾
		try {
			getFileFromSftp(tempDir);
			try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(tempDir))) {
				for (Path file: stream) {
					fileList.add(file.getFileName().toString());
			    }
			}
		}catch(Exception e) {
			logger.error("stockholderSynchronize exception", e);
			sendEmail(putResultMap(executeTime, new Date(), false, null), tempDir, printError(e));
			return this.getReturnResult("FTP取檔案失敗");
		}
		
		List<Map<String,Object>> stockHolderFilelist = new ArrayList<>();
		Map<String,Object> fileMap = null;
		try {
			stockholderSynchronizeService.truncateCtbcStockholderTemp();
			ZipUtil zipUtil = new ZipUtil();
			for(int i=0;i<fileList.size();i++) {
				zipUtil.unzip(tempDir+fileList.get(i), tempDir, configUtil.getString("shZipPwd"));
				String filename = fileList.get(i).substring(0,fileList.get(i).lastIndexOf("."));
				List<String> dataList = readFile(tempDir+filename+".txt");
				long dataCount = Long.parseLong(dataList.get(0).split(",")[1]);//資料總筆數
				long successCount = 0L;//成功筆數
				fileMap = new HashMap<>();
				fileMap.put("dataCount", dataCount);
				fileMap.put("filename", filename);
				fileMap.put("successCount", successCount);
				for(int j=1;j<dataList.size()-1;j++) {
					stockholderSynchronizeService.insertCtbcStockholderTemp(new CtbcStockholderTemp(dataList.get(j),filename));
					successCount ++;
					fileMap.put("successCount", successCount);
				}
				stockHolderFilelist.add(fileMap);
			}
			moveAndDeleteFile(fileList, tempDir, backupDir);
		}catch(Exception e) {
			logger.error("StockholderSynchronize error", e);
			/* mantis：OTH0134，處理人員：CC009，需求單編號：OTH0134_金控利關人同步排程APS異常修正 */
			if(fileMap != null) {
				stockHolderFilelist.add(fileMap);
			}
			sendEmail(putResultMap(executeTime, new Date(), false, stockHolderFilelist), tempDir, printError(e));
			return this.getReturnResult("處理利關人檔案流程異常");
		}
		
		try {
			msSqlSHSpService.runSpCtbcStockholder();
		} catch (Exception e) {
			logger.error("StockholderSynchronize error", e);
			sendEmail(putResultMap(executeTime, new Date(), false, stockHolderFilelist), tempDir, printError(e));
			return this.getReturnResult("備份與更新流程異常");
		}
		
		sendEmail(putResultMap(executeTime, new Date(), true, stockHolderFilelist), "", "");
		return this.getReturnResult("執行完成!");
	}
	
	private String printError(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
	
	public Map<String,Object> putResultMap(Date executeTime, Date finishTime, boolean status, List<Map<String,Object>> stockHolderFilelist){
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("executeTime", sysdateSDF.format(executeTime));
		resultMap.put("finishTime", sysdateSDF.format(finishTime));
		resultMap.put("seconds", (new BigDecimal(finishTime.getTime()-executeTime.getTime()).divide(new BigDecimal(1000))));
		resultMap.put("status", status?"成功":"異常");
		resultMap.put("resultList", stockHolderFilelist);
		return resultMap;
		
	}
	
	private List<String> readFile(String filepath) throws IOException {
		List<String> fileDataList = new ArrayList<>();
		File file = new File(filepath);
		if(file.length()==0) {
			return fileDataList;
		}
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath),StandardCharsets.UTF_8))){
			String line;
			while ((line = br.readLine()) != null) {
				if (line.trim().length() > 0) {
					fileDataList.add(line);
				}
			}
		}
		return fileDataList;
	}
	
	private void moveAndDeleteFile(List<String> fileList, String tempDir, String backupDir) throws IOException {
		File tempFileZip = null;
		File backupFile = null;
		File destFilePath = new File(backupDir);
		if(!destFilePath.exists()) {
			destFilePath.mkdirs();
		}
		for(String filename : fileList) {
			tempFileZip = new File(tempDir + filename);
			backupFile = new File(backupDir + File.separator + filename);
			if(backupFile.exists()) {
				Files.delete(backupFile.toPath()); 
			}
			Files.copy(tempFileZip.toPath(), backupFile.toPath());
			Files.delete(tempFileZip.toPath());
			Files.delete(new File(tempDir + filename.substring(0,filename.lastIndexOf("."))+".txt").toPath());
		}
	}
	
	private void sendEmail(Map<String,Object> resultMap, String tempDir, String errMsg) throws Exception {
		String[] filePath = new String[1];
		String[] fileName = new String[1];

		if(!StringUtil.isSpace(errMsg)) {
			genErrMsgFile(tempDir, errMsg);
			filePath[0] = tempDir+ERRFILENAME;
			fileName[0] = ERRFILENAME;
		}else {
			filePath = null;
			fileName = null;
		}
		
		String mailContent = genMailContent(resultMap);
		Mailer mailer = new Mailer();
		try {
			String subject = "[利關人排程通知-測試]金控利害關係人資料同步-"+resultMap.get("status");
			String mailTo = configUtil.getString("shMailTo");
			String mailCc = "";
			
			mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", 
					"", mailTo, "", mailCc, "", "", "", mailContent, "smtp","newims", "2012newims", 
					filePath, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(filePath!=null) {
				Files.delete(Paths.get(filePath[0]));
			}
		}
	}
	
	public void genErrMsgFile(String tempDir, String errMsg) {
		File file = new File(tempDir+ERRFILENAME);
		try(BufferedWriter bufWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8))){
			bufWriter.write(errMsg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String genMailContent(Map<String,Object> resultMap) throws IOException, TemplateException {
		Configuration config = new Configuration();
	    config.setClassForTemplateLoading(this.getClass(), "/");
	    Template template = config.getTemplate("stockholderSynchronizeMail.ftl", "UTF-8");
	    Map<String, Object> map = new HashMap<>();
	    map.put("result", resultMap);
	    StringWriter stringWriter = new StringWriter();
	    template.process(map, stringWriter);
	    return stringWriter.toString();
	}
	
	private void getFileFromSftp(String tempDir) throws Exception {
		File tempPath = new File(tempDir);
		if(!tempPath.exists()) {
			tempPath.mkdirs();
		}
		//將temp資料夾底下檔案刪除，以免因上一次執行異常殘留下檔案
		File[] files = tempPath.listFiles(); 
		for (int i = 0; i < files.length; i++) { 
			if (files[i].isFile()) { 
				Files.delete(files[i].toPath());
			}
		}
			
		String[] account = configUtil.getString("shAccount").split(",");
		String ftpHost = configUtil.getString("shFTP");
		
		for(int i=0;i<account.length;i++) {
			String ftpUser = configUtil.getString("shFtpUser"+account[i]);
			String ftpPwd = configUtil.getString("shFptPwd"+account[i]);
			String remoteDir = configUtil.getString("shRemotePath"+account[i]);
			
			FtpClientUtil ftpClient = new FtpClientUtil();
			ftpClient.login(ftpHost, ftpUser, ftpPwd);
			ftpClient.get(remoteDir, tempPath, "", true);
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
	
	public StockholderSynchronizeService getStockholderSynchronizeService() {
		return stockholderSynchronizeService;
	}

	public void setStockholderSynchronizeService(StockholderSynchronizeService stockholderSynchronizeService) {
		this.stockholderSynchronizeService = stockholderSynchronizeService;
	}

	public MsSqlSHSpService getMsSqlSHSpService() {
		return msSqlSHSpService;
	}

	public void setMsSqlSHSpService(MsSqlSHSpService msSqlSHSpService) {
		this.msSqlSHSpService = msSqlSHSpService;
	}
}
