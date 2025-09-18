package com.tlg.aps.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.vo.AmlInsuredListVo;
import com.tlg.aps.vo.AmlInsuredVo;
import com.tlg.aps.vo.AmlResponseVo;
import com.tlg.aps.webService.metaAmlService.client.AmlService;
import com.tlg.dms.service.PrpdRiskService;
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
import com.tlg.util.WebserviceObjConvert;

@SuppressWarnings("serial")
public class APS011Action extends BaseAction {
	/* mantis：OTH0087，處理人員：BJ085，需求單編號：OTH0087 AML手動登錄 start */
	private AmlInsuredListVo amlInsuredListVo;
	private AmlResponseVo amlResponseVo;
	private AmlQueryObjMain amlQueryObjMain;
	
	private AmlService clientAmlQueryService;
	private AmlQueryObjMainService amlQueryObjMainService;
	private AmlQueryObjDetailService amlQueryObjDetailService;
	private MetaAmlResultDetailService metaAmlResultDetailService;
	private MetaAmlResultMainService metaAmlResultMainService;
	private PrpdRiskService prpdRiskService;
	private MetaAmlResultMain metaAmlResultMain;
	
	private ArrayList<AmlInsuredVo> amlInsuredList;
	private List<AmlQueryObjMain> queryMainDevResults;
	private List<AmlQueryObjDetail> queryDtlDevResults;
	private List<MetaAmlResultDetail> resultDtlDevResults;
	private List<MetaAmlResultMain> resultMainDevResults;
	
	private PageInfo ePageInfo;
	private Map<String, String> eFilter;
	private String ePageInfoName = this.getClass().getSimpleName() + "ePageInfo";
	private PageInfo rPageInfo;
	private Map<String, String> rFilter;
	private String rPageInfoName = this.getClass().getSimpleName() + "rPageInfo";

	@SuppressWarnings("unused")
	private String userId;
	private String mainOid;
	private String workStatus;
	private Map<String,String> riskcodeMap = new HashMap<>();
	
	/** 載入畫面下拉資料 */
	private void formLoad(String type) throws SystemException, Exception {

	}

	/** 進入頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		try {
			formLoad("execute");
			resetEPageInfo();
			resetRPageInfo();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	/** APS011A0， 按下送出，呼叫webService做新增動作 */
	public String btnCreate() throws Exception {
		try {
			amlInsuredListVo.setResend("0");
			create();
			formLoad("create");
			if(amlResponseVo!=null) {
				this.amlInsuredListVo = null;
				getRequest().setAttribute("message","新增成功，回傳結果請點選查詢按鈕查看!");				
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public void create() {
		if(!this.amlInsuredList.isEmpty()) {
			for(int i=0;i<amlInsuredList.size();i++) {
				if(amlInsuredList.get(i)==null) {
					amlInsuredList.remove(i);
				}
			}
		}
		this.amlInsuredListVo.setUserId(getUserInfo().getUserId().toUpperCase());
		this.amlInsuredListVo.setAmlInsuredList(this.amlInsuredList);
		callAmlWbService();
	}

	public void callAmlWbService() {		
		try {
		String soapxml = WebserviceObjConvert.convertObjToBase64Str(AmlInsuredListVo.class, this.amlInsuredListVo);
		String returnValue = clientAmlQueryService.amlQuery(soapxml);
		amlResponseVo = (AmlResponseVo)WebserviceObjConvert.convertBase64StrToObj(returnValue, AmlResponseVo.class);
		} catch (Exception e) {
			getRequest().setAttribute("message","webService錯誤，請稍後再試!:"+e);
		}
	}
	
	/** APS011U0， 按下送出，呼叫webService做新增動作 */
	public String btnUpdate() throws Exception {
		try {
			amlInsuredListVo.setResend("1");
			create();
			formLoad("create");
			if(amlResponseVo!=null) {
				this.amlInsuredListVo = null;
				getRequest().setAttribute("message","修改成功，回傳結果請點選查詢按鈕查看!");				
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	/** 按下清除鍵，做清除動作 */
	public String btnCreateCancel() throws Exception {
		try {
			formLoad("create");
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** 連結到新增頁面 */
	public String lnkGoCreate() throws Exception {
		formLoad("create");
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
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	/*-------------------------------------------------主檔查詢頁面--------------------------------------------------*/

	/** (掃描結果主檔)負責處理查詢結果Grid */
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{			
			getStatus();
			getPageInfo().setCurrentPage(1);				
			getPageInfo().getFilter().put("userId", getUserInfo().getUserId().toUpperCase());
			getPageInfo().getFilter().put("sortBy", "CREATETIME");
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

	/** APS011E0.jsp，分頁資料中，重新輸入要顯示的頁數 */
	public String txtChangePageIndex() throws Exception {
		try {
			getStatus();
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("execption", e);
		}
		return Action.SUCCESS;
	}

	/** APS011E0.jsp，分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
	public String ddlPageSizeChanged() throws Exception {
		try {
			getStatus();
			PageInfo pageInfo = getPageInfo();
			pageInfo.setCurrentPage(1);
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("execption", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	private void query() throws Exception {
		Result result = amlQueryObjMainService.findAmlQueryObjMainMaxOid(getPageInfo());
		if (null != result.getResObject()) {
			queryMainDevResults = (List<AmlQueryObjMain>) result.getResObject();
		} else {
			setMessage(result.getMessage().toString());
		}
	}
	
	private void getStatus() {
		if (getPageInfo().getFilter().get("classCode")!=null &&
				"All".equals(getPageInfo().getFilter().get("classCode") )) {
			getPageInfo().getFilter().remove("classCode");
		}
	}

	//----------------------------------APS011E1.jsp主檔明細、AML查詢結果主檔、明細頁面start----------------------
	/** (明細)導頁至掃描結果明細檔查詢結果頁面 */
	@SuppressWarnings("unchecked")
	public String lnkDtlGoQuery() throws Exception {
		String forward = "input";
		try {
			if (null == amlQueryObjMain.getOid()) {
				setMessage("請重新操作");
				return forward;
			} 
			getEPageInfo().getFilter().put("oidAmlQueryObjMain",amlQueryObjMain.getOid());
			getEPageInfo().getFilter().put("sortBy","OID");
			getEPageInfo().getFilter().put("sortType","ASC");
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
			getRequest().setAttribute("exception", e);
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
		params.put("oidAmlQueryObjMain", amlQueryObjMain.getOid());
		result = metaAmlResultMainService.findMetaAmlResultMainByParams(params);
		if (null != result.getResObject()) {
			resultMainDevResults = (List<MetaAmlResultMain>) result.getResObject();
			metaAmlResultMain = resultMainDevResults.get(0);
			getRPageInfo().getFilter().put("oidMetaAmlResultMain",metaAmlResultMain.getOid());
			getRPageInfo().getFilter().put("sortBy","SCREEN_SEQ");
			getRPageInfo().getFilter().put("sortType","ASC");
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
		BigDecimal oidAmlQueryObjMain = (BigDecimal) getEPageInfo().getFilter().get("oidAmlQueryObjMain");
		mainQuery(oidAmlQueryObjMain);
		Result result = amlQueryObjDetailService.findAmlQueryObjDetailByPageInfo(getEPageInfo());
		if (null != result.getResObject()) {
			queryDtlDevResults = (List<AmlQueryObjDetail>) result.getResObject();
		}else {
			setMessage(result.getMessage().toString());
		}
		return result;
	}
	
	/** (掃描結果明細)APS011E1.jsp，分頁資料中，重新輸入要顯示的頁數 */
	public String txtDtlChangePageIndex() throws Exception {
		try {
			dtlQuery();
			amlResultQuery();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exection", e);
		}
		return Action.SUCCESS;
	}
	
	/** (掃描結果明細)APS011E1.jsp，分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
	public String ddlDtlPageSizeChanged() throws Exception {
		try {
			PageInfo epageInfo = getEPageInfo();
			epageInfo.setCurrentPage(1);
			dtlQuery();
			amlResultQuery();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exection", e);
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
	
	/** (掃描結果明細)APS011E1.jsp，點選重送資料或查詢最新結果按鈕，呼叫webService重新查詢 */
	public String btnResend() throws Exception {
		try {
			formLoad("resend");
			resendQuery();
			if("05".equals(workStatus)||"06".equals(workStatus)) {
				amlInsuredListVo.setResend("1");
			}else if("01".equals(workStatus)||"02".equals(workStatus)) {
				amlInsuredListVo.setResend("0");				
			}
			callAmlWbService();
			if(amlResponseVo!=null) {
				this.amlInsuredListVo = null;
				getRequest().setAttribute("message","操作成功，回傳結果請在查詢頁面中查看!");
			}
			getPageInfo().setCurrentPage(1);
			query();
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	public String lnkGoEdit() throws Exception{
		try {
			resendQuery();
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public void resendQuery() throws Exception {
		//傳送主檔查詢(一筆)
		mainQuery(new BigDecimal(mainOid));
		amlInsuredListVo = new AmlInsuredListVo();
		BeanUtils.copyProperties(amlQueryObjMain,amlInsuredListVo);
		
		//傳送明細檔查詢(多筆)
		Map<String,String> params = new HashMap<>();
		params.put("oidAmlQueryObjMain", mainOid);
		Result result = amlQueryObjDetailService.findAmlQueryObjDetailByParams(params);
		List<AmlQueryObjDetail> dtlList = (List<AmlQueryObjDetail>) result.getResObject();
		amlInsuredList = new ArrayList<>();
		for(AmlQueryObjDetail amlQueryObjDetail : dtlList) {
			AmlInsuredVo amlInsuredVo = new AmlInsuredVo();
			BeanUtils.copyProperties(amlQueryObjDetail,amlInsuredVo);
			amlInsuredList.add(amlInsuredVo);
		}
		
		amlInsuredListVo.setAmlType("2");
		amlInsuredListVo.setAmlInsuredList(amlInsuredList);
	}

	public AmlInsuredListVo getAmlInsuredListVo() {
		return amlInsuredListVo;
	}

	public void setAmlInsuredListVo(AmlInsuredListVo amlInsuredListVo) {
		this.amlInsuredListVo = amlInsuredListVo;
	}

	public ArrayList<AmlInsuredVo> getAmlInsuredList() {
		return amlInsuredList;
	}

	public void setAmlInsuredList(ArrayList<AmlInsuredVo> amlInsuredList) {
		this.amlInsuredList = amlInsuredList;
	}

	public AmlService getClientAmlQueryService() {
		return clientAmlQueryService;
	}

	public void setClientAmlQueryService(AmlService clientAmlQueryService) {
		this.clientAmlQueryService = clientAmlQueryService;
	}

	public AmlResponseVo getAmlResponseVo() {
		return amlResponseVo;
	}

	public void setAmlResponseVo(AmlResponseVo amlResponseVo) {
		this.amlResponseVo = amlResponseVo;
	}
	
	public AmlQueryObjMain getAmlQueryObjMain() {
		return amlQueryObjMain;
	}
	
	public void setAmlQueryObjMain(AmlQueryObjMain amlQueryObjMain) {
		this.amlQueryObjMain = amlQueryObjMain;
	}

	public AmlQueryObjMainService getAmlQueryObjMainService() {
		return amlQueryObjMainService;
	}

	public void setAmlQueryObjMainService(AmlQueryObjMainService amlQueryObjMainService) {
		this.amlQueryObjMainService = amlQueryObjMainService;
	}

	public List<AmlQueryObjMain> getQueryMainDevResults() {
		return queryMainDevResults;
	}

	public void setQueryMainDevResults(List<AmlQueryObjMain> queryMainDevResults) {
		this.queryMainDevResults = queryMainDevResults;
	}

	public AmlQueryObjDetailService getAmlQueryObjDetailService() {
		return amlQueryObjDetailService;
	}

	public void setAmlQueryObjDetailService(AmlQueryObjDetailService amlQueryObjDetailService) {
		this.amlQueryObjDetailService = amlQueryObjDetailService;
	}

	public MetaAmlResultDetailService getMetaAmlResultDetailService() {
		return metaAmlResultDetailService;
	}

	public void setMetaAmlResultDetailService(MetaAmlResultDetailService metaAmlResultDetailService) {
		this.metaAmlResultDetailService = metaAmlResultDetailService;
	}

	public MetaAmlResultMainService getMetaAmlResultMainService() {
		return metaAmlResultMainService;
	}

	public void setMetaAmlResultMainService(MetaAmlResultMainService metaAmlResultMainService) {
		this.metaAmlResultMainService = metaAmlResultMainService;
	}

	public List<MetaAmlResultDetail> getResultDtlDevResults() {
		return resultDtlDevResults;
	}

	public void setResultDtlDevResults(List<MetaAmlResultDetail> resultDtlDevResults) {
		this.resultDtlDevResults = resultDtlDevResults;
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
		this.eFilter.put("sortType", "ASC");
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
		this.rFilter.put("sortType", "ASC");
		this.rPageInfo.setPageSize(10);
		this.rPageInfo.setFilter(this.rFilter);
		super.getSession().put(rPageInfoName, this.rPageInfo);
	}

	public String getUserId() {
		return getUserInfo().getUserId().toUpperCase();
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Map<String, String> getRiskcodeMap() {
		return riskcodeMap;
	}

	public void setRiskcodeMap(Map<String, String> riskcodeMap) {
		this.riskcodeMap = riskcodeMap;
	}

	public PrpdRiskService getPrpdRiskService() {
		return prpdRiskService;
	}

	public void setPrpdRiskService(PrpdRiskService prpdRiskService) {
		this.prpdRiskService = prpdRiskService;
	}

	public String getMainOid() {
		return mainOid;
	}

	public void setMainOid(String mainOid) {
		this.mainOid = mainOid;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public MetaAmlResultMain getMetaAmlResultMain() {
		return metaAmlResultMain;
	}

	public void setMetaAmlResultMain(MetaAmlResultMain metaAmlResultMain) {
		this.metaAmlResultMain = metaAmlResultMain;
	}
}
