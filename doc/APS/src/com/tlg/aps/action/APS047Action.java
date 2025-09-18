package com.tlg.aps.action;

import java.util.List;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.mobclaimService.MobReviewService;
import com.tlg.aps.vo.Aps046ResultVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.service.FetclaimmainService;
import com.tlg.util.BaseAction;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：MOB0019，處理人員：BJ085，需求單編號：MOB0019 理賠審核確認作業 */
@SuppressWarnings("serial")
public class APS047Action extends BaseAction {
	
	private List<Aps046ResultVo> devResults;
	private FetclaimmainService fetclaimmainService;
	private MobReviewService mobReviewService;
	
	private String token;
	private String wda00;
	
	/** 進入頁面前會進來做的事情 */
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

	/** APS046E0.jsp頁面按下查詢鍵,開始查詢主檔資料 */
	public String btnQuery() throws Exception {
		try{
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** APS046U0.jsp頁面按下提交按鈕,將TMP檔資料寫入正式資料檔 */
	public String btnSubmit() throws Exception {
		try {
			Result result = mobReviewService.reviewClaimData(getUserInfo().getUserId().toUpperCase(), wda00);
			setMessage(result.getMessage().toString());
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
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
	
	/**連結至轉檔頁面*/
	public String lnkGoUpload() throws Exception {
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
		getPageInfo().getFilter().put("sortBy", "MAIN.OID");
		getPageInfo().getFilter().put("sortType", "ASC");
		getPageInfo().getFilter().put("wda61", "4");
		Result result = fetclaimmainService.findForMainDataByPageInfo(getPageInfo());
		if (null != result.getResObject()) {
			devResults = (List<Aps046ResultVo>) result.getResObject();
		} else {
			setMessage(result.getMessage().toString());
		}
	}
	
	/** APS047E0.jsp(主檔)分頁資料中，重新輸入要顯示的頁數  */
	public String txtChangePageIndex() throws Exception {
		try {
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
		}
		return Action.SUCCESS;
	}
	
	/** APS047E0.jsp，(主檔)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
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
	
	@SuppressWarnings("unchecked")
	private void parameterHandler() throws Exception {
		String wde06 = (String)getPageInfo().getFilter().get("wde06");
		if(StringUtil.isSpace(wde06)) {
			getPageInfo().getFilter().remove("wde06Select");
			getPageInfo().getFilter().remove("wde06Else");
		}else if("else".equals(wde06)){
			getPageInfo().getFilter().remove("wde06Select");
			getPageInfo().getFilter().put("wde06Else","Y");
		}else {
			getPageInfo().getFilter().put("wde06Select",wde06);
			getPageInfo().getFilter().remove("wde06Else");
		}
	}
	
	public List<Aps046ResultVo> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<Aps046ResultVo> devResults) {
		this.devResults = devResults;
	}

	public FetclaimmainService getFetclaimmainService() {
		return fetclaimmainService;
	}

	public void setFetclaimmainService(FetclaimmainService fetclaimmainService) {
		this.fetclaimmainService = fetclaimmainService;
	}

	public String getWda00() {
		return wda00;
	}

	public void setWda00(String wda00) {
		this.wda00 = wda00;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public MobReviewService getMobReviewService() {
		return mobReviewService;
	}

	public void setMobReviewService(MobReviewService mobReviewService) {
		this.mobReviewService = mobReviewService;
	}
}
