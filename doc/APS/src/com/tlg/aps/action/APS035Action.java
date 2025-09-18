package com.tlg.aps.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.firRnproposalRenewalFileService.FirRnproposalRenewalFileService;
import com.tlg.aps.util.DeleteAfterDownloadFileInputStream;
import com.tlg.aps.vo.Aps035DetailVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirCtbcRnproposalMain;
import com.tlg.prpins.service.FirCtbcRnproposalDtlService;
import com.tlg.prpins.service.FirCtbcRnproposalMainService;
import com.tlg.util.BaseAction;
import com.tlg.util.ConfigUtil;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：FIR0459，處理人員：CC009，需求單編號：FIR0459 住火-APS中信續件要保書-排程查詢作業 
	mantis：FIR0458，處理人員：CC009，需求單編號：FIR0458 住火-APS中信續件要保書-資料接收排程 */
@SuppressWarnings("serial")
public class APS035Action extends BaseAction {
	private ConfigUtil configUtil;
	// PageInfoName、PageInfo及Filter
	private String pageInfoName = this.getClass().getSimpleName() + "PageInfo";
	private PageInfo pageInfo;
	private Map<String, String> filter;
	// scope
	private Map<String, Object> session;
	// 下拉
	private Map<String, String> fileStatusMap = new LinkedHashMap<String, String>();
	private Map<String, String> coreYnMap = new LinkedHashMap<String, String>();
	// 查詢結果
	private List<FirCtbcRnproposalMain> devResults = new ArrayList<FirCtbcRnproposalMain>();
	private List<Aps035DetailVo> devResults2 = new ArrayList<Aps035DetailVo>();
	//Service
	private FirCtbcRnproposalMainService firCtbcRnproposalMainService;
	private FirCtbcRnproposalDtlService firCtbcRnproposalDtlService;
	private FirRnproposalRenewalFileService firRnproposalRenewalFileService;
	
	private Aps035DetailVo aps035DetailVo;
	private InputStream fileInputStream;
	private OutputStream fileOutputStream;
	

	/** 載入畫面下拉資料 */
	private void formLoad(String type) throws SystemException, Exception {
		fileStatusMap.put("", "");
		fileStatusMap.put("S", "正常");
		fileStatusMap.put("L", "缺檔");
		fileStatusMap.put("E", "ZIP檔案異常");
		fileStatusMap.put("A", "新增錯誤");
		fileStatusMap.put("Z", "檔案無資料");
		
		coreYnMap.put("", "");
		coreYnMap.put("Y", "Y");
		coreYnMap.put("N", "N");
	}
	
	/** 參數處理 */
	@SuppressWarnings("unchecked")
	private void parameterHandler(String queryType) throws SystemException, Exception {
		String sDate = "";
		String eDate = "";
		if("1".equals(queryType)) {
			sDate = (String)getPageInfo().getFilter().get("sDate");
			eDate = (String)getPageInfo().getFilter().get("eDate");
		} else {
			sDate = (String)getPageInfo().getFilter().get("dtlSDate");
			eDate = (String)getPageInfo().getFilter().get("dtlEDate");
		}
		
		if(sDate != null && sDate.length() > 0) {
			sDate += " 00:00:00";
			getPageInfo().getFilter().put("startCreateDate", sDate);
		}else {
			getPageInfo().getFilter().remove("startCreateDate");
		}
		
		if(eDate != null && eDate.length() > 0) {
			eDate += " 23:59:59";
			getPageInfo().getFilter().put("endCreateDate", eDate);
		}else {
			getPageInfo().getFilter().remove("endCreateDate");
		}
	}
	
	/** 負責處理查詢結果Grid */
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		String forward = Action.INPUT;
		try{
			formLoad("execute");
			getPageInfo().setCurrentPage(1);
			getPageInfo().setPageSize(10);
			getPageInfo().getFilter().put("sortBy", "BATCH_NO DESC,BATCH_SEQ");
			getPageInfo().getFilter().put("sortType", "DESC");
			getPageInfo().getFilter().put("queryType", "1");
			query("1");
			forward = Action.SUCCESS;
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return forward;
	}
	
	/** 負責處理查詢結果Grid */
	@SuppressWarnings("unchecked")
	public String btnQuery2() throws Exception {
		String forward = Action.INPUT;
		try{
			formLoad("execute");
			getPageInfo().setCurrentPage(1);
			getPageInfo().setPageSize(10);
			getPageInfo().getFilter().put("sortBy", "DTL.BATCH_NO DESC,DTL.BATCH_SEQ DESC,DTL.POLICYNO");
			getPageInfo().getFilter().put("sortType", "ASC");
			getPageInfo().getFilter().put("queryType", "2");
			query("2");
			forward = Action.SUCCESS;
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return forward;
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

	public void resetPageInfo() {
		pageInfo = new PageInfo();
		filter = new HashMap<String, String>();
		pageInfo.setPageSize(10);
		pageInfo.setFilter(filter);
		session.put(pageInfoName, pageInfo);
	}
	
	/** (主檔)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
	public String ddlPageSizeChanged() throws Exception {
		try {
			formLoad("query");
			PageInfo pageInfo = getPageInfo();
			pageInfo.setCurrentPage(1);
			String queryType = (String)pageInfo.getFilter().get("queryType");
			query(queryType);
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
			formLoad("query");
			PageInfo pageInfo = getPageInfo();
			String queryType = (String)pageInfo.getFilter().get("queryType");
			query(queryType);
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String lnkDownloadPDF() throws Exception {
		String forward = Action.INPUT;
		formLoad("execute");
		String path1 = aps035DetailVo.getDcreate().substring(0, 6);
		String path2 = aps035DetailVo.getDcreate();
		String url = configUtil.getString("localRnproposalFilePath") + path1 + "/" + path2 + "/" + aps035DetailVo.getPdfName();
		logger.info("url : " + url);
        try {
            File file = new File(url);
            File outputFile = new File(configUtil.getString("tempFolder") + aps035DetailVo.getPdfName());
            if(!file.exists()){
                setMessage("檔案不存在");
                return forward;
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
            forward = Action.SUCCESS;

		}catch (IOException e) {
			e.printStackTrace();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}finally{
            try {
                if(fileOutputStream != null){
                	fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            query("2");
		}
		return forward;
	}
	
	/** 負責處理查詢結果Grid */
	@SuppressWarnings("unchecked")
	private void query(String queryType) throws SystemException, Exception {

		parameterHandler(queryType);
		if("1".equals(queryType)) {
			// 批次清單查詢
			Result result = this.firCtbcRnproposalMainService.findFirCtbcRnproposalMainByPageInfo(getPageInfo());
			
			if(result != null && result.getResObject() != null) {
				devResults = (List<FirCtbcRnproposalMain>)result.getResObject();
			}else {
				setMessage(result.getMessage().toString());
			}
		}else {
			// 明細清單查詢
			Result result = this.firCtbcRnproposalDtlService.findFirCtbcRnproposalDtlByPageInfoJoinPc(getPageInfo());
			if(result != null && result.getResObject() != null) {
				devResults2 = (List<Aps035DetailVo>)result.getResObject();
			}else {
				setMessage(result.getMessage().toString());
			}
		}
	}
	
	/** APS004E0.jsp頁面按下手動執行鍵,開始執行 **/
	public String btnExecuteRenewalFile() throws Exception {
		try{
			Result result = firRnproposalRenewalFileService.runToProcessFile(getUserInfo().getUserId().toUpperCase(), new Date(), "FIR_CTBC_03");
			setMessage(result.getMessage().toString());
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
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

	public Map<String, String> getFileStatusMap() {
		return fileStatusMap;
	}

	public void setFileStatusMap(Map<String, String> fileStatusMap) {
		this.fileStatusMap = fileStatusMap;
	}

	public List<FirCtbcRnproposalMain> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<FirCtbcRnproposalMain> devResults) {
		this.devResults = devResults;
	}

	public List<Aps035DetailVo> getDevResults2() {
		return devResults2;
	}

	public void setDevResults2(List<Aps035DetailVo> devResults2) {
		this.devResults2 = devResults2;
	}

	public Aps035DetailVo getAps035DetailVo() {
		return aps035DetailVo;
	}

	public void setAps035DetailVo(Aps035DetailVo aps035DetailVo) {
		this.aps035DetailVo = aps035DetailVo;
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

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public Map<String, String> getCoreYnMap() {
		return coreYnMap;
	}

	public void setCoreYnMap(Map<String, String> coreYnMap) {
		this.coreYnMap = coreYnMap;
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

	public FirRnproposalRenewalFileService getFirRnproposalRenewalFileService() {
		return firRnproposalRenewalFileService;
	}

	public void setFirRnproposalRenewalFileService(FirRnproposalRenewalFileService firRnproposalRenewalFileService) {
		this.firRnproposalRenewalFileService = firRnproposalRenewalFileService;
	}
	
}
