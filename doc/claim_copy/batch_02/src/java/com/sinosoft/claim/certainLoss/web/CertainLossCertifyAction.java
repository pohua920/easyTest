package com.sinosoft.claim.certainLoss.web;

import ins.framework.web.Struts2Action;

import javax.servlet.http.*;

import com.sinosoft.claim.certify.util.DAACertifyViewHelper;

/**
 * 车险理赔定损编辑界面
 * <p>
 * Title: 车险理赔定损编辑界面信息
 * </p>
 * <p>
 * Description: 车险理赔定损编辑界面信息
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: sinosoft.com.cn
 * </p>
 * author 中科软
 */
public class CertainLossCertifyAction extends Struts2Action {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 单证viewHelper */
	private DAACertifyViewHelper daaCertifyViewHelper;

	/**
	 * 定损单证处理
	 * @return
	 * @throws Exception
	 */
	public String certainLossCertify() throws Exception {
		HttpServletRequest httpServletRequest = super.getRequest();
		String forward = ""; // 向前流转
		String editType = httpServletRequest.getParameter("editType");
		// 报案号码
		String registNo = httpServletRequest.getParameter("registNo");
		if (editType != null && editType.equals("Certify")) {
			// 用viewHelper整理界面输入
			daaCertifyViewHelper.certifyDtoToView(httpServletRequest, registNo, "certa");
			forward = "target1";
		} else {
			// 单证上传的时候
		}
		return forward;
	}

	public DAACertifyViewHelper getDaaCertifyViewHelper() {
		return daaCertifyViewHelper;
	}

	public void setDaaCertifyViewHelper(DAACertifyViewHelper daaCertifyViewHelper) {
		this.daaCertifyViewHelper = daaCertifyViewHelper;
	}
}
