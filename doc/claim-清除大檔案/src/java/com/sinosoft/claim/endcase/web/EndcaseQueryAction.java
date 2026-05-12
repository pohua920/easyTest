/*
 * @(#)EndcaseQueryAction.java	Mar 8, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.endcase.web;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.util.StringConvert;
import com.sinosoft.claim.endcase.util.DAAEndcaseViewHelper;
import com.sinosoft.claim.workflow.vo.WorkFlowQueryDto;
import com.sinosoft.sysframework.reference.AppConfig;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @Author <Administrator>
 * @Date <Mar 8, 2013>
 * @description
 */
public class EndcaseQueryAction extends Struts2Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**结案数据收集*/
	private DAAEndcaseViewHelper daaEndcaseViewHelper;

	/**
	 * 结案查询
	 * @return
	 * @throws Exception
	 */
	public String endcaseQuery() throws Exception {
		HttpServletRequest httpServletRequest = this.getRequest();
		// 业务类型：ADD-新增 EDIT-修改 SHOW-显示
		String editType = httpServletRequest.getParameter("editType");
		String claimNo = httpServletRequest.getParameter("ClaimNo"); // 赔案号
		String policyNo = httpServletRequest.getParameter("PolicyNo"); // 保单号
		String caseNo = httpServletRequest.getParameter("CaseNo"); // 结案号
		// Modify By sunhao 2004-08-24 Reason:增加车牌号，案件状态，操作时间查询条件
		String licenseNo = httpServletRequest.getParameter("LicenseNo");// 车牌号
		String status = httpServletRequest.getParameter("caseFlag");// 案件状态
		String operateDate = httpServletRequest.getParameter("OperateDate");// 操作时间
		String comCode = httpServletRequest.getParameter("comCode");// 案件状态
		String claimDate = httpServletRequest.getParameter("claimDate");// 操作时间
		String registNo = httpServletRequest.getParameter("RegistNo");// 报案号
		// 去掉status中最後一个逗号
		if (status != null && status.trim().length() > 0) {
			status = status.substring(0, status.length() - 1);
		}
		String insuredName = StringConvert.getParam(super.getRequest(), "InsuredName", ConstantCodes.YUI_CHARSET);
		String claimDateSign = httpServletRequest.getParameter("claimDateSign");
		String comCodeSign = httpServletRequest.getParameter("comCodeSign");
		String claimNoSign = httpServletRequest.getParameter("ClaimNoSign");
		String policyNoSign = httpServletRequest.getParameter("PolicyNoSign");
		String licenseNoSign = httpServletRequest.getParameter("LicenseNoSign");
		String operateDateSign = httpServletRequest.getParameter("OperateDateSign");
		String insuredNameSign = httpServletRequest.getParameter("InsuredNameSign");
		String registNoSign = httpServletRequest.getParameter("RegistNoSign");
		String caseNoSign = httpServletRequest.getParameter("CaseNoSign");
		WorkFlowQueryDto workFlowQueryDto = new WorkFlowQueryDto();
		workFlowQueryDto.setClaimNoSign(claimNoSign);
		workFlowQueryDto.setComCodeSign(comCodeSign);
		workFlowQueryDto.setClaimDateSign(claimDateSign);
		workFlowQueryDto.setClaimDate(claimDate);
		workFlowQueryDto.setComCode(comCode);
		workFlowQueryDto.setClaimNo(claimNo);
		workFlowQueryDto.setCaseNo(caseNo);
		workFlowQueryDto.setPolicyNo(policyNo);
		workFlowQueryDto.setLicenseNo(licenseNo);
		workFlowQueryDto.setStatus(status);
		workFlowQueryDto.setOperateDate(operateDate);
		workFlowQueryDto.setInsuredName(insuredName);
		workFlowQueryDto.setPolicyNoSign(policyNoSign);
		workFlowQueryDto.setLicenseNoSign(licenseNoSign);
		workFlowQueryDto.setOperateDateSign(operateDateSign);
		workFlowQueryDto.setInsuredNameSign(insuredNameSign);
		workFlowQueryDto.setRegistNo(registNo);
		workFlowQueryDto.setRegistNoSign(registNoSign);
		workFlowQueryDto.setCaseNoSign(caseNoSign);
		String forward = "";
		// 尚未加入type异常处理{}、其它必须参数异常处理{}
		if (editType.equals("ADD") || editType.equals("EDIT") || editType.equals("SHOW")) {
			// 查询结案信息,整理输入，用於初始界面显示
			String pageSize = httpServletRequest.getParameter("pageSize");
			if (pageSize == null || "".equals(pageSize.trim())) {
				pageSize = AppConfig.get("sysconst.ROWS_PERPAGE");
			}
			String pageNo = httpServletRequest.getParameter("pageNo");
			if (pageNo == null || pageNo.trim().equals("")) {
				pageNo = "1";
			}
			int intRecordPerPage = Integer.parseInt(pageSize);
			int intPageNo = Integer.parseInt(pageNo);
			Page page = daaEndcaseViewHelper.setPrpLendcaseDtoToView(httpServletRequest, workFlowQueryDto, intPageNo, intRecordPerPage);
			this.writeJSONData(page, "claimNo", "riskCode", "caseNo", "policyNo", "endCaserCode", "endCaserName", "endCaseDate");
			forward = NONE;
		}
		return forward;
	}

	public DAAEndcaseViewHelper getDaaEndcaseViewHelper() {
		return daaEndcaseViewHelper;
	}

	public void setDaaEndcaseViewHelper(DAAEndcaseViewHelper daaEndcaseViewHelper) {
		this.daaEndcaseViewHelper = daaEndcaseViewHelper;
	}

}
