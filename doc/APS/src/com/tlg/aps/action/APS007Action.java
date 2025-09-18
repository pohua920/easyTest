package com.tlg.aps.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.Action;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirRuleAddrExcp;
import com.tlg.prpins.service.FirRuleAddrExcpService;
import com.tlg.util.BaseAction;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@SuppressWarnings("serial")
public class APS007Action extends BaseAction {
	/* mantis：FIR0238，處理人員：BJ085，需求單編號：FIR0238 稽核議題檢核-例外地址維護作業 start */
	private List<FirRuleAddrExcp> devResults = new ArrayList<>();
	private FirRuleAddrExcpService firRuleAddrExcpService;
	private FirRuleAddrExcp firRuleAddrExcp;

	/** 載入畫面下拉資料 */
	private void formLoad(String type) throws SystemException, Exception {
		
	}
	
	private void getStatus() {
		if (getPageInfo().getFilter().get("deleteFlag")!=null ) {
			if ("BLANK".equals( (String)getPageInfo().getFilter().get("deleteFlag") ) ) {
				getPageInfo().getFilter().remove("deleteFlag");
			}
		}
	}
	
	/** 進入查詢頁面前會進來做的事情 */
	public String execute() throws Exception {
		try {
			if (getPageInfo().getRowCount() > 0) {
				getStatus();
				query();
			}
			formLoad("execute");
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
	public String btnQuery() throws Exception {
		try{
			formLoad("query");
			getStatus();
			getPageInfo().setCurrentPage(1);
			getPageInfo().getFilter().put("riskcode", "F02");
			getPageInfo().getFilter().put("sortBy", "ADDRESS");
			getPageInfo().getFilter().put("sortType", "ASC");
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** 連結至查詢頁面 
	 * @throws Exception */
	public String lnkGoQuery() throws Exception {
		try {
			formLoad("query");
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
	
	@SuppressWarnings("unchecked")
	private void query() throws SystemException, Exception {
		Result result = firRuleAddrExcpService.findFirRuleAddrExcpByPageInfo(getPageInfo());
		if (null != result.getResObject()) {
			devResults = (List<FirRuleAddrExcp>) result.getResObject();
		} else {
			setMessage(result.getMessage().toString());
		}
	}
	
	/** APS007E0.jsp，分頁資料中，重新輸入要顯示的頁數 */
	public String txtChangePageIndex() throws Exception {
		try {
			getStatus();
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
		}
		return Action.SUCCESS;
	}
	
	/** APS007E0.jsp，分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
	public String ddlPageSizeChanged() throws Exception {
		try {
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
	
	/** 連結至新增頁面 */
	public String lnkGoCreate() throws Exception {
		this.formLoad("create");
		
		return Action.SUCCESS;	
	}
	
	/** 按下存檔鍵，做新增儲存動作 */
	public String btnCreate() throws Exception {
		try {
			formLoad("create");
			FirRuleAddrExcp temp = create();
			if (null != temp) {
				this.firRuleAddrExcp = temp;
				setFirRuleAddrExcp(new FirRuleAddrExcp());
			}
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
	
	/** 新增*/
	private FirRuleAddrExcp create() throws SystemException, Exception{
		firRuleAddrExcp.setRiskcode("F02");
		firRuleAddrExcp.setIcreate(getUserInfo().getUserId().toUpperCase());
		firRuleAddrExcp.setDcreate(new Date());
		Result result = firRuleAddrExcpService.insertFirRuleAddrExcp(firRuleAddrExcp);
		setMessage(result.getMessage().toString());
		
		return (FirRuleAddrExcp) result.getResObject();
	}
	
	/** 連結至修改頁面 */
	public String lnkGoUpdate() throws Exception {
		String forward = "input";
		try {
			formLoad("update");
			if (null == firRuleAddrExcp.getOid()) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
			} else {
				Result result = firRuleAddrExcpService.findFirRuleAddrExcpByOid(firRuleAddrExcp.getOid());
				if (null == result.getResObject()) {
					setMessage(result.getMessage().toString());
				} else {
					firRuleAddrExcp = (FirRuleAddrExcp) result.getResObject();
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
			formLoad("update");
			update();
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** 負責處理update動作  */
	private FirRuleAddrExcp update() throws SystemException, Exception {
		firRuleAddrExcp.setIupdate(getUserInfo().getUserId().toUpperCase());
		firRuleAddrExcp.setDupdate(new Date());
		Result result = firRuleAddrExcpService.updateFirRuleAddrExcp(firRuleAddrExcp);
		setMessage(result.getMessage().toString());
		return (FirRuleAddrExcp) result.getResObject();
	}
	


	public List<FirRuleAddrExcp> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<FirRuleAddrExcp> devResults) {
		this.devResults = devResults;
	}

	public FirRuleAddrExcpService getFirRuleAddrExcpService() {
		return firRuleAddrExcpService;
	}

	public void setFirRuleAddrExcpService(FirRuleAddrExcpService firRuleAddrExcpService) {
		this.firRuleAddrExcpService = firRuleAddrExcpService;
	}

	public FirRuleAddrExcp getFirRuleAddrExcp() {
		return firRuleAddrExcp;
	}

	public void setFirRuleAddrExcp(FirRuleAddrExcp firRuleAddrExcp) {
		this.firRuleAddrExcp = firRuleAddrExcp;
	}
}


