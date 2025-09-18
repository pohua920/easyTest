package com.tlg.aps.action;

import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.Action;
import com.tlg.aps.bs.othBatchPassbookServerce.PassbookCalSpService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.OthBatchPassbookNcData;
import com.tlg.prpins.service.OthBatchPassbookNcDataService;
import com.tlg.util.BaseAction;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：OTH0131，處理人員：BJ085，需求單編號：OTH0131 保發中心-保單存摺各險寫入中介Table作業 */
@SuppressWarnings("serial")
public class APS037Action extends BaseAction {
	
	private OthBatchPassbookNcDataService othBatchPassbookNcDataService;
	private PassbookCalSpService passbookCalSpService;
	private List<OthBatchPassbookNcData> devResults;
	
	private String filename;
	private String riskcode;
	private String policyno;
	private Date undate; 
	private String type;
	
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

	/** APS037A0.jsp頁面按下執行鍵,開始執行 **/
	public String btnExecute() throws Exception {
		try{
			Result result = passbookCalSpService.excuteAndCallSp(getUserInfo().getUserId().toUpperCase(), 
					"OTH_PASSBOOK_NC_DATA", riskcode, filename, policyno, type, undate);
			setMessage(result.getMessage().toString());
			
		} catch (SystemException se) {
			setMessage(se.getMessage());
		} catch (Exception e) {
			getRequest().setAttribute("exception", e);
			throw e;
		}
		return Action.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String btnQuery() throws Exception {
		try{
			getPageInfo().getFilter().put("sortBy", "BATCH_NO");
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
	
	public String lnkGoExecute() throws Exception {
		return Action.SUCCESS;
	}
	
	/** APS037E0.jsp(主檔)分頁資料中，重新輸入要顯示的頁數  */
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
	
	/** APS037E0.jsp，(主檔)分頁資料中，重新選擇下拉式選單中更改每頁所顯示的資料筆數 */
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
	
	/** APS037E0.jsp， 查詢結果點選上下三角型排序 */
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
	
	@SuppressWarnings("unchecked")
	private void query() throws Exception {
		parameterHandler();
		Result result = othBatchPassbookNcDataService.findOthBatchPassbookNcDataByPageInfo(getPageInfo());
		if (null != result.getResObject()) {
			devResults = (List<OthBatchPassbookNcData>) result.getResObject();
		} else {
			setMessage(result.getMessage().toString());
		}
	}
	
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
		
		//當所有查詢條件都為空時，預設查詢條件為批次狀態=N
		if ("BLANK".equals(getPageInfo().getFilter().get("filename")) && "BLANK".equals(getPageInfo().getFilter().get("status")) 
				&& "".equals(getPageInfo().getFilter().get("batchNo")) && "".equals(getPageInfo().getFilter().get("batchSerial"))
				&& "".equals(getPageInfo().getFilter().get("riskcode")) && "BLANK".equals(getPageInfo().getFilter().get("procType"))
				&& "".equals(getPageInfo().getFilter().get("sDate")) && "".equals(getPageInfo().getFilter().get("eDate"))) {
			getPageInfo().getFilter().put("status", "N");
		}
		if (getPageInfo().getFilter().get("status")!=null &&
				"BLANK".equals(getPageInfo().getFilter().get("status"))) {
			getPageInfo().getFilter().remove("status");
		}
		if (getPageInfo().getFilter().get("filename")!=null &&
				"BLANK".equals(getPageInfo().getFilter().get("filename"))) {
			getPageInfo().getFilter().remove("filename");
		}
		if (getPageInfo().getFilter().get("procType")!=null &&
				"BLANK".equals(getPageInfo().getFilter().get("procType"))) {
			getPageInfo().getFilter().remove("procType");
		}
	}
	
	public OthBatchPassbookNcDataService getOthBatchPassbookNcDataService() {
		return othBatchPassbookNcDataService;
	}

	public void setOthBatchPassbookNcDataService(OthBatchPassbookNcDataService othBatchPassbookNcDataService) {
		this.othBatchPassbookNcDataService = othBatchPassbookNcDataService;
	}

	public PassbookCalSpService getPassbookCalSpService() {
		return passbookCalSpService;
	}

	public void setPassbookCalSpService(PassbookCalSpService passbookCalSpService) {
		this.passbookCalSpService = passbookCalSpService;
	}

	public List<OthBatchPassbookNcData> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<OthBatchPassbookNcData> devResults) {
		this.devResults = devResults;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getRiskcode() {
		return riskcode;
	}

	public void setRiskcode(String riskcode) {
		this.riskcode = riskcode;
	}

	public String getPolicyno() {
		return policyno;
	}

	public void setPolicyno(String policyno) {
		this.policyno = policyno;
	}

	public Date getUndate() {
		return undate;
	}

	public void setUndate(Date undate) {
		this.undate = undate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
