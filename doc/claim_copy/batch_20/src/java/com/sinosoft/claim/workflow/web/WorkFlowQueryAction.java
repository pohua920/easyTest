/*
 * @(#)WorkFlowFlowQueryAction.java	Mar 16, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.workflow.web;

import ins.framework.common.Page;
import ins.framework.utils.DataUtils;
import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.regist.util.DAARegistViewHelper;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.claim.workflow.vo.WorkFlowQueryDto;
import com.sinosoft.sysframework.reference.AppConfig;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class WorkFlowQueryAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	/**报案viewHelper*/
	private DAARegistViewHelper daaRegistViewHelper;
	/**工作流viewHelper*/
	private WorkFlowViewHelper workFlowViewHelper;

	/**
	 * 查询理赔节点状态信息,整理输入，用於初始界面显示
	 * @return
	 * @throws Exception
	 */
	public String workFlowQuery() throws Exception {
		HttpServletRequest httpServletRequest = this.getRequest();
		// 业务类型：ADD-新增 EDIT-修改 SHOW-显示
		String editType = httpServletRequest.getParameter("editType");
		String caseType = httpServletRequest.getParameter("caseType");
		String policyNo = httpServletRequest.getParameter("prpLregistPolicyNo"); // 保单号
		String registNo = httpServletRequest.getParameter("prpLregistRegistNo"); // 报案号
		// 增加车牌号，案件状态，操作时间查询条件
		String licenseNo = httpServletRequest.getParameter("prpLregistLicenseNo");// 车牌号
		String status = httpServletRequest.getParameter("caseFlag");// 案件状态
		String operateDate = httpServletRequest.getParameter("operateDate");// 操作时间
		String riskCode = httpServletRequest.getParameter("prpLregistRiskCode"); // 险种代码
		String insuredName = httpServletRequest.getParameter("prpLregistInsuredName");// 被保险人姓名
		String riskCodeName = httpServletRequest.getParameter("prpLregistRiskCodeName");
		String riskCodeNameSign = httpServletRequest.getParameter("RiskCodeNameSign");
		// 新增以立案号查询条件
		String claimNo = httpServletRequest.getParameter("prpLregistClaimNo");
		WorkFlowQueryDto workFlowQueryDto = new WorkFlowQueryDto();
		workFlowQueryDto.setCaseType(caseType);
		workFlowQueryDto.setInsuredName(insuredName);
		workFlowQueryDto.setLicenseNo(licenseNo);
		workFlowQueryDto.setClaimNo(claimNo);// 新增以立案号查询条件
		workFlowQueryDto.setPolicyNo(policyNo);
		workFlowQueryDto.setRegistNo(registNo);
		workFlowQueryDto.setRiskCode(riskCode);
		workFlowQueryDto.setRiskCodeName(riskCodeName);
		workFlowQueryDto.setRiskCodeNameSign(riskCodeNameSign);
		// 去掉status中最後一个逗号
		if (status != null && status.trim().length() > 0) {
			status = status.substring(0, status.length() - 1);
		}
		String forward = ""; // 向前
		try {
			// 尚未加入type异常处理{}、其它必须参数异常处理{}
			if ("ADD".equals(editType) || "SHOW".equals(editType)) {
				// 查询理赔节点状态信息,整理输入，用於初始界面显示
				// 每页显示的行数
				String recordPerPage = AppConfig.get("sysconst.ROWS_PERPAGE");
				String pageNo = httpServletRequest.getParameter("pageNo");
				if (DataUtils.emptyToNull(pageNo) == null) {
					pageNo = "1";
				}
				this.daaRegistViewHelper.setPrpLregistDtoToView(httpServletRequest, registNo, policyNo, licenseNo, status, operateDate, riskCode, insuredName, pageNo, recordPerPage);
				forward = "success";
			}
			if ("RegistBeforeQuery".equals(editType)) {
				editType = "WorkFlow";
			}
			if ("WorkFlow".equals(editType) || "EDIT".equals(editType)) {
				// 每页显示的行数
				if (pageNo == 0) {
					pageNo = 1;
				}
				if (pageSize == 0) {
					pageSize = Integer.parseInt(AppConfig.get("sysconst.ROWS_PERPAGE"));
				}
				// 查询理赔节点状态信息,整理输入，用於初始界面显示
				Page page = this.workFlowViewHelper.getWorkFlowList(httpServletRequest, pageNo, pageSize);
				this.writeJSONData(page, "id", "businessNo", "relatePolicyList", "insuredName", "lossItemName","comName", "handlerName", "submitTime", "riskCodeName", "otherFlag" , "registNo");
				return NONE;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return forward;

	}

	public DAARegistViewHelper getDaaRegistViewHelper() {
		return daaRegistViewHelper;
	}

	public void setDaaRegistViewHelper(DAARegistViewHelper daaRegistViewHelper) {
		this.daaRegistViewHelper = daaRegistViewHelper;
	}

	public WorkFlowViewHelper getWorkFlowViewHelper() {
		return workFlowViewHelper;
	}

	public void setWorkFlowViewHelper(WorkFlowViewHelper workFlowViewHelper) {
		this.workFlowViewHelper = workFlowViewHelper;
	}
}
