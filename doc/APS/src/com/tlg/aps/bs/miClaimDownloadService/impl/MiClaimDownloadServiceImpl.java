package com.tlg.aps.bs.miClaimDownloadService.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.miClaimDownloadService.MiClaimDownloadNewTransService;
import com.tlg.aps.bs.miClaimDownloadService.MiClaimDownloadService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.Tmpfetclaimcomm;
import com.tlg.prpins.entity.Tmpfetclaimkind;
import com.tlg.prpins.entity.Tmpfetclaimmain;
import com.tlg.prpins.entity.Tmpfetclaimpay;
import com.tlg.prpins.service.IntfprpjpayrefrecService;
import com.tlg.prpins.service.PrpcmainService;
import com.tlg.prpins.service.PrpcplanService;
import com.tlg.prpins.service.TmpfetclaimcommService;
import com.tlg.prpins.service.TmpfetclaimkindService;
import com.tlg.prpins.service.TmpfetclaimmainService;
import com.tlg.prpins.service.TmpfetclaimpayService;
import com.tlg.util.AppContext;
import com.tlg.util.ConfigUtil;
import com.tlg.util.GUID;
import com.tlg.util.Mailer;
import com.tlg.util.Result;
import com.tlg.util.SftpUtil;
import com.tlg.util.StringUtil;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class MiClaimDownloadServiceImpl implements MiClaimDownloadService {
	
	private static final Logger logger = Logger.getLogger(MiClaimDownloadServiceImpl.class);
	private TmpfetclaimmainService tmpfetclaimmainService;
	private TmpfetclaimkindService tmpfetclaimkindService;
	private TmpfetclaimpayService tmpfetclaimpayService;
	private TmpfetclaimcommService tmpfetclaimcommService;
	private PrpcplanService prpcplanService;
	private PrpcmainService prpcmainService;
	private IntfprpjpayrefrecService intfprpjpayrefrecService;
	private ConfigUtil configUtil;
	private final String mainFileName = "_MAIN.csv";
	private final String kindFileName = "_KIND.csv";
	private final String commFileName = "_COMM.csv";
	private final String payFileName = "_PAY.csv";
	private final String CSV = ".csv";
	private final String XLS = ".xls";
	private final String XLSX = ".xlsx";
	private final String COMMA_DELIMITER = "\\|\\|";
	private MiClaimDownloadNewTransService miClaimDownloadNewTransService;
	
	@Override
	public Result download(final String yyyMM) throws SystemException, Exception {
		
		Result result = new Result();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String executeDate = dateFormat.format(new Date());
		ArrayList<String> errorList = new ArrayList<String>();
		String tempPath = System.getProperty("java.io.tmpdir");
		File folder = new File(tempPath + File.separator + new GUID().toString(20));
		try{
			//刪除檔案
			this.miClaimDownloadNewTransService.deleteAllData();
			
			if (!folder.exists()) {
		    	folder.mkdir();
			}
			//取得檔案
			getFtpFile(yyyMM, folder.getAbsolutePath());
			
			//確認檔案是否存在
			FileFilter directoryFilter = new FileFilter() {
				public boolean accept(File file) {
					return file.getName().indexOf(yyyMM) != -1;
				}
			};
			File files[] = folder.listFiles(directoryFilter);
			Map<String, File> map = new HashMap<String, File>();
			for(File file: files){
				map.put(file.getName(), file);
			}	
			if(map.size() != 4){
				String error = "";
				if(!map.containsKey(yyyMM + this.mainFileName)){
					error = StringUtil.appendStr(error, yyyMM + this.mainFileName + "\\r\\n", "");
				}
				if(!map.containsKey(yyyMM + this.kindFileName)){
					error = StringUtil.appendStr(error, yyyMM + this.kindFileName + "\\r\\n", "");
				}
				if(!map.containsKey(yyyMM + this.commFileName)){
					error = StringUtil.appendStr(error, yyyMM + this.commFileName + "\\r\\n", "");
				}
				if(!map.containsKey(yyyMM + this.payFileName)){
					error = StringUtil.appendStr(error, yyyMM + this.payFileName + "\\r\\n", "");
				}
				throw new SystemException("執行失敗，缺少理賠檔案如下：\\r\\n" + error);
			}
			
			//處理4個檔案
			//1.main
			File mainFile = map.get(yyyMM + this.mainFileName);
//			ArrayList<Tmpfetclaimmain> listTmpfetclaimmain = getTmpfetclaimmainList(mainFile);
			ArrayList<Tmpfetclaimmain> listTmpfetclaimmain = getTmpfetclaimmainListByCsv(yyyMM, mainFile);
			File kindFile = map.get(yyyMM + this.kindFileName);
//			ArrayList<Tmpfetclaimkind> listTmpfetclaimkind = getTmpfetclaimkindList(kindFile);
			ArrayList<Tmpfetclaimkind> listTmpfetclaimkind = getTmpfetclaimkindListByCsv(yyyMM, kindFile);
			File payFile = map.get(yyyMM + this.payFileName);
//			ArrayList<Tmpfetclaimpay> listTmpfetclaimpay = getTmpfetclaimpayList(payFile);
			ArrayList<Tmpfetclaimpay> listTmpfetclaimpay = getTmpfetclaimpayListByCsv(yyyMM, payFile);
			File commFile = map.get(yyyMM + this.commFileName);
//			ArrayList<Tmpfetclaimcomm> listTmpfetclaimcomm = getTmpfetclaimcommList(commFile);
			ArrayList<Tmpfetclaimcomm> listTmpfetclaimcomm = getTmpfetclaimcommListByCsv(yyyMM, commFile);
			
			//寫入資料庫
			miClaimDownloadNewTransService.batchInsertFtpData(listTmpfetclaimmain, listTmpfetclaimkind, listTmpfetclaimpay, listTmpfetclaimcomm);
			
			//資料檢核
			checkData(errorList, listTmpfetclaimmain);
			
			//若沒有錯誤則搬檔案至備份區，有錯就留著
			if(errorList.size() == 0){
				moveFtpFile(yyyMM, folder.getAbsolutePath());
				result.setResObject(Boolean.TRUE);
			}
			
		} catch (SystemException se) {
			logger.debug(se);
			se.printStackTrace();
			throw se;
			
		} catch (Exception e) {
			logger.debug(e);
			e.printStackTrace();
			throw e;
		} finally {
			if(errorList.size() > 0){
				sendErrorMail(errorList, executeDate, yyyMM + "-行動裝置險理賠資料轉入作業異常");
			}
			if(folder != null){
				FileUtils.deleteDirectory(folder);
			}
		}
		return  result;
	}
	
	private void checkData(ArrayList<String> errorList, ArrayList<Tmpfetclaimmain> listTmpfetclaimmain) throws SystemException, Exception{
		
		for(Tmpfetclaimmain main:listTmpfetclaimmain){
			
			if(!"4".equals(main.getWda61())){
				continue;
			}
			
			String claimNo = main.getWda02();
			String wda03 = main.getWda03();
			String policyNo = main.getWda04();
			String accidentDate = String.valueOf(Integer.parseInt(String.valueOf(main.getWda13()).substring(0, 3)) + 1911) + String.valueOf(main.getWda13()).substring(3);
			String reporder = main.getReporder();
			
			//賠案號
			Map<String, String> params = new HashMap<String, String>();
			params.put("wda02", claimNo);
			params.put("checkAllStatus", "Y");
			//檢查main狀態筆數應該要有3筆
			int count = this.tmpfetclaimmainService.countTmpfetclaimmain(params);
			if(count != 3){
				errorList.add(claimNo + "," + wda03 + "," + reporder + ",Tmpfetclaimmain 賠案狀態資料筆數不足3筆");
			}
			params.clear();
			params.put("wdc02", claimNo);
			params.put("checkAllStatus", "Y");
			//檢查main狀態筆數應該要有3筆
			count = this.tmpfetclaimkindService.countTmpfetclaimkind(params);
			if(count < 3){
				errorList.add(claimNo + "," + wda03 + "," + reporder + ",Tmpfetclaimkind 賠案狀態資料筆數不足3筆");
			}
			params.clear();
			params.put("wde02", claimNo);
			params.put("checkAllStatus", "Y");
			//檢查main狀態筆數應該要有3筆
			count = this.tmpfetclaimpayService.countTmpfetclaimpay(params);
			if(count != 2){
				errorList.add(claimNo + "," + wda03 + "," + reporder + ",Tmpfetclaimpay 賠案狀態資料筆數不足2筆");
			}
			params.clear();
			params.put("wdf02", claimNo);
			params.put("checkAllStatus", "Y");
			//檢查main狀態筆數應該要有3筆
			count = this.tmpfetclaimcommService.countTmpfetclaimcomm(params);
			if(count != 2){
				errorList.add(claimNo + "," + wda03 + "," + reporder + ",Tmpfetclaimcomm 賠案狀態資料筆數不足2筆");
			}
			
			//A 查詢保單資料
			params.clear();
			params.put("policyno", policyNo);
			Result result = prpcmainService.findPrpcmainByParams(params);
			if(result.getResObject() == null){
				errorList.add(claimNo + "," + wda03 + "," + reporder + ",保單號：" + policyNo + "查詢不到Prpcmain資料");
			}
			/** mantis：CLM0184，處理人員：CD094，需求單編號：mantis：CLM0184 APS-行動裝置險收費紀錄檢核取消 start*/
//			// B.	取得對應核心保單檢核是否已收費且出險時間於有效保期內。
//			params.clear();
//			params.put("policyno", policyNo);
//			params.put("dateBetween", accidentDate);
//			int countPrpcplan = prpcplanService.countPrpcplan(params);
//			if(countPrpcplan == 0){
//				errorList.add(claimNo + "," + wda03 + "," + reporder + ",保單號：" + policyNo + "無法判斷收費且出險時間是否於有效保期");
//			}
			/** mantis：CLM0184，處理人員：CD094，需求單編號：mantis：CLM0184 APS-行動裝置險收費紀錄檢核取消 end */
			//D.	出險日期(TMPFEBCLAIMMAIN.WDA13)需落在有效保期內。
			//mantis：CLM0184，處理人員：CD094，需求單編號：CLM0184 APS-行動裝置險收費紀錄檢核取消 
			if(!"180024MI0002456".equals(policyNo)&&!"180024MI0005028".equals(policyNo)){
				params.clear();
				params.put("policyno", policyNo);
				int countIntfpr = intfprpjpayrefrecService.countIntfprpjpayrefrec(params);
				if(countIntfpr == 0){
					errorList.add(claimNo + "," + wda03 + "," + reporder + ",保單號：" + policyNo + "非落在有效保期內。");
				}				
			}
			//C.	該保單(PRPCMAIN.POLICYNO)於同一天出險日期(TMPFEBCLAIMMAIN.WDA13)，不可有多個賠案號碼(TMPFEBCLAIMMAIN.WDA02)。

			// mantis：CLM0200，處理人員：DP0714，APS-行動裝置險資料轉入出險日期重複檢核確認 -- start
			params.clear();
			params.put("policyno", policyNo);
			params.put("wda13", main.getWda13().toString());
			int countMultiClaim = tmpfetclaimmainService.countMultiClaim2(params);
            // mantis：CLM0200，處理人員：DP0714，APS-行動裝置險資料轉入出險日期重複檢核確認 -- end
			if(countMultiClaim > 1){
				errorList.add(claimNo + "," + wda03 + "," + reporder + ",保單號：" + policyNo + "於同一天出險日期有多個賠案號碼。");
			}
			//D.	比對理賠歷程資料(理賠狀態’4’)的主表賠款總計、賠付對象表賠付金額，與險種表所有細項險種實際賠款金額總和皆應相同。
			result = tmpfetclaimmainService.findPayoutDataByParams(policyNo);
			if(result.getResObject() != null){
				ArrayList<HashMap<String, BigDecimal>> list = (ArrayList<HashMap<String, BigDecimal>>)result.getResObject();
				HashMap<String, BigDecimal> map = list.get(0);
				BigDecimal wda35 = (BigDecimal)map.get("wda35");
				BigDecimal wde22 = (BigDecimal)map.get("wde22");
				BigDecimal wdc09 = (BigDecimal)map.get("wdc09");
				if(wda35.compareTo(wde22) != 0 || wda35.compareTo(wdc09) != 0 || wde22.compareTo(wdc09) != 0){
					errorList.add(claimNo + "," + wda03 + "," + reporder + ",主表賠款總計、賠付對象表賠付金額，與險種表所有細項險種實際賠款金額總和皆應相同");
				}
			}
		}
	}
	
	private void sendErrorMail(ArrayList<String> errorList, String executeDate, String title) throws AddressException, UnsupportedEncodingException, MessagingException{
		
		 ConfigUtil configUtil = (ConfigUtil) AppContext.getBean("configUtil");
		 Mailer mailer = new Mailer();
		 String smtpServer = configUtil.getString("smtp_host");
		 String userName = configUtil.getString("smtp_username");
		 String password = configUtil.getString("smtp_pwd");
		 String contentType = "text/html; charset=utf-8";
		 String auth = "smtp";
		 String subject = title;
		 String from = configUtil.getString("mail_from_address");
		 String to = configUtil.getString("claimMailReceiver");
		 String cc = "";
		 StringBuffer mailBody = new StringBuffer();
		 mailBody.append("時間：" + executeDate + "<BR>");
		 mailBody.append("<table border='1' cellspacing='0'><tr><td>序號</td><td>賠案號</td><td>賠付次數</td><td>維修單號</td><td>備註</td></tr>");
		 int count = 0;
		 for(String str:errorList){
			 String s1 = str.split(",")[0];
			 String s2 = str.split(",")[1];
			 String s3 = str.split(",")[2];
			 String s4 = str.split(",")[3];
			 mailBody.append("<tr><td>" + (++count) + "</td><td>" + s1 + "</td><td>" + s2 + "</td><td>" + s3 + "</td><td>" + s4 + "</td></tr>");
		 }
		 mailBody.append("</table>");
		 mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", cc, "", "", "", mailBody.toString(), auth, userName, password);

	}
	
	private Map getFtpFile(String yyyMM, String workingDirPath) throws Exception{
		final String ftpPath = "/AUTH/ACCOUNT/";
		ArrayList<String> fileNameList = new ArrayList<String>(); 
		try{
			
			String ip = configUtil.getString("claimSftpIp");
			String port = configUtil.getString("claimSftpPort");
			String user = configUtil.getString("claimSftpUser");
			String pd = configUtil.getString("claimSftpPd");
			if(StringUtil.isSpace(ip) || StringUtil.isSpace(port) || StringUtil.isSpace(user) || StringUtil.isSpace(pd)){
				throw new SystemException("SFTP設定值不可為空值");
			}
			
			SftpUtil sftpUtil = new SftpUtil(ip, new Integer(port).intValue(), user, pd);
			List<String> fileList = sftpUtil.getFileListFromSftp(ftpPath);
			if(fileList == null){
				throw new SystemException("SFTP遠端路徑不存在，無法執行檔案下載");
			}
			for(String fileName : fileList){
				if(fileName.indexOf(".csv") == -1){
					continue;
				}
				fileNameList.add(fileName);
				System.out.println("fileName = " + fileName);
			}
			sftpUtil.getFileFromSftp(ftpPath, workingDirPath, fileNameList);
			
			
		}catch (SystemException se) {
			se.printStackTrace();
			throw se;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}
	
	private Map moveFtpFile(String yyyMM, String workingDirPath) throws Exception{
		String ftpPath = "/AUTH/ACCOUNT/";
		try{
			String ip = configUtil.getString("claimSftpIp");
			String port = configUtil.getString("claimSftpPort");
			String user = configUtil.getString("claimSftpUser");
			String pd = configUtil.getString("claimSftpPd");
			if(StringUtil.isSpace(ip) || StringUtil.isSpace(port) || StringUtil.isSpace(user) || StringUtil.isSpace(pd)){
				throw new SystemException("SFTP設定值不可為空值");
			}
			
			SftpUtil sftpUtil = new SftpUtil(ip, new Integer(port).intValue(), user, pd);
			String ftpBackupPath = "/AUTH/ACCOUNT/BACKUP/" + yyyMM + "-" + System.currentTimeMillis();
			sftpUtil.putFileToSftp(ftpBackupPath , workingDirPath);
			//刪除檔案
			File folder = new File(workingDirPath);
			File files[] = folder.listFiles();
			for(File file:files){
				sftpUtil = new SftpUtil(ip, new Integer(port).intValue(), user, pd);
				sftpUtil.deleteFileToSftp(ftpPath, file.getName());
			}
		}catch (SystemException se) {
			se.printStackTrace();
			throw se;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}
	
	private ArrayList<Tmpfetclaimmain> getTmpfetclaimmainListByCsv(String yyyMM, File excelFile) throws Exception{
		
		ArrayList<Tmpfetclaimmain> list = new ArrayList<Tmpfetclaimmain>();
		if(excelFile == null){
			throw new SystemException("Tmpfetclaimmain不存在，無法轉換成物件");
		}
		String extString = excelFile.getName().substring(excelFile.getName().lastIndexOf("."));

		if (!CSV.equalsIgnoreCase(extString)) {
			throw new SystemException(excelFile.getName() + "並非是csv檔案");
		}
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(excelFile), "UTF-8"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] row = line.split(COMMA_DELIMITER);
		        Tmpfetclaimmain excelData = convertTmpfetclaimmainRowToData(yyyMM, row);
		        list.add(excelData);
		    }
		}
		return list;
	}
	
	
	private ArrayList<Tmpfetclaimmain> getTmpfetclaimmainList(File excelFile) throws Exception{
		ArrayList<Tmpfetclaimmain> list = new ArrayList<Tmpfetclaimmain>();
		if(excelFile == null){
			throw new SystemException("Tmpfetclaimmain不存在，無法轉換成物件");
		}
		String extString = excelFile.getName().substring(excelFile.getName().lastIndexOf("."));
		InputStream is;
		Workbook wb = null;
		try {
			is = new FileInputStream(excelFile);
			if (XLS.equalsIgnoreCase(extString)) {
				wb = new HSSFWorkbook(is);
			} else if (XLSX.equalsIgnoreCase(extString)) {
				wb = new XSSFWorkbook(is);
			}
			
			if(wb == null){
				throw new SystemException("無法取得Tmpfetclaimmain wb物件");
			}
			
			Sheet sheet = wb.getSheetAt(0);
			
			if(sheet == null){
				throw new SystemException("無法取得Tmpfetclaimmain sheet物件");
			}
			
			int firstRowNum = sheet.getFirstRowNum();
			Row firstRow = sheet.getRow(firstRowNum);
			if (null == firstRow) {
				throw new SystemException("解析Tmpfetclaimmain Excel失敗");
			}

			int rowStart = firstRowNum + 1;
			int rowEnd = sheet.getPhysicalNumberOfRows();
			for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
				Row row = sheet.getRow(rowNum);
				if (null == row) {
					continue;
				}
				Tmpfetclaimmain excelData = convertTmpfetclaimmainRowToData(row);
				if (null == excelData) {
					continue;
				}
				list.add(excelData);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			excelFile = null;
			wb = null;
		}
		
		return list;
		
	}
	
	private ArrayList<Tmpfetclaimkind> getTmpfetclaimkindListByCsv(String yyyMM, File excelFile) throws Exception{
		ArrayList<Tmpfetclaimkind> list = new ArrayList<Tmpfetclaimkind>();
		if(excelFile == null){
			throw new SystemException("Tmpfetclaimkind不存在，無法轉換成物件");
		}
		String extString = excelFile.getName().substring(excelFile.getName().lastIndexOf("."));

		if (!CSV.equalsIgnoreCase(extString)) {
			throw new SystemException(excelFile.getName() + "並非是csv檔案");
		}
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(excelFile), "UTF-8"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] row = line.split(COMMA_DELIMITER);
		        Tmpfetclaimkind excelData = convertTmpfetclaimkindRowToData(yyyMM, row);
		        list.add(excelData);
		    }
		}
		
		return list;
	}
	
	private ArrayList<Tmpfetclaimkind> getTmpfetclaimkindList(File excelFile) throws Exception{
		ArrayList<Tmpfetclaimkind> list = new ArrayList<Tmpfetclaimkind>();
		if(excelFile == null){
			throw new SystemException("Tmpfetclaimkind不存在，無法轉換成物件");
		}
		String extString = excelFile.getName().substring(excelFile.getName().lastIndexOf("."));
		InputStream is;
		Workbook wb = null;
		try {
			is = new FileInputStream(excelFile);
			if (XLS.equalsIgnoreCase(extString)) {
				wb = new HSSFWorkbook(is);
			} else if (XLSX.equalsIgnoreCase(extString)) {
				wb = new XSSFWorkbook(is);
			}
			
			if(wb == null){
				throw new SystemException("無法取得Tmpfetclaimkind wb物件");
			}
			
			Sheet sheet = wb.getSheetAt(0);
			
			if(sheet == null){
				throw new SystemException("無法取得Tmpfetclaimkind wb物件");
			}
			
			int firstRowNum = sheet.getFirstRowNum();
			Row firstRow = sheet.getRow(firstRowNum);
			if (null == firstRow) {
				throw new SystemException("無法取得Tmpfetclaimkind Excel失敗");
			}

			int rowStart = firstRowNum + 1;
			int rowEnd = sheet.getPhysicalNumberOfRows();
			for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
				Row row = sheet.getRow(rowNum);
				if (null == row) {
					continue;
				}
				Tmpfetclaimkind excelData = convertTmpfetclaimkindRowToData(row);
				if (null == excelData) {
					continue;
				}
				list.add(excelData);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			excelFile = null;
			wb = null;
		}
		return list;
	}
	
	private ArrayList<Tmpfetclaimpay> getTmpfetclaimpayListByCsv(String yyyMM, File excelFile) throws Exception{
		
		ArrayList<Tmpfetclaimpay> list = new ArrayList<Tmpfetclaimpay>();
		if(excelFile == null){
			throw new SystemException("Tmpfetclaimpay不存在，無法轉換成物件");
		}
		String extString = excelFile.getName().substring(excelFile.getName().lastIndexOf("."));

		if (!CSV.equalsIgnoreCase(extString)) {
			throw new SystemException(excelFile.getName() + "並非是csv檔案");
		}
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(excelFile), "UTF-8"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] row = line.split(COMMA_DELIMITER);
		        Tmpfetclaimpay excelData = convertTmpfetclaimpayRowToData(yyyMM, row);
		        list.add(excelData);
		    }
		}
		return list;
	}
	
	private ArrayList<Tmpfetclaimpay> getTmpfetclaimpayList(File excelFile) throws Exception{
		ArrayList<Tmpfetclaimpay> list = new ArrayList<Tmpfetclaimpay>();
		if(excelFile == null){
			throw new SystemException("Tmpfetclaimkind不存在，無法轉換成物件");
		}
		String extString = excelFile.getName().substring(excelFile.getName().lastIndexOf("."));
		InputStream is;
		Workbook wb = null;
		try {
			is = new FileInputStream(excelFile);
			if (XLS.equalsIgnoreCase(extString)) {
				wb = new HSSFWorkbook(is);
			} else if (XLSX.equalsIgnoreCase(extString)) {
				wb = new XSSFWorkbook(is);
			}
			
			if(wb == null){
				throw new SystemException("無法取得Tmpfetclaimpay wb物件");
			}
			
			Sheet sheet = wb.getSheetAt(0);
			
			if(sheet == null){
				throw new SystemException("無法取得Tmpfetclaimpay wb物件");
			}
			
			int firstRowNum = sheet.getFirstRowNum();
			Row firstRow = sheet.getRow(firstRowNum);
			if (null == firstRow) {
				throw new SystemException("無法取得Tmpfetclaimpay Excel失敗");
			}

			int rowStart = firstRowNum + 1;
			int rowEnd = sheet.getPhysicalNumberOfRows();
			for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
				Row row = sheet.getRow(rowNum);
				if (null == row) {
					continue;
				}
				Tmpfetclaimpay excelData = convertTmpfetclaimpayRowToData(row);
				if (null == excelData) {
					continue;
				}
				list.add(excelData);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			excelFile = null;
			wb = null;
		}
		return list;
	}
	
	private ArrayList<Tmpfetclaimcomm> getTmpfetclaimcommListByCsv(String yyyMM, File excelFile) throws Exception{
		
		ArrayList<Tmpfetclaimcomm> list = new ArrayList<Tmpfetclaimcomm>();
		if(excelFile == null){
			throw new SystemException("Tmpfetclaimcomm不存在，無法轉換成物件");
		}
		String extString = excelFile.getName().substring(excelFile.getName().lastIndexOf("."));

		if (!CSV.equalsIgnoreCase(extString)) {
			throw new SystemException(excelFile.getName() + "並非是csv檔案");
		}
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(excelFile), "UTF-8"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] row = line.split(COMMA_DELIMITER);
		        Tmpfetclaimcomm excelData = convertTmpfetclaimcommRowToData(yyyMM, row);
		        list.add(excelData);
		    }
		}
		return list;
	}
	
	private ArrayList<Tmpfetclaimcomm> getTmpfetclaimcommList(File excelFile) throws Exception{
		ArrayList<Tmpfetclaimcomm> list = new ArrayList<Tmpfetclaimcomm>();
		if(excelFile == null){
			throw new SystemException("Tmpfetclaimcomm不存在，無法轉換成物件");
		}
		String extString = excelFile.getName().substring(excelFile.getName().lastIndexOf("."));
		InputStream is;
		Workbook wb = null;
		try {
			is = new FileInputStream(excelFile);
			if (XLS.equalsIgnoreCase(extString)) {
				wb = new HSSFWorkbook(is);
			} else if (XLSX.equalsIgnoreCase(extString)) {
				wb = new XSSFWorkbook(is);
			}
			
			if(wb == null){
				throw new SystemException("無法取得Tmpfetclaimcomm wb物件");
			}
			
			Sheet sheet = wb.getSheetAt(0);
			
			if(sheet == null){
				throw new SystemException("無法取得Tmpfetclaimcomm wb物件");
			}
			
			int firstRowNum = sheet.getFirstRowNum();
			Row firstRow = sheet.getRow(firstRowNum);
			if (null == firstRow) {
				throw new SystemException("無法取得Tmpfetclaimcomm Excel失敗");
			}

			int rowStart = firstRowNum + 1;
			int rowEnd = sheet.getPhysicalNumberOfRows();
			for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
				Row row = sheet.getRow(rowNum);
				if (null == row) {
					continue;
				}
				Tmpfetclaimcomm excelData = convertTmpfetclaimcommRowToData(row);
				if (null == excelData) {
					continue;
				}
				list.add(excelData);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			excelFile = null;
			wb = null;
		}
		return list;
	}
	
//	private Workbook getWorkbook(String path) {
//		Workbook wb = null;
//		if (path == null)
//			return null;
//		String extString = path.substring(path.lastIndexOf("."));
//		InputStream is;
//		try {
//			is = new FileInputStream(path);
//			if (XLS.equalsIgnoreCase(extString)) {
//				wb = new HSSFWorkbook(is);
//			} else if (XLSX.equalsIgnoreCase(extString)) {
//				wb = new XSSFWorkbook(is);
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return wb;
//	}
	
//	private static List<Object> parseExcel(Workbook workbook) {
//		List<Object> excelDataList = new ArrayList<>();
//		//遍歷每一個sheet
//		for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
//			Sheet sheet = workbook.getSheetAt(sheetNum);
//
//			if (sheet == null) {
//				continue;
//			}
//
//			int firstRowNum = sheet.getFirstRowNum();
//			Row firstRow = sheet.getRow(firstRowNum);
//			if (null == firstRow) {
//				System.out.println("解析Excel失敗");
//			}
//
//			int rowStart = firstRowNum + 1;
//			int rowEnd = sheet.getPhysicalNumberOfRows();
//			for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
//				Row row = sheet.getRow(rowNum);
//				if (null == row) {
//					continue;
//				}
//				ExcelDataBean excelData = convertRowToData(row);
//				if (null == excelData) {
//					continue;
//				}
//				excelDataList.add(excelData);
//			}
//		}
//		return excelDataList;
//	}
	
	
	private Tmpfetclaimmain convertTmpfetclaimmainRowToData(String yyyMM, String[] row) throws Exception {
		Tmpfetclaimmain excelData = new Tmpfetclaimmain();
		
		int num = 0;

//		String wda00 = StringUtil.nullToSpace(row[num++]); 
		num++; //0
		excelData.setWda00(new Integer(yyyMM));
		
		String wda01 = StringUtil.nullToSpace(row[num++]); //1
		if(!StringUtil.isSpace(wda01)){
			excelData.setWda01(wda01);
		}

		String wda02 = StringUtil.nullToSpace(row[num++]); //2
		if(!StringUtil.isSpace(wda02)){
			excelData.setWda02(wda02);
		}
		
		String wda03 = StringUtil.nullToSpace(row[num++]); //3
		if(!StringUtil.isSpace(wda03)){
			excelData.setWda03(wda03);
		}
		
		String wda04 = StringUtil.nullToSpace(row[num++]); //4
		if(!StringUtil.isSpace(wda04)){
			excelData.setWda04(wda04);
		}
		
		String wda05 = StringUtil.nullToSpace(row[num++]); //5
		if(!StringUtil.isSpace(wda05)){
			excelData.setWda05(wda05);
		}
		
		String wda06 = StringUtil.nullToSpace(row[num++]); //6
		if(!StringUtil.isSpace(wda06)){
			excelData.setWda06(wda06);
		}
		
		String wda07 = StringUtil.nullToSpace(row[num++]); //7
		if(!StringUtil.isSpace(wda07)){
			excelData.setWda07(wda07);
		}
		
		String wda08 = StringUtil.nullToSpace(row[num++]); //8
		if(!StringUtil.isSpace(wda08)){
			excelData.setWda08(new Integer(wda08));
		}
		
		String wda09 = StringUtil.nullToSpace(row[num++]); //9
		if(!StringUtil.isSpace(wda09)){
			excelData.setWda09(new Integer(wda09));
		}
		
		String wda10 = StringUtil.nullToSpace(row[num++]); //10
		if(!StringUtil.isSpace(wda10)){
			excelData.setWda10(new Integer(wda10));
		}
		
		String wda11 = StringUtil.nullToSpace(row[num++]); //11
		if(!StringUtil.isSpace(wda11)){
			excelData.setWda11(new Integer(wda11));
		}
		
		String wda12 = StringUtil.nullToSpace(row[num++]); //12
		if(!StringUtil.isSpace(wda12)){
			excelData.setWda12(wda12);
		}
		
		String wda13 = StringUtil.nullToSpace(row[num++]); //13
		if(!StringUtil.isSpace(wda13)){
			excelData.setWda13(new Integer(wda13));
		}
		
		String wda14 = StringUtil.nullToSpace(row[num++]); //14
		if(!StringUtil.isSpace(wda14)){
			excelData.setWda14(new Integer(wda14));
		}
		
		String wda15 = StringUtil.nullToSpace(row[num++]); //15
		if(!StringUtil.isSpace(wda15)){
			excelData.setWda15(wda15);
		}
		
		String wda16 = StringUtil.nullToSpace(row[num++]); //16
		if(!StringUtil.isSpace(wda16)){
			excelData.setWda16(wda16);
		}
		
		String wda17 = StringUtil.nullToSpace(row[num++]); //17
		if(!StringUtil.isSpace(wda17)){
			excelData.setWda17(wda17);
		}
		
		String wda18 = StringUtil.nullToSpace(row[num++]); //18
		if(!StringUtil.isSpace(wda18)){
			excelData.setWda18(wda18);
		}
		
		String wda19 = StringUtil.nullToSpace(row[num++]); //19
		if(!StringUtil.isSpace(wda19)){
			excelData.setWda19(wda19);
		}
		
		String wda20 = StringUtil.nullToSpace(row[num++]); //20
		if(!StringUtil.isSpace(wda20)){
			excelData.setWda20(new Integer(wda20));
		}
		
		String wda21 = StringUtil.nullToSpace(row[num++]); //21
		if(!StringUtil.isSpace(wda21)){
			excelData.setWda21(wda21);
		}
		
		String wda22 = StringUtil.nullToSpace(row[num++]); //22
		if(!StringUtil.isSpace(wda22)){
			excelData.setWda22(wda22);
		}
		
		String wda23 = StringUtil.nullToSpace(row[num++]); //23
		if(!StringUtil.isSpace(wda23)){
			excelData.setWda23(wda23);
		}
		
		String wda24 = StringUtil.nullToSpace(row[num++]); //24
		if(!StringUtil.isSpace(wda24)){
			excelData.setWda24(wda24);
		}
		
		String wda25 = StringUtil.nullToSpace(row[num++]); //25
		if(!StringUtil.isSpace(wda25)){
			excelData.setWda25(wda25);
		}
		
		String wda26 = StringUtil.nullToSpace(row[num++]); //26
		if(!StringUtil.isSpace(wda26)){
			excelData.setWda26(wda26);
		}
		
		String wda27 = StringUtil.nullToSpace(row[num++]); //27
		if(!StringUtil.isSpace(wda27)){
			excelData.setWda27(wda27);
		}
		
		String wda28 = StringUtil.nullToSpace(row[num++]); //28
		if(!StringUtil.isSpace(wda28)){
			excelData.setWda28(new Long(wda28));
		}
		
		String wda29 = StringUtil.nullToSpace(row[num++]); //29
		if(!StringUtil.isSpace(wda29)){
			excelData.setWda29(new Integer(wda29));
		}
		
		String wda30 = StringUtil.nullToSpace(row[num++]); //30
		if(!StringUtil.isSpace(wda30)){
			excelData.setWda30(new Long(wda30));
		}
		
		String wda31 = StringUtil.nullToSpace(row[num++]); //31
		if(!StringUtil.isSpace(wda31)){
			excelData.setWda31(new Integer(wda31));
		}
		
		String wda32 = StringUtil.nullToSpace(row[num++]); //32
		if(!StringUtil.isSpace(wda32)){
			excelData.setWda32(wda32);
		}
		
		String wda33 = StringUtil.nullToSpace(row[num++]); //33
		if(!StringUtil.isSpace(wda33)){
			excelData.setWda33(new Integer(wda33));
		}
		
		String wda34 = StringUtil.nullToSpace(row[num++]); //34
		if(!StringUtil.isSpace(wda34)){
			excelData.setWda34(new Integer(wda34));
		}
		
		String wda35 = StringUtil.nullToSpace(row[num++]); //35
		if(!StringUtil.isSpace(wda35)){
			excelData.setWda35(new Long(wda35));
		}
		
		String wda36 = StringUtil.nullToSpace(row[num++]); //36
		if(!StringUtil.isSpace(wda36)){
			excelData.setWda36(new Long(wda36));
		}
		
		String wda37 = StringUtil.nullToSpace(row[num++]); //37
		if(!StringUtil.isSpace(wda37)){
			excelData.setWda37(new Integer(wda37));
		}
		
		String wda38 = StringUtil.nullToSpace(row[num++]); //38
		if(!StringUtil.isSpace(wda38)){
			excelData.setWda38(wda38);
		}
		
		String wda39 = StringUtil.nullToSpace(row[num++]); //39
		if(!StringUtil.isSpace(wda39)){
			excelData.setWda39(wda39);
		}
		
		String wda40 = StringUtil.nullToSpace(row[num++]); //40
		if(!StringUtil.isSpace(wda40)){
			excelData.setWda40(wda40);
		}
		
		String wda41 = StringUtil.nullToSpace(row[num++]); //41
		if(!StringUtil.isSpace(wda41)){
			excelData.setWda41(new Integer(wda41));
		}
		
		String wda42 = StringUtil.nullToSpace(row[num++]); //42
		if(!StringUtil.isSpace(wda42)){
			excelData.setWda42(wda42);
		}
		
		String wda43 = StringUtil.nullToSpace(row[num++]); //43
		if(!StringUtil.isSpace(wda43)){
			excelData.setWda43(wda43);
		}
		
		String wda44 = StringUtil.nullToSpace(row[num++]); //44
		if(!StringUtil.isSpace(wda44)){
			excelData.setWda44(new BigDecimal(wda44));
		}
		
		String wda45 = StringUtil.nullToSpace(row[num++]); //45
		if(!StringUtil.isSpace(wda45)){
			excelData.setWda45(wda45);
		}
		
		String wda46 = StringUtil.nullToSpace(row[num++]); //46
		if(!StringUtil.isSpace(wda46)){
			excelData.setWda46(new Integer(wda46));
		}
		
		String wda47 = StringUtil.nullToSpace(row[num++]); //47
		if(!StringUtil.isSpace(wda47)){
			excelData.setWda47(wda47);
		}
		
		String wda48 = StringUtil.nullToSpace(row[num++]); //48
		if(!StringUtil.isSpace(wda48)){
			excelData.setWda48(wda48);
		}
		
		String wda49 = StringUtil.nullToSpace(row[num++]); //49
		if(!StringUtil.isSpace(wda49)){
			excelData.setWda49(wda49);
		}
		
		String wda50 = StringUtil.nullToSpace(row[num++]); //50
		if(!StringUtil.isSpace(wda50)){
			excelData.setWda50(wda50);
		}
		
		String wda51 = StringUtil.nullToSpace(row[num++]); //51
		if(!StringUtil.isSpace(wda51)){
			excelData.setWda51(wda51);
		}
		
		String wda52 = StringUtil.nullToSpace(row[num++]); //52
		if(!StringUtil.isSpace(wda52)){
			excelData.setWda52(wda52);
		}
		
		String wda53 = StringUtil.nullToSpace(row[num++]); //53
		if(!StringUtil.isSpace(wda53)){
			excelData.setWda53(wda53);
		}
		
		String wda54 = StringUtil.nullToSpace(row[num++]); //54
		if(!StringUtil.isSpace(wda54)){
			excelData.setWda54(wda54);
		}
		
		String wda55 = StringUtil.nullToSpace(row[num++]); //55
		if(!StringUtil.isSpace(wda55)){
			excelData.setWda55(wda55);
		}
		
		String wda56 = StringUtil.nullToSpace(row[num++]); //56
		if(!StringUtil.isSpace(wda56)){
			excelData.setWda56(wda56);
		}
		
		String wda57 = StringUtil.nullToSpace(row[num++]); //57
		if(!StringUtil.isSpace(wda57)){
			excelData.setWda57(wda57);
		}
		
		String wda58 = StringUtil.nullToSpace(row[num++]); //58
		if(!StringUtil.isSpace(wda58)){
			excelData.setWda58(wda58);
		}
		
		String wda59 = StringUtil.nullToSpace(row[num++]); //59
		if(!StringUtil.isSpace(wda59)){
			excelData.setWda59(wda59);
		}
		
		String wda60 = StringUtil.nullToSpace(row[num++]); //60
		if(!StringUtil.isSpace(wda60)){
			excelData.setWda60(wda60);
		}
		
		String wda61 = StringUtil.nullToSpace(row[num++]); //61
		if(!StringUtil.isSpace(wda61)){
			excelData.setWda61(wda61);
		}
		
		String wda62 = StringUtil.nullToSpace(row[num++]); //62
		if(!StringUtil.isSpace(wda62)){
			excelData.setWda62(wda62);
		}
		
		String wda63 = StringUtil.nullToSpace(row[num++]); //63
		if(!StringUtil.isSpace(wda63)){
			excelData.setWda63(wda63);
		}
		
		String wda64 = StringUtil.nullToSpace(row[num++]); //64
		if(!StringUtil.isSpace(wda64)){
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			excelData.setWda64(dateFormat.parse(wda64));
		}
		
		String wda65 = StringUtil.nullToSpace(row[num++]); //65
		if(!StringUtil.isSpace(wda65)){
			excelData.setWda65(wda65);
		}
		
		String wda66 = StringUtil.nullToSpace(row[num++]); //66
		if(!StringUtil.isSpace(wda66)){
			excelData.setWda66(wda66);
		}
		
		String wda67 = StringUtil.nullToSpace(row[num++]); //67 DRAWOUTDATE
		if(!StringUtil.isSpace(wda67)){
//			excelData.setWda66(wda67);
		}
		
		String wda68 = StringUtil.nullToSpace(row[num++]); //68 DATASOURCE
		if(!StringUtil.isSpace(wda68)){
			excelData.setDatasource(wda68);
		}
		
		String wda69 = StringUtil.nullToSpace(row[num++]); //69 FLOWID
		if(!StringUtil.isSpace(wda69)){
			excelData.setFlowid(wda69);
		}
		
		String wda70 = StringUtil.nullToSpace(row[num++]); //70 REGISTNO
		if(!StringUtil.isSpace(wda70)){
			excelData.setRegistno(wda70);
		}
		
		String wda71 = StringUtil.nullToSpace(row[num++]); //71 REPORDER
		if(!StringUtil.isSpace(wda71)){
			excelData.setReporder(wda71);
		}
		
		return excelData;
	}
	
	private Tmpfetclaimmain convertTmpfetclaimmainRowToData(Row row) throws Exception {
		Tmpfetclaimmain excelData = new Tmpfetclaimmain();
		Cell cell;
		int cellNum = 0;

		cell = row.getCell(cellNum++); //0
		String wda00 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda00)){
			excelData.setWda00(new Integer(wda00));
		}else{
			return null;
		}
		
		cell = row.getCell(cellNum++); //1
		String wda01 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda01)){
			excelData.setWda01(wda01);
		}

		cell = row.getCell(cellNum++); //2
		String wda02 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda02)){
			excelData.setWda02(wda02);
		}
		
		cell = row.getCell(cellNum++); //3
		String wda03 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda03)){
			excelData.setWda03(wda03);
		}
		
		cell = row.getCell(cellNum++); //4
		String wda04 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda04)){
			excelData.setWda04(wda04);
		}
		
		cell = row.getCell(cellNum++); //5
		String wda05 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda05)){
			excelData.setWda05(wda05);
		}
		
		cell = row.getCell(cellNum++); //6
		String wda06 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda06)){
			excelData.setWda06(wda06);
		}
		
		cell = row.getCell(cellNum++); //7
		String wda07 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda07)){
			excelData.setWda07(wda07);
		}
		
		cell = row.getCell(cellNum++); //8
		String wda08 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda08)){
			excelData.setWda08(new Integer(wda08));
		}
		
		cell = row.getCell(cellNum++); //9
		String wda09 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda09)){
			excelData.setWda09(new Integer(wda09));
		}
		
		cell = row.getCell(cellNum++); //10
		String wda10 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda10)){
			excelData.setWda10(new Integer(wda10));
		}
		
		cell = row.getCell(cellNum++); //11
		String wda11 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda11)){
			excelData.setWda11(new Integer(wda11));
		}
		
		cell = row.getCell(cellNum++); //12
		String wda12 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda12)){
			excelData.setWda12(wda12);
		}
		
		cell = row.getCell(cellNum++); //13
		String wda13 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda13)){
			excelData.setWda13(new Integer(wda13));
		}
		
		cell = row.getCell(cellNum++); //14
		String wda14 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda14)){
			excelData.setWda14(new Integer(wda14));
		}
		
		cell = row.getCell(cellNum++); //15
		String wda15 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda15)){
			excelData.setWda15(wda15);
		}
		
		cell = row.getCell(cellNum++); //16
		String wda16 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda16)){
			excelData.setWda16(wda16);
		}
		
		cell = row.getCell(cellNum++); //17
		String wda17 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda17)){
			excelData.setWda17(wda17);
		}
		
		cell = row.getCell(cellNum++); //18
		String wda18 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda18)){
			excelData.setWda18(wda18);
		}
		
		cell = row.getCell(cellNum++); //19
		String wda19 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda19)){
			excelData.setWda19(wda19);
		}
		
		cell = row.getCell(cellNum++); //20
		String wda20 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda20)){
			excelData.setWda20(new Integer(wda20));
		}
		
		cell = row.getCell(cellNum++); //21
		String wda21 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda21)){
			excelData.setWda21(wda21);
		}
		
		cell = row.getCell(cellNum++); //22
		String wda22 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda22)){
			excelData.setWda22(wda22);
		}
		
		cell = row.getCell(cellNum++); //23
		String wda23 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda23)){
			excelData.setWda23(wda23);
		}
		
		cell = row.getCell(cellNum++); //24
		String wda24 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda24)){
			excelData.setWda24(wda24);
		}
		
		cell = row.getCell(cellNum++); //25
		String wda25 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda25)){
			excelData.setWda25(wda25);
		}
		
		cell = row.getCell(cellNum++); //26
		String wda26 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda26)){
			excelData.setWda26(wda26);
		}
		
		cell = row.getCell(cellNum++); //27
		String wda27 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda27)){
			excelData.setWda27(wda27);
		}
		
		cell = row.getCell(cellNum++); //28
		String wda28 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda28)){
			excelData.setWda28(new Long(wda28));
		}
		
		cell = row.getCell(cellNum++); //29
		String wda29 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda29)){
			excelData.setWda29(new Integer(wda29));
		}
		
		cell = row.getCell(cellNum++); //30
		String wda30 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda30)){
			excelData.setWda30(new Long(wda30));
		}
		
		cell = row.getCell(cellNum++); //31
		String wda31 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda31)){
			excelData.setWda31(new Integer(wda31));
		}
		
		cell = row.getCell(cellNum++); //32
		String wda32 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda32)){
			excelData.setWda32(wda32);
		}
		
		cell = row.getCell(cellNum++); //33
		String wda33 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda33)){
			excelData.setWda33(new Integer(wda33));
		}
		
		cell = row.getCell(cellNum++); //34
		String wda34 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda34)){
			excelData.setWda34(new Integer(wda34));
		}
		
		cell = row.getCell(cellNum++); //35
		String wda35 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda35)){
			excelData.setWda35(new Long(wda35));
		}
		
		cell = row.getCell(cellNum++); //36
		String wda36 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda36)){
			excelData.setWda36(new Long(wda36));
		}
		
		cell = row.getCell(cellNum++); //37
		String wda37 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda37)){
			excelData.setWda37(new Integer(wda37));
		}
		
		cell = row.getCell(cellNum++); //38
		String wda38 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda38)){
			excelData.setWda38(wda38);
		}
		
		cell = row.getCell(cellNum++); //39
		String wda39 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda39)){
			excelData.setWda39(wda39);
		}
		
		cell = row.getCell(cellNum++); //40
		String wda40 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda40)){
			excelData.setWda40(wda40);
		}
		
		cell = row.getCell(cellNum++); //41
		String wda41 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda41)){
			excelData.setWda41(new Integer(wda41));
		}
		
		cell = row.getCell(cellNum++); //42
		String wda42 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda42)){
			excelData.setWda42(wda42);
		}
		
		cell = row.getCell(cellNum++); //43
		String wda43 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda43)){
			excelData.setWda43(wda43);
		}
		
		cell = row.getCell(cellNum++); //44
		String wda44 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda44)){
			excelData.setWda44(new BigDecimal(wda44));
		}
		
		cell = row.getCell(cellNum++); //45
		String wda45 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda45)){
			excelData.setWda45(wda45);
		}
		
		cell = row.getCell(cellNum++); //46
		String wda46 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda46)){
			excelData.setWda46(new Integer(wda46));
		}
		
		cell = row.getCell(cellNum++); //47
		String wda47 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda47)){
			excelData.setWda47(wda47);
		}
		
		cell = row.getCell(cellNum++); //48
		String wda48 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda48)){
			excelData.setWda48(wda48);
		}
		
		cell = row.getCell(cellNum++); //49
		String wda49 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda49)){
			excelData.setWda49(wda49);
		}
		
		cell = row.getCell(cellNum++); //50
		String wda50 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda50)){
			excelData.setWda50(wda50);
		}
		
		cell = row.getCell(cellNum++); //51
		String wda51 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda51)){
			excelData.setWda51(wda51);
		}
		
		cell = row.getCell(cellNum++); //52
		String wda52 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda52)){
			excelData.setWda52(wda52);
		}
		
		cell = row.getCell(cellNum++); //53
		String wda53 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda53)){
			excelData.setWda53(wda53);
		}
		
		cell = row.getCell(cellNum++); //54
		String wda54 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda54)){
			excelData.setWda54(wda54);
		}
		
		cell = row.getCell(cellNum++); //55
		String wda55 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda55)){
			excelData.setWda55(wda55);
		}
		
		cell = row.getCell(cellNum++); //56
		String wda56 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda56)){
			excelData.setWda56(wda56);
		}
		
		cell = row.getCell(cellNum++); //57
		String wda57 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda57)){
			excelData.setWda57(wda57);
		}
		
		cell = row.getCell(cellNum++); //58
		String wda58 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda58)){
			excelData.setWda58(wda58);
		}
		
		cell = row.getCell(cellNum++); //59
		String wda59 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda59)){
			excelData.setWda59(wda59);
		}
		
		cell = row.getCell(cellNum++); //60
		String wda60 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda60)){
			excelData.setWda60(wda60);
		}
		
		cell = row.getCell(cellNum++); //61
		String wda61 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda61)){
			excelData.setWda61(wda61);
		}
		
		cell = row.getCell(cellNum++); //62
		String wda62 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda62)){
			excelData.setWda62(wda62);
		}
		
		cell = row.getCell(cellNum++); //63
		String wda63 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda63)){
			excelData.setWda63(wda63);
		}
		
		cell = row.getCell(cellNum++); //64
		if(cell != null){
			excelData.setWda64(cell.getDateCellValue());
		}
		
		cell = row.getCell(cellNum++); //65
		String wda65 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda65)){
			excelData.setWda65(wda65);
		}
		
		cell = row.getCell(cellNum++); //66
		String wda66 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wda66)){
			excelData.setWda66(wda66);
		}
		
		cell = row.getCell(cellNum++); //67 DRAWOUTDATE
		String wda67 = convertCellValueToString(cell);
		
		
		cell = row.getCell(cellNum++); //68 DATASOURCE
		String datasource = convertCellValueToString(cell);
		if(!StringUtil.isSpace(datasource)){
			excelData.setDatasource(datasource);
		}
		
		cell = row.getCell(cellNum++); //69 FLOWID
		String wda69 = convertCellValueToString(cell);
		
		
		cell = row.getCell(cellNum++); //70 REGISTNO
		String wda70 = convertCellValueToString(cell);
		
		
		cell = row.getCell(cellNum++); //71 REPORDER
		String reporder = convertCellValueToString(cell);
		if(!StringUtil.isSpace(reporder)){
			excelData.setReporder(reporder);
		}

		return excelData;
	}
	
	private Tmpfetclaimkind convertTmpfetclaimkindRowToData(String yyyMM, String[] row) throws Exception {
		Tmpfetclaimkind excelData = new Tmpfetclaimkind();
		int num = 0;
		
		num++; //00
		excelData.setWdc00(new Integer(yyyMM));
		
		
		String wdc01 = StringUtil.nullToSpace(row[num++]); //01
		if(!StringUtil.isSpace(wdc01)){
			excelData.setWdc01(wdc01);
		}
		
		String wdc02 = StringUtil.nullToSpace(row[num++]); //02
		if(!StringUtil.isSpace(wdc02)){
			excelData.setWdc02(wdc02);
		}
		
		String wdc03 = StringUtil.nullToSpace(row[num++]); //03
		if(!StringUtil.isSpace(wdc03)){
			excelData.setWdc03(wdc03);
		}
		
		String wdc04 = StringUtil.nullToSpace(row[num++]); //04
		if(!StringUtil.isSpace(wdc04)){
			excelData.setWdc04(wdc04);
		}
		
		String wdc05 = StringUtil.nullToSpace(row[num++]); //05
		if(!StringUtil.isSpace(wdc05)){
			excelData.setWdc05(new Integer(wdc05));
		}
		
		String wdc06 = StringUtil.nullToSpace(row[num++]); //06
		if(!StringUtil.isSpace(wdc06)){
			excelData.setWdc06(new Long(wdc06));		
		}
		
		String wdc07 = StringUtil.nullToSpace(row[num++]); //07
		if(!StringUtil.isSpace(wdc07)){
			excelData.setWdc07(new Long(wdc07));
		}
		
		String wdc08 = StringUtil.nullToSpace(row[num++]); //08
		if(!StringUtil.isSpace(wdc08)){
			excelData.setWdc08(new Long(wdc08));
		}
		
		String wdc09 = StringUtil.nullToSpace(row[num++]); //09
		if(!StringUtil.isSpace(wdc09)){
			excelData.setWdc09(new Long(wdc09));
		}
		
		String wdc10 = StringUtil.nullToSpace(row[num++]); //10
		if(!StringUtil.isSpace(wdc10)){
			excelData.setWdc10(new Integer(wdc10));
		}
		
		String wdc11 = StringUtil.nullToSpace(row[num++]); //11
		if(!StringUtil.isSpace(wdc11)){
			excelData.setWdc11(wdc11);
		}
		
		String wdc12 = StringUtil.nullToSpace(row[num++]); //12
		if(!StringUtil.isSpace(wdc12)){
			excelData.setWdc12(wdc12);
		}
		
		String wdc13 = StringUtil.nullToSpace(row[num++]); //13
		if(!StringUtil.isSpace(wdc13)){
			excelData.setWdc13(wdc13);
		}
		
		String wdc14 = StringUtil.nullToSpace(row[num++]); //14
		if(!StringUtil.isSpace(wdc14)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			excelData.setWdc14(sdf.parse(wdc14));
		}
		
		String wdc15 = StringUtil.nullToSpace(row[num++]); //15 DRAWOUTDATE
		if(!StringUtil.isSpace(wdc15)){
			String drawoutdate = StringUtil.nullToSpace(row[num++]);
		}
		
		String wdc16 = StringUtil.nullToSpace(row[num++]); //16 DATASOURCE
		if(!StringUtil.isSpace(wdc16)){
			excelData.setDatasource(wdc16);
		}
		
		return excelData;
	}
	
	private Tmpfetclaimkind convertTmpfetclaimkindRowToData(Row row) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Tmpfetclaimkind excelData = new Tmpfetclaimkind();
		Cell cell;
		int cellNum = 0;

		cell = row.getCell(cellNum++); //00
		String wdc00 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wdc00)){
			excelData.setWdc00(new Integer(wdc00));
		}else{
			return null;
		}
		
		cell = row.getCell(cellNum++); //01
		String wdc01 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wdc01)){
			excelData.setWdc01(wdc01);
		}
		
		cell = row.getCell(cellNum++); //02
		String wdc02 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wdc02)){
			excelData.setWdc02(wdc02);
		}
		
		cell = row.getCell(cellNum++); //03
		String wdc03 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wdc03)){
			excelData.setWdc03(wdc03);
		}
		
		cell = row.getCell(cellNum++); //04
		String wdc04 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wdc04)){
			excelData.setWdc04(wdc04);
		}
		
		cell = row.getCell(cellNum++); //05
		String wdc05 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wdc05)){
			excelData.setWdc05(new Integer(wdc05));
		}
		
		cell = row.getCell(cellNum++); //06
		String wdc06 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wdc06)){
			excelData.setWdc06(new Long(wdc06));		
		}
		
		cell = row.getCell(cellNum++); //07
		String wdc07 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wdc07)){
			excelData.setWdc07(new Long(wdc07));
		}
		
		cell = row.getCell(cellNum++); //08
		String wdc08 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wdc08)){
			excelData.setWdc08(new Long(wdc08));
		}
		
		cell = row.getCell(cellNum++); //09
		String wdc09 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wdc09)){
			excelData.setWdc09(new Long(wdc09));
		}
		
		cell = row.getCell(cellNum++); //10
		String wdc10 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wdc10)){
			excelData.setWdc10(new Integer(wdc10));
		}
		
		cell = row.getCell(cellNum++); //11
		String wdc11 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wdc11)){
			excelData.setWdc11(wdc11);
		}
		
		cell = row.getCell(cellNum++); //12
		String wdc12 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wdc12)){
			excelData.setWdc12(wdc12);
		}
		
		cell = row.getCell(cellNum++); //13
		String wdc13 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wdc13)){
			excelData.setWdc13(wdc13);
		}
		
		cell = row.getCell(cellNum++); //14
		String wdc14 = convertCellValueToString(cell);
		if(cell != null){
			excelData.setWdc14(sdf.parse(wdc14));
		}
		
		cell = row.getCell(cellNum++); //15 DRAWOUTDATE
		String drawoutdate = convertCellValueToString(cell);
		
		cell = row.getCell(cellNum++); //16 DATASOURCE
		String datasource = convertCellValueToString(cell);
		if(!StringUtil.isSpace(datasource)){
			excelData.setDatasource(datasource);
		}
		
		return excelData;
	}
	
	private Tmpfetclaimpay convertTmpfetclaimpayRowToData(String yyyMM, String[] row) throws Exception {
		Tmpfetclaimpay excelData = new Tmpfetclaimpay();
		int num = 0;
		
		num++; //00
		excelData.setWde00(new Integer(yyyMM));

		
		String wde01 = StringUtil.nullToSpace(row[num++]); //01
		if(!StringUtil.isSpace(wde01)){
			excelData.setWde01(wde01);
		}
		
		String wde02 = StringUtil.nullToSpace(row[num++]); //02
		if(!StringUtil.isSpace(wde02)){
			excelData.setWde02(wde02);
		}
		
		String wde03 = StringUtil.nullToSpace(row[num++]); //03
		if(!StringUtil.isSpace(wde03)){
			excelData.setWde03(wde03);
		}
		
		String wde04 = StringUtil.nullToSpace(row[num++]); //04
		if(!StringUtil.isSpace(wde04)){
			excelData.setWde04(new Integer(wde04));
		}
		
		String wde05 = StringUtil.nullToSpace(row[num++]); //05
		if(!StringUtil.isSpace(wde05)){
			excelData.setWde05(wde05);
		}
		
		String wde06 = StringUtil.nullToSpace(row[num++]); //06
		if(!StringUtil.isSpace(wde06)){
			excelData.setWde06(wde06);		
		}
		
		String wde07 = StringUtil.nullToSpace(row[num++]); //07
		if(!StringUtil.isSpace(wde07)){
			excelData.setWde07(wde07);
		}
		
		String wde08 = StringUtil.nullToSpace(row[num++]); //08
		if(!StringUtil.isSpace(wde08)){
			excelData.setWde08(wde08);
		}
		
		String wde09 = StringUtil.nullToSpace(row[num++]); //09
		if(!StringUtil.isSpace(wde09)){
			excelData.setWde09(wde09);
		}
		
		String wde10 = StringUtil.nullToSpace(row[num++]); //10
		if(!StringUtil.isSpace(wde10)){
			excelData.setWde10(wde10);
		}
		
		String wde11 = StringUtil.nullToSpace(row[num++]); //11
		if(!StringUtil.isSpace(wde11)){
			excelData.setWde11(wde11);
		}
		
		String wde12 = StringUtil.nullToSpace(row[num++]); //12
		if(!StringUtil.isSpace(wde12)){
			excelData.setWde12(wde12);
		}
		
		String wde13 = StringUtil.nullToSpace(row[num++]); //13
		if(!StringUtil.isSpace(wde13)){
			excelData.setWde13(wde13);
		}
		
		String wde14 = StringUtil.nullToSpace(row[num++]); //14
		if(!StringUtil.isSpace(wde14)){
			excelData.setWde14(wde14);
		}
		
		String wde15 = StringUtil.nullToSpace(row[num++]); //15
		if(!StringUtil.isSpace(wde15)){
			excelData.setWde15(wde15);
		}
		
		String wde16 = StringUtil.nullToSpace(row[num++]); //16
		if(!StringUtil.isSpace(wde16)){
			excelData.setWde16(wde16);
		}
		
		String wde17 = StringUtil.nullToSpace(row[num++]); //17
		if(!StringUtil.isSpace(wde17)){
			excelData.setWde17(wde17);
		}
		
		String wde18 = StringUtil.nullToSpace(row[num++]); //18
		if(!StringUtil.isSpace(wde18)){
			excelData.setWde18(wde18);
		}
		
		String wde19 = StringUtil.nullToSpace(row[num++]); //19
		if(!StringUtil.isSpace(wde19)){
			excelData.setWde19(wde19);
		}
		
		String wde21 = StringUtil.nullToSpace(row[num++]); //21
		if(!StringUtil.isSpace(wde21)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			excelData.setWde21(sdf.parse(wde21));
		}
				
		String wde22 = StringUtil.nullToSpace(row[num++]); //22
		if(!StringUtil.isSpace(wde22)){
			excelData.setWde22(new Long(wde22));
		}
		
		String wde23 = StringUtil.nullToSpace(row[num++]); //23 DRAWOUTDATE
		if(!StringUtil.isSpace(wde23)){
			String drawoutdate = wde23;
		}
		
		String wde24 = StringUtil.nullToSpace(row[num++]); //24 DATASOURCE
		if(!StringUtil.isSpace(wde24)){
			excelData.setDatasource(wde24);
		}

		return excelData;
	}
	
	private Tmpfetclaimpay convertTmpfetclaimpayRowToData(Row row) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Tmpfetclaimpay excelData = new Tmpfetclaimpay();
		Cell cell;
		int cellNum = 0;

		cell = row.getCell(cellNum++); //00
		String wde00 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wde00)){
			excelData.setWde00(new Integer(wde00));
		}else{
			return null;
		}
		
		cell = row.getCell(cellNum++); //01
		String wde01 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wde01)){
			excelData.setWde01(wde01);
		}
		
		cell = row.getCell(cellNum++); //02
		String wde02 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wde02)){
			excelData.setWde02(wde02);
		}
		
		cell = row.getCell(cellNum++); //03
		String wde03 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wde03)){
			excelData.setWde03(wde03);
		}
		
		cell = row.getCell(cellNum++); //04
		String wde04 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wde04)){
			excelData.setWde04(new Integer(wde04));
		}
		
		cell = row.getCell(cellNum++); //05
		String wde05 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wde05)){
			excelData.setWde05(wde05);
		}
		
		cell = row.getCell(cellNum++); //06
		String wde06 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wde06)){
			excelData.setWde06(wde06);		
		}
		
		cell = row.getCell(cellNum++); //07
		String wde07 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wde07)){
			excelData.setWde07(wde07);
		}
		
		cell = row.getCell(cellNum++); //08
		String wde08 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wde08)){
			excelData.setWde08(wde08);
		}
		
		cell = row.getCell(cellNum++); //09
		String wde09 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wde09)){
			excelData.setWde09(wde09);
		}
		
		cell = row.getCell(cellNum++); //10
		String wde10 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wde10)){
			excelData.setWde10(wde10);
		}
		
		cell = row.getCell(cellNum++); //11
		String wde11 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wde11)){
			excelData.setWde11(wde11);
		}
		
		cell = row.getCell(cellNum++); //12
		String wde12 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wde12)){
			excelData.setWde12(wde12);
		}
		
		cell = row.getCell(cellNum++); //13
		String wde13 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wde13)){
			excelData.setWde13(wde13);
		}
		
		cell = row.getCell(cellNum++); //14
		String wde14 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wde14)){
			excelData.setWde14(wde14);
		}
		
		cell = row.getCell(cellNum++); //15
		String wde15 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wde15)){
			excelData.setWde15(wde15);
		}
		
		cell = row.getCell(cellNum++); //16
		String wde16 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wde16)){
			excelData.setWde16(wde16);
		}
		
		cell = row.getCell(cellNum++); //17
		String wde17 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wde17)){
			excelData.setWde17(wde17);
		}
		
		cell = row.getCell(cellNum++); //18
		String wde18 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wde18)){
			excelData.setWde18(wde18);
		}
		
		cell = row.getCell(cellNum++); //19
		String wde19 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wde19)){
			excelData.setWde19(wde19);
		}
		
		cell = row.getCell(cellNum++); //21
		String wde21 = convertCellValueToString(cell);
		if(cell != null){
			excelData.setWde21(sdf.parse(wde21));
		}
				
		cell = row.getCell(cellNum++); //22
		String wde22 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wde22)){
			excelData.setWde22(new Long(wde22));
		}
		
		cell = row.getCell(cellNum++); //23 DRAWOUTDATE
		String drawoutdate = convertCellValueToString(cell);
		
		cell = row.getCell(cellNum++); //24 DATASOURCE
		String datasource = convertCellValueToString(cell);
		if(!StringUtil.isSpace(datasource)){
			excelData.setDatasource(datasource);
		}
		
		return excelData;
	}
	
	private Tmpfetclaimcomm convertTmpfetclaimcommRowToData(String yyyMM, String[] row) throws Exception {
		Tmpfetclaimcomm excelData = new Tmpfetclaimcomm();
		int num = 0;
		
		num++; //00
		excelData.setWdf00(new Integer(yyyMM));
		
		String wdf01 = StringUtil.nullToSpace(row[num++]); //01
		if(!StringUtil.isSpace(wdf01)){
			excelData.setWdf01(wdf01);
		}
		
		String wdf02 = StringUtil.nullToSpace(row[num++]); //02
		if(!StringUtil.isSpace(wdf02)){
			excelData.setWdf02(wdf02);
		}
		
		String wdf03 = StringUtil.nullToSpace(row[num++]); //03
		if(!StringUtil.isSpace(wdf03)){
			excelData.setWdf03(wdf03);
		}
		
		String wdf04 = StringUtil.nullToSpace(row[num++]); //04
		if(!StringUtil.isSpace(wdf04)){
			excelData.setWdf04(new Integer(wdf04));
		}
		
		String wdf05 = StringUtil.nullToSpace(row[num++]); //05
		if(!StringUtil.isSpace(wdf05)){
			excelData.setWdf05(wdf05);
		}
		
		String wdf06 = StringUtil.nullToSpace(row[num++]); //06
		if(!StringUtil.isSpace(wdf06)){
			excelData.setWdf06(wdf06);		
		}
		
		String wdf07 = StringUtil.nullToSpace(row[num++]); //07
		if(!StringUtil.isSpace(wdf07)){
			excelData.setWdf07(wdf07);
		}
		
		String wdf09 = StringUtil.nullToSpace(row[num++]); //09
		if(!StringUtil.isSpace(wdf09)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			excelData.setWdf09(sdf.parse(wdf09));
		}
		
		String wdf10 = StringUtil.nullToSpace(row[num++]); //10 DRAWOUTDATE
		if(!StringUtil.isSpace(wdf10)){
			String drawoutdate = wdf10;
		}
		
		String wdf11 = StringUtil.nullToSpace(row[num++]); //11 DATASOURCE
		if(!StringUtil.isSpace(wdf11)){
			excelData.setDatasource(wdf11);
		}
		
		return excelData;
	}
	
	private Tmpfetclaimcomm convertTmpfetclaimcommRowToData(Row row) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Tmpfetclaimcomm excelData = new Tmpfetclaimcomm();
		Cell cell;
		int cellNum = 0;

		cell = row.getCell(cellNum++); //00
		String wdf00 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wdf00)){
			excelData.setWdf00(new Integer(wdf00));
		}else{
			return null;
		}
		
		cell = row.getCell(cellNum++); //01
		String wdf01 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wdf01)){
			excelData.setWdf01(wdf01);
		}
		
		cell = row.getCell(cellNum++); //02
		String wdf02 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wdf02)){
			excelData.setWdf02(wdf02);
		}
		
		cell = row.getCell(cellNum++); //03
		String wdf03 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wdf03)){
			excelData.setWdf03(wdf03);
		}
		
		cell = row.getCell(cellNum++); //04
		String wdf04 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wdf04)){
			excelData.setWdf04(new Integer(wdf04));
		}
		
		cell = row.getCell(cellNum++); //05
		String wdf05 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wdf05)){
			excelData.setWdf05(wdf05);
		}
		
		cell = row.getCell(cellNum++); //06
		String wdf06 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wdf06)){
			excelData.setWdf06(wdf06);		
		}
		
		cell = row.getCell(cellNum++); //07
		String wdf07 = convertCellValueToString(cell);
		if(!StringUtil.isSpace(wdf07)){
			excelData.setWdf07(wdf07);
		}
		
		cell = row.getCell(cellNum++); //09
		String wdf09 = convertCellValueToString(cell);
		if(cell != null){
			excelData.setWdf09(sdf.parse(wdf09));
		}
		
		cell = row.getCell(cellNum++); //10 DRAWOUTDATE
		String drawoutdate = convertCellValueToString(cell);
		
		cell = row.getCell(cellNum++); //11 DATASOURCE
		String datasource = convertCellValueToString(cell);
		if(!StringUtil.isSpace(datasource)){
			excelData.setDatasource(datasource);
		}
		
		return excelData;
	}
	
	private String convertCellValueToString(Cell cell) {
		if (cell == null) {
			return null;
		}
		String returnValue = null;
		switch (cell.getCellTypeEnum()) {
		case NUMERIC:
			Double doubleValue = cell.getNumericCellValue();
			DecimalFormat df = new DecimalFormat("0");
			returnValue = df.format(doubleValue);
			break;
		case STRING:
			returnValue = cell.getStringCellValue();
			break;
		case BOOLEAN:
			Boolean booleanValue = cell.getBooleanCellValue();
			returnValue = booleanValue.toString();
			break;
		case BLANK:
			break;
		case FORMULA:
			returnValue = cell.getCellFormula();
			break;
		case ERROR:
			break;
		default:
			break;
		}
		return returnValue;
	}
	
	public static void main(String args[]){

		String tempPath = System.getProperty("java.io.tmpdir");
		System.out.println("tempPath = " + tempPath);
		
		File f = new File("D:\\temp\\b2b2cDev.cer");
		System.out.println("getName = " + f.getName());
		
		String d = "1120507";
		System.out.println(Integer.parseInt(d.substring(0, 3)) + 1911 + "" + d.substring(3));
		
		
		String s1 = "1||||||4";
		String[] sary = s1.split("\\|\\|");
		System.out.println("sary.length = " + sary.length);
	}
	
	public ConfigUtil getConfigUtil() {
		return configUtil;
	}
	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public MiClaimDownloadNewTransService getMiClaimDownloadNewTransService() {
		return miClaimDownloadNewTransService;
	}

	public void setMiClaimDownloadNewTransService(
			MiClaimDownloadNewTransService miClaimDownloadNewTransService) {
		this.miClaimDownloadNewTransService = miClaimDownloadNewTransService;
	}

	public TmpfetclaimmainService getTmpfetclaimmainService() {
		return tmpfetclaimmainService;
	}

	public void setTmpfetclaimmainService(
			TmpfetclaimmainService tmpfetclaimmainService) {
		this.tmpfetclaimmainService = tmpfetclaimmainService;
	}

	public TmpfetclaimkindService getTmpfetclaimkindService() {
		return tmpfetclaimkindService;
	}

	public void setTmpfetclaimkindService(
			TmpfetclaimkindService tmpfetclaimkindService) {
		this.tmpfetclaimkindService = tmpfetclaimkindService;
	}

	public TmpfetclaimpayService getTmpfetclaimpayService() {
		return tmpfetclaimpayService;
	}

	public void setTmpfetclaimpayService(TmpfetclaimpayService tmpfetclaimpayService) {
		this.tmpfetclaimpayService = tmpfetclaimpayService;
	}

	public TmpfetclaimcommService getTmpfetclaimcommService() {
		return tmpfetclaimcommService;
	}

	public void setTmpfetclaimcommService(
			TmpfetclaimcommService tmpfetclaimcommService) {
		this.tmpfetclaimcommService = tmpfetclaimcommService;
	}

	public PrpcplanService getPrpcplanService() {
		return prpcplanService;
	}

	public void setPrpcplanService(PrpcplanService prpcplanService) {
		this.prpcplanService = prpcplanService;
	}

	public IntfprpjpayrefrecService getIntfprpjpayrefrecService() {
		return intfprpjpayrefrecService;
	}

	public void setIntfprpjpayrefrecService(
			IntfprpjpayrefrecService intfprpjpayrefrecService) {
		this.intfprpjpayrefrecService = intfprpjpayrefrecService;
	}

	public PrpcmainService getPrpcmainService() {
		return prpcmainService;
	}

	public void setPrpcmainService(PrpcmainService prpcmainService) {
		this.prpcmainService = prpcmainService;
	}
}
