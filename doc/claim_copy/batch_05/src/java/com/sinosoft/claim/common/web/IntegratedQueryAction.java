package com.sinosoft.claim.common.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;
import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;

/**
 * 分发HTTP GET 综合查询
 * <p>
 * Title: 赔案综合查询信息
 * </p>
 * <p>
 * Description: 赔案综合查询信息
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author 中科软
 * @version 1.0
 */
public class IntegratedQueryAction extends Struts2Action {

	/** serialVersionUID */
	private static final long serialVersionUID = 6446791208730906232L;
	/** 工作流数据收集工具类 */
	private WorkFlowViewHelper workFlowViewHelper;
	/** 编辑类型 */
	private String editType = "";

	public String integratedQuery() throws Exception {
		HttpServletRequest httpServletRequest = getRequest();
		if (pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == 0) {
			pageSize = 20;
		}
		try {
			Page page = workFlowViewHelper.integratedQuery(httpServletRequest, pageNo, pageSize);
			this.writeJSONData(page, "id", "nodeStatus", "riskCode", "riskCodeName", "nodeType", "nodeName", "businessNo", "policyNo", "modelNo", "nodeNo", "insuredName", "handlerCode", "handlerName", "typeFlag", "registNo", "handleTime", "keyIn",
					"keyOut", "lossItemCode", "lossItemName", "iFlowID", "iModelNo", "iNodeNo", "businessType", "iBusinessNo", "iLogNo");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return NONE;
	}

	public WorkFlowViewHelper getWorkFlowViewHelper() {
		return workFlowViewHelper;
	}

	public void setWorkFlowViewHelper(WorkFlowViewHelper workFlowViewHelper) {
		this.workFlowViewHelper = workFlowViewHelper;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

}
