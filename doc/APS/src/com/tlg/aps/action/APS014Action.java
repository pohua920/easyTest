package com.tlg.aps.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirCtbcDeptinfo;
import com.tlg.prpins.entity.Prpduser;
import com.tlg.prpins.service.FirCtbcDeptinfoService;
import com.tlg.prpins.service.PrpduserService;
import com.tlg.util.BaseAction;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@SuppressWarnings("serial")
public class APS014Action extends BaseAction {
	/* mantis：FIR0182，處理人員：BJ085，需求單編號：FIR0182 中信新件服務人員維護作業 start */
	private List<FirCtbcDeptinfo> devResults = new ArrayList<>();
	private FirCtbcDeptinfoService firCtbcDeptinfoService;
	private PrpduserService prpduserService;
	private FirCtbcDeptinfo firCtbcDeptinfo;

	/** 載入畫面下拉資料 */
	private void formLoad(String type) throws SystemException, Exception {
		
	}
	
	private void getStatus() {
		if (getPageInfo().getFilter().get("deleteFlag")!=null 
				&& "BLANK".equals(getPageInfo().getFilter().get("deleteFlag"))) {
			getPageInfo().getFilter().remove("deleteFlag");
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
			getPageInfo().getFilter().put("sortBy", "BRANCH_NO, RECEIVED_BRANCH");
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
	
	/** APS014E0.jsp，分頁資料中，重新輸入要顯示的頁數 */
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
	
	/** APS014E0.jsp，分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
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

	@SuppressWarnings("unchecked")
	private void query() throws SystemException, Exception {
		Result result = firCtbcDeptinfoService.findFirCtbcDeptinfoByPageInfo(getPageInfo());
		if (null != result.getResObject()) {
			devResults = (List<FirCtbcDeptinfo>) result.getResObject();
		} else {
			setMessage(result.getMessage().toString());
		}
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
			//檢核服務人員代號需存在核心且有效
			String comcode = qureyByUsercode(); 
			if(StringUtil.isSpace(comcode)) {
				throw new SystemException("服務人員代號錯誤，查無資料。"); 
			}
			//檢核放款帳務號+洽訂文件歸屬行不可已存在於設定檔裡
			Map<String,String> params = new HashMap<>();
			params.put("branchNo",firCtbcDeptinfo.getBranchNo());
			params.put("receivedBranch",firCtbcDeptinfo.getReceivedBranch());
			Result result = firCtbcDeptinfoService.findFirCtbcDeptinfoByParams(params);
			if(result!=null && result.getResObject()!=null) {
				throw new SystemException("放款帳務行及洽訂文件歸屬行已存在於設定檔，請透過「修改」功能進行服務人員調整，勿重複新增。"); 
			}
			firCtbcDeptinfo.setComcode(comcode);
			create();
			if (getPageInfo().getRowCount() > 0) {
				getStatus();
				query();
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
			return Action.INPUT;
		} catch (Exception e) {
			setMessage("新增失敗\\n"+e.getMessage().replace("\n", "\\n"));
			return Action.INPUT;
		}
		return Action.SUCCESS;
	}
	
	/** 新增*/
	private void create() throws SystemException, Exception{
		firCtbcDeptinfo.setDeleteFlag("N");
		firCtbcDeptinfo.setIcreate(getUserInfo().getUserId().toUpperCase());
		firCtbcDeptinfo.setDcreate(new Date());
		Result result = firCtbcDeptinfoService.insertFirCtbcDeptinfo(firCtbcDeptinfo);
		setMessage(result.getMessage().toString());
	}
	
	/** 連結至修改頁面 */
	public String lnkGoUpdate() throws Exception {
		try {
			formLoad("update");
			if (null == firCtbcDeptinfo.getOid()) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
				query();
				return Action.INPUT;
			}
			Result result = firCtbcDeptinfoService.findFirCtbcDeptinfoByOid(firCtbcDeptinfo.getOid());
			if (null == result.getResObject()) {
				setMessage(result.getMessage().toString());
			} else {
				firCtbcDeptinfo = (FirCtbcDeptinfo) result.getResObject();
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	/** 按下儲存鍵，做資料修改的動作 */
	public String btnUpdate() throws Exception {
		try {
			formLoad("update");
			String comcode = qureyByUsercode();
			if(StringUtil.isSpace(comcode)) {
				setFirCtbcDeptinfo(firCtbcDeptinfo);
				throw new SystemException("服務人員代號錯誤，查無資料。"); 
			}
			firCtbcDeptinfo.setComcode(comcode);
			update();
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
			return Action.INPUT;
		} catch (Exception e) {
			setMessage("儲存失敗\\n"+e.getMessage().replace("\n", "\\n"));
			return Action.INPUT;
		}
		return Action.SUCCESS;
	}
	
	/** 負責處理update動作  */
	private void update() throws SystemException, Exception {
		firCtbcDeptinfo.setIupdate(getUserInfo().getUserId().toUpperCase());
		firCtbcDeptinfo.setDupdate(new Date());
		Result result = firCtbcDeptinfoService.updateFirCtbcDeptinfo(firCtbcDeptinfo);
		setMessage(result.getMessage().toString());
	}
	
	//檢核服務人員代號需存在核心且有效
	@SuppressWarnings("unchecked")
	public String qureyByUsercode() throws SystemException, Exception {
		Map<String,String> params = new HashMap<>();
		params.put("validstatus", "1");
		params.put("usercode", firCtbcDeptinfo.getHandler1code());
		Result result = prpduserService.findPrpduserByParams(params);
		if(result == null || result.getResObject()==null) {
			return null;
		}
		List<Prpduser> resultList = (List<Prpduser>)result.getResObject();
		Prpduser prpdUser = resultList.get(0);
		if(StringUtil.isSpace(prpdUser.getComcode())) {
			return null;
		}
		return prpdUser.getComcode();
	}

	public List<FirCtbcDeptinfo> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<FirCtbcDeptinfo> devResults) {
		this.devResults = devResults;
	}

	public FirCtbcDeptinfoService getFirCtbcDeptinfoService() {
		return firCtbcDeptinfoService;
	}

	public void setFirCtbcDeptinfoService(FirCtbcDeptinfoService firCtbcDeptinfoService) {
		this.firCtbcDeptinfoService = firCtbcDeptinfoService;
	}

	public PrpduserService getPrpduserService() {
		return prpduserService;
	}

	public void setPrpduserService(PrpduserService prpduserService) {
		this.prpduserService = prpduserService;
	}

	public FirCtbcDeptinfo getFirCtbcDeptinfo() {
		return firCtbcDeptinfo;
	}

	public void setFirCtbcDeptinfo(FirCtbcDeptinfo firCtbcDeptinfo) {
		this.firCtbcDeptinfo = firCtbcDeptinfo;
	}
}


