package com.tlg.aps.action;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;

import com.tlg.dms.entity.PrpdNewCode;
import com.tlg.dms.service.PrpdNewCodeService;
import com.tlg.exception.SystemException;
import com.tlg.util.BaseAction;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@SuppressWarnings("serial")
public class APS056Action extends BaseAction {
	private List<PrpdNewCode> devResults = new ArrayList<>();
	private InputStream inputStream;
	private PrpdNewCodeService prpdNewCodeService;
	private PrpdNewCode prpdNewCode;

	/** 載入畫面下拉資料 */
	private void formLoad(String type) throws SystemException, Exception {
		
	}

	/** 進入查詢頁面前會進來做的事情 */
	@Override
	public String execute() throws Exception {
		try {
			if (getPageInfo().getRowCount() > 0) {
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

	@SuppressWarnings("unchecked")
	private void query() throws SystemException, Exception {
		getPageInfo().getFilter().put("codetype", "OldException");
		Result result = prpdNewCodeService.findPrpdNewCodeByPageInfo(getPageInfo());
		if (null != result.getResObject()) {
			devResults = (List<PrpdNewCode>) result.getResObject();
		} else {
			setMessage(result.getMessage().toString());
		}
	}

	/** 負責處理update動作 */
	private void update() throws SystemException, Exception {
		prpdNewCode.setUpdateuser(getUserInfo().getUserId().toUpperCase());
		prpdNewCode.setUpdatedate(new Date());
		Result result = prpdNewCodeService.updatePrpdNewCode(prpdNewCode);
		setMessage(result.getMessage().toString());
	}

	/** 新增 */
	@SuppressWarnings("unchecked")
	private void create() throws SystemException, Exception {

		// 取得最新的 CODECODE 流水號，自己+1
		Map<String, String> params = new HashMap<String, String>();
		params.put("codetype", "OldException");

		Result queryResult = prpdNewCodeService.findPrpdNewCodeByParams(params);
		String codecode = "1";
		if (queryResult.getResObject()!=null) {
			List<PrpdNewCode> searchResult = (List<PrpdNewCode>) queryResult.getResObject();
			PrpdNewCode prpdNewCode = searchResult.get(0);
			codecode = Integer.toString(Integer.parseInt(prpdNewCode.getCodecode()) + 1);
		}
		prpdNewCode.setCodetype("OldException");
		prpdNewCode.setCodecode(codecode);
		prpdNewCode.setCreateuser(getUserInfo().getUserId().toUpperCase());
		prpdNewCode.setCreatedate(new Date());
		Result result = prpdNewCodeService.insertPrpdnewcode(prpdNewCode);
		setMessage(result.getMessage().toString());
	}

	/** 負責處理查詢結果Grid */
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try {
			formLoad("query");
			getPageInfo().getFilter().put("sortBy", "oid");
			getPageInfo().getFilter().put("sortType", "ASC");
			getPageInfo().setCurrentPage(1);
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}

	/** 按下存檔鍵，做新增儲存動作 */
	public String btnCreate() throws Exception {
		try {
			formLoad("create");
			create();
			if (getPageInfo().getRowCount() > 0) {
				query();
			}
		} catch (SystemException se) {
			setMessage(se.getMessage());
			return Action.INPUT;
		} catch (Exception e) {
			setMessage("新增失敗\\n" + e.getMessage().replace("\n", "\\n"));
			return Action.INPUT;
		}
		return Action.SUCCESS;
	}

	/** 按下儲存鍵，做資料修改的動作 */
	public String btnUpdate() throws Exception {
		try {
			formLoad("update");
			update();
			query();
		} catch (SystemException se) {
			setMessage(se.getMessage());
			return Action.INPUT;
		} catch (Exception e) {
			setMessage("儲存失敗\\n" + e.getMessage().replace("\n", "\\n"));
			return Action.INPUT;
		}
		return Action.SUCCESS;
	}

	/**
	 * 連結至查詢頁面
	 * 
	 * @throws Exception
	 */
	public String lnkGoQuery() throws Exception {
		try {
			formLoad("query");
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

	/** 連結至修改頁面 */
	@SuppressWarnings("unchecked")
	public String lnkGoUpdate() throws Exception {
		try {
			formLoad("update");
			if (prpdNewCode.getCodetype()==null || prpdNewCode.getCodecode()==null) {
				setMessage("系統發生異常，無法帶入對應資料，請聯繫資訊人員。");
				query();
				return Action.INPUT;
			}

			Map<String, String> params = new HashMap<String, String>();
			params.put("codetype", prpdNewCode.getCodetype());
			params.put("codecode", prpdNewCode.getCodecode());

			Result result = prpdNewCodeService.findPrpdNewCodeByParams(params);
			if (null == result.getResObject()) {
				setMessage(result.getMessage().toString());
			} else {
				List<PrpdNewCode> searchResult = (List<PrpdNewCode>) result.getResObject();
				if (searchResult.size()==1) {
					prpdNewCode = searchResult.get(0);
				} else {
					setMessage("系統發生異常，資料庫存在多筆資料，請聯繫資訊人員。");
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

	// /** 連結至新增頁面 */
	public String lnkGoCreate() throws Exception {
		this.formLoad("create");
		return Action.SUCCESS;
	}

	/** APS053E0.jsp，分頁資料中，重新輸入要顯示的頁數 */
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

	/** APS053E0.jsp，分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
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

	public List<PrpdNewCode> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<PrpdNewCode> devResults) {
		this.devResults = devResults;
	}

	public void setPrpdNewCodeService(PrpdNewCodeService prpdNewCodeService) {
		this.prpdNewCodeService = prpdNewCodeService;
	}

	public PrpdNewCode getPrpdNewCode() {
		return prpdNewCode;
	}

	public void setPrpdNewCode(PrpdNewCode prpdNewCode) {
		this.prpdNewCode = prpdNewCode;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
