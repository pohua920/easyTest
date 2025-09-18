package com.tlg.aps.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.AmlQueryObjDetail;
import com.tlg.prpins.entity.AmlQueryObjMain;
import com.tlg.prpins.entity.MetaAmlResultDetail;
import com.tlg.prpins.entity.MetaAmlResultMain;
import com.tlg.prpins.service.AmlQueryObjDetailService;
import com.tlg.prpins.service.AmlQueryObjMainService;
import com.tlg.prpins.service.MetaAmlResultDetailService;
import com.tlg.prpins.service.MetaAmlResultMainService;
import com.tlg.util.BaseAction;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@SuppressWarnings("serial")
public class APS006Action extends BaseAction {
	/* mantis：OTH0065，處理人員：BJ085，需求單編號：OTH0065 建置AML洗錢查詢畫面 start */
	
	//查詢結果
	private List<AmlQueryObjMain> queryMainDevResults = new ArrayList<>();
	private List<AmlQueryObjDetail> queryDtlDevResults = new ArrayList<>();
	private List<MetaAmlResultMain> resultMainDevResults = new ArrayList<>();
	private List<MetaAmlResultDetail> resultDtlDevResults = new ArrayList<>();

	//Service
	private AmlQueryObjMainService amlQueryObjMainService;
	private AmlQueryObjDetailService amlQueryObjDetailService;
	private MetaAmlResultMainService metaAmlResultMainService;
	private MetaAmlResultDetailService metaAmlResultDetailService;

	//Entity
	private AmlQueryObjMain amlQueryObjMain;
	private MetaAmlResultMain metaAmlResultMain;

	// 分頁查詢 APS006E1.jsp
	private PageInfo ePageInfo;
	private Map<String, String> eFilter;
	private String ePageInfoName = this.getClass().getSimpleName() + "ePageInfo";
	private PageInfo rPageInfo;
	private Map<String, String> rFilter;
	private String rPageInfoName = this.getClass().getSimpleName() + "rPageInfo";

	private static final String SORTBY = "sortBy";
	private static final String SORTTYPE = "sortType";
	private static final String OIDAMLQUERYOBJMAIN = "oidAmlQueryObjMain";
	private static final String EXCEPTION = "exception";


	private void getStatus() {
		if (getPageInfo().getFilter().get("classCode")!=null &&
				"All".equals(getPageInfo().getFilter().get("classCode") )) {
			getPageInfo().getFilter().remove("classCode");
		}
	}

	/** 進入查詢頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		try {
			if (getPageInfo().getRowCount() > 0) {
				getStatus();
				query();
			}
			resetEPageInfo();
			resetRPageInfo();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute(EXCEPTION, e);
			throw e;
		}
		return Action.SUCCESS;
	}

	//----------------------------------APS006E0.jsp主檔查詢頁面 start--------------------------------------
	/** (掃描結果主檔)負責處理查詢結果Grid */
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
			getStatus();
			getPageInfo().setCurrentPage(1);
			getPageInfo().getFilter().put(SORTBY, "CREATETIME");
			getPageInfo().getFilter().put(SORTTYPE, "DESC");
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute(EXCEPTION, e);
			throw e;
		}
		return Action.SUCCESS;
	}

	/** (掃描結果主檔)連結至查詢頁面 
	 * @throws Exception */
	public String lnkGoQuery() throws Exception {
		try {
			if (getPageInfo().getRowCount() > 0) {
				getStatus();
				query();
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute(EXCEPTION, e);
			throw e;
		}
		return Action.SUCCESS;
	}

	/** APS006E0.jsp，分頁資料中，重新輸入要顯示的頁數 */
	public String txtChangePageIndex() throws Exception {
		try {
			getStatus();
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute(EXCEPTION, e);
		}
		return Action.SUCCESS;
	}

	/** APS006E0.jsp，分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
	public String ddlPageSizeChanged() throws Exception {
		try {
			getStatus();
			PageInfo pageInfo = getPageInfo();
			pageInfo.setCurrentPage(1);
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute(EXCEPTION, e);
			throw e;
		}
		return Action.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	private void query() throws Exception {
		Result result = amlQueryObjMainService.findAmlQueryObjMainByPageInfo(getPageInfo());
		if (null != result.getResObject()) {
			queryMainDevResults = (List<AmlQueryObjMain>) result.getResObject();
		} else {
			setMessage(result.getMessage().toString());
		}
	}
	//----------------------------------APS006E0.jsp主檔查詢頁面 end----------------------------------------
	
	//----------------------------------APS006E1.jsp主檔明細、AML查詢結果主檔、明細頁面start----------------------
	/** (明細)導頁至掃描結果明細檔查詢結果頁面 */
	@SuppressWarnings("unchecked")
	public String lnkDtlGoQuery() throws Exception {
		String forward = "input";
		try {
			if (null == amlQueryObjMain.getOid()) {
				setMessage("請重新操作");
				return forward;
			} 
			getEPageInfo().getFilter().put(OIDAMLQUERYOBJMAIN,amlQueryObjMain.getOid());
			//mantis：OTH0087，處理人員：BJ085，需求單編號：OTH0087 AML手動登錄
			getEPageInfo().getFilter().put(SORTBY,"OID");
			getEPageInfo().getFilter().put(SORTTYPE,"ASC");
			Result result = dtlQuery();
			if(result.getMessage()!=null) {
				if (getPageInfo().getRowCount() > 0) {
					getStatus();
					query();
				}
				return forward;
			}
			amlResultQuery();
			forward = Action.SUCCESS;
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute(EXCEPTION, e);
			throw e;
		}
		return forward;
	}

	//AML回傳結果、明細查詢
	@SuppressWarnings("unchecked")
	private void amlResultQuery() throws Exception {
		Result result;
		//dialog
		Map<String,BigDecimal> params = new HashMap<>();
		params.put(OIDAMLQUERYOBJMAIN, amlQueryObjMain.getOid());
		result = metaAmlResultMainService.findMetaAmlResultMainByParams(params);
		if (null != result.getResObject()) {
			resultMainDevResults = (List<MetaAmlResultMain>) result.getResObject();
			getRPageInfo().getFilter().put("oidMetaAmlResultMain",resultMainDevResults.get(0).getOid());
			getRPageInfo().getFilter().put(SORTBY,"SCREEN_SEQ");
			getRPageInfo().getFilter().put(SORTTYPE,"ASC");
			amlResultDtlQuery();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void amlResultDtlQuery() throws Exception {
		Result result = metaAmlResultDetailService.findMetaAmlResultDetailByPageInfo(getRPageInfo());
		if (null != result.getResObject()) {
			resultDtlDevResults = (List<MetaAmlResultDetail>) result.getResObject();
		}
	}
	
	//掃描結果明細查詢
	@SuppressWarnings("unchecked")
	private Result dtlQuery() throws Exception {
		BigDecimal oidAmlQueryObjMain = (BigDecimal) getEPageInfo().getFilter().get(OIDAMLQUERYOBJMAIN);
		mainQuery(oidAmlQueryObjMain);
		Result result = amlQueryObjDetailService.findAmlQueryObjDetailByPageInfo(getEPageInfo());
		if (null != result.getResObject()) {
			queryDtlDevResults = (List<AmlQueryObjDetail>) result.getResObject();
		}else {
			setMessage(result.getMessage().toString());
		}
		return result;
	}
	
	/** (掃描結果明細)APS006E1.jsp，分頁資料中，重新輸入要顯示的頁數 */
	public String txtDtlChangePageIndex() throws Exception {
		try {
			dtlQuery();
			amlResultQuery();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute(EXCEPTION, e);
		}
		return Action.SUCCESS;
	}
	
	/** (掃描結果明細)APS006E1.jsp，分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
	public String ddlDtlPageSizeChanged() throws Exception {
		try {
			PageInfo epageInfo = getEPageInfo();
			epageInfo.setCurrentPage(1);
			dtlQuery();
			amlResultQuery();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute(EXCEPTION, e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	private void mainQuery(BigDecimal oid) throws Exception {
		
		if(amlQueryObjMain == null){
			amlQueryObjMain = new AmlQueryObjMain();
		}
		this.amlQueryObjMain.setOid(oid);
		Result result = amlQueryObjMainService.findAmlQueryObjMainByOid(oid);
		if(result.getResObject() == null) {
			setMessage(result.getMessage().toString());
		}else {
			amlQueryObjMain = (AmlQueryObjMain) result.getResObject();			
		}
	}
	
	//----------------------------------APS006E1.jsp主檔明細、AML查詢結果主檔、明細頁面end----------------------//


	//getter&setter
	public List<AmlQueryObjMain> getQueryMainDevResults() {
		return queryMainDevResults;
	}

	public void setQueryMainDevResults(List<AmlQueryObjMain> queryMainDevResults) {
		this.queryMainDevResults = queryMainDevResults;
	}

	public List<AmlQueryObjDetail> getQueryDtlDevResults() {
		return queryDtlDevResults;
	}

	public void setQueryDtlDevResults(List<AmlQueryObjDetail> queryDtlDevResults) {
		this.queryDtlDevResults = queryDtlDevResults;
	}

	public List<MetaAmlResultMain> getResultMainDevResults() {
		return resultMainDevResults;
	}

	public void setResultMainDevResults(List<MetaAmlResultMain> resultMainDevResults) {
		this.resultMainDevResults = resultMainDevResults;
	}

	public List<MetaAmlResultDetail> getResultDtlDevResults() {
		return resultDtlDevResults;
	}

	public void setResultDtlDevResults(List<MetaAmlResultDetail> resultDtlDevResults) {
		this.resultDtlDevResults = resultDtlDevResults;
	}

	public AmlQueryObjMainService getAmlQueryObjMainService() {
		return amlQueryObjMainService;
	}

	public void setAmlQueryObjMainService(AmlQueryObjMainService amlQueryObjMainService) {
		this.amlQueryObjMainService = amlQueryObjMainService;
	}

	public AmlQueryObjDetailService getAmlQueryObjDetailService() {
		return amlQueryObjDetailService;
	}

	public void setAmlQueryObjDetailService(AmlQueryObjDetailService amlQueryObjDetailService) {
		this.amlQueryObjDetailService = amlQueryObjDetailService;
	}

	public MetaAmlResultMainService getMetaAmlResultMainService() {
		return metaAmlResultMainService;
	}

	public void setMetaAmlResultMainService(MetaAmlResultMainService metaAmlResultMainService) {
		this.metaAmlResultMainService = metaAmlResultMainService;
	}

	public MetaAmlResultDetailService getMetaAmlResultDetailService() {
		return metaAmlResultDetailService;
	}

	public void setMetaAmlResultDetailService(MetaAmlResultDetailService metaAmlResultDetailService) {
		this.metaAmlResultDetailService = metaAmlResultDetailService;
	}

	public AmlQueryObjMain getAmlQueryObjMain() {
		return amlQueryObjMain;
	}

	public void setAmlQueryObjMain(AmlQueryObjMain amlQueryObjMain) {
		this.amlQueryObjMain = amlQueryObjMain;
	}

	public MetaAmlResultMain getMetaAmlResultMain() {
		return metaAmlResultMain;
	}

	public void setMetaAmlResultMain(MetaAmlResultMain metaAmlResultMain) {
		this.metaAmlResultMain = metaAmlResultMain;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.ePageInfo=(PageInfo)session.get(this.ePageInfoName);
		this.rPageInfo=(PageInfo)session.get(this.rPageInfoName);
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
		this.eFilter.put(SORTTYPE, "ASC");
		this.ePageInfo.setPageSize(10);
		this.ePageInfo.setFilter(this.eFilter);
		super.getSession().put(ePageInfoName, this.ePageInfo);
	}

	public PageInfo getRPageInfo() {
		if(super.getSession().containsKey(rPageInfoName)){
			this.rPageInfo = (PageInfo)super.getSession().get(rPageInfoName);
		}
		return rPageInfo;
	}

	public void setRPageInfo(PageInfo rPageInfo) {
		this.rPageInfo = rPageInfo;
		super.getSession().put(rPageInfoName, this.rPageInfo);
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getRFilter() {
		if (rPageInfo.getFilter() == null) {             //沒有值 則設定一個新的MAP  並且傳進session裡
			this.rFilter = new HashMap<>();//此Filter如為空 則也要new 一個新的MAP進去 否則會nullpointException
			this.rPageInfo.setFilter(this.rFilter);
			super.getSession().put(rPageInfoName, this.rPageInfo);
		} else {
			this.rFilter = this.rPageInfo.getFilter();   //有值 則沿用此ePageInfo.getFilter
		}
		return rFilter;
	}

	public void setRFilter(Map<String, String> rFilter) {
		this.rFilter = rFilter;
		this.rPageInfo.setFilter(this.rFilter);         //將eFilter設定進Filter 這樣getFilter時就會取得eFilter
	}

	public String getRPageInfoName() {
		return rPageInfoName;
	}

	public void setRPageInfoName(String rPageInfoName) {
		this.rPageInfoName = rPageInfoName;
	}

	/**重設EPageInfo*/
	private void resetRPageInfo() {
		this.rPageInfo = new PageInfo();
		this.rFilter = new HashMap<>();
		this.rFilter.put(SORTTYPE, "ASC");
		this.rPageInfo.setPageSize(10);
		this.rPageInfo.setFilter(this.rFilter);
		super.getSession().put(rPageInfoName, this.rPageInfo);
	}
}
