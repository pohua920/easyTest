package com.tlg.aps.action;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.firAS400RenewalImportService.FirAS400RenewalImportService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtrnAs400Data;
import com.tlg.prpins.entity.FirAgtrnAs400DataErr;
import com.tlg.prpins.entity.FirAgtrnAs400DataUplist;
import com.tlg.prpins.service.FirAgtrnAs400DataErrService;
import com.tlg.prpins.service.FirAgtrnAs400DataService;
import com.tlg.prpins.service.FirAgtrnAs400DataUplistService;
import com.tlg.util.BaseAction;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：FIR0388，處理人員：BJ085，需求單編號：FIR0388 AS400續保資料匯入 */
@SuppressWarnings("serial")
public class APS029Action extends BaseAction {
	
	// PageInfoName、PageInfo及Filter
	private String ePageInfoName = this.getClass().getSimpleName() + "ePageInfo";
	private PageInfo ePageInfo;
	private Map<String, String> eFilter;
	private String fPageInfoName = this.getClass().getSimpleName() + "fPageInfo";
	private PageInfo fPageInfo;
	private Map<String, String> fFilter;

	private File upload = null;//上傳的檔案
	private String uploadFileName;//上傳檔案名稱
	private String downloadFileName;//下載SAMPLE檔案名稱
	private InputStream inputStream;
	private String businessnature;
	private String rnYyyymm;
	private FirAgtrnAs400DataUplist firAgtrnAs400DataUplist;
	
	private FirAS400RenewalImportService firAS400RenewalImportService;
	private FirAgtrnAs400DataUplistService firAgtrnAs400DataUplistService;
	private FirAgtrnAs400DataService firAgtrnAs400DataService;
	private FirAgtrnAs400DataErrService firAgtrnAs400DataErrService;
	
	private List<FirAgtrnAs400Data> successDevResults;
	private List<FirAgtrnAs400DataUplist> devResults;
	private List<FirAgtrnAs400DataErr> errMsgDevResults;
	
	//轉入作業確定轉入按鈕，做資料匯入動作
	public String btnDataImport() throws Exception {
		try{
			if(upload!=null) {
				Result result = firAS400RenewalImportService.RenewalDataUploadAndImport(getUserInfo().getUserId().toUpperCase(), 
						upload, getUploadFileName(), businessnature, rnYyyymm);
				setMessage(result.getMessage().toString());
			}else {
				throw new SystemException("檔案讀取失敗");
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** (主檔)按下查詢鍵，開始搜尋*/
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
			getPageInfo().setCurrentPage(1);
			getPageInfo().getFilter().put("sortBy", "DUPDATE");
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
	
	/** (成功資料查詢頁面)點選資料成功筆數，開始搜尋 */
	@SuppressWarnings("unchecked")
	public String btnQuery2() throws Exception {
		String forward = "input";
		try {
			if (null == firAgtrnAs400DataUplist.getBusinessnature() || null == firAgtrnAs400DataUplist.getRnYyyymm()) {
				setMessage("請重新操作");
			} else {
				getEPageInfo().setCurrentPage(1);
				getEPageInfo().getFilter().put("sortBy", "OLDPOLICYNO");
				getEPageInfo().getFilter().put("sortType", "ASC");
				getEPageInfo().getFilter().put("businessnature", firAgtrnAs400DataUplist.getBusinessnature());
				getEPageInfo().getFilter().put("rnYyyymm", firAgtrnAs400DataUplist.getRnYyyymm());
				successDataQuery();
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
	
	/** (異常資料查詢頁面)點選資料失敗筆數，開始搜尋 */
	@SuppressWarnings("unchecked")
	public String btnQuery3() throws Exception {
		String forward = "input";
		try {
			if (null == firAgtrnAs400DataUplist.getBusinessnature() || null == firAgtrnAs400DataUplist.getRnYyyymm()) {
				setMessage("請重新操作");
			} else {
				getFPageInfo().setCurrentPage(1);
				getFPageInfo().getFilter().put("sortBy", "EXCEL_ROW");
				getFPageInfo().getFilter().put("sortType", "ASC");
				getFPageInfo().getFilter().put("businessnature", firAgtrnAs400DataUplist.getBusinessnature());
				getFPageInfo().getFilter().put("rnYyyymm", firAgtrnAs400DataUplist.getRnYyyymm());
				
				Map<String,String> params = new HashMap<>();
				params.put("businessnature", firAgtrnAs400DataUplist.getBusinessnature());
				params.put("rnYyyymm", firAgtrnAs400DataUplist.getRnYyyymm());
				Result result = firAgtrnAs400DataUplistService.findFirAgtrnAs400DataUplistByUk(params);
				
				if(result.getResObject()!=null) {
					FirAgtrnAs400DataUplist searchEntity = (FirAgtrnAs400DataUplist) result.getResObject();
					firAgtrnAs400DataUplist.setFilename(searchEntity.getFilename());
				}
				
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
	
	/** (主檔)分頁資料中，重新輸入要顯示的頁數 */
	public String txtChangePageIndex() throws Exception {
		try {
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** (主檔)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
	public String ddlPageSizeChanged() throws Exception {
		try {
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

	/** (匯入成功資料頁面)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
	public String ddlEPageSizeChanged() throws Exception {
		try {
			PageInfo epageInfo = getEPageInfo();
			epageInfo.setCurrentPage(1);
			successDataQuery();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** (匯入成功資料頁面)分頁資料中，重新輸入要顯示的頁數 */
	public String txtEChangePageIndex() throws Exception {
		try {
			successDataQuery();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	/** (異常訊息頁面)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
	public String ddlFPageSizeChanged() throws Exception {
		try {
			PageInfo fpageInfo = getFPageInfo();
			fpageInfo.setCurrentPage(1);
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
	public String txtFChangePageIndex() throws Exception {
		try {
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
			resetEPageInfo();
			resetFPageInfo();
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
		Result result = this.firAgtrnAs400DataUplistService.findFirAgtrnAs400DataUplistByPageInfo(getPageInfo());
		if(result != null && result.getResObject() != null) {
			devResults = (List<FirAgtrnAs400DataUplist>)result.getResObject();
		}else {
			setMessage(result.getMessage().toString());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void successDataQuery() throws Exception {
		Result result = firAgtrnAs400DataService.findFirAgtrnAs400DataByPageInfo(getEPageInfo());
		if (null == result.getResObject()) {
			setMessage(result.getMessage().toString());
		} else {
			successDevResults = (List<FirAgtrnAs400Data>)result.getResObject();
		}
	}

	@SuppressWarnings("unchecked")
	private void errMsgQuery() throws Exception {
		Result result = firAgtrnAs400DataErrService.findFirAgtrnAs400DataErrByPageInfo(getFPageInfo());
		if (null == result.getResObject()) {
			setMessage(result.getMessage().toString());
		} else {
			errMsgDevResults = (List<FirAgtrnAs400DataErr>)result.getResObject();
		}
	}
	

	/** 連結至查詢頁面 */
	public String lnkGoQuery() throws Exception {
		try {
			if (getPageInfo().getRowCount() > 0) {
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
	public String lnkGoUpload(){
		return Action.SUCCESS;
	}
	
	/*下載sample檔案*/
	public String btnDowloadSample() throws Exception {
		try {
			downloadFileName = "SAMPLE.xlsx";
			/* mantis：FIR0619，處理人員：CD078，需求單編號：FIR0619 住火_臺銀續保作業_AS400資料匯入新增臺銀格式 Start */
			// mantis：FIR0666，處理人員：DP0714，住火_元大續保作業_AS400資料匯入新增元大格式 -- start
			if (null == businessnature){
				inputStream = APS029Action.class.getClassLoader().getResourceAsStream("xlsx/AS400RenewalDataSample.xlsx"); // I99065、I99060 Sample
			} else if (businessnature.equals("I99004")) {
				inputStream = APS029Action.class.getClassLoader().getResourceAsStream("xlsx/AS400RenewalDataSample2.xlsx");// I99004 Sample
			} else if (businessnature.equals("I00006")) {
				inputStream = APS029Action.class.getClassLoader().getResourceAsStream("xlsx/AS400RenewalDataSample3.xlsx");// I00006 Sample
			}
			// mantis：FIR0666，處理人員：DP0714，住火_元大續保作業_AS400資料匯入新增元大格式 -- end
			/* mantis：FIR0619，處理人員：CD078，需求單編號：FIR0619 住火_臺銀續保作業_AS400資料匯入新增臺銀格式 End */
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** 參數處理 */
	@SuppressWarnings("unchecked")
	private void parameterHandler() throws Exception {
		String strDate = (String)getPageInfo().getFilter().get("sDate");
		if(!StringUtil.isSpace(strDate)) {
			strDate += " 00:00:00";
			getPageInfo().getFilter().put("startDate", strDate);
		}else {
			getPageInfo().getFilter().remove("startDate");
		}
		
		strDate = (String)getPageInfo().getFilter().get("eDate");
		if(!StringUtil.isSpace(strDate)) {
			strDate += " 23:59:59";
			getPageInfo().getFilter().put("endDate", strDate);
		}else {
			getPageInfo().getFilter().remove("endDate");
		}
		
		String rndate = (String)getPageInfo().getFilter().get("rnYyyymm");
		if("".equals(rndate)) {
			getPageInfo().getFilter().remove("rnYyyymm");
		}
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}
	
	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	
	public List<FirAgtrnAs400DataUplist> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<FirAgtrnAs400DataUplist> devResults) {
		this.devResults = devResults;
	}

	public List<FirAgtrnAs400DataErr> getErrMsgDevResults() {
		return errMsgDevResults;
	}

	public void setErrMsgDevResults(List<FirAgtrnAs400DataErr> errMsgDevResults) {
		this.errMsgDevResults = errMsgDevResults;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.ePageInfo=(PageInfo)session.get(this.ePageInfoName);
		super.setSession(session);
	}

	public PageInfo getFPageInfo() {
		if(super.getSession().containsKey(fPageInfoName)){
			this.fPageInfo = (PageInfo)super.getSession().get(fPageInfoName);
		}
		return fPageInfo;
	}

	public void setFPageInfo(PageInfo fPageInfo) {
		this.fPageInfo = fPageInfo;
		super.getSession().put(fPageInfoName, this.fPageInfo);
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getFFilter() {
		if (fPageInfo.getFilter() == null) {             //沒有值 則設定一個新的MAP  並且傳進session裡
			this.fFilter = new HashMap<>();//此Filter如為空 則也要new 一個新的MAP進去 否則會nullpointException
			this.fPageInfo.setFilter(this.fFilter);
			super.getSession().put(fPageInfoName, this.fPageInfo);			
		} else {
			this.fFilter = this.fPageInfo.getFilter();   //有值 則沿用此ePageInfo.getFilter
		}
		return fFilter;
	}

	public void setFFilter(Map<String, String> fFilter) {
		this.fFilter = fFilter;
		this.fPageInfo.setFilter(this.fFilter);         //將eFilter設定進Filter 這樣getFilter時就會取得eFilter
	}

	public String getFPageInfoName() {
		return fPageInfoName;
	}

	public void setFPageInfoName(String fPageInfoName) {
		this.fPageInfoName = fPageInfoName;
	}

	/**重設FPageInfo*/
	private void resetFPageInfo() {
		this.fPageInfo = new PageInfo();
		this.fFilter = new HashMap<>();
		this.fPageInfo.setPageSize(10);
		this.fPageInfo.setFilter(this.fFilter);
		super.getSession().put(fPageInfoName, this.fPageInfo);
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

	public FirAS400RenewalImportService getFirAS400RenewalImportService() {
		return firAS400RenewalImportService;
	}

	public void setFirAS400RenewalImportService(FirAS400RenewalImportService firAS400RenewalImportService) {
		this.firAS400RenewalImportService = firAS400RenewalImportService;
	}

	public String getBusinessnature() {
		return businessnature;
	}

	public void setBusinessnature(String businessnature) {
		this.businessnature = businessnature;
	}

	public String getRnYyyymm() {
		return rnYyyymm;
	}

	public void setRnYyyymm(String rnYyyymm) {
		this.rnYyyymm = rnYyyymm;
	}

	public FirAgtrnAs400DataUplistService getFirAgtrnAs400DataUplistService() {
		return firAgtrnAs400DataUplistService;
	}

	public void setFirAgtrnAs400DataUplistService(FirAgtrnAs400DataUplistService firAgtrnAs400DataUplistService) {
		this.firAgtrnAs400DataUplistService = firAgtrnAs400DataUplistService;
	}

	public FirAgtrnAs400DataService getFirAgtrnAs400DataService() {
		return firAgtrnAs400DataService;
	}

	public void setFirAgtrnAs400DataService(FirAgtrnAs400DataService firAgtrnAs400DataService) {
		this.firAgtrnAs400DataService = firAgtrnAs400DataService;
	}

	public FirAgtrnAs400DataErrService getFirAgtrnAs400DataErrService() {
		return firAgtrnAs400DataErrService;
	}

	public void setFirAgtrnAs400DataErrService(FirAgtrnAs400DataErrService firAgtrnAs400DataErrService) {
		this.firAgtrnAs400DataErrService = firAgtrnAs400DataErrService;
	}

	public FirAgtrnAs400DataUplist getFirAgtrnAs400DataUplist() {
		return firAgtrnAs400DataUplist;
	}

	public void setFirAgtrnAs400DataUplist(FirAgtrnAs400DataUplist firAgtrnAs400DataUplist) {
		this.firAgtrnAs400DataUplist = firAgtrnAs400DataUplist;
	}

	public List<FirAgtrnAs400Data> getSuccessDevResults() {
		return successDevResults;
	}

	public void setSuccessDevResults(List<FirAgtrnAs400Data> successDevResults) {
		this.successDevResults = successDevResults;
	}
}
