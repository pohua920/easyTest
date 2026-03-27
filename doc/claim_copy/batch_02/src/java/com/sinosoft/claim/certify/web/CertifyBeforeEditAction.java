/*
 * @(#)CertifyBeforeEditGetAction.java	Jan 23, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.certify.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.certify.util.DAACertifyViewHelper;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.util.BusinessRuleUtil;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;

import ins.framework.web.Struts2Action;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class CertifyBeforeEditAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	/** 编辑类型 */
	private String editType = "";
	/** 节点类型 */
	private String nodeType = "";
	/** swfLog的联合主键 */
	private String swfLogFlowID = null;
	private String swfLogLogNo = null;
	/** 备案号码 */
	private String registNo = "";
	/** 保单号码 */
	private String policyNo = "";
	/** 险种 */
	private String riskCode = "";
	/** 单证viewHelper */
	private DAACertifyViewHelper daaCertifyViewHelper = null;
	private String ifclose = "";
	/** 状态 */
	private String status = "";
	private String editTypeOther = "";
	/** url参数 */
	private String core_URL = "";
	/** 代码翻译service */
	private CodeService codeService;
	/** 工作流service*/
	private WorkFlowService workFlowService;

	/**
	 * 单证查询前处理信息
	 * @return
	 * @throws Exception
	 */
	public String certifyBeforeEdit() throws Exception {
		String forward = "";
		// 业务类型：ADD-新增 EDIT-修改 SHOW-显示
		HttpServletRequest httpServletRequest = getRequest();
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user"); // 用户信息
		editType = httpServletRequest.getParameter("editType");
		// 尚未加入type异常处理{}、其它必须参数异常处理{}
		if (editType.equals("ADD")) {
			// 查询立案信息,整理输入，用於初始界面显示
			registNo = httpServletRequest.getParameter("RegistNo"); // 赔案号
			// ==================================================
			// 进行占号分析，只有当有工作流号码和LogNo的时候才能使用。
			// 如果没有flowID和logno则不进行判断。
			if (swfLogFlowID != null && swfLogLogNo != null) {
				SwfLog swfLogDto = this.getWorkFlowService().holdNode(swfLogFlowID, Integer.parseInt(swfLogLogNo), user.getUserCode(), user.getUserName());
				if (swfLogDto.getHoldNode() == false) {
					//案件'	'已經被代碼:'	',名稱:'		'的用戶所佔用,請選擇其它案件進行處理!
					String msg = getText("prompt.certify.case") + registNo + getText("prompt.certify.alreadyByCode") + swfLogDto.getHandlerCode() + getText("prompt.certify.codeName") + swfLogDto.getHandlerName() + getText("prompt.certify.userOperating");
					throw new UserException(1, 3, getText("prompt.certify.workFlow"), msg);//工作流
				}
			}
			// ===============================================
			riskCode = BusinessRuleUtil.getRiskCode(registNo, "RegistNo");
			daaCertifyViewHelper.certifyDtoToView(httpServletRequest, registNo, nodeType);
		}
		// 当编辑的时候
		if (editType.equals("EDIT") || editType.equals("SHOW") || editType.equals("DELETE")) {
			// 查询单证信息,整理输入，用於初始界面显示
			registNo = httpServletRequest.getParameter("prpLcertifyCertifyNo"); // 单证号
			riskCode = BusinessRuleUtil.getRiskCode(registNo, "RegistNo");
			daaCertifyViewHelper.certifyDtoToView(httpServletRequest, registNo, nodeType);
		}
		// 当处理索赔清单的时候
		if (editType.equals("CertifyDirect")) {
			// 查询单证信息,整理输入，用於初始界面显示
			registNo = httpServletRequest.getParameter("RegistNo"); // 单证号
			riskCode = BusinessRuleUtil.getRiskCode(registNo, "RegistNo");
			daaCertifyViewHelper.certifyDtoToView(httpServletRequest, registNo, nodeType);
			httpServletRequest.setAttribute("riskCode", riskCode);
			String strRiskType = codeService.translateRiskCodetoRiskType(riskCode);
			if ("D".equals(strRiskType)) {
				return "ADDCertifyDirect";
			} else {
				return "ADDOtherCertifyDirect";
			}
		}
		// 当打印索赔清单的时候
		if (editType.equals("CertifyDirectPrint")) {
			// 查询单证信息,整理输入，用於初始界面显示
			registNo = httpServletRequest.getParameter("RegistNo"); // 单证号
			riskCode = BusinessRuleUtil.getRiskCode(registNo, "RegistNo");
			daaCertifyViewHelper.certifyDtoToView(httpServletRequest, registNo, nodeType);
			// reason:非车险打印索赔须知清单,判断险种
			String strRiskType = codeService.translateRiskCodetoRiskType(riskCode);
			String strRiskName = codeService.translateRiskCode(riskCode, true);
			daaCertifyViewHelper.showView(httpServletRequest, "CertifyDirectPrint");
			if ("D".equals(strRiskType)) {
				return "CertifyDirectPrint";
			} else {
				httpServletRequest.setAttribute("riskName", strRiskName);
				return "OtherCertifyDirectPrint";
			}
		}
		// 当打印索赔清单的时候
		if (editType.equals("CertifyDirectPrintAdd")) {
			// 查询单证信息,整理输入，用於初始界面显示
			registNo = httpServletRequest.getParameter("RegistNo"); // 单证号
			riskCode = BusinessRuleUtil.getRiskCode(registNo, "RegistNo");
			daaCertifyViewHelper.certifyDtoToView(httpServletRequest, registNo, nodeType);
			// reason:非车险打印索赔须知清单,判断险种
			String strRiskType = codeService.translateRiskCodetoRiskType(riskCode);
			String strRiskName = codeService.translateRiskCode(riskCode, true);
			if ("D".equals(strRiskType)) {
				return "CertifyDirectPrintAdd";
			} else {
				httpServletRequest.setAttribute("riskName", strRiskName);
				return "OtherCertifyDirectPrintAdd";
			}
		}

		// 未处理单证任务的放弃处理
		if (editType.equals("GIVUP")) {
			// add by huangyunzhong 20051226 放弃未暂存和提交的单证任务，删去结点操作人，使其他人可见可处理
			String FlowID = httpServletRequest.getParameter("swfLogFlowID");
			int LogNo = Integer.parseInt((String) httpServletRequest.getParameter("swfLogLogNo"));
			SwfLog swfLogDto = this.getWorkFlowService().findNodeByPrimaryKey(FlowID, LogNo);
			if (swfLogDto.getNodeType().equals("certi")) {
				swfLogDto.setHandlerCode("");
				swfLogDto.setHandlerName("");
				swfLogDto.setFlowStatus("1");
			}
			this.getWorkFlowService().updateFlow(swfLogDto);
			this.clearErrorsAndMessages();
			this.addActionMessage(this.getText("button.giveUpTask.value"));
			this.addActionMessage(this.getText("prompt.queRegist.RegistNo"));
			this.addActionMessage(swfLogDto.getRegistNo());
			forward = "success";
			return forward;
		}
		// 取得forward
		forward = BusinessRuleUtil.getForward(httpServletRequest, riskCode, "certi", editType, 1);
		// 页面上的代码整理到这个方法当中s
		daaCertifyViewHelper.showView(httpServletRequest, forward);
		return forward;
	}

	public DAACertifyViewHelper getDaaCertifyViewHelper() {
		return daaCertifyViewHelper;
	}

	public void setDaaCertifyViewHelper(DAACertifyViewHelper daaCertifyViewHelper) {
		this.daaCertifyViewHelper = daaCertifyViewHelper;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public String getRegistNo() {
		return registNo;
	}

	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public String getSwfLogFlowID() {
		return swfLogFlowID;
	}

	public void setSwfLogFlowID(String swfLogFlowID) {
		this.swfLogFlowID = swfLogFlowID;
	}

	public String getSwfLogLogNo() {
		return swfLogLogNo;
	}

	public void setSwfLogLogNo(String swfLogLogNo) {
		this.swfLogLogNo = swfLogLogNo;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getIfclose() {
		return ifclose;
	}

	public void setIfclose(String ifclose) {
		this.ifclose = ifclose;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEditTypeOther() {
		return editTypeOther;
	}

	public void setEditTypeOther(String editTypeOther) {
		this.editTypeOther = editTypeOther;
	}

	public String getCore_URL() {
		if (core_URL == null || "".equals(core_URL)) {
			try {
				core_URL = AppConfig.get("sysconst.Core_URL");
			} catch (Exception e) {
				core_URL = "";
			}
		}
		return core_URL;
	}

	public void setCore_URL(String core_URL) {
		this.core_URL = core_URL;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

}
