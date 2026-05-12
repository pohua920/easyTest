/*
 * @(#)PrepayQueryAction.java	Mar 5, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.specailCase.web;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.specailCase.util.DAAPrepayViewHelper;

import ins.framework.web.Struts2Action;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class PrepayQueryAction extends Struts2Action {
	/**
	 * 序列号ID号
	 */
	private static final long serialVersionUID = 1L;
	/**预赔数据收集 */
	private DAAPrepayViewHelper daaPrepayViewHelper;

	/**
	 * 预赔查询
	 * @return
	 * @throws Exception
	 */
	public String prepayQuery() throws Exception {
		HttpServletRequest httpServletRequest = this.getRequest();
		// 业务类型：ADD-新增 EDIT-修改 SHOW-显示
		String editType = httpServletRequest.getParameter("editType");
		String policyNo = httpServletRequest.getParameter("PolicyNo"); // 保单号
		String claimNo = httpServletRequest.getParameter("ClaimNo"); // 赔案号
		String prepayNo = httpServletRequest.getParameter("PrepayNo"); // 预赔计算书号
		// Modify By sunhao 2004-08-24 Reason:增加车牌号，案件状态，操作时间,核赔标志查询条件
		String licenseNo = httpServletRequest.getParameter("LicenseNo");// 车牌号
		String status = httpServletRequest.getParameter("caseFlag");// 案件状态
		String operateDate = httpServletRequest.getParameter("OperateDate");// 操作时间
		String underWriteFlag = httpServletRequest.getParameter("prepayFlag");// 核赔标志

		// 去掉status中最後一个逗号
		if (status != null && status.trim().length() > 0) {
			status = status.substring(0, status.length() - 1);
		}
		// 去掉compensateFlag中最後一个逗号
		if (underWriteFlag != null && underWriteFlag.trim().length() > 0) {
			underWriteFlag = underWriteFlag.substring(0, underWriteFlag.length() - 1);
		}
		String forward = ""; // 向前
		// 尚未加入type异常处理{}、其它必须参数异常处理{}
		if (editType.equals("ADD") || editType.equals("EDIT") || editType.equals("SHOW")) {
			// 查询预赔信息,整理输入，用於初始界面显示

			daaPrepayViewHelper.setPrpLprepayDtoToView(httpServletRequest, prepayNo, policyNo, claimNo, licenseNo, status, operateDate, underWriteFlag);

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
