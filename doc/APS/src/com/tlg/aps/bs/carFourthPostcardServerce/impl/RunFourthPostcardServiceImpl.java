package com.tlg.aps.bs.carFourthPostcardServerce.impl;

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
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.carFourthPostcardServerce.FourthPostcardService;
import com.tlg.aps.bs.carFourthPostcardServerce.RunFourthPostcardService;
// mantis：CAR0554，處理人員：DP0714，APS-TVBCM&TVMCQ作業通知信調整
import com.tlg.aps.vo.TiiTvmcqVo;
import com.tlg.prpins.entity.Prpcmain;
import com.tlg.prpins.entity.Renewalnotice;
import com.tlg.prpins.entity.TiiTvmcq;
import com.tlg.prpins.service.PrpcmainService;
import com.tlg.prpins.service.RenewalnoticeService;
import com.tlg.prpins.service.TiiTvmcqService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Mailer;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;
import com.tlg.util.ZipUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/*mantis：CAR0427，處理人員：BJ085，需求單編號：CAR0427 機車強制車險重新投保發對接功能-第四次明信片*/
@Transactional(value="prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class RunFourthPostcardServiceImpl implements RunFourthPostcardService {
	
	private static final Logger logger = Logger.getLogger(RunFourthPostcardServiceImpl.class);
	
	private TiiTvmcqService tiiTvmcqService;
	private PrpcmainService prpcmainService;
	private FourthPostcardService fourthPostcardService;
	private RenewalnoticeService renewalnoticeService;
	
	private ConfigUtil configUtil;
	private final SimpleDateFormat sysdateSdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	private final SimpleDateFormat renewalSdf = new SimpleDateFormat("yyyyMMdd");
	private static final String ERRFILENAME = "例外訊息.txt";

	@SuppressWarnings("unchecked")
	public Result readFileAndImportData(Date executeTime) throws Exception{
		String workDir = configUtil.getString("tvmcqWorkPath");
		String backupDir = configUtil.getString("tvmcqBackupPath");
		String filename = "";
		List<String> fileList = new ArrayList<>();
		List<Map<String,Object>> tvmcqFilelist = new ArrayList<>();
		Map<String, Object> fileMap = new HashMap<>();
		List<String> filenameList = new ArrayList<>();
		List<String> notEmptyList = new ArrayList<>();
		
		//取得第四次明信片檔案解壓縮後匯入到第四次明信片資料表
		String batchno = "TVMCQ"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		try {
			fileList = getFileFromWorkDir(workDir);
			if (fileList.isEmpty()) {
				sendEmail(putResultMap(executeTime, true, null, "作業資料夾中無檔案"), "");
				return this.getReturnResult("作業資料夾中無檔案");
			}

			//可能有多個zip檔、多個解壓縮後的檔案
			for(String ognfilename : fileList) {
				filenameList = unzipFile(workDir, ognfilename);
				
				for (int i = 0; i < filenameList.size(); i++) {
					filename = filenameList.get(i);
					List<String> dataList = readFile(workDir + filename);
					if(!dataList.isEmpty()) {
						notEmptyList.add(filename);
					}
					Long id = (long) tiiTvmcqService.findMaxID();
					
					// 匯入第四次明信片資料存放表
					int successCount = 0;
					fileMap = new HashMap<>();
					fileMap.put("dataCount", dataList.size());
					fileMap.put("filename", filename);
					fileMap.put("successCount", successCount);
					for (int j = 0; j < dataList.size(); j++) {
						id = id + 1;
						String[] dataArray = dataList.get(j).split("\\|\\|");
						for (int k = 0; k < dataArray.length; k++) {
							dataArray[k] = dataArray[k].replaceAll("^[\"]", "").replaceAll("[\"]$", "");
						}
						TiiTvmcq tiiTvmcq = new TiiTvmcq();
						tiiTvmcq.setId(id);
						tiiTvmcq.setBarcode1(dataArray[0]);
						tiiTvmcq.setBarcode2(dataArray[1]);
						tiiTvmcq.setBarcode3(dataArray[2]);
						tiiTvmcq.setBillduedate(dataArray[3]);
						tiiTvmcq.setInsuredcardno(dataArray[4]);
						tiiTvmcq.setCarno(dataArray[5]);
						tiiTvmcq.setInsfee(dataArray[6]);
						tiiTvmcq.setOperatedate(new Date());
						tiiTvmcq.setFilename(filename);
						tiiTvmcq.setBatchno(batchno);
						fourthPostcardService.insertTiiTvmcq(tiiTvmcq);
						successCount++;
						fileMap.put("successCount", successCount);
					}
					tvmcqFilelist.add(fileMap);
				}
				moveAndDeleteFile(workDir, backupDir, filenameList, ognfilename);
			}
		} catch (Exception e) {
			logger.error("carFourthPostcard import exception", e);
			sendEmail(putResultMap(executeTime, false, tvmcqFilelist, ""), printError(e));
			return this.getReturnResult("檔案匯入失敗");
		}
		
		
		//依匯入資料表生成相對應資料回寫核心並呼叫核心URL
		Map<String, String> params;
		for (int i = 0; i < notEmptyList.size(); i++) {
			params = new HashMap<>();
			filename = notEmptyList.get(i);

			params.put("batchno", batchno);
			params.put("filename", filename);
			Result result = tiiTvmcqService.findTiiTvmcqByParams(params);
			if (result.getResObject() == null) {
				sendEmail(putResultMap(executeTime, false, tvmcqFilelist, ""),
						"執行失敗，查無此批次匯入TII_TVMCQ資料，檔案名稱:" + filename);
				return this.getReturnResult("執行失敗，查無此批次匯入TII_TVMCQ資料，檔案名稱:" + filename);
			}

			String checktype;
			List<TiiTvmcq> tiiTvmcqList = (List<TiiTvmcq>) result.getResObject();
			
			for (TiiTvmcq tiiTvmcq : tiiTvmcqList) {
				try {
					String insuredcardno = tiiTvmcq.getInsuredcardno();
					checktype = "1";
					Renewalnotice renewalnotice = new Renewalnotice();
					params.clear();
					params.put("printno", insuredcardno);
					params.put("riskcode", "B01");
					result = prpcmainService.findPrpcmainByParams(params);
					if (result.getResObject() == null) {
						continue;
					}
					
					params.put("filename", filename);
					params.remove("riskcode");
					if(renewalnoticeService.countRenewalnotice(params) > 0) {
						checktype = "2";
						tiiTvmcq.setChecktype(checktype);
						fourthPostcardService.updateTiiTvmcq(tiiTvmcq);
						continue;
					}
					
					List<Prpcmain> resultList = (List<Prpcmain>) result.getResObject();
					renewalnotice.setPolicyno(resultList.get(0).getPolicyno());
					renewalnotice.setEnddate(getEnddate(tiiTvmcq.getBillduedate()));
					renewalnotice.setUsenaturecode("M");
					renewalnotice.setNoticetimes("4");
					renewalnotice.setOperatedate(new Date());
					renewalnotice.setFilename(filename);
					renewalnotice.setBusinessnature("Y00013");
					renewalnotice.setHandlercode("A9999");
					renewalnotice.setPrintno(insuredcardno);
					fourthPostcardService.insertRenewalnotice(renewalnotice);
					checktype = "0";
				}catch (Exception e) {
					checktype = "3";
					logger.error("carFourthPostcard exception", e);
				}
				tiiTvmcq.setChecktype(checktype);
				fourthPostcardService.updateTiiTvmcq(tiiTvmcq);
			}
			callUrl(filename);
		}
		sendEmail(putResultMap(executeTime, true, tvmcqFilelist, ""), "");
		return this.getReturnResult("執行完成!");

	}
	
	//檢查前一天匯入的資料，產生excel檔案通知USER
	@SuppressWarnings("unchecked")
	public void checkTvmcqAndSendMail() throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -1);
		String lastday = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
		
		Map<String,String> params = new HashMap<>();
		params.put("lastOperatedate", lastday);
		// mantis：CAR0554，處理人員：DP0714，APS-TVBCM&TVMCQ作業通知信調整
		Result result = tiiTvmcqService.selectTiiTvmcq2();
		if(result.getResObject()!=null) {
			try(XSSFWorkbook workbook = new XSSFWorkbook()){
				XSSFSheet sheet = workbook.createSheet();
				// mantis：CAR0554，處理人員：DP0714，APS-TVBCM&TVMCQ作業通知信調整
				String[] titleArr = {"ID", "繳費條碼1", "繳費條碼2", "繳費條碼3", "繳費期限", "到期保險證號", "車號", 
						"保險費", "資料建檔時間", "檔案名稱", "產生的報價單號", "狀態", "批次號","核保狀態","虛擬碼"};
				XSSFRow rowTitle = sheet.createRow(0);
				for(int i=0;i<titleArr.length;i++) {
					rowTitle.createCell(i).setCellValue(titleArr[i]);
				}
				// mantis：CAR0554，處理人員：DP0714，APS-TVBCM&TVMCQ作業通知信調整
				List<TiiTvmcqVo> dataList = (List<TiiTvmcqVo>) result.getResObject();
				for(int i = 0; i < dataList.size(); i++) {
					XSSFRow row = sheet.createRow(i + 1); // 建立儲存格
					row.createCell(0).setCellValue(dataList.get(i).getId());
					row.createCell(1).setCellValue(dataList.get(i).getBarcode1());
					row.createCell(2).setCellValue(dataList.get(i).getBarcode2());
					row.createCell(3).setCellValue(dataList.get(i).getBarcode3());
					row.createCell(4).setCellValue(dataList.get(i).getBillduedate());
					row.createCell(5).setCellValue(dataList.get(i).getInsuredcardno());
					row.createCell(6).setCellValue(dataList.get(i).getCarno());
					row.createCell(7).setCellValue(dataList.get(i).getInsfee());
					row.createCell(8).setCellValue(sysdateSdf.format(dataList.get(i).getOperatedate()));
					row.createCell(9).setCellValue(dataList.get(i).getFilename());
					row.createCell(10).setCellValue(dataList.get(i).getProposalno());
					row.createCell(11).setCellValue(dataList.get(i).getChecktype());
					row.createCell(12).setCellValue(dataList.get(i).getBatchno());
					// mantis：CAR0554，處理人員：DP0714，APS-TVBCM&TVMCQ作業通知信調整 -- start
					row.createCell(13).setCellValue(dataList.get(i).getUnderWriteFlag());
					row.createCell(14).setCellValue(dataList.get(i).getPrintvirtualcode());
					// mantis：CAR0554，處理人員：DP0714，APS-TVBCM&TVMCQ作業通知信調整 -- end
				}
				
				String tempDir = configUtil.getString("tempFolder");
				
				String filename = "TVMCQ"+lastday+".xlsx";
				FileOutputStream fos = new FileOutputStream(new File(tempDir+filename));
				workbook.write(fos);
				sendEmail2(tempDir, filename);
			}
		}
	}
	
	//呼叫核心URL，不需等待回應，設定timeout時間，讓程式繼續往下執行
	private void callUrl(String filename) throws IOException {
		String url = configUtil.getString("tvbcmCallUrl")+filename;
		
		logger.info("carFourthPostcard url:"+url);
		SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(5000).build();
		try(CloseableHttpClient httpClient = HttpClients.custom().setDefaultSocketConfig(socketConfig).build()){
			httpClient.execute(new HttpGet(url));
		}catch(SocketTimeoutException se) {
			logger.info("carFourthPostcard url catch timeout to continue");
		}
	}
	
	private void sendEmail(Map<String,Object> resultMap, String errMsg) throws Exception {
		String tempDir = configUtil.getString("tempFolder");
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
			String subject = "[關貿第四次明信片通知] 執行作業(測試)-"+resultMap.get("status");
			String mailTo = configUtil.getString("tvmcqMailTo");
			String mailCc = configUtil.getString("tvmcqMailCc");
			
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
	
	private void sendEmail2(String tempDir, String filename) throws Exception {
		String[] filePath = new String[1];
		String[] fileName = new String[1];

		if(!StringUtil.isSpace(filename)) {
			filePath[0] = tempDir+File.separator+filename;
			fileName[0] = filename;
		}else {
			filePath = null;
			fileName = null;
		}
		
		String mailContent = "<B>關貿第四次明信片排程信件通知</B><BR/>";
		Mailer mailer = new Mailer();
		try {
			String subject = "[關貿第四次明信片排程] 通知作業(測試)";
			String mailTo = configUtil.getString("tvmcqMailTo");
			String mailCc = configUtil.getString("tvmcqMailCc");
			
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
	
	public String genMailContent(Map<String,Object> resultMap) throws IOException, TemplateException {
		Configuration config = new Configuration();
	    config.setClassForTemplateLoading(this.getClass(), "/");
	    Template template = config.getTemplate("ftl/fourthPostcardMail.ftl", "UTF-8");
	    Map<String, Object> map = new HashMap<>();
	    map.put("result", resultMap);
	    StringWriter stringWriter = new StringWriter();
	    template.process(map, stringWriter);
	    return stringWriter.toString();
	}
	
	private List<String> getFileFromWorkDir(String workDir) throws IOException {
		File workPath = new File(workDir);
		if (!workPath.exists()) {
			workPath.mkdirs();
		}

		List<String> fileList = new ArrayList<>();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(workDir))) {
			for (Path file : stream) {
				fileList.add(file.getFileName().toString());
			}
		}
		return fileList;
	}
	
	private List<String> unzipFile(String workDir, String filename) throws Exception {
		ZipUtil zipUtil = new ZipUtil();
		if(filename.contains(".zip")) {
			zipUtil.unzip(workDir + filename, workDir, configUtil.getString("tvmcqZipPwd"));
		}
		List<String> filenameList = new ArrayList<>();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(workDir))) {
			for (Path file : stream) {
				if(!file.getFileName().toString().contains(".zip")) {
					filename = file.getFileName().toString();
					filenameList.add(filename);
				}
			}
		}
		return filenameList;
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
	
	private void moveAndDeleteFile(String workDir, String backupDir, List<String> fileList, String zipFilename) throws Exception {
		File workFile = null;
		File zipWorkFile = null;
		File backupFile = null;
		
		File destFilePath = new File(backupDir);
		if(!destFilePath.exists()) {
			destFilePath.mkdirs();
		}
		
		for(String filename : fileList) {
			workFile = new File(workDir + filename);
			Files.delete(workFile.toPath());
		}
		
		zipWorkFile = new File(workDir + zipFilename);
		if(zipFilename.contains(".zip")) {
			backupFile = new File(backupDir + File.separator + zipFilename);
			if(backupFile.exists()) {
				Files.delete(backupFile.toPath()); 
			}
			Files.copy(zipWorkFile.toPath(), backupFile.toPath());
			Files.delete(zipWorkFile.toPath());
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
	
	public Map<String,Object> putResultMap(Date executeTime, boolean status, List<Map<String,Object>> stockHolderFilelist, String msg){
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("executeTime", sysdateSdf.format(executeTime));
		resultMap.put("status", status?"成功":"異常");
		resultMap.put("resultList", stockHolderFilelist);
		resultMap.put("msg", msg);
		return resultMap;
	}
	
	private Date getEnddate(String billduedate) throws Exception{
		if(StringUtil.isSpace(billduedate)) {
			return null;
		}
		Date date;
		try {
			date = renewalSdf.parse(billduedate);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		//mantis：CAR0438，處理人員：BI086，需求單編號：CAR0438 原程式的規則移除，不需-45 day
//		cal.add(Calendar.DATE, -45);
		return cal.getTime();
	}
	
	private String printError(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
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

	public TiiTvmcqService getTiiTvmcqService() {
		return tiiTvmcqService;
	}

	public void setTiiTvmcqService(TiiTvmcqService tiiTvmcqService) {
		this.tiiTvmcqService = tiiTvmcqService;
	}

	public PrpcmainService getPrpcmainService() {
		return prpcmainService;
	}

	public void setPrpcmainService(PrpcmainService prpcmainService) {
		this.prpcmainService = prpcmainService;
	}

	public FourthPostcardService getFourthPostcardService() {
		return fourthPostcardService;
	}

	public void setFourthPostcardService(FourthPostcardService fourthPostcardService) {
		this.fourthPostcardService = fourthPostcardService;
	}

	public RenewalnoticeService getRenewalnoticeService() {
		return renewalnoticeService;
	}

	public void setRenewalnoticeService(RenewalnoticeService renewalnoticeService) {
		this.renewalnoticeService = renewalnoticeService;
	}

}
