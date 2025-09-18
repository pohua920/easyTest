package com.tlg.aps.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.util.DeleteAfterDownloadFileInputStream;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAddrCkdata;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.service.FirAddrCkdataService;
import com.tlg.prpins.service.FirBatchLogService;
import com.tlg.util.BaseAction;
import com.tlg.util.ConfigUtil;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@SuppressWarnings("serial")
public class APS024Action extends BaseAction {
	/* mantis：FIR0357，處理人員：BJ085，需求單編號：FIR0357 火險地址維護作業 */
	private FirAddrCkdataService firAddrCkdataService;
	private List<FirAddrCkdata> devResults;
	private FirAddrCkdata firAddrCkdata;
	/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 start */
	private List<FirAddrCkdata> devResults2;
	private ConfigUtil configUtil;
	private FirBatchLogService firBatchLogService;
	private String pageInfoName = this.getClass().getSimpleName() + "PageInfo";
	private PageInfo pageInfo;
	private Map<String, String> filter;
	private Map<String, Object> session;
	private InputStream fileInputStream;
	private OutputStream fileOutputStream;
	private String fileName;
	/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 end */

	/** 進入頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		try {
			if (getPageInfo().getRowCount() > 0) {
				/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 start */
				String queryType = (String)pageInfo.getFilter().get("queryType");
				query(queryType);
				/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 end */
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** APS024E0.jsp按下查詢鍵，搜尋資料 */
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
			/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 start */
			getPageInfo().setCurrentPage(1);
			getPageInfo().setPageSize(10);
			getPageInfo().getFilter().put("sortBy", "POLICYNO");
			getPageInfo().getFilter().put("sortType", "ASC");
			getPageInfo().getFilter().put("queryType", "1");
			getPageInfo().getFilter().remove("formattedResult");
			getPageInfo().getFilter().remove("formattedCity");
			getPageInfo().getFilter().remove("formattedDistrict");
			query("1");
			/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 end */
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 start */
	@SuppressWarnings("unchecked")
	private void query(String queryType) throws SystemException, Exception {
		parameterHandler();
		Result result = firAddrCkdataService.findFirAddrCkdataByPageInfo(getPageInfo());
		if (null != result.getResObject()) {
			if("1".equals(queryType)) {
				devResults = (List<FirAddrCkdata>) result.getResObject();
			} else if("2".equals(queryType)) {
				devResults2 = (List<FirAddrCkdata>) result.getResObject();
			}
		} else {
			setMessage(result.getMessage().toString());
		}
	}
	/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 end */
	
	/** APS024E0.jsp(主檔)分頁資料中，重新輸入要顯示的頁數  */
	public String txtChangePageIndex() throws Exception {
		try {
			/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 start */
			PageInfo pageInfo = getPageInfo();
			String queryType = (String)pageInfo.getFilter().get("queryType");
			query(queryType);
			/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 end */
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
		}
		return Action.SUCCESS;
	}
	
	/** APS024E0.jsp，(主檔)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
	public String ddlPageSizeChanged() throws Exception {
		try {
			PageInfo pageInfo = getPageInfo();
			pageInfo.setCurrentPage(1);
			/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 start */
			String queryType = (String)pageInfo.getFilter().get("queryType");
			query(queryType);
			/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 end */
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** APS024E0.jsp 連結至修改頁面 */
	public String lnkGoUpdate() throws Exception {
		String forward = "input";
		try {
			if (null == firAddrCkdata.getOid()) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
			} else {
				Result result = firAddrCkdataService.findFirAddrCkdataByOid(firAddrCkdata.getOid());
				if (null == result.getResObject()) {
					setMessage(result.getMessage().toString());
				} else {
					firAddrCkdata = (FirAddrCkdata) result.getResObject();
					forward = Action.SUCCESS;
				}
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return forward;
	}
	
	/** 按下儲存鍵，做資料修改的動作 */
	public String btnUpdate() throws Exception {
		try {
			/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 start */
			String queryType = (String) getPageInfo().getFilter().get("queryType");
			update();
			query(queryType);
			/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 end */
		} catch (SystemException se) {
			/*mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業 start*/
			se.printStackTrace();
			setMessage("存檔失敗:"+se.getMessage().replace("\"", "").replace("\n", "").replace("\r", ""));
			/*mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業 end*/
			return "input";
		} catch (Exception e) {
			/*mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業 start*/
			e.printStackTrace();
			setMessage("存檔失敗:"+e.getMessage().replace("\"", "").replace("\n", "").replace("\r", ""));
			/*mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業 end*/
			return "input";
		}
		return Action.SUCCESS;
		
	}
	
	/** 負責處理update動作  */
	private void update() throws SystemException, Exception {
		firAddrCkdata.setIupdate(getUserInfo().getUserId().toUpperCase());
		firAddrCkdata.setDupdate(new Date());
		Result result = firAddrCkdataService.updateFirAddrCkdata(firAddrCkdata);
		if(result.getResObject()!=null) {
			setMessage("存檔完成");
		}else {
			setMessage("存檔失敗");
		}
	}
	
	/** 參數處理 */
	@SuppressWarnings("unchecked")
	private void parameterHandler() throws Exception {
		String strDate = (String)getPageInfo().getFilter().get("sDate");
		if(!StringUtil.isSpace(strDate)) {
			strDate += " 00:00:00";
			getPageInfo().getFilter().put("startCreateDate", strDate);
		}else {
			getPageInfo().getFilter().remove("startCreateDate");
		}
		
		strDate = (String)getPageInfo().getFilter().get("eDate");
		if(!StringUtil.isSpace(strDate)) {
			strDate += " 23:59:59";
			getPageInfo().getFilter().put("endCreateDate", strDate);
		}else {
			getPageInfo().getFilter().remove("endCreateDate");
		}
	}
	
	/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 start */
	@SuppressWarnings("unchecked")
	public String btnFormattedQuery() throws Exception {
		try{
			getPageInfo().setCurrentPage(1);
			getPageInfo().setPageSize(10);
			getPageInfo().getFilter().put("sortBy", "POLICYNO");
			getPageInfo().getFilter().put("sortType", "ASC");
			getPageInfo().getFilter().put("queryType", "2");
			query("2");
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String btnExport() throws Exception {
		try{
			Map<String, Object> params = new HashMap<>();
			params.put("prgId", "FIR_ADDR_EXPORT");
			params.put("sortBy", "DCREATE");
			params.put("sortType", "DESC");
			Result result = firBatchLogService.findFirBatchLogByParams(params);
			getRequest().setAttribute("checkExport", true);
			getRequest().setAttribute("checkAgainExportMsg", "");
			if(result.getResObject()!=null) {
				List<FirBatchLog> list = (List<FirBatchLog>) result.getResObject();
				FirBatchLog firBatchLog = list.get(0);
				if(StringUtils.isBlank(firBatchLog.getIupdate())) {
					String dcreate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(firBatchLog.getDcreate());
					String msg = "上次由"+firBatchLog.getIcreate()+"於"+dcreate+"執行的匯出尚未完成，是否再次執行？";
					getRequest().setAttribute("checkAgainExportMsg", msg);
				}
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String checkExport() throws Exception {
		try{
			String batchNo = "EXPORT" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			
			FirBatchLog firBatchLog = new FirBatchLog();
			firBatchLog.setBatchNo(batchNo);
			firBatchLog.setPrgId("FIR_ADDR_EXPORT");
			firBatchLog.setStatus("1");
			firBatchLog.setIcreate(getUserInfo().getUserId().toUpperCase());
			firBatchLog.setDcreate(new Date());
			Result result = firBatchLogService.insertFirBatchLog(firBatchLog);
			getRequest().setAttribute("batchNo", batchNo);
			if(result.getResObject()!=null) {
				getRequest().setAttribute("exportMsg", true);
			}else {
				setMessage("新增批次執行記錄失敗");
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String btnDownload() throws Exception {
        try{
        	Map<String, String> params = new HashMap<>();
        	params.put("prgId", "FIR_ADDR_EXPORT");
			params.put("sortBy", "DCREATE");
			params.put("sortType", "DESC");
			Result result = firBatchLogService.findFirBatchLogByParams(params);
    		if(result != null && result.getResObject() != null) {
    			List<FirBatchLog> list = (List<FirBatchLog>) result.getResObject();
    			FirBatchLog batchLog = list.get(0);
    			fileName = batchLog.getBatchNo()+".xlsx";
    			if("S".equals(batchLog.getRemark())) {
    				String url = configUtil.getString("aps024ExportPath")+fileName;
    				logger.info("url : " + url);
    				File file = new File(url);
    				File outputFile = new File(configUtil.getString("tempFolder") +fileName);
    				if(!file.exists()){
    	                setMessage("查無檔案");
    	                return Action.INPUT;
    	            }
    				fileInputStream = new FileInputStream(file);
    	            fileOutputStream = new FileOutputStream(outputFile);
    	            byte[] buffer = new byte[4096];
    	            int readLength = 0;
    	            while ((readLength=fileInputStream.read(buffer)) > 0) {
    	                byte[] bytes = new byte[readLength];
    	                System.arraycopy(buffer, 0, bytes, 0, readLength);
    	                fileOutputStream.write(bytes);
    	            }
    	            fileOutputStream.flush();
    	            this.fileInputStream = new DeleteAfterDownloadFileInputStream(outputFile);
    			}else if("N".equals(batchLog.getRemark())) {
    				setMessage("查無資料(BATCH_NO)，請變更查詢條件後重新執行「正規化結果匯出」。");
    				return Action.INPUT;
    			}else {
    				setMessage("「正規化結果匯出」執行異常(BATCH_NO)，請洽系統服務人員。");
    				return Action.INPUT;
    			}
    		} else {
    			setMessage("查無匯出記錄，請先執行「正規化結果匯出」。");
    			return Action.INPUT;
    		}
		}catch (IOException e) {
	            e.printStackTrace();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	public void resetPageInfo() {
		pageInfo = new PageInfo();
		filter = new HashMap<String, String>();
		pageInfo.setPageSize(10);
		pageInfo.setFilter(filter);
		session.put(pageInfoName, pageInfo);
	}
	
	public String getPageInfoName() {
		return pageInfoName;
	}

	public void setPageInfoName(String pageInfoName) {
		this.pageInfoName = pageInfoName;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, String> getFilter() {
		if (pageInfo.getFilter() == null) {
			pageInfo.setFilter(new HashMap());
			session.put(pageInfoName, pageInfo);
			filter = new HashMap<String, String>();
		} else {
			filter = pageInfo.getFilter();
		}
		return filter;
	}

	public void setFilter(Map<String, String> filter) {
		this.filter = filter;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
		this.pageInfo = (PageInfo) session.get(pageInfoName);
		if (this.pageInfo == null) {
			this.resetPageInfo();
		}
	}
	
	public List<FirAddrCkdata> getDevResults2() {
		return devResults2;
	}

	public void setDevResults2(List<FirAddrCkdata> devResults2) {
		this.devResults2 = devResults2;
	}
	
	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public FirBatchLogService getFirBatchLogService() {
		return firBatchLogService;
	}
	
	public void setFirBatchLogService(FirBatchLogService firBatchLogService) {
		this.firBatchLogService = firBatchLogService;
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
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 end */

	public FirAddrCkdataService getFirAddrCkdataService() {
		return firAddrCkdataService;
	}

	public void setFirAddrCkdataService(FirAddrCkdataService firAddrCkdataService) {
		this.firAddrCkdataService = firAddrCkdataService;
	}

	public List<FirAddrCkdata> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<FirAddrCkdata> devResults) {
		this.devResults = devResults;
	}

	public FirAddrCkdata getFirAddrCkdata() {
		return firAddrCkdata;
	}

	public void setFirAddrCkdata(FirAddrCkdata firAddrCkdata) {
		this.firAddrCkdata = firAddrCkdata;
	}
}
