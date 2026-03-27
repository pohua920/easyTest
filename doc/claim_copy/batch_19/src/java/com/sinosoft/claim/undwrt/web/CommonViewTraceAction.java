/*
 * @(#)CommonViewTraceAction.java	Feb 19, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.undwrt.web;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.undwrt.util.CommonViewTraceViewHelper;
import com.sinosoft.undwrt.dto.domain.WfLogDto;

import ins.framework.web.Struts2Action;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class CommonViewTraceAction extends Struts2Action {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**轨迹信息ViewHelper*/
	private CommonViewTraceViewHelper commonViewTraceViewHelper;

	/**
	 * 核赔轨迹信息处理
	 * @return
	 * @throws Exception
	 */
	public String commonViewTrace() throws Exception {
		String forward = "";
		Collection<WfLogDto> colTraceInfoList = new ArrayList<WfLogDto>();
		HttpServletRequest req = this.getRequest();
		colTraceInfoList = commonViewTraceViewHelper.getTraceInfoList(req);
		req.setAttribute("TraceInfoList", colTraceInfoList);
		forward = "success";
		return forward;
	}

	public CommonViewTraceViewHelper getCommonViewTraceViewHelper() {
		return commonViewTraceViewHelper;
	}

	public void setCommonViewTraceViewHelper(CommonViewTraceViewHelper commonViewTraceViewHelper) {
		this.commonViewTraceViewHelper = commonViewTraceViewHelper;
	}

}
