package com.sinosoft.claim.common.web;

import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.util.RelateBusinessNoViewHelper;
import com.sinosoft.sysframework.log.Logger;

/**
 * <p>
 * Title: 关联页面
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:sinosoft
 * </p>
 * @author 中科软
 * @version 1.0
 */
public class RelateBusinessNoAction extends Struts2Action {
	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/** 日誌*/
	Logger log = Logger.getLogger(RelateBusinessNoAction.class);
	/** 关联页面相关数据整理 */
	private RelateBusinessNoViewHelper relateBusinessNoViewHelper;

	/**
	 * 关联页面中查询相关信息
	 * @param actionMapping ActionMapping
	 * @param actionForm ActionForm
	 * @param httpServletRequest HttpServletRequest
	 * @param httpServletResponse HttpServletResponse
	 * @throws Exception
	 * @return ActionForward
	 */
	public String relateBusinessNo() throws Exception {
		HttpServletRequest httpServletRequest = getRequest();
		String forward = "";
		relateBusinessNoViewHelper.queryRelateInfoToDto(httpServletRequest);
		forward = "success";
		return forward;
	}

	public RelateBusinessNoViewHelper getRelateBusinessNoViewHelper() {
		return relateBusinessNoViewHelper;
	}

	public void setRelateBusinessNoViewHelper(RelateBusinessNoViewHelper relateBusinessNoViewHelper) {
		this.relateBusinessNoViewHelper = relateBusinessNoViewHelper;
	}

}
