package com.tlg.aps.action;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.carAddrUploadAndImportService.CarAddrUploadAndImportService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtrnAs400DataUplist;
import com.tlg.prpins.service.CarAddrImportlistService;
import com.tlg.util.BaseAction;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：CAR0504，處理人員：CC009，需求單編號：CAR0504 微型電動二輪車【已領牌&未領牌】資料交換作業 */
@SuppressWarnings("serial")
public class APS040Action extends BaseAction {
	private static final Logger logger = Logger.getLogger(APS040Action.class);
	private File upload = null;//上傳的檔案
	private String uploadFileName;//上傳檔案名稱
	private InputStream inputStream;
	private String ultype;
	private CarAddrUploadAndImportService carAddrUploadAndImportService;
	private CarAddrImportlistService carAddrImportlistService;
	private List<FirAgtrnAs400DataUplist> devResults;
	
	//轉入作業確定轉入按鈕，做資料匯入動作
	public String btnDataImport() throws Exception {
		try{
			if(upload!=null) {
				Result result = carAddrUploadAndImportService.RenewalDataUploadAndImport(getUserInfo().getUserId().toUpperCase(), 
						upload, getUploadFileName(), ultype);
				setMessage(result.getMessage().toString());
			}else {
				setMessage("檔案讀取失敗");
			}
		} catch (SystemException se) {
			logger.error(se.getMessage());
			setMessage(se.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			getRequest().setAttribute("exception", e);
			setMessage("上傳失敗");
		}
		return Action.SUCCESS;
	}
	
	/** (主檔)按下查詢鍵，開始搜尋*/
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
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

	/** 進入查詢頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		try {
			
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
		Result result = carAddrImportlistService.findCarAddrImportlistByPageInfo(getPageInfo());
		if(result != null && result.getResObject() != null) {
			devResults = (List<FirAgtrnAs400DataUplist>)result.getResObject();
		}else {
			setMessage(result.getMessage().toString());
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
		
		String ultype = (String)getPageInfo().getFilter().get("ultype");
		if("".equals(ultype)) {
			getPageInfo().getFilter().remove("ultype");
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

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getUltype() {
		return ultype;
	}

	public void setUltype(String ultype) {
		this.ultype = ultype;
	}

	public CarAddrUploadAndImportService getCarAddrUploadAndImportService() {
		return carAddrUploadAndImportService;
	}

	public void setCarAddrUploadAndImportService(CarAddrUploadAndImportService carAddrUploadAndImportService) {
		this.carAddrUploadAndImportService = carAddrUploadAndImportService;
	}

	public CarAddrImportlistService getCarAddrImportlistService() {
		return carAddrImportlistService;
	}

	public void setCarAddrImportlistService(CarAddrImportlistService carAddrImportlistService) {
		this.carAddrImportlistService = carAddrImportlistService;
	}

	public List<FirAgtrnAs400DataUplist> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<FirAgtrnAs400DataUplist> devResults) {
		this.devResults = devResults;
	}
}
