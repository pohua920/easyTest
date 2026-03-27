/*
 * @(#)ReplevyResultQueryAction.java	Mar 11, 2013
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
public class ReplevyResultQueryAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	/**追偿数据收集 */
	private ReplevyViewHelper replevyViewHelper;

	/**
	 * 追偿查询
	 * @return
	 * @throws Exception
	 */
	public String replevyResultQuery() throws Exception {
		String forward = "";
		HttpServletRequest httpServletRequest = this.getRequest();
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
		if ("QUERY".equals(editType)) {
			try {
				replevyViewHelper.prpLcompensateListToView(httpServletRequest, intPageNo, intRecordPerPage);
				Page page = (Page) httpServletRequest.getAttribute("page");
				this.writeJSONData(page, "compensateNo", "claimNo", "policyNo", "indemnityDuty", "sumThisPaid", "statisticsYM");
			} catch (Exception e) {
				e.printStackTrace();
			}
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
