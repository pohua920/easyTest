/**
 * 20210222
 * mantis：FIR0220，處理人員：BJ016，需求單編號：FIR0220 中信代理投保通知處理
 * */
package com.tlg.aps.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.commons.io.FileUtils;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.firCtbcRewNoticeService.FirCtbcRewNoticeService;
import com.tlg.aps.bs.firCtbcRewNoticeService.RewDataInsertService;
import com.tlg.aps.thread.FirCtbcRewNoticeBatchThread;
import com.tlg.aps.util.DeleteAfterDownloadFileInputStream;
import com.tlg.aps.vo.Aps012DetailVo;
import com.tlg.aps.vo.FirCtbcRewNoticeBatchVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirCtbcRewFix180;
import com.tlg.prpins.entity.FirCtbcRewNoshowword;
import com.tlg.prpins.entity.FirCtbcRewNoticeBatch;
import com.tlg.prpins.service.FirCtbcRewFix180Service;
import com.tlg.prpins.service.FirCtbcRewNoticeBatchService;
import com.tlg.util.BaseAction;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;
import com.tlg.util.UserInfo;

@SuppressWarnings("serial")
public class APS012Action extends BaseAction {
	
	private File upload1;//上傳的檔案
	private String upload1ContentType;//檔案型態
	private String upload1FileName; //檔案名稱
	
	private File upload2;//上傳的檔案
	private String upload2ContentType;//檔案型態
	private String upload2FileName; //檔案名稱
	
	private File upload3;//上傳的檔案
	private String upload3ContentType;//檔案型態
	private String upload3FileName; //檔案名稱
	
	private List<FirCtbcRewNoticeBatch> devResults = new ArrayList<FirCtbcRewNoticeBatch>();
	private List<FirCtbcRewFix180> devFix180Results = new ArrayList<FirCtbcRewFix180>();
	
	private RewDataInsertService rewDataInsertService;
	private FirCtbcRewNoticeBatchService firCtbcRewNoticeBatchService;
	private FirCtbcRewFix180Service firCtbcRewFix180Service;
	private FirCtbcRewNoticeService firCtbcRewNoticeService;
	
	private Aps012DetailVo aps012DetailVo;
	private FirCtbcRewFix180 editFirCtbcRewFix180;
	
	private InputStream fileInputStream;
	private OutputStream fileOutputStream;
	
	private long lengthOfMyFile;
	private String token;
	
	/** 載入畫面下拉資料 */
	private void formLoad(String type) throws SystemException, Exception {

	}
	
	/** 參數處理 */
	@SuppressWarnings("unchecked")
	private void parameterHandler() throws SystemException, Exception {
		String year = (String)getPageInfo().getFilter().get("year");
		String month = (String)getPageInfo().getFilter().get("month");
		String ctbcno = "";
		if(year != null && year.length() > 0) {
			ctbcno += year;
			if(month != null && month.length() > 0) {
				ctbcno += month;
			}
			getPageInfo().getFilter().put("likeCtbcno", ctbcno);
		}else {
			if(month != null && month.length() > 0) {
				//沒輸入民國年只輸入月份的話，故意讓人查不到
				getPageInfo().getFilter().put("likeCtbcno", "yyymm");
			}else {
				getPageInfo().getFilter().remove("likeCtbcno");
			}
		}
		
		String invalidflag = (String)getPageInfo().getFilter().get("invalidflag");
		if(invalidflag != null && "*".equals(invalidflag)) {
			getPageInfo().getFilter().remove("invalidflag");
		}
	}

	/** 進入查詢頁面前會進來做的事情 */
	public String execute() throws Exception {
		try {
			
			formLoad("execute");

		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String lnkGoExecute() throws Exception {
		try {
			System.out.println("lnkGoExecute.....");
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String lnkGoEditO180Fix() throws Exception {
		try {
			Map<String, String> filter = new HashMap<String, String>();
			if(aps012DetailVo.getOid() == null || aps012DetailVo.getOid().length() <= 0) {
				setMessage("查無資料");
				query();
				return Action.ERROR;
			}
			if(aps012DetailVo.getO180filename() == null || aps012DetailVo.getO180filename().length() <= 0) {
				setMessage("查無資料");
				query();
				return Action.ERROR;
			}
			getPageInfo().setFilter(filter);
			getPageInfo().setCurrentPage(1);
			getPageInfo().getFilter().put("batchOid",aps012DetailVo.getOid());
			getPageInfo().getFilter().put("o180filename",aps012DetailVo.getO180filename());
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String lnkGoEditO180FixPage() throws Exception {
		try {
			Map params = new HashMap();
			params.put("batchOid", aps012DetailVo.getOid());
			params.put("o180filename", aps012DetailVo.getO180filename());
			params.put("linenum", aps012DetailVo.getLinenum());
			Result result = this.firCtbcRewFix180Service.findFirCtbcRewFix180ByParams(params);
			if(result != null && result.getResObject() != null) {
				List<FirCtbcRewFix180> searchResult = (List<FirCtbcRewFix180>)result.getResObject();
				editFirCtbcRewFix180 = searchResult.get(0);
			}else {
				setMessage("查無資料");
				this.queryFix180();
				return "returnQuery";
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String btnQuery() throws Exception {
		try{
			formLoad("execute");
			getPageInfo().setCurrentPage(1);
			getPageInfo().getFilter().put("sortBy", "DCREATE");
			getPageInfo().getFilter().put("sortType", "DESC");
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String btnQueryFix180() throws Exception {
		try{
			getPageInfo().getFilter().put("sortBy", "LINENUM");
			getPageInfo().getFilter().put("sortType", "ASC");
			queryFix180();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String btnUpdateFix180() throws Exception {
		try{
			this.rewDataInsertService.updateFirCtbcRewFix180(editFirCtbcRewFix180, getUserInfo());
			setMessage("更新成功");
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String btnCancelBatch() throws Exception {
		try{
			Map params = new HashMap();
			params.put("oid", aps012DetailVo.getOid());
			Result result = this.firCtbcRewNoticeBatchService.findFirCtbcRewNoticeBatchByParams(params);
			if(result != null && result.getResObject() != null) {
				List<FirCtbcRewNoticeBatch> searchResult =(List<FirCtbcRewNoticeBatch>)result.getResObject();
				FirCtbcRewNoticeBatch firCtbcRewNoticeBatch = searchResult.get(0);
				firCtbcRewNoticeBatch.setInvalidflag("Y");
				this.firCtbcRewNoticeBatchService.updateFirCtbcRewNoticeBatch(firCtbcRewNoticeBatch);
			}
			
			this.query();
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	/** 進入查詢頁面前會進來做的事情 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String btnExecute() throws Exception {
		try {
			FirCtbcRewNoticeBatchVo voObject = new FirCtbcRewNoticeBatchVo();
			boolean hasFile = false;
			boolean mustHaveFile = false;
			String errorMsg = "";
			String file1Date = "";
			String file2Date = "";
			File destFile = null;
			if (upload1 != null) {
				hasFile = true;
				mustHaveFile = true;
				if(!this.checkFilename(0, upload1FileName)) {
					errorMsg += "請重新選擇正確的個金檔案。";
				}else {
					destFile  = new File("D:\\temp\\testRfr\\", upload1FileName);
					FileUtils.copyFile(upload1, destFile);
					voObject.setP180FileName(upload1FileName);
					voObject.setP180File(destFile);
				}
				
				if(upload1FileName != null && upload1FileName.length() >= 15) {
					file1Date = upload1FileName.substring(8, 15);
				}
			}
			
			if (upload2 != null) {
				hasFile = true;
				mustHaveFile = true;
				if(!this.checkFilename(1, upload2FileName)) {
					errorMsg += "請重新選擇正確的法金檔案。";
				}else {
					destFile  = new File("D:\\temp\\testRfr\\", upload2FileName);
					FileUtils.copyFile(upload2, destFile);
					voObject.setC180FileName(upload2FileName);
					voObject.setC180File(destFile);
				}
				
				if(upload2FileName != null && upload2FileName.length() >= 16) {
					file2Date = upload2FileName.substring(9, 16);
				}
			}

			if (upload3 != null) {
				hasFile = true;
				if(!this.checkFilename(2, upload3FileName)) {
					errorMsg += "不通知續保件必須為excel 檔案。";
				}else {
					destFile  = new File("D:\\temp\\testRfr\\", upload3FileName);
					FileUtils.copyFile(upload3, destFile);
					voObject.setDontnoticeFileName(upload3FileName);
					voObject.setDontnoticeFile(destFile);
				}
			}
			
			if(!hasFile) {
				setMessage("請先選擇檔案！");
				return Action.SUCCESS;
			}
			
			if(errorMsg.length() > 0) {
				setMessage(errorMsg);
				return Action.SUCCESS;
			}
			
			if(!mustHaveFile) {
				setMessage("尚未選擇180檔案。");
				return Action.SUCCESS;
			}else {
				if(file1Date.length() > 0 && file2Date.length() > 0 && !file1Date.equals(file2Date)) {
					setMessage("非同期的個金法金180檔案，請重新選擇。");
					return Action.SUCCESS;
				}
			}
			
			String fileDate = "";
			if(file1Date.length() > 0 && fileDate.length() <= 0)
				fileDate = file1Date;
			
			if(file2Date.length() > 0 && fileDate.length() <= 0)
				fileDate = file2Date;
			
			voObject.setCtbcno(fileDate);
			
			Map params = new HashMap();
			params.put("ctbcno", fileDate);
			params.put("alreadyUpload", "Y");
			Result result = firCtbcRewNoticeBatchService.findFirCtbcRewNoticeBatchByParams(params);
			if(result != null && result.getResObject() != null) {
				String msg = "";
				List<FirCtbcRewNoticeBatch> searchResult = (List<FirCtbcRewNoticeBatch>)result.getResObject();
				FirCtbcRewNoticeBatch firCtbcRewNoticeBatch = searchResult.get(0);
				if("N".equals(firCtbcRewNoticeBatch.getInvalidflag())) 
					msg = "本檔案已經上傳過，若需要重新上傳，請先至查詢功能作廢已上傳的批次。";
				else if("Y".equals(firCtbcRewNoticeBatch.getTransing())) 
					msg = "本檔案已經上傳過，仍在轉檔中，請稍後至查詢功能檢視結果。";
				
				if(msg.length() > 0) {
					setMessage(msg);
					return Action.SUCCESS;
				}
			}
			
			UserInfo userInfo = this.getUserInfo();
			FirCtbcRewNoticeBatchThread fThread = new FirCtbcRewNoticeBatchThread(userInfo, voObject);
			fThread.start();
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String btnGenCSV() throws Exception {
		try {
			File csvFile = this.firCtbcRewNoticeService.genCSV(aps012DetailVo.getOid());

//			fileOutputStream = new FileOutputStream(csvFile);  
//            byte[] buffer = new byte[4096];
//            int readLength = 0;
//            while ((readLength=fileInputStream.read(buffer)) > 0) {
//                byte[] bytes = new byte[readLength];
//                System.arraycopy(buffer, 0, bytes, 0, readLength);
//                fileOutputStream.write(bytes);
//            }
//            
//            fileOutputStream.flush();
            this.fileInputStream = new DeleteAfterDownloadFileInputStream(csvFile);
            
            Map params = new HashMap();
            params.put("oid", aps012DetailVo.getOid());
            Result result = this.firCtbcRewNoticeBatchService.findFirCtbcRewNoticeBatchByParams(params);
            if(result != null && result.getResObject() != null) {
            	List<FirCtbcRewNoticeBatch> searchResult = (List<FirCtbcRewNoticeBatch>)result.getResObject();
            	FirCtbcRewNoticeBatch firCtbcRewNoticeBatch = searchResult.get(0);
            	firCtbcRewNoticeBatch.setInsdwtime(new Date());
            	firCtbcRewNoticeBatch.setInsdwuser(getUserInfo().getUserId());
            	this.firCtbcRewNoticeBatchService.updateFirCtbcRewNoticeBatch(firCtbcRewNoticeBatch);
            }
          //連動cookie，讓頁面上的blockUI解除block狀態----START
            Cookie cookie = new Cookie("aps012Download",this.token);
            cookie.setPath("/");
            this.getResponse().addCookie(cookie);
            //連動cookie，讓頁面上的blockUI解除block狀態----END
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String btnGenExcel() throws Exception {
		try {

			File file = this.firCtbcRewNoticeService.genExcel(aps012DetailVo.getOid(), aps012DetailVo.getO180filename());

//			fileOutputStream = new FileOutputStream(file);  
//            byte[] buffer = new byte[4096];
//            int readLength = 0;
//            while ((readLength=fileInputStream.read(buffer)) > 0) {
//                byte[] bytes = new byte[readLength];
//                System.arraycopy(buffer, 0, bytes, 0, readLength);
//                fileOutputStream.write(bytes);
//            }
//            
//            fileOutputStream.flush();
			lengthOfMyFile = file.length();
            this.fileInputStream = new DeleteAfterDownloadFileInputStream(file);
            
            Map params = new HashMap();
            params.put("oid", aps012DetailVo.getOid());
            Result result = this.firCtbcRewNoticeBatchService.findFirCtbcRewNoticeBatchByParams(params);
            if(result != null && result.getResObject() != null) {
            	List<FirCtbcRewNoticeBatch> searchResult = (List<FirCtbcRewNoticeBatch>)result.getResObject();
            	FirCtbcRewNoticeBatch firCtbcRewNoticeBatch = searchResult.get(0);
            	
            	Date now = new Date();
            	String userid = getUserInfo().getUserId();
            	if("P".equals(aps012DetailVo.getExcelType())) {
            		firCtbcRewNoticeBatch.setPrenewaldwtime(now);
                	firCtbcRewNoticeBatch.setPrenewaldwuser(userid);
            	}else if("C".equals(aps012DetailVo.getExcelType())) {
            		firCtbcRewNoticeBatch.setCrenewaldwtime(now);
                	firCtbcRewNoticeBatch.setCrenewaldwuser(userid);
            	}
            	this.firCtbcRewNoticeBatchService.updateFirCtbcRewNoticeBatch(firCtbcRewNoticeBatch);
            }
            
            //連動cookie，讓頁面上的blockUI解除block狀態----START
            Cookie cookie = new Cookie("aps012Download",this.token);
            cookie.setPath("/");
            this.getResponse().addCookie(cookie);
            //連動cookie，讓頁面上的blockUI解除block狀態----END
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String btnCompareData() throws Exception {
		try {
			Result result = this.firCtbcRewNoticeService.compareData(getUserInfo(), aps012DetailVo.getOid());
			boolean check = false;
			if(result != null && result.getResObject() != null) {
				check = (Boolean)result.getResObject();
			}
			
			if(check) {
				setMessage("比對成功。");
			}else {
				setMessage(result.getMessage().getMessageString());
			}
			
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String btnDownloadCompareData() throws Exception {
		try {
			File xlsFile = this.firCtbcRewNoticeService.genExcelDiff(aps012DetailVo.getMatchcoreoid());
			if(xlsFile != null) {
				this.fileInputStream = new DeleteAfterDownloadFileInputStream(xlsFile);
			}else {
				setMessage("查無差異資料");
				return "error";
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String btnDownloadCompareData2() throws Exception {
		try {
			File xlsFile = this.firCtbcRewNoticeService.genExcelDiff2(aps012DetailVo.getMatchcoreoid());
			if(xlsFile != null) {
				this.fileInputStream = new DeleteAfterDownloadFileInputStream(xlsFile);
			}else {
				setMessage("查無差異資料");
				return "error";
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	private boolean checkFilename(int type, String filename) {
		
		if(filename == null || filename.length() < 0) {
			return false;
		}
		
		String fileDate;
		String strStartWith = "";
		String strEndWith = "";
		if(type == 0) {
			strStartWith = "ReCaspo0";
			strEndWith = ".180";//20210406：BJ016：需求單編號：FIR0220 中信代理投保通知處理。修正檔名檢核，將中文去掉
		}else if(type == 1) {
			strStartWith = "ReCaspo10";
			strEndWith = ".180";//20210406：BJ016：需求單編號：FIR0220 中信代理投保通知處理。修正檔名檢核，將中文去掉
		}else if(type == 2) {
			if(filename.endsWith("xlsx") || filename.endsWith("xls")) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
		
		if(!filename.startsWith(strStartWith) || !filename.endsWith(strEndWith)) {
			return false;
		}
		
		if(filename != null && filename.length() >= strStartWith.length() && filename.length() <= strStartWith.length()+7) {
			fileDate = filename.substring(strStartWith.length(), strStartWith.length()+7);
			if(!StringUtil.isNumeric(fileDate)) {
				return false;
			}
		}
		
		return true;
	}
	
	public String ddlPageSizeChanged() throws Exception {
		try {
			formLoad("query");
			PageInfo pageInfo = getPageInfo();
			pageInfo.setCurrentPage(1);
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String ddlPageSizeChangedFix180() throws Exception {
		try {
			formLoad("query");
			PageInfo pageInfo = getPageInfo();
			pageInfo.setCurrentPage(1);
			queryFix180();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String txtChangePageIndex() throws Exception {
		try {
			formLoad("query");
			PageInfo pageInfo = getPageInfo();
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String txtChangePageIndexFix180() throws Exception {
		try {
			formLoad("query");
			PageInfo pageInfo = getPageInfo();
			queryFix180();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	private void query() throws SystemException, Exception {

		parameterHandler();
		
		// 檔案清單查詢
		Result result = this.firCtbcRewNoticeBatchService.findFirCtbcRewNoticeBatchByPageInfo(getPageInfo());
		
		if(result != null && result.getResObject() != null) {
			devResults = (List<FirCtbcRewNoticeBatch>)result.getResObject();
		}else {
			setMessage(result.getMessage().toString());
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void queryFix180() throws SystemException, Exception {

//		Map params = new HashMap();
//		params.put("batchOid",aps012DetailVo.getOid());
//		params.put("o180filename",aps012DetailVo.getO180filename());
//		params.put("policynumber",aps012DetailVo.getPolicynumber());
//		params.put("sortBy", "LINENUM");
//		params.put("sortType", "ASC");
		// 查詢
		Result result = this.firCtbcRewFix180Service.findFirCtbcRewFix180ByPageInfo(getPageInfo());
		
		if(result != null && result.getResObject() != null) {
			devFix180Results = (List<FirCtbcRewFix180>)result.getResObject();
		}else {
			setMessage(result.getMessage().toString());
		}
	}
	
	public String rocToAd(String rocDate, String delimiter) {
		String[] arrDate = rocDate.split(delimiter);
		if(arrDate.length >= 3) {
			return Integer.parseInt(arrDate[0]) + 1911 + "/" + arrDate[1] + "/" + arrDate[2] ;
		}
		return "";
	}

	public File getUpload1() {
		return upload1;
	}

	public void setUpload1(File upload1) {
		this.upload1 = upload1;
	}

	public String getUpload1ContentType() {
		return upload1ContentType;
	}

	public void setUpload1ContentType(String upload1ContentType) {
		this.upload1ContentType = upload1ContentType;
	}

	public String getUpload1FileName() {
		return upload1FileName;
	}

	public void setUpload1FileName(String upload1FileName) {
		this.upload1FileName = upload1FileName;
	}

	public File getUpload2() {
		return upload2;
	}

	public void setUpload2(File upload2) {
		this.upload2 = upload2;
	}

	public String getUpload2ContentType() {
		return upload2ContentType;
	}

	public void setUpload2ContentType(String upload2ContentType) {
		this.upload2ContentType = upload2ContentType;
	}

	public String getUpload2FileName() {
		return upload2FileName;
	}

	public void setUpload2FileName(String upload2FileName) {
		this.upload2FileName = upload2FileName;
	}

	public File getUpload3() {
		return upload3;
	}

	public void setUpload3(File upload3) {
		this.upload3 = upload3;
	}

	public String getUpload3ContentType() {
		return upload3ContentType;
	}

	public void setUpload3ContentType(String upload3ContentType) {
		this.upload3ContentType = upload3ContentType;
	}

	public String getUpload3FileName() {
		return upload3FileName;
	}

	public void setUpload3FileName(String upload3FileName) {
		this.upload3FileName = upload3FileName;
	}

	public FirCtbcRewNoticeBatchService getFirCtbcRewNoticeBatchService() {
		return firCtbcRewNoticeBatchService;
	}

	public void setFirCtbcRewNoticeBatchService(FirCtbcRewNoticeBatchService firCtbcRewNoticeBatchService) {
		this.firCtbcRewNoticeBatchService = firCtbcRewNoticeBatchService;
	}

	public List<FirCtbcRewNoticeBatch> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<FirCtbcRewNoticeBatch> devResults) {
		this.devResults = devResults;
	}

	public Aps012DetailVo getAps012DetailVo() {
		return aps012DetailVo;
	}

	public void setAps012DetailVo(Aps012DetailVo aps012DetailVo) {
		this.aps012DetailVo = aps012DetailVo;
	}

	public FirCtbcRewFix180Service getFirCtbcRewFix180Service() {
		return firCtbcRewFix180Service;
	}

	public void setFirCtbcRewFix180Service(FirCtbcRewFix180Service firCtbcRewFix180Service) {
		this.firCtbcRewFix180Service = firCtbcRewFix180Service;
	}

	public List<FirCtbcRewFix180> getDevFix180Results() {
		return devFix180Results;
	}

	public void setDevFix180Results(List<FirCtbcRewFix180> devFix180Results) {
		this.devFix180Results = devFix180Results;
	}

	public FirCtbcRewFix180 getEditFirCtbcRewFix180() {
		return editFirCtbcRewFix180;
	}

	public void setEditFirCtbcRewFix180(FirCtbcRewFix180 editFirCtbcRewFix180) {
		this.editFirCtbcRewFix180 = editFirCtbcRewFix180;
	}

	public FirCtbcRewNoticeService getFirCtbcRewNoticeService() {
		return firCtbcRewNoticeService;
	}

	public void setFirCtbcRewNoticeService(FirCtbcRewNoticeService firCtbcRewNoticeService) {
		this.firCtbcRewNoticeService = firCtbcRewNoticeService;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	public OutputStream getFileOutputStream() {
		return fileOutputStream;
	}

	public void setFileOutputStream(OutputStream fileOutputStream) {
		this.fileOutputStream = fileOutputStream;
	}

	public RewDataInsertService getRewDataInsertService() {
		return rewDataInsertService;
	}

	public void setRewDataInsertService(RewDataInsertService rewDataInsertService) {
		this.rewDataInsertService = rewDataInsertService;
	}

	public long getLengthOfMyFile() {
		return lengthOfMyFile;
	}

	public void setLengthOfMyFile(long lengthOfMyFile) {
		this.lengthOfMyFile = lengthOfMyFile;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
