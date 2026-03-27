package com.sinosoft.claim.certainLoss.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.certainLoss.service.facade.CertainLossService;
import com.sinosoft.claim.certainLoss.util.DAACertainLossViewHelper;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.util.StringConvert;
import com.sinosoft.claim.schema.model.PrpLverifyLoss;
import com.sinosoft.claim.workflow.vo.WorkFlowQueryDto;
import com.sinosoft.sysframework.reference.AppConfig;

/**
 * 定损查询
 * <p>
 * Title: 车险理赔定损查询信息
 * </p>
 * <p>
 * Description: 车险理赔定损定损查询信息系统样本程序
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @ClassName CertainLossQueryAction
 * @Description 
 * @author 中科软
 */
public class CertainLossQueryAction extends Struts2Action {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 报案号查询匹配 */
	private String RegistNoSign;
	/** 报案号 */
	private String RegistNo;
	/** 保单号查询匹配 */
	private String PolicyNoSign;
	/** 保单号 */
	private String PolicyNo;
	/** 车牌号码查询匹配 */
	private String LicenseNoSign;
	/** 车牌号码 */
	private String LicenseNo;
	/** 操作时间查询匹配 */
	private String OperateDateSign;
	/** 操作时间 */
	private String OperateDate;
	/** 被保险人名称查询匹配 */
	private String InsuredNameSign;
	/** 被保险人名称 */
	private String InsuredName;
	/** 状态 */
	private String status;
	/** 编辑类型 */
	private String editType = "SHOW";
	/** 定损service */
	private CertainLossService certainLossService;
	/** 定损viewHelper */
	private DAACertainLossViewHelper daaCertainLossViewHelper;

	/**
	 * 定损查询
	 * @return
	 * @throws Exception
	 */
	public String certainLossQuery() throws Exception {
		HttpServletRequest httpServletRequest = super.getRequest();
		// 业务类型：ADD-新增 EDIT-修改 SHOW-显示
		WorkFlowQueryDto workFlowQueryDto = new WorkFlowQueryDto();
		workFlowQueryDto.setPolicyNo(PolicyNo);
		workFlowQueryDto.setRegistNo(RegistNo);
		workFlowQueryDto.setLicenseNo(StringConvert.getParam(super.getRequest(), "LicenseNo", ConstantCodes.YUI_CHARSET));
		workFlowQueryDto.setStatus(status);
		workFlowQueryDto.setOperateDate(OperateDate);
		workFlowQueryDto.setInsuredName(StringConvert.getParam(super.getRequest(), "InsuredName", ConstantCodes.YUI_CHARSET));
		workFlowQueryDto.setRegistNoSign(RegistNoSign);
		workFlowQueryDto.setPolicyNoSign(PolicyNoSign);
		workFlowQueryDto.setLicenseNoSign(LicenseNoSign);
		workFlowQueryDto.setOperateDateSign(OperateDateSign);
		workFlowQueryDto.setInsuredNameSign(InsuredNameSign);
		if (editType.equals("ADD") || editType.equals("EDIT") || editType.equals("SHOW")) {
			try {
				// 查询定损信息,整理输入，用於初始界面显示
				if (pageNo == 0) {
					pageNo = 1;
				}
				if (pageSize == 0) {
					pageSize = Integer.parseInt(AppConfig.get("sysconst.ROWS_PERPAGE"));
				}
				Page page = this.daaCertainLossViewHelper.setPrpLcertainLossDtoToView(httpServletRequest, workFlowQueryDto, pageNo, pageSize);
				if (editType.equals("SHOW")) {
					httpServletRequest.setAttribute("type", "SHOW");
				}
				for (int i = 0; i < page.getResult().size(); i++) {
					((PrpLverifyLoss)page.getResult().get(i)).setEditType(editType);
				}
				this.writeJSONData(page, "id", "policyNo", "handlerCode", "defLossDate", "operateDate", "status", "riskCode", "lossItemName", "relatepolicyNo", "editType");
				return NONE;
			} catch (Exception ex) {
				ex.printStackTrace();
				this.writeJSONMsg(ex.getMessage());
			}
		}
		if (editType.equals("Certify")) {
			// 查询定损信息,整理输入，用於初始界面显示
			this.daaCertainLossViewHelper.setPrpLcertainLossDtoToView(httpServletRequest, workFlowQueryDto);
			return "certifyUpload";
		}
		return NONE;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public CertainLossService getCertainLossService() {
		return certainLossService;
	}

	public void setCertainLossService(CertainLossService certainLossService) {
		this.certainLossService = certainLossService;
	}

	public void setRegistNoSign(String registNoSign) {
		this.RegistNoSign = registNoSign;
	}

	public void setRegistNo(String registNo) {
		this.RegistNo = registNo;
	}

	public void setPolicyNoSign(String policyNoSign) {
		this.PolicyNoSign = policyNoSign;
	}

	public void setPolicyNo(String policyNo) {
		this.PolicyNo = policyNo;
	}

	public void setLicenseNoSign(String licenseNoSign) {
		this.LicenseNoSign = licenseNoSign;
	}

	public String getLicenseNo() {
		return this.LicenseNo;
	}
	
	public void setLicenseNo(String licenseNo) {
		this.LicenseNo = licenseNo;
	}

	public void setOperateDateSign(String operateDateSign) {
		this.OperateDateSign = operateDateSign;
	}

	public void setOperateDate(String operateDate) {
		this.OperateDate = operateDate;
	}

	public void setInsuredNameSign(String insuredNameSign) {
		this.InsuredNameSign = insuredNameSign;
	}
	
	public String getInsuredName() {
		return this.InsuredName;
	}

	public void setInsuredName(String insuredName) {
		this.InsuredName = insuredName;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public DAACertainLossViewHelper getDaaCertainLossViewHelper() {
		return daaCertainLossViewHelper;
	}

	public void setDaaCertainLossViewHelper(DAACertainLossViewHelper daaCertainLossViewHelper) {
		this.daaCertainLossViewHelper = daaCertainLossViewHelper;
	}
}
