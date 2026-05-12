package com.sinosoft.claim.claim.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.claim.util.DAAClaimViewHelper;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.util.StringConvert;
import com.sinosoft.claim.workflow.vo.WorkFlowQueryDto;
import com.sinosoft.sysframework.reference.AppConfig;

/**
 * 分发HTTP GET 立案查询
 * <p>
 * Title: 车险理赔立案查询信息
 * </p>
 * <p>
 * Description: 车险理赔立案立案查询信息系统
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
public class ClaimQueryAction extends Struts2Action {
	private static final long serialVersionUID = 1L;
	/** 编辑类型 */
	private String editType = "";
	/** 赔案号/立案号 */
	private String ClaimNo;
	private String ClaimNoSign;
	/** 保单号 */
	private String PolicyNo;
	private String PolicyNoSign;
	/** 保单号 */
	private String RegistNo;
	private String RegistNoSign;
	/** 车牌号 */
	private String LicenseNo;
	private String LicenseNoSign;
	/** 操作时间 */
	private String OperateDate;
	private String OperateDateSign;
	/** 被保险人 */
	private String InsuredName;
	private String InsuredNameSign;
	/** 案件状态 */
	private String status;
	/** 车险立案ViewHelper */
	private DAAClaimViewHelper daaClaimViewHelper;

	/**
	 * 查询立案信息 查询条件
	 * @return 页面类型
	 * @throws Exception
	 */
	public String query() throws Exception {
		logger.debug("查询满足条件的 立案信息");
		Page page = null;
		if (pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == 0) {
			pageSize = Integer.parseInt(AppConfig.get("sysconst.ROWS_PERPAGE"));
		}
		HttpServletRequest httpServletRequest = getRequest();
		WorkFlowQueryDto workFlowQueryDto = new WorkFlowQueryDto();
		workFlowQueryDto.setPolicyNo(PolicyNo);
		workFlowQueryDto.setRegistNo(RegistNo);
		workFlowQueryDto.setLicenseNo(StringConvert.getParam(httpServletRequest,"LicenseNo",ConstantCodes.YUI_CHARSET));
		workFlowQueryDto.setStatus(status);
		workFlowQueryDto.setOperateDate(OperateDate);
		workFlowQueryDto.setInsuredName(StringConvert.getParam(httpServletRequest,"InsuredName",ConstantCodes.YUI_CHARSET));
		workFlowQueryDto.setRegistNoSign(RegistNoSign);
		workFlowQueryDto.setPolicyNoSign(PolicyNoSign);
		workFlowQueryDto.setLicenseNoSign(LicenseNoSign);
		workFlowQueryDto.setOperateDateSign(OperateDateSign);
		workFlowQueryDto.setInsuredNameSign(InsuredNameSign);
		workFlowQueryDto.setClaimNo(ClaimNo);
		workFlowQueryDto.setClaimNoSign(ClaimNoSign);
		workFlowQueryDto.setInsuredNameSign(InsuredNameSign);
		// 尚未加入type异常处理{}、其它必须参数异常处理{}
		try {
			if (editType.equals("ADD") || editType.equals("EDIT") || editType.equals("SHOW")) {
				// 查询理赔节点状态信息,整理输入，用於初始界面显示
				page = daaClaimViewHelper.setPrpLclaimDtoToView(getRequest(), workFlowQueryDto, pageNo, pageSize);
				if (editType.equals("SHOW")) {
					httpServletRequest.setAttribute("type", "SHOW");
				}
				this.writeJSONData(page, "status", "claimNo", "policyNo", "riskCode", "registNo", "operatorCode", "operatorName", "operateDate", "caseType", "reportDate", "inputDate");
			}
			// 已超时的报案
			if (editType.equals("registTimeOut")) {
				// 查询理赔节点状态信息,整理输入，用於初始界面显示
				page = daaClaimViewHelper.registTimeOut(httpServletRequest, pageNo, pageSize);
				this.writeJSONData(page, "status", "registNo", "policyNo", "insuredName", "riskCode", "operatorCode", "operatorName", "reportDate");
			}
			if (editType.equals("claimTimeOut")) {
				// 查询理赔节点状态信息,整理输入，用於初始界面显示
				page = daaClaimViewHelper.claimTimeOut(httpServletRequest, pageNo, pageSize);
				this.writeJSONData(page, "status", "claimNo", "policyNo", "riskCode", "registNo", "operatorCode", "operatorName", "operateDate", "caseType", "reportDate", "inputDate");
			}
			// 获取已超时赔付
			if (editType.equals("compeTimeOut")) {
				page = daaClaimViewHelper.compeTimeOut(httpServletRequest, pageNo, pageSize);
				this.writeJSONData(page, "businessNo", "policyNo", "riskCodeName", "insuredName", "operateDate");
			}
			// 获取已超时赔付
			// 特殊赔案
			if (editType.equals("SpecialCase")) {
				// 查询理赔节点状态信息,整理输入，用於初始界面显示
				page = daaClaimViewHelper.getSpecialList(httpServletRequest, ClaimNo, PolicyNo);
			}
			// mantis： CLM0197，處理人員：CD078，需求單編號：CLM0197 新核心-新增立案修改出險日期及出險地區功能Start
			if (editType.equals("EditCase")) {
				page = daaClaimViewHelper.claimEditCase(getRequest(), pageNo, pageSize);
				this.writeJSONData(page, "status", "claimNo", "policyNo", "riskCode", "registNo", "operatorCode","insuredName","remark");
			}
			// mantis： CLM0197，處理人員：CD078，需求單編號：CLM0197 新核心-新增立案修改出險日期及出險地區功能End
		} catch (Exception ex) {
			ex.printStackTrace();
			this.writeJSONMsg(ex.getMessage());
		}
		return NONE;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public String getClaimNo() {
		return ClaimNo;
	}

	public void setClaimNo(String claimNo) {
		ClaimNo = claimNo;
	}

	public String getClaimNoSign() {
		return ClaimNoSign;
	}

	public void setClaimNoSign(String claimNoSign) {
		ClaimNoSign = claimNoSign;
	}

	public String getPolicyNo() {
		return PolicyNo;
	}

	public void setPolicyNo(String policyNo) {
		PolicyNo = policyNo;
	}

	public String getPolicyNoSign() {
		return PolicyNoSign;
	}

	public void setPolicyNoSign(String policyNoSign) {
		PolicyNoSign = policyNoSign;
	}

	public String getRegistNo() {
		return RegistNo;
	}

	public void setRegistNo(String registNo) {
		RegistNo = registNo;
	}

	public String getRegistNoSign() {
		return RegistNoSign;
	}

	public void setRegistNoSign(String registNoSign) {
		RegistNoSign = registNoSign;
	}

	public String getLicenseNo() {
		return LicenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		LicenseNo = licenseNo;
	}

	public String getLicenseNoSign() {
		return LicenseNoSign;
	}

	public void setLicenseNoSign(String licenseNoSign) {
		LicenseNoSign = licenseNoSign;
	}

	public String getOperateDate() {
		return OperateDate;
	}

	public void setOperateDate(String operateDate) {
		OperateDate = operateDate;
	}

	public String getOperateDateSign() {
		return OperateDateSign;
	}

	public void setOperateDateSign(String operateDateSign) {
		OperateDateSign = operateDateSign;
	}

	public String getInsuredName() {
		return InsuredName;
	}

	public void setInsuredName(String insuredName) {
		InsuredName = insuredName;
	}

	public String getInsuredNameSign() {
		return InsuredNameSign;
	}

	public void setInsuredNameSign(String insuredNameSign) {
		InsuredNameSign = insuredNameSign;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public DAAClaimViewHelper getDaaClaimViewHelper() {
		return daaClaimViewHelper;
	}

	public void setDaaClaimViewHelper(DAAClaimViewHelper daaClaimViewHelper) {
		this.daaClaimViewHelper = daaClaimViewHelper;
	}

}