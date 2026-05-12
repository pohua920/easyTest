package com.sinosoft.claim.check.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.check.service.facade.AcciCheckService;
import com.sinosoft.claim.check.util.DAACheckViewHelper;
import com.sinosoft.claim.check.util.PrpLacciCheckViewHelper;
import com.sinosoft.claim.claim.service.facade.ClaimService;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.util.StringConvert;
import com.sinosoft.claim.regist.service.facade.RegistService;
import com.sinosoft.claim.schema.model.PrpLacciCheck;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.workflow.vo.WorkFlowQueryDto;
import com.sinosoft.sysframework.reference.AppConfig;
/**
 * 查勘查询处理
 * @author 中科软
 *
 */
public class CheckQueryAction extends Struts2Action {
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
	private String caseFlag;
	/** 流程状态*/
	private String status;
	/** 报案service*/
	private RegistService registService;
	/** 代码Service */
	private CodeService codeService;
	/** 立案Service */
	private ClaimService claimService;
	/** 意健险查勘Service */
	private AcciCheckService acciCheckService;
	/** 立案Service */
	private PrpLclaimService prpLclaimService;
	/** 报案主表Service */
	private PrpLregistService prpLregistService;
	/** 车险查勘ViewHelper */
	private DAACheckViewHelper daaCheckViewHelper;
	/**
	 * 意见险调查viewhelper
	 */
	private PrpLacciCheckViewHelper prpLacciCheckViewHelper;

	/***
	 * 查勘查询主函数
	 * @return
	 * @throws Exception
	 */
	public String query() throws Exception {
		HttpServletRequest httpServletRequest = getRequest();
		// 业务类型：ADD-新增 EDIT-修改 SHOW-显示
		if (pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == 0) {
			pageSize = Integer.parseInt(AppConfig.get("sysconst.ROWS_PERPAGE"));
		}
		status = httpServletRequest.getParameter("caseFlag");// 案件状态
		// 去掉status中最後一个逗号
		if (status != null && status.trim().length() > 0) {
			status = status.substring(0, status.length() - 1);
		}
		WorkFlowQueryDto workFlowQueryDto = new WorkFlowQueryDto();
		workFlowQueryDto.setClaimNo(ClaimNo);
		workFlowQueryDto.setPolicyNo(PolicyNo);
		workFlowQueryDto.setRegistNo(RegistNo);
		workFlowQueryDto.setLicenseNo(StringConvert.getParam(httpServletRequest,"LicenseNo",ConstantCodes.YUI_CHARSET));
		workFlowQueryDto.setStatus(status);
		workFlowQueryDto.setOperateDate(OperateDate);
		workFlowQueryDto.setInsuredName(StringConvert.getParam(httpServletRequest,"InsuredName",ConstantCodes.YUI_CHARSET));
		workFlowQueryDto.setRegistNoSign(RegistNoSign);
		workFlowQueryDto.setPolicyNoSign(PolicyNoSign);
		workFlowQueryDto.setClaimNoSign(ClaimNoSign);
		workFlowQueryDto.setLicenseNoSign(LicenseNoSign);
		workFlowQueryDto.setOperateDateSign(OperateDateSign);
		workFlowQueryDto.setInsuredNameSign(InsuredNameSign);
		String forward = ""; // 向前
		// 尚未加入type异常处理{}、其它必须参数异常处理{}
		if (editType.equals("ADD") || editType.equals("EDIT") || editType.equals("SHOW")) {
			// 查询查勘信息,整理输入，用於初始界面显示
			try {
				Page page = daaCheckViewHelper.setPrpLcheckToView(httpServletRequest, workFlowQueryDto, pageNo, pageSize);
				this.writeJSONData(page, "status", "id", "editType", "riskCode", "checkNo", "relatepolicyNo", "checker1", "operateDate");
			} catch (Exception ex) {
				ex.printStackTrace();
				this.writeJSONMsg(ex.getMessage());
			}
			return NONE;
		}
		if (editType.equals("DELETE")) {
			daaCheckViewHelper.setPrpLcheckToView(httpServletRequest, workFlowQueryDto);
			forward = "deleteSuccess";
		}
		if (editType.equals("Certify")) {
			// 查询查勘信息,整理输入，用於初始界面显示
			daaCheckViewHelper.setPrpLcheckToView(httpServletRequest, workFlowQueryDto);
			forward = "certifyUpload";
		}
		// 打印调查报告前,先查出所有的调查号,显示成调查列表.
		if (editType.equals("PRINT")) {
			String comditions = " registNo='" + RegistNo + "'";
			List<PrpLacciCheck> prpLacciCheckList = acciCheckService.findByConditionsAcciCheck(comditions);
			httpServletRequest.setAttribute("prpLacciCheckDtoList", prpLacciCheckList);
			forward = "acciCheckList";
		}
		return forward;
	}
	
	/**
	 * 历史调查记录查询
	 * @return
	 * @throws Exception
	 */
	public String acciCheckQuery() throws Exception {
		HttpServletRequest httpServletRequest = getRequest();
		// 业务类型：ADD-新增 EDIT-修改 SHOW-显示
		String editType = httpServletRequest.getParameter("editType");
		String registNo = httpServletRequest.getParameter("RegistNo"); // 报案号
		// //车牌号
		String status = httpServletRequest.getParameter("caseFlag"); // 案件状态

		String forward = ""; // 向前
		// 整理查询条件中的状态去掉status中最后一个逗号
		if (status != null && status.trim().length() > 0) {
			status = status.substring(0, status.length() - 1);
		}
		// 尚未加入type异常处理{}、其它必须参数异常处理{}
		// 1。报案一般的查询，查询理赔节点状态信息,整理输入，用于初始界面显示
		if (editType.equals("ADD") || editType.equals("EDIT") || editType.equals("SHOW")) {
			forward = "target1";
		}else if (editType.equals("LacciCheckBeforeQuery")) {
			// 需要进行翻页处理
			// 每页显示的行数
			String recordPerPage = httpServletRequest.getParameter("pageSize");
			String pageNo = httpServletRequest.getParameter("pageNo");
			Page page = prpLacciCheckViewHelper.policyListToView(httpServletRequest, registNo, pageNo, recordPerPage);
			this.writeJSONData(page, "checkContext","certiType","checkObject","checkNature","checkDate","checkEndDate","checkerCode");
			forward = NONE;
		}
		return forward;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public ClaimService getClaimService() {
		return claimService;
	}

	public void setClaimService(ClaimService claimService) {
		this.claimService = claimService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public String getClaimNoSign() {
		return ClaimNoSign;
	}

	public void setClaimNoSign(String claimNoSign) {
		ClaimNoSign = claimNoSign;
	}

	public String getClaimNo() {
		return ClaimNo;
	}

	public void setClaimNo(String claimNo) {
		ClaimNo = claimNo;
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

	public String getCaseFlag() {
		return caseFlag;
	}

	public void setCaseFlag(String caseFlag) {
		this.caseFlag = caseFlag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public RegistService getRegistService() {
		return registService;
	}

	public void setRegistService(RegistService registService) {
		this.registService = registService;
	}

	public AcciCheckService getAcciCheckService() {
		return acciCheckService;
	}

	public void setAcciCheckService(AcciCheckService acciCheckService) {
		this.acciCheckService = acciCheckService;
	}

	public DAACheckViewHelper getDaaCheckViewHelper() {
		return daaCheckViewHelper;
	}

	public void setDaaCheckViewHelper(DAACheckViewHelper daaCheckViewHelper) {
		this.daaCheckViewHelper = daaCheckViewHelper;
	}

	public PrpLacciCheckViewHelper getPrpLacciCheckViewHelper() {
		return prpLacciCheckViewHelper;
	}

	public void setPrpLacciCheckViewHelper(PrpLacciCheckViewHelper prpLacciCheckViewHelper) {
		this.prpLacciCheckViewHelper = prpLacciCheckViewHelper;
	}
}
