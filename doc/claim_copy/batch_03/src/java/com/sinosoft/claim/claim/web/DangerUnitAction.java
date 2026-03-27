/*
 * @(#)DangerUnitAction.java	Feb 3, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.claim.web;

import java.util.List;

import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.reins.service.ReinsServiceManager;
import com.sinosoft.sysframework.common.datatype.DateTime;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class DangerUnitAction extends Struts2Action {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**  */
	private String openerIndex = "";
	/**  */
	private String PageType = "";
	/** 再保接口service */
	private ReinsServiceManager reinsServiceManager;

	/**
	 * 查询危险单位信息
	 * @return 页面类型
	 * @throws Exception
	 */
	public String dangerUnit() throws Exception {
		String forward = "success";
		HttpServletRequest httpServletRequest = getRequest();
		String policyNo = httpServletRequest.getParameter("policyNo");
		String DamageDate = httpServletRequest.getParameter("damageDate");
		DateTime damageDate = new DateTime(DamageDate);
		List<?> reinsDangerUnitCollection = (List<?>) reinsServiceManager.getReinsService().getDangerUnit(policyNo, damageDate);
		httpServletRequest.setAttribute("dangerUnitList", reinsDangerUnitCollection);
		return forward;
	}

	public String getOpenerIndex() {
		return openerIndex;
	}

	public void setOpenerIndex(String openerIndex) {
		this.openerIndex = openerIndex;
	}

	public String getPageType() {
		return PageType;
	}

	public void setPageType(String pageType) {
		PageType = pageType;
	}

	public ReinsServiceManager getReinsServiceManager() {
		return reinsServiceManager;
	}

	public void setReinsServiceManager(ReinsServiceManager reinsServiceManager) {
		this.reinsServiceManager = reinsServiceManager;
	}
}
