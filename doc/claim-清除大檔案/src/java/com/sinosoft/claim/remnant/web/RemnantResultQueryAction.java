package com.sinosoft.claim.remnant.web;



import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.remnant.util.RemnantViewHelper;
import com.sinosoft.sysframework.reference.AppConfig;

/**
 * 残余物查询
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class RemnantResultQueryAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	/** 残余物ViewHelper */
	private RemnantViewHelper remnantViewHelper;
	/**残余物结果查询
	 * @return
	 * @throws Exception
	 */
	public String remnantResultQuery() throws Exception {
		String forward = "";
		HttpServletRequest httpServletRequest = this.getRequest();
		String pageNo = httpServletRequest.getParameter("pageNo");
		String pageSize = httpServletRequest.getParameter("pageSize");
		String editType = httpServletRequest.getParameter("editType");
		if (pageSize == null || "".equals(pageSize)) {
			pageSize = AppConfig.get("sysconst.ROWS_PERPAGE");
		}
		int intRecordPerPage = Integer.parseInt(pageSize);
		int intPageNo = 1;
		if (pageNo != null && !pageNo.trim().equals("")) {
			intPageNo = Integer.parseInt(pageNo);
		}
		if("addquery".equals(editType)){//残余物处理查询
			remnantViewHelper.addQueryDtoToView(httpServletRequest, intPageNo, intRecordPerPage);
			Page page = (Page) httpServletRequest.getAttribute("page");
			this.writeJSONData(page,"claimNo","comCode","riskCodeName","comName","policyNo","remnantDate");
		}else if("undwrtquery".equals(editType)){//残余物审核查询
			remnantViewHelper.undwrtQueryDtoToView(httpServletRequest, intPageNo, intRecordPerPage);
			Page page = (Page) httpServletRequest.getAttribute("page");
			this.writeJSONData(page,"claimNo","comCode","riskCodeName","comName","policyNo","compensateNo","remnantDate","underWriteFlag","flowID", "logNo", "nodeName");
		}else if("editquery".equals(editType)){//残余物修改查询
			remnantViewHelper.editQueryDtoToView(httpServletRequest, intPageNo, intRecordPerPage);
			Page page = (Page) httpServletRequest.getAttribute("page");
			this.writeJSONData(page,"claimNo","comCode","riskCodeName","comName","policyNo","compensateNo","remnantDate","flowID", "logNo", "nodeName");
		}else if("showquery".equals(editType)){//残余物查询
			remnantViewHelper.showQueryDtoToView(httpServletRequest, intPageNo, intRecordPerPage);
			Page page = (Page) httpServletRequest.getAttribute("page");
			this.writeJSONData(page,"claimNo","comCode","riskCodeName","comName","policyNo","compensateNo","remnantDate");
		}
		forward = NONE;
		return forward;
	}

	public RemnantViewHelper getRemnantViewHelper() {
		return remnantViewHelper;
	}

	public void setRemnantViewHelper(RemnantViewHelper remnantViewHelper) {
		this.remnantViewHelper = remnantViewHelper;
	}



}
