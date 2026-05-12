/*
 * @(#)PrepayApproveQueryAction.java	Mar 5, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.specailCase.web;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.ui.control.viewHelper.DAAPrepayViewHelper;

import ins.framework.web.Struts2Action;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class PrepayApproveQueryAction extends Struts2Action {
	/**
	 * 序列号ID号
	 */
	private static final long serialVersionUID = 1L;
	private DAAPrepayViewHelper daaPrepayViewHelper;

	public String prepayApproveQuery() throws Exception {
		HttpServletRequest httpServletRequest = this.getRequest();
		// 业务类型：ADD-新增 EDIT-修改 SHOW-显示
		String editType = httpServletRequest.getParameter("editType");
		String policyNo = httpServletRequest.getParameter("PolicyNo"); // 保单号
		String claimNo = httpServletRequest.getParameter("ClaimNo"); // 赔案号
		String prepayNo = httpServletRequest.getParameter("PrepayNo"); // 预赔号
		String forward = ""; // 向前

		// 尚未加入type异常处理{}、其它必须参数异常处理{}
		if (editType.equals("ADD") || editType.equals("EDIT") || editType.equals("SHOW") || editType.equals("Approve")) {
			// 查询预赔信息,整理输入，用於初始界面显示
			daaPrepayViewHelper.getApprovePrepayList(httpServletRequest, prepayNo, policyNo, claimNo);
			forward = "success";
		}

		return forward;

	}

	public DAAPrepayViewHelper getDaaPrepayViewHelper() {
		return daaPrepayViewHelper;
	}

	public void setDaaPrepayViewHelper(DAAPrepayViewHelper daaPrepayViewHelper) {
		this.daaPrepayViewHelper = daaPrepayViewHelper;
	}

}
