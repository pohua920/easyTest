/*
 * @(#)ReplevyBeforeEditAction.java	Mar 11, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.remnant.web;

import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.service.facade.PrpDcompanyService;
import com.sinosoft.claim.common.service.facade.PrpDuserService;
import com.sinosoft.claim.remnant.util.RemnantViewHelper;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class RemnantBeforeEditAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	/** 残余物ViewHelper */
	private RemnantViewHelper remnantViewHelper;
	/** 员工代码Service */
	private PrpDuserService prpDuserService;
	/** 机构代码Service */
	private PrpDcompanyService prpDcompanyService;
	/**赔款计算书Service */
	private PrpLcompensateService prpLcompensateService;
	/**保单基本信息Service */
	private PrpCmainService prpCmainService;
	/**立案信息Service */
	private PrpLclaimService prpLclaimService;
	private WorkFlowService workFlowService;
	/**编辑类型*/
	private String editType = "";
	/**处理机构名称*/
	private String makeComName = "";
	private String outerCode = null;

	/**残余物查询前处理
	 * @return
	 * @throws Exception
	 */
	public String remnantBeforeEdit() throws Exception {
		String forward = "";
		HttpServletRequest httpServletRequest = this.getRequest();
//		String claimNo = httpServletRequest.getParameter("claimNo");
		String policyNo = httpServletRequest.getParameter("policyNo");
		String compensateNo = httpServletRequest.getParameter("compensateNo");
		PrpCmain prpCmain = this.prpCmainService.findByPrimaryKey(policyNo);
		httpServletRequest.setAttribute("prpCmain", prpCmain);
		if ("add".equals(editType) ) {//残余物处理查询结果的展示
			remnantViewHelper.remnantDtoToView(httpServletRequest, compensateNo);
		}
		if ("edit".equals(editType) ) {//残余物修改查询结果的展示
			remnantViewHelper.remnantDtoToView(httpServletRequest, compensateNo);
		}
		if ("undwrt".equals(editType)) {//残余物审核查询结果的展示
			remnantViewHelper.remnantDtoToView(httpServletRequest, compensateNo);
		}
		if ("show".equals(editType)) {//残余物查询查询结果的展示
			remnantViewHelper.remnantDtoToView(httpServletRequest, compensateNo);
		}
		forward = editType;
		return forward;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public RemnantViewHelper getRemnantViewHelper() {
		return remnantViewHelper;
	}

	public void setRemnantViewHelper(RemnantViewHelper remnantViewHelper) {
		this.remnantViewHelper = remnantViewHelper;
	}

	public PrpDuserService getPrpDuserService() {
		return prpDuserService;
	}

	public void setPrpDuserService(PrpDuserService prpDuserService) {
		this.prpDuserService = prpDuserService;
	}

	public PrpDcompanyService getPrpDcompanyService() {
		return prpDcompanyService;
	}

	public void setPrpDcompanyService(PrpDcompanyService prpDcompanyService) {
		this.prpDcompanyService = prpDcompanyService;
	}

	public String getMakeComName() {
		return makeComName;
	}

	public void setMakeComName(String makeComName) {
		this.makeComName = makeComName;
	}

	public String getOuterCode() {
		return outerCode;
	}

	public void setOuterCode(String outerCode) {
		this.outerCode = outerCode;
	}

	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public PrpCmainService getPrpCmainService() {
		return prpCmainService;
	}

	public void setPrpCmainService(PrpCmainService prpCmainService) {
		this.prpCmainService = prpCmainService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

}
