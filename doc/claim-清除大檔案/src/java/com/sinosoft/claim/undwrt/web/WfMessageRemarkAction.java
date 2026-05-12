/*
 * @(#)WfMessageRemarkAction.java	Feb 19, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.undwrt.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.sinosoft.claim.undwrt.util.WfMessageRemarkViewHelper;

import ins.framework.web.Struts2Action;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class WfMessageRemarkAction extends Struts2Action {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WfMessageRemarkViewHelper wfMessageRemarkViewHelper;

	/**
	 * 核赔情况记录
	 * @return
	 * @throws Exception
	 */
	public String wfMessageRemark() throws Exception {
		HttpServletRequest request = this.getRequest();
		String actionType = StringUtils.trimToEmpty(request.getParameter("actionType"));
		if (actionType.equals("query")) {
			wfMessageRemarkViewHelper.query(request);
		} else if (actionType.equals("save")) {
			wfMessageRemarkViewHelper.save(request);
		}
		return actionType;
	}

	public WfMessageRemarkViewHelper getWfMessageRemarkViewHelper() {
		return wfMessageRemarkViewHelper;
	}

	public void setWfMessageRemarkViewHelper(WfMessageRemarkViewHelper wfMessageRemarkViewHelper) {
		this.wfMessageRemarkViewHelper = wfMessageRemarkViewHelper;
	}

}
