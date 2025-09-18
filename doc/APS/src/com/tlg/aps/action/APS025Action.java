package com.tlg.aps.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchmailExcludedata;
import com.tlg.prpins.service.FirBatchmailExcludedataService;
import com.tlg.util.BaseAction;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@SuppressWarnings("serial")
public class APS025Action extends BaseAction {
	/* mantis：FIR0369，處理人員：BJ085，需求單編號：FIR0369 增加電子保單不寄送單位業務來源設定 */
	private FirBatchmailExcludedataService firBatchmailExcludedataService;
	private List<FirBatchmailExcludedata> devResults;
	private FirBatchmailExcludedata firBatchmailExcludedata;

	/** 進入頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
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
	
	/** APS025E0.jsp按下查詢鍵，搜尋資料 */
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
			getPageInfo().getFilter().put("sortBy", "DUPDATE");
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

	@SuppressWarnings("unchecked")
	private void query() throws SystemException, Exception {
		Result result = firBatchmailExcludedataService.findFirBatchmailExcludedataByPageInfo(getPageInfo());
		if (null != result.getResObject()) {
			devResults = (List<FirBatchmailExcludedata>) result.getResObject();
		} else {
			setMessage(result.getMessage().toString());
		}
	}
	
	/** APS025E0.jsp(主檔)分頁資料中，重新輸入要顯示的頁數  */
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
	
	/** APS025E0.jsp，(主檔)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
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
	
	/** APS025E0.jsp， 查詢結果點選上下三角型排序 */
	public String lnkSortQuery() throws Exception {
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
	
	/** APS025E0.jsp，連結至新增頁面 */
	public String lnkGoCreate() throws Exception {
		try {
			System.out.println("lnkGoCreate.....");
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** 按下儲存鍵，做資料修改的動作 */
	public String btnCreate() throws Exception {
		try{
			firBatchmailExcludedata.setSno(new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
			firBatchmailExcludedata.setIcreate(getUserInfo().getUserId().toUpperCase());
			firBatchmailExcludedata.setDcreate(new Date());
			firBatchmailExcludedata.setIupdate(getUserInfo().getUserId().toUpperCase());
			firBatchmailExcludedata.setDupdate(new Date());
			this.firBatchmailExcludedataService.insertFirBatchmailExcludedata(firBatchmailExcludedata);
			
			setMessage("新增成功");
			firBatchmailExcludedata = new FirBatchmailExcludedata();
		} catch (SystemException se) {
			setMessage(se.getMessage());
			return "input";
		} catch (Exception e) {
			setMessage("新增失敗:"+e.getMessage().replace("\"", "'").replace("\n", "\\n").replace("\r", "\\r"));
			return "input";
		}
		return Action.SUCCESS;
	}
	
	/** APS025E0.jsp 連結至修改頁面 */
	public String lnkGoUpdate() throws Exception {
		String forward = "input";
		try {
			if (null == firBatchmailExcludedata.getSno()) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
			} else {
				Map<String,String> params = new HashMap<>();
				params.put("sno", firBatchmailExcludedata.getSno());
				Result result = firBatchmailExcludedataService.findFirBatchmailExcludedataByUk(params);
				if (null == result.getResObject()) {
					setMessage(result.getMessage().toString());
				} else {
					firBatchmailExcludedata = (FirBatchmailExcludedata) result.getResObject();
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
			update();
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
			return "input";
		} catch (Exception e) {
			setMessage("存檔失敗:"+e.getMessage().replace("\"", "'").replace("\n", "\\n").replace("\r", "\\r"));
			return "input";
		}
		return Action.SUCCESS;
	}
	
	/** 負責處理update動作  */
	private void update() throws SystemException, Exception {
		firBatchmailExcludedata.setIupdate(getUserInfo().getUserId().toUpperCase());
		firBatchmailExcludedata.setDupdate(new Date());
		Result result = firBatchmailExcludedataService.updateFirBatchmailExcludedata(firBatchmailExcludedata);
		if(result.getResObject()!=null) {
			setMessage("存檔完成");
		}else {
			setMessage("存檔失敗");
		}
	}
	
	public FirBatchmailExcludedataService getFirBatchmailExcludedataService() {
		return firBatchmailExcludedataService;
	}

	public void setFirBatchmailExcludedataService(FirBatchmailExcludedataService firBatchmailExcludedataService) {
		this.firBatchmailExcludedataService = firBatchmailExcludedataService;
	}

	public List<FirBatchmailExcludedata> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<FirBatchmailExcludedata> devResults) {
		this.devResults = devResults;
	}

	public FirBatchmailExcludedata getFirBatchmailExcludedata() {
		return firBatchmailExcludedata;
	}

	public void setFirBatchmailExcludedata(FirBatchmailExcludedata firBatchmailExcludedata) {
		this.firBatchmailExcludedata = firBatchmailExcludedata;
	}
}
