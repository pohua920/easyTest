/*
 * @(#)ReplevyBeforeQueryAction.java	Mar 11, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.replevy.web;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.replevy.util.ReplevyViewHelper;
import com.sinosoft.sysframework.reference.AppConfig;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class ReplevyBeforeQueryAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	/**追偿数据收集*/
	private ReplevyViewHelper replevyViewHelper;

	public String replevyBeforeQuery() throws Exception {
		HttpServletRequest httpServletRequest = this.getRequest();
		String forward = "";
		String editType = httpServletRequest.getParameter("editType");
		String pageNo = httpServletRequest.getParameter("pageNo");
		String pageSize = httpServletRequest.getParameter("pageSize");
		if (pageSize == null || "".equals(pageSize)) {
			pageSize = AppConfig.get("sysconst.ROWS_PERPAGE");
		}
		int intRecordPerPage = Integer.parseInt(pageSize);
		int intPageNo = 1;
		if (pageNo != null && !pageNo.trim().equals("")) {
			intPageNo = Integer.parseInt(pageNo);
		}
		if ("PRINT".equals(editType)) {//追償列印
			replevyViewHelper.replevyPrintQueryDtoToView(httpServletRequest, intPageNo, intRecordPerPage);
			forward = "PRINT";
		} else if ("QUERY".equals(editType))  {//追償查詢
			replevyViewHelper.replevyQueryDtoToView(httpServletRequest, intPageNo, intRecordPerPage);
			Page page = (Page) httpServletRequest.getAttribute("page");
			this.writeJSONData(page, "claimNo", "registNo", "policyNo", "insuredCode", "insuredName", "claimDate");
			forward = NONE;
		} else {//追償處理、追償修改、登錄修改查詢
			forward = NONE;
		}
		return forward;
	}

	public ReplevyViewHelper getReplevyViewHelper() {
		return replevyViewHelper;
	}

	public void setReplevyViewHelper(ReplevyViewHelper replevyViewHelper) {
		this.replevyViewHelper = replevyViewHelper;
	}

}
