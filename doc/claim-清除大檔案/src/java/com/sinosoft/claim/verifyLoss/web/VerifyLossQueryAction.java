package com.sinosoft.claim.verifyLoss.web;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.util.StringConvert;
import com.sinosoft.claim.schema.model.PrpLverifyLoss;
import com.sinosoft.claim.verifyLoss.util.DAAVerifyLossViewHelper;
import com.sinosoft.claim.workflow.vo.WorkFlowQueryDto;
import com.sinosoft.sysframework.reference.AppConfig;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;
/**
 * @ClassName VerifyLossEditAction
 * @Description 车险理赔核损查询
 * @author 中科软
 */
@SuppressWarnings({ "unchecked", "serial" })
public class VerifyLossQueryAction extends Struts2Action {
	private String RegistNoSign;
	/**报案号码*/
	private String RegistNo;
	private String PolicyNoSign;
	/**保单号码*/
	private String PolicyNo;
	private String LicenseNoSign;
	/**车牌号码*/
	private String LicenseNo;
	private String OperateDateSign;
	/**处理时间*/
	private String OperateDate;
	private String InsuredNameSign;
	/**被保险人*/
	private String InsuredName;
	/**状态*/
	private String status;
	/**编辑类型*/
	private String editType = "SHOW";
	/**节点类型*/
	private String nodeType = "verif";
	/**立案号码*/
	private String ClaimNo;
	/**核损数据收集*/
	private DAAVerifyLossViewHelper daaVerifyLossViewHelper;

	/**
	 * 查询核损信息
	 * @return 无
	 * @throws Exception
	 */
	public String verifyLossQuery() throws Exception {
		HttpServletRequest httpServletRequest = super.getRequest();
		// 业务类型：ADD-新增 EDIT-修改 SHOW-显示
		String claimNo = httpServletRequest.getParameter("ClaimNo"); // 赔案号
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
		workFlowQueryDto.setNodeType(nodeType);
		workFlowQueryDto.setClaimNo(claimNo);
		// 尚未加入type异常处理{}、其它必须参数异常处理{}
		if (editType.equals("ADD") || editType.equals("EDIT") || editType.equals("SHOW")) {
			// 查询核损信息,整理输入，用於初始界面显示
			String recordPerPage = AppConfig.get("sysconst.ROWS_PERPAGE");
			String pageNo = httpServletRequest.getParameter("pageNo");
			if (pageNo == null) {
				pageNo = "1";
			}
			int intRecordPerPage = Integer.parseInt(recordPerPage);
			int intPageNo = Integer.parseInt(pageNo);
			try {
				Page page = this.daaVerifyLossViewHelper.setPrpLverifyLossDtoToView(httpServletRequest, workFlowQueryDto, intPageNo, intRecordPerPage);
				if (editType.equals("SHOW")) {
					httpServletRequest.setAttribute("type", "SHOW");
				}
				this.writeJSONData(page, "id", "policyNo", "handlerCode","underWriteCode","lossItemName", "underWriteEndDate", "status", "riskCode", "relatepolicyNo", "editType", "insureCarFlag");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return NONE;
	}

	public String getRegistNoSign() {
		return RegistNoSign;
	}

	public void setRegistNoSign(String registNoSign) {
		RegistNoSign = registNoSign;
	}

	public String getRegistNo() {
		return RegistNo;
	}

	public void setRegistNo(String registNo) {
		RegistNo = registNo;
	}

	public String getPolicyNoSign() {
		return PolicyNoSign;
	}

	public void setPolicyNoSign(String policyNoSign) {
		PolicyNoSign = policyNoSign;
	}

	public String getPolicyNo() {
		return PolicyNo;
	}

	public void setPolicyNo(String policyNo) {
		PolicyNo = policyNo;
	}

	public String getLicenseNoSign() {
		return LicenseNoSign;
	}

	public void setLicenseNoSign(String licenseNoSign) {
		LicenseNoSign = licenseNoSign;
	}

	public String getLicenseNo() {
		return LicenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		LicenseNo = licenseNo;
	}

	public String getOperateDateSign() {
		return OperateDateSign;
	}

	public void setOperateDateSign(String operateDateSign) {
		OperateDateSign = operateDateSign;
	}

	public String getOperateDate() {
		return OperateDate;
	}

	public void setOperateDate(String operateDate) {
		OperateDate = operateDate;
	}

	public String getInsuredNameSign() {
		return InsuredNameSign;
	}

	public void setInsuredNameSign(String insuredNameSign) {
		InsuredNameSign = insuredNameSign;
	}

	public String getInsuredName() {
		return InsuredName;
	}

	public void setInsuredName(String insuredName) {
		InsuredName = insuredName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public DAAVerifyLossViewHelper getDaaVerifyLossViewHelper() {
		return daaVerifyLossViewHelper;
	}

	public void setDaaVerifyLossViewHelper(DAAVerifyLossViewHelper daaVerifyLossViewHelper) {
		this.daaVerifyLossViewHelper = daaVerifyLossViewHelper;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getClaimNo() {
		return ClaimNo;
	}

	public void setClaimNo(String claimNo) {
		ClaimNo = claimNo;
	}
}
