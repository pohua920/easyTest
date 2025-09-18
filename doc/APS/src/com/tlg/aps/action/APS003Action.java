package com.tlg.aps.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.vo.Aps003DetailVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.service.FirCtbcRstService;
import com.tlg.util.BaseAction;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@SuppressWarnings("serial")
public class APS003Action extends BaseAction {
	// PageInfoName、PageInfo及Filter
	private String pageInfoName = this.getClass().getSimpleName() + "PageInfo";
	private PageInfo pageInfo;
	private Map<String, String> filter;
	// scope
	private Map<String, Object> session;
	// 下拉
	private Map<String, String> rstTypeMap = new LinkedHashMap<String, String>();// 回饋類型
	// 查詢結果
	//mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢)
	private List<Aps003DetailVo> devResults = new ArrayList<Aps003DetailVo>();
	//Service
	private FirCtbcRstService firCtbcRstService;
	

	/** 載入畫面下拉資料 */
	private void formLoad(String type) throws SystemException, Exception {
		
		rstTypeMap.put("", "");
		rstTypeMap.put("1", "資料異常");
		rstTypeMap.put("2", "核心撤單");
		rstTypeMap.put("3", "核心出單");
		/* mantis：FIR0498，處理人員：BJ085，需求單編號：FIR0498 中信保代網投_新件回饋檔排程查詢作業規格_新增保經代網投start */
		rstTypeMap.put("4", "續保叫單");
		rstTypeMap.put("5", "保代網投");
		/* mantis：FIR0498，處理人員：BJ085，需求單編號：FIR0498 中信保代網投_新件回饋檔排程查詢作業規格_新增保經代網投end */

	}
	
	/** 參數處理 */
	@SuppressWarnings("unchecked")
	private void parameterHandler() throws SystemException, Exception {
		String strDate = (String)getPageInfo().getFilter().get("sDate");
		strDate = rocToAd(strDate, "/");
		if(strDate != null && strDate.length() > 0) {
			strDate += " 00:00:00";
			getPageInfo().getFilter().put("startCreateDate", strDate);
		}else {
			getPageInfo().getFilter().remove("startCreateDate");
		}
		
		strDate = (String)getPageInfo().getFilter().get("eDate");
		strDate = rocToAd(strDate, "/");
		if(strDate != null && strDate.length() > 0) {
			strDate += " 23:59:59";
			getPageInfo().getFilter().put("endCreateDate", strDate);
		}else {
			getPageInfo().getFilter().remove("endCreateDate");
		}
	}
	
	/** 負責處理查詢結果Grid */
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
			formLoad("execute");
			getPageInfo().setCurrentPage(1);
			getPageInfo().getFilter().put("sortBy", "BATCH_NO, RST_TYPE, FK_ORDER_SEQ");
//			getPageInfo().getFilter().put("sortType", "ASC");
//			getPageInfo().getFilter().put("queryType", "1");
			query("1");
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
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


	/** APS003E0.jsp頁面按下清除鍵,清除所有 pageInfo的資料 */
	public String btnQueryCancel() throws Exception {
		try {

			
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
	
	/** 負責處理查詢結果Grid */
	@SuppressWarnings("unchecked")
	private void query(String queryType) throws SystemException, Exception {

		parameterHandler();
		//mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢)
		Result result = this.firCtbcRstService.findForAps003Detail(getPageInfo());
		if(result != null && result.getResObject() != null) {
			//mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢)
			devResults = (List<Aps003DetailVo>)result.getResObject();
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

	//mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢)
	public List<Aps003DetailVo> getDevResults() {
		return devResults;
	}

	//mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢)
	public void setDevResults(List<Aps003DetailVo> devResults) {
		this.devResults = devResults;
	}

	public Map<String, String> getRstTypeMap() {
		return rstTypeMap;
	}

	public void setRstTypeMap(Map<String, String> rstTypeMap) {
		this.rstTypeMap = rstTypeMap;
	}

	public FirCtbcRstService getFirCtbcRstService() {
		return firCtbcRstService;
	}

	public void setFirCtbcRstService(FirCtbcRstService firCtbcRstService) {
		this.firCtbcRstService = firCtbcRstService;
	}

}
