/*
 * @(#)CertifyQueryAction.java	Feb 25, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.certify.web;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.certify.util.DAACertifyViewHelper;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.util.StringConvert;
import com.sinosoft.claim.workflow.vo.WorkFlowQueryDto;
import com.sinosoft.sysframework.reference.AppConfig;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class CertifyQueryAction extends Struts2Action {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 状态 */
	private String status;
	/** 单证viewHelper */
	private DAACertifyViewHelper daaCertifyViewHelper;
	
	/**
	 * 单证查询
	 * @return 页面类型
	 * @throws Exception
	 */
	public String certifyQuery() throws Exception {

		HttpServletRequest httpServletRequest = this.getRequest();

		// 业务类型：ADD-新增 EDIT-修改 SHOW-显示
		String editType = httpServletRequest.getParameter("editType");
		String registNo = httpServletRequest.getParameter("RegistNo"); // 赔案号
		// Modify By sunhao 2004-08-24 Reason:增加车牌号，案件状态，操作时间查询条件
		String licenseNo = StringConvert.getParam(super.getRequest(), "LicenseNo", ConstantCodes.YUI_CHARSET);// 车牌号
		String operateDate = httpServletRequest.getParameter("OperateDate");// 操作时间

		// add by miaowenjun 20060412
		String insuredName = StringConvert.getParam(super.getRequest(), "InsuredName", ConstantCodes.YUI_CHARSET);
		String registNoSign = httpServletRequest.getParameter("RegistNoSign");
		String licenseNoSign = httpServletRequest.getParameter("LicenseNoSign");
		String operateDateSign = httpServletRequest.getParameter("OperateDateSign");
		String insuredNameSign = httpServletRequest.getParameter("InsuredNameSign");
		WorkFlowQueryDto workFlowQueryDto = new WorkFlowQueryDto();
		workFlowQueryDto.setRegistNo(registNo);
		workFlowQueryDto.setLicenseNo(licenseNo);
		workFlowQueryDto.setStatus(status);
		workFlowQueryDto.setOperateDate(operateDate);
		workFlowQueryDto.setInsuredName(insuredName);
		workFlowQueryDto.setRegistNoSign(registNoSign);
		workFlowQueryDto.setLicenseNoSign(licenseNoSign);
		workFlowQueryDto.setOperateDateSign(operateDateSign);
		workFlowQueryDto.setInsuredNameSign(insuredNameSign);


		String forward = NONE; // 向前
		// 尚未加入type异常处理{}、其它必须参数异常处理{}
		if (editType.equals("ADD") || editType.equals("EDIT") || editType.equals("SHOW")) {

			// 查询单证信息,整理输入，用於初始界面显示
			// add by zhaolu 20060803 start
			String pageSize = httpServletRequest.getParameter("pageSize");
			if (pageSize == null || "".equals(pageSize)) {
				pageSize = AppConfig.get("sysconst.ROWS_PERPAGE");
			}
			String pageNo = httpServletRequest.getParameter("pageNo");
			if (pageNo == null || "".equals(pageNo)) {
				pageNo = "1";
			}
			int intRecordPerPage = Integer.parseInt(pageSize);
			int intPageNo = Integer.parseInt(pageNo);
			daaCertifyViewHelper.setPrpLcertifyDtoToView(httpServletRequest, workFlowQueryDto, intPageNo, intRecordPerPage);
			Page page = (Page) httpServletRequest.getAttribute("page");
			this.writeJSONData(page, "status", "id", "startDate", "operatorCode", "operatorName", "operateDate", "collectFlag", "riskCode");
		}
		return forward;
	}

	public DAACertifyViewHelper getDaaCertifyViewHelper() {
		return daaCertifyViewHelper;
	}

	public void setDaaCertifyViewHelper(DAACertifyViewHelper daaCertifyViewHelper) {
		this.daaCertifyViewHelper = daaCertifyViewHelper;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
