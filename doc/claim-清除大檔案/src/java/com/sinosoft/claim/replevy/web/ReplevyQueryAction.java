package com.sinosoft.claim.replevy.web;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.replevy.util.ReplevyViewHelper;
import com.sinosoft.sysframework.reference.AppConfig;

import ins.framework.common.Page;
import ins.framework.utils.DataUtils;
import ins.framework.web.Struts2Action;

public class ReplevyQueryAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	private ReplevyViewHelper replevyViewHelper;
	
	public String replevyQuery() throws Exception{
		HttpServletRequest request = this.getRequest();
		String editType = request.getParameter("editType");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		if (pageSize == null || "".equals(pageSize)) {
			pageSize = AppConfig.get("sysconst.ROWS_PERPAGE");
		}
		int intRecordPerPage = Integer.parseInt(pageSize);
		int intPageNo = 1;
		if (pageNo != null && !pageNo.trim().equals("")) {
			intPageNo = Integer.parseInt(pageNo);
		}
		try {
			if ("addQuery".equals(editType)) {//追償登錄查詢
				this.replevyViewHelper.replevyQueryForAddQuery(request, intPageNo, intRecordPerPage);
				Page page = (Page) request.getAttribute("page");
				this.writeJSONData(page, "claimNo", "registNo", "policyNo", "insuredCode", "insuredName", "claimDate", "hasReplevy");
			} else if ("ADD".equals(editType) || "editQuery".equals(editType)) {//追償處理、追償修改查詢
				replevyViewHelper.replevyQueryForAdd(request, intPageNo, intRecordPerPage);
				Page page = (Page) request.getAttribute("page");
				this.writeJSONData(page, "compensateNo", "claimNo", "policyNo", "operatorCode", "operatorName", "inputDate");
			} else if ("EDIT".equals(editType) || "UNDWRT".equals(editType)) {
				replevyViewHelper.replevyQueryForUndwrt(request, Integer.parseInt(pageNo), Integer.parseInt(pageSize));
				Page page = (Page) request.getAttribute("page");
				this.writeJSONData(page, "compensateNo", "claimNo", "policyNo","operatorCode","operatorName", "flowID", "logNo", "nodeName","handlerCode","handlerName", "flowInTime");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}
	
	
	public String replevyUndwrtQuery() throws Exception{
		try {
			HttpServletRequest request = super.getRequest();
			String pageNo = request.getParameter("pageNo");
			String pageSize = request.getParameter("pageSize");
			if (DataUtils.emptyToNull(pageSize) == null) {
				pageSize = AppConfig.get("sysconst.ROWS_PERPAGE");
			}
			replevyViewHelper.replevyQueryForUndwrt(request, Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			Page page = (Page) request.getAttribute("page");
			this.writeJSONData(page, "compensateNo", "claimNo", "policyNo","operatorCode","operatorName", "flowID", "logNo", "nodeName","handlerCode","handlerName","flowInTime");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}

	public ReplevyViewHelper getReplevyViewHelper() {
		return replevyViewHelper;
	}

	public void setReplevyViewHelper(ReplevyViewHelper replevyViewHelper) {
		this.replevyViewHelper = replevyViewHelper;
	}

	
}
