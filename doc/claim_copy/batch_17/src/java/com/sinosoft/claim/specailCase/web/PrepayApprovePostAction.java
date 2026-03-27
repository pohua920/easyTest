/*
 * @(#)PrepayApprovePostAction.java	Mar 5, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.specailCase.web;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.compensate.service.facade.PrepayService;
import com.sinosoft.claim.dto.custom.UserDto;

import ins.framework.web.Struts2Action;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class PrepayApprovePostAction extends Struts2Action {
	/**
	 * 序列号ID号
	 */
	private static final long serialVersionUID = 1L;
	/** 预赔Service */
	private PrepayService prepayService;

	public String prepayApprovePost() throws Exception {
		HttpServletRequest httpServletRequest = this.getRequest();
		String forward = ""; // 向前流转
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		String preCompensateNo = httpServletRequest.getParameter("prpLprepayPreCompensateNo");
		String underWriteFlag = httpServletRequest.getParameter("prpLprepayUnderWriteFlag");
		// 保存预赔信息
		prepayService.approve(preCompensateNo, user.getUserCode(), underWriteFlag);
		user.setUserMessage(preCompensateNo);
		this.clearMessages();
		this.saveMessage(getText("title.prepayApprove.approveSuccess"));
		this.saveMessage(getText("db.prpLprepay.preCompensateNo"));
		forward = "success";
		return forward;

	}

	public PrepayService getPrepayService() {
		return prepayService;
	}

	public void setPrepayService(PrepayService prepayService) {
		this.prepayService = prepayService;
	}
}
