package com.sinosoft.claim.claim.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.sinosoft.claim.claim.util.ExcludeClaimViewHelper;

/**
 * @author 中科软
 */
public class ExcludeClaimAction extends Struts2Action {
	private static final long serialVersionUID = 4631368321879327536L;
	/** 立案除外 */
	private ExcludeClaimViewHelper excludeClaimViewHelper = null;
	
	/**
	 * 立案除外信息
	 * @return 操作类型
	 * @throws Exception
	 */
	public String excludeClaim() throws Exception {
		HttpServletRequest request = getRequest();
		HttpServletResponse response = getResponse();
		String actionType = StringUtils.trimToEmpty(request.getParameter("actionType"));
		String forward = null;
		if (actionType.equals("insertPrepare")) {
			forward = actionType;
		}
		if (actionType.equals("historyPrepare")) {
			forward = actionType;
		}

		if (actionType.equals("insertQuery")) {
			Page page = excludeClaimViewHelper.insertQuery(request, response);
			this.writeJSONData(page, "registNo", "status","policyNo", "insuredName", "reportDate", "comCode");
			forward = NONE;
		}
		if (actionType.equals("historyQuery")) {
			Page page = excludeClaimViewHelper.historyQuery(request, response);
			this.writeJSONData(page, "registNo", "policyNo", "operatorCode", "operatorname", "inputDate", "excludereason");
			forward = NONE;
		}

		if (actionType.equals("prepareInsert")) {
			excludeClaimViewHelper.prepareInsert(request, response);
			forward = actionType;
		}
		if (actionType.equals("insert")) {
			excludeClaimViewHelper.insert(request, response);
			forward = "success";
		}
		return forward;
	}

	public ExcludeClaimViewHelper getExcludeClaimViewHelper() {
		return excludeClaimViewHelper;
	}

	public void setExcludeClaimViewHelper(ExcludeClaimViewHelper excludeClaimViewHelper) {
		this.excludeClaimViewHelper = excludeClaimViewHelper;
	}

}