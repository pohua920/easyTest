package com.tlg.aps.action;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.firAddressImportService.RunFirAddressImportService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAddrImporterr;
import com.tlg.prpins.entity.FirAddrImportlist;
import com.tlg.prpins.service.FirAddrImporterrService;
import com.tlg.prpins.service.FirAddrImportlistService;
import com.tlg.util.BaseAction;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@SuppressWarnings("serial")
public class APS005Action extends BaseAction {
	/* mantis：FIR0183，處理人員：BJ085，需求單編號：FIR0183 火險地址資料匯入 start */
	
	// PageInfoName、PageInfo及Filter
	private String ePageInfoName = this.getClass().getSimpleName() + "ePageInfo";
	private PageInfo ePageInfo;
	private Map<String, String> eFilter;
	
	private FirAddrImportlistService firAddrImportlistService;
	private FirAddrImporterrService firAddrImporterrService;
	private RunFirAddressImportService runFirAddressImportService;
	
	private FirAddrImportlist firAddrImportlist;
	private List<FirAddrImportlist> devResults;
	private List<FirAddrImporterr> errMsgDevResults;

	private static final String FILEPATH = "D:\\APS005FileUpload\\";
	private File upload = null;//上傳的檔案
	private String fileType = "xlsx";//檔案型態
	private String uploadFileName; //檔案名稱
	private String downloadFileName;
	private InputStream inputStream;
	
	/**mantis：FIR0183，處理人員：BJ016，需求單編號：FIR0183 火險地址資料匯入:排程依據上傳方式決定全刪全增或是部份增修*/
	private String ultype;//上傳類型
	
	/** 載入畫面下拉資料 */
	private void formLoad(String type) throws Exception {
		
	}
	
	/** 參數處理 */
	@SuppressWarnings("unchecked")
	private void parameterHandler() throws Exception {
		String strDate = (String)getPageInfo().getFilter().get("sDate");
		strDate = rocToAd(strDate, "/");
		if(!StringUtil.isSpace(strDate)) {
			strDate += " 00:00:00";
			getPageInfo().getFilter().put("startCreateDate", strDate);
		}else {
			getPageInfo().getFilter().remove("startCreateDate");
		}
		
		strDate = (String)getPageInfo().getFilter().get("eDate");
		strDate = rocToAd(strDate, "/");
		if(!StringUtil.isSpace(strDate)) {
			strDate += " 23:59:59";
			getPageInfo().getFilter().put("endCreateDate", strDate);
		}else {
			getPageInfo().getFilter().remove("endCreateDate");
		}
		
		/**mantis：FIR0183，處理人員：BJ016，需求單編號：FIR0183 火險地址資料匯入:排程依據上傳方式決定全刪全增或是部份增修*/
		String ultype = (String)getPageInfo().getFilter().get("ultype");
		if("*".equals(ultype)) {
			getPageInfo().getFilter().remove("ultype");
		}
	}
	
	/** (主檔)按下查詢鍵，開始搜尋*/
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
			formLoad("btnQuery");
			getStatus();
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
	/** 分頁資料中，重新輸入要顯示的頁數 */
	public String txtChangePageIndex() throws Exception {
		try {
			formLoad("txtChangePageIndex");
			getStatus();			
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	/** 分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
	public String ddlPageSizeChanged() throws Exception {
		try {
			formLoad("ddlPageSizeChanged");
			getStatus();			
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
	
	/** (異常訊息頁面)點選資料失敗筆數，開始搜尋 */
	@SuppressWarnings("unchecked")
	public String btnQuery2() throws Exception {
		String forward = "input";
		try {
			formLoad("btnQuery2");
			if (null == firAddrImportlist.getFilenameNew()) {
				setMessage("請重新操作");
			} else {
				getEPageInfo().setCurrentPage(1);
				getEPageInfo().getFilter().put("sortBy", "EXCEL_ROW");
				getEPageInfo().getFilter().put("sortType", "ASC");
				getEPageInfo().getFilter().put("filenameNew", firAddrImportlist.getFilenameNew());
				errMsgQuery();
				forward = Action.SUCCESS;
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return forward;
	}

	/** (異常訊息頁面)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
	public String ddlEmPageSizeChanged() throws Exception {
		try {
			formLoad("ddlEmPageSizeChanged");
			PageInfo epageInfo = getEPageInfo();
			epageInfo.setCurrentPage(1);
			errMsgQuery();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	/** (異常訊息頁面)分頁資料中，重新輸入要顯示的頁數 */
	public String txtEmChangePageIndex() throws Exception {
		try {
			formLoad("txtChangePageIndex");			
			errMsgQuery();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	/** 進入查詢頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		try {
			formLoad("execute");
			resetEPageInfo();
			/**mantis：FIR0183，處理人員：BJ016，需求單編號：FIR0183 火險地址資料匯入:排程依據上傳方式決定全刪全增或是部份增修*/
			this.ultype = "2";
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	/**點選確定轉入鍵，開始上傳檔案 */
	public String btnFileUpd() throws Exception{
		try {
			formLoad("btnFileUpd");
			Result result = fileUpload();
			setMessage(result.getMessage().toString());
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		
		return Action.SUCCESS;		
	}

	@SuppressWarnings("unchecked")
	private void query() throws Exception {
		parameterHandler();
		Result result = this.firAddrImportlistService.findFirAddrImportlistByPageInfo(getPageInfo());
		if(result != null && result.getResObject() != null) {
			devResults = (List<FirAddrImportlist>)result.getResObject();
		}else {
			setMessage(result.getMessage().toString());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void errMsgQuery() throws Exception {
		Result result = firAddrImporterrService.findFirAddrImporterrByPageInfo(getEPageInfo());
		if (null == result.getResObject()) {
			setMessage(result.getMessage().toString());
		} else {
			errMsgDevResults = (List<FirAddrImporterr>)result.getResObject();
		}
	}

	/** 連結至查詢頁面 */
	public String lnkGoQuery() throws Exception {
		try {
			formLoad("lnkGoQuery");
			if (getPageInfo().getRowCount() > 0) {
				getStatus();
				query();
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** 連結至檔案上傳頁面 */
	public String lnkGoUpload() throws Exception {
		try {
			formLoad("lnkGoUpload");
			
			/**mantis：FIR0183，處理人員：BJ016，需求單編號：FIR0183 火險地址資料匯入:排程依據上傳方式決定全刪全增或是部份增修*/
			this.ultype = "2";
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** 火險地址資料匯入作業(手動) */
	public String btnDataImport() throws Exception {
		try{
			formLoad("btnDataImport");
			Date excuteTime = new Date();
			String programId = "FIR_ADDR_01";
			Result result = runFirAddressImportService.AddressDataImport(getUserInfo(),excuteTime,programId);
			setMessage(result.getMessage().toString());
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/*下載sample檔案*/
	public String btnDowloadSample() throws Exception {
		try {
			downloadFileName = "SAMPLE.xlsx";
			//mantis：FIR0611，處理人員：BJ085，需求單編號：FIR0611 住火-標的物地址正規化-FIR0520_183地址匯入排程調整
			inputStream = APS005Action.class.getClassLoader().getResourceAsStream("xlsx/AddressImportSample.xlsx");		
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	//上傳檔案
	public Result fileUpload() throws Exception {
		Result result = new Result();	
		if (upload == null) {
			throw new SystemException("請先選擇檔案！");
		}
		if (upload.length() == 0) {
			String path = upload.getPath();
			File file = new File(path);
			FileUtils.writeStringToFile(file, "");
			file = null;
		}
		
		uploadFileName = StringUtil.nullToSpace(getUploadFileName());
		String filtType = "";
		int pos = uploadFileName.lastIndexOf(".");
		if (pos != -1) {
			filtType = uploadFileName.substring(pos + 1);
		} else {
			filtType = "";
		}
		if (!"".equals(fileType) && !"".equals(filtType) && fileType.indexOf(filtType) == -1) {
				throw new SystemException("上傳文件只允許." + fileType + "副檔名！");
		}
		
		File f = new File(FILEPATH);
		if (!f.exists()) {
			FileUtils.forceMkdir(f);
		}

		Date date = new Date();
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMddHHmmss");		
		String dateTime = sdFormat.format(date);
		String filenameNew = "FIRADDR"+dateTime+".xlsx";
		File file = new File(FILEPATH, filenameNew);
		FileUtils.copyFile(getUpload(), file);
		
		firAddrImportlist = new FirAddrImportlist(); 
		firAddrImportlist.setFilenameOri(uploadFileName);
		firAddrImportlist.setFilenameNew(filenameNew);
		firAddrImportlist.setFileStatus("N");
		firAddrImportlist.setDeleteFlag("N");
		
		/**mantis：FIR0183，處理人員：BJ016，需求單編號：FIR0183 火險地址資料匯入:排程依據上傳方式決定全刪全增或是部份增修*/
		firAddrImportlist.setUltype(this.ultype);
		
		firAddrImportlist.setIcreate(getUserInfo().getUserId().toUpperCase());
		firAddrImportlist.setDcreate(new Date());
		Result faiResult = firAddrImportlistService.insertFirAddrImportlist(firAddrImportlist);
		if(faiResult==null || faiResult.getResObject()==null) {
			throw new SystemException("上傳失敗!");
		}
		
		Message m = new Message();
		m.setMessageString("上傳完成!");
		result.setMessage(m);
		
		return result;
	}

	//下拉選單中檔案狀態的值
	private void getStatus() {
		if (getPageInfo().getFilter().get("fileStatus")!=null 
				&& "All".equals(getPageInfo().getFilter().get("fileStatus"))) {
			getPageInfo().getFilter().remove("fileStatus");
		}
	}
	
	//轉換民國年為西元年
	public String rocToAd(String rocDate, String delimiter) {
		String[] arrDate = rocDate.split(delimiter);
		if(arrDate.length >= 3) {
			return Integer.parseInt(arrDate[0]) + 1911 + "/" + arrDate[1] + "/" + arrDate[2] ;
		}
		return "";
	}

	public RunFirAddressImportService getRunFirAddressImportService() {
		return runFirAddressImportService;
	}

	public void setRunFirAddressImportService(RunFirAddressImportService runFirAddressImportService) {
		this.runFirAddressImportService = runFirAddressImportService;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	public FirAddrImportlistService getFirAddrImportlistService() {
		return firAddrImportlistService;
	}

	public void setFirAddrImportlistService(FirAddrImportlistService firAddrImportlistService) {
		this.firAddrImportlistService = firAddrImportlistService;
	}

	public FirAddrImporterrService getFirAddrImporterrService() {
		return firAddrImporterrService;
	}

	public void setFirAddrImporterrService(FirAddrImporterrService firAddrImporterrService) {
		this.firAddrImporterrService = firAddrImporterrService;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	
	public FirAddrImportlist getFirAddrImportlist() {
		return firAddrImportlist;
	}

	public void setFirAddrImportlist(FirAddrImportlist firAddrImportlist) {
		this.firAddrImportlist = firAddrImportlist;
	}

	public List<FirAddrImportlist> getDevResults() {
		return devResults;
	}
	
	public void setDevResults(List<FirAddrImportlist> devResults) {
		this.devResults = devResults;
	}
	
	public List<FirAddrImporterr> getErrMsgDevResults() {
		return errMsgDevResults;
	}

	public void setErrMsgDevResults(List<FirAddrImporterr> errMsgDevResults) {
		this.errMsgDevResults = errMsgDevResults;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.ePageInfo=(PageInfo)session.get(this.ePageInfoName);
		super.setSession(session);
	}

	public PageInfo getEPageInfo() {
		if(super.getSession().containsKey(ePageInfoName)){
			this.ePageInfo = (PageInfo)super.getSession().get(ePageInfoName);
		}
		return ePageInfo;
	}

	public void setEPageInfo(PageInfo ePageInfo) {
		this.ePageInfo = ePageInfo;
		super.getSession().put(ePageInfoName, this.ePageInfo);
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getEFilter() {
		if (ePageInfo.getFilter() == null) {             //沒有值 則設定一個新的MAP  並且傳進session裡
			this.eFilter = new HashMap<>();//此Filter如為空 則也要new 一個新的MAP進去 否則會nullpointException
			this.ePageInfo.setFilter(this.eFilter);
			super.getSession().put(ePageInfoName, this.ePageInfo);			
		} else {
			this.eFilter = this.ePageInfo.getFilter();   //有值 則沿用此ePageInfo.getFilter
		}
		return eFilter;
	}

	public void setEFilter(Map<String, String> eFilter) {
		this.eFilter = eFilter;
		this.ePageInfo.setFilter(this.eFilter);         //將eFilter設定進Filter 這樣getFilter時就會取得eFilter
	}

	public String getEPageInfoName() {
		return ePageInfoName;
	}

	public void setEPageInfoName(String ePageInfoName) {
		this.ePageInfoName = ePageInfoName;
	}

	/**重設EPageInfo*/
	private void resetEPageInfo() {
		this.ePageInfo = new PageInfo();
		this.eFilter = new HashMap<>();
		this.ePageInfo.setPageSize(10);
		this.ePageInfo.setFilter(this.eFilter);
		super.getSession().put(ePageInfoName, this.ePageInfo);
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}
	
	public InputStream getFileInputStream() {
		return inputStream;
	}

	public String getUltype() {
		return ultype;
	}

	public void setUltype(String ultype) {
		this.ultype = ultype;
	}
}
