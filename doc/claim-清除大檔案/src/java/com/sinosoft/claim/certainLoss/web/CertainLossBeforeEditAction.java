package com.sinosoft.claim.certainLoss.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ins.framework.web.Struts2Action;

import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.claim.certainLoss.util.DAACertainLossViewHelper;
import com.sinosoft.claim.check.util.DAACheckViewHelper;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.util.BusinessRuleUtil;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.ui.control.action.UIQuickCaseAction;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;

/**
 * 分发HTTP GET 车险理赔定损前查询保单请求
 * <p>
 * Title: 车险理赔定损前查询保单信息
 * </p>
 * <p>
 * Description: 车险理赔定损前查询保单信息系统样本程序
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * author 中科软
 * 原：UILCertainLossBeforeEditGetFacade.java
 */
@SuppressWarnings({ "serial" })
public class CertainLossBeforeEditAction extends Struts2Action {
	/** 险种 */
	private String riskCode;
	/** 主车标志 */
	private String insureCarFlag;
	/** 损失标的序号 */
	private String lossItemCode;
	/** 损失标的车牌号码 */
	private String lossItemName;
	/** 编辑类型 */
	private String editType;
	/** 定损报案号 */
	private String prpLverifyLossRegistNo;
	/** 报案号*/
	private String RegistNo;
	/** 操作执行後，显示的信息*/
	private String message;
	/** 代码翻译service*/
	private CodeService codeService;
	/** 定损viewHelper*/
	private DAACertainLossViewHelper daaCertainLossViewHelper;
	/** 查勘viewHelper*/
	private DAACheckViewHelper daaCheckViewHelper;
	/** 保单service*/
	private PolicyService policyService;
	/** 工作流service*/
	private WorkFlowService workFlowService;
	
    /**
     * 定损前处理
     * @return
     * @throws Exception
     */
	public String certainLossBeforeEdit() throws Exception {
		HttpServletRequest httpServletRequest = super.getRequest();
		HttpServletResponse httpServletResponse = super.getResponse();
		this.clearErrorsAndMessages();
		String nodeType = httpServletRequest.getParameter("nodeType");
		httpServletRequest.setAttribute("nodeType",nodeType);
		// 业务类型：ADD-新增 EDIT-修改 SHOW-显示
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user"); // 用户信息
		if (user == null) {
			user = new UserDto();
		}
		// 定损类型
		String forward = ""; // 向前
		int intLossItemCode = Integer.parseInt(DataUtils.nullToZero(lossItemCode)); // 险种
		// 查询车型
		if ("findCarModel".equals(editType)) {
			this.daaCertainLossViewHelper.prpDcarModelDtoToView(httpServletRequest);
			return "findCarModel";
		}
		// 当不是主车並且是从待处理任务进入此action的时候，不进定损类型选择画面，直接进入修理换件画面
		// 尚未加入type异常处理{}、其它必须参数异常处理{}
		if ("ADD".equals(editType)) {
			// 查询立案信息,整理输入，用於初始界面显示
			RegistNo = httpServletRequest.getParameter("RegistNo"); // 报案号
			if (RegistNo == null || RegistNo.trim().length() < 1) {
				RegistNo = prpLverifyLossRegistNo; // 报案号
			}
			if (riskCode == null || riskCode.length() < 1 || riskCode.trim().equals("")) {
				riskCode = BusinessRuleUtil.getRiskCode(RegistNo, "RegistNo");
			}
			// 增加简易赔案的数据判断,如果是的话，跳转到简易赔案的查看
			UIQuickCaseAction uiQuickCaseAction = new UIQuickCaseAction();
			// 增加控制跳转到简易赔案页面，後续程序无须在执行
			boolean blFwd = uiQuickCaseAction.checkQuickCaseAndForwadToSHOW(RegistNo, httpServletResponse);
			if (blFwd) {
				return "";
			}
			// add by liping 080709
			String policyNo = (String) httpServletRequest.getParameter("policyNo");
			String conditions = "POLICYNO = '" + policyNo + "'";
			int payFee = this.policyService.checkPay(conditions);
			httpServletRequest.setAttribute("payFee", String.valueOf(payFee));
			// 进行占号分析，只有当有工作流号码和LogNo的时候才能使用。
			// 如果没有flowID和logno则不进行判断。
			String flowID = httpServletRequest.getParameter("swfLogFlowID");
			String logNo = httpServletRequest.getParameter("swfLogLogNo");
			if (!"".equals(DataUtils.dbNullToEmpty(flowID)) && !"".equals(DataUtils.dbNullToEmpty(logNo))) {
				SwfLog swfLogDto = this.getWorkFlowService().holdNode(flowID, Integer.parseInt(logNo), user.getUserCode(), user.getUserName());
				if (swfLogDto.getHoldNode() == false) {
					String msg = "案件'" + RegistNo + "'已經被代碼:'" + swfLogDto.getHandlerCode() + "',名稱:'" + swfLogDto.getHandlerName() + "'的用戶所佔用,請選擇其它案件進行處理!";
					throw new UserException(1, 3, "工作流", msg);
				}
			}
			// ===============================================
			// 已经到核价、核损的案件不能在处理
			boolean verifyFlag = false;
			boolean veripFlag = false;
			// 非车险的不需要核价、核损，所以也就不用检查了
			String strRiskType = this.codeService.translateRiskCodetoRiskType(riskCode);
			if ("D".equals(strRiskType)) {
				veripFlag = this.daaCertainLossViewHelper.checkVerifyLoss(httpServletRequest, RegistNo, lossItemCode, "verip");
				if (veripFlag == true) {
					this.addActionMessage("此案件已經到核價，請通知核價操作員退回此案件才能繼續處理");
					return "success";
				}
				verifyFlag = this.daaCertainLossViewHelper.checkVerifyLoss(httpServletRequest, RegistNo, lossItemCode, nodeType);
				if (verifyFlag == true) {
					this.addActionMessage("此案件已經到核損，請通知核損操作員退回此案件才能繼續處理");
					return "success";
				}
			}
			this.daaCheckViewHelper.registDtoToView(httpServletRequest, RegistNo, "ADD");
			this.daaCertainLossViewHelper.registDtoToView(httpServletRequest, RegistNo, editType);
		}
		if (editType.equals("EDIT") || editType.equals("SHOW")) {
			// 查询定损信息,整理输入，用於初始界面显示
			RegistNo = httpServletRequest.getParameter("prpLverifyLossRegistNo"); // 报案号
			// 增加简易赔案的数据判断,如果是的话，跳转到简易赔案的查看
			UIQuickCaseAction uiQuickCaseAction = new UIQuickCaseAction();
			// 增加控制跳转到简易赔案页面，後续程序无须在执行
			boolean blFwd = uiQuickCaseAction.checkQuickCaseAndForwadToSHOW(RegistNo, httpServletResponse);
			if (blFwd) {
				return "";
			}
			String policyNo = (String) httpServletRequest.getParameter("policyNo");
			String conditions = "POLICYNO = '" + policyNo + "'";
			int payFee = policyService.checkPay(conditions);
			httpServletRequest.setAttribute("payFee", String.valueOf(payFee));
			if (RegistNo == null || RegistNo.trim().length() < 1) {
				RegistNo = httpServletRequest.getParameter("RegistNo"); // 报案号
			}
			if (riskCode == null || riskCode.length() < 1 || riskCode.trim().equals("")) {
				riskCode = BusinessRuleUtil.getRiskCode(RegistNo, "RegistNo");
			}
			this.daaCheckViewHelper.registDtoToView(httpServletRequest, RegistNo, "ADD");
			try {
				this.daaCertainLossViewHelper.certainLossDtoView(httpServletRequest, RegistNo, editType);
			} catch (Exception e) {
				forward = "failure";
				throw e;
			}

		}
		String nodeStatus = httpServletRequest.getParameter("status");
		int count = 0;
		if ("3".equals(nodeStatus)) {
			count = 1;
		}
		if (count == 0) {
			httpServletRequest.setAttribute("prplCertianLossFirst", "First");
		} else {
			httpServletRequest.setAttribute("prplCertianLossFirst", "NotFirst");
		}
		// 已暂存查勘任务的放弃处理
		if (editType.equals("giveupTemporarySave")) {
			String FlowID = httpServletRequest.getParameter("swfLogFlowID");
			int LogNo = Integer.parseInt((String) httpServletRequest.getParameter("swfLogLogNo"));
			SwfLog swfLogDto = this.getWorkFlowService().findNodeByPrimaryKey(FlowID, LogNo);
			if ("certa".equals(swfLogDto.getNodeType())) {
				swfLogDto.setHandlerCode("");
				swfLogDto.setHandlerName("");
				swfLogDto.setNodeStatus("0");
				swfLogDto.setFlowStatus("1");
			}
			this.getWorkFlowService().updateFlow(swfLogDto);
			this.addActionMessage("任務已經放棄");
			return "success";
		}
		if (forward != "failure") {
			forward = BusinessRuleUtil.getForward(httpServletRequest, riskCode, nodeType, editType, intLossItemCode);
		}
		if (editType.equals("SelectLossType")) {
			forward = "SelectLossType";
		}
		return forward;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public String getInsureCarFlag() {
		return insureCarFlag;
	}

	public void setInsureCarFlag(String insureCarFlag) {
		this.insureCarFlag = insureCarFlag;
	}

	public String getLossItemCode() {
		return lossItemCode;
	}

	public void setLossItemCode(String lossItemCode) {
		this.lossItemCode = lossItemCode;
	}

	public String getLossItemName() {
		return lossItemName;
	}

	public void setLossItemName(String lossItemName) {
		this.lossItemName = lossItemName;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public String getPrpLverifyLossRegistNo() {
		return prpLverifyLossRegistNo;
	}

	public void setPrpLverifyLossRegistNo(String prpLverifyLossRegistNo) {
		this.prpLverifyLossRegistNo = prpLverifyLossRegistNo;
	}

	public String getRegistNo() {
		return RegistNo;
	}

	public void setRegistNo(String registNo) {
		this.RegistNo = registNo;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public DAACertainLossViewHelper getDaaCertainLossViewHelper() {
		return daaCertainLossViewHelper;
	}

	public void setDaaCertainLossViewHelper(DAACertainLossViewHelper daaCertainLossViewHelper) {
		this.daaCertainLossViewHelper = daaCertainLossViewHelper;
	}

	public DAACheckViewHelper getDaaCheckViewHelper() {
		return daaCheckViewHelper;
	}

	public void setDaaCheckViewHelper(DAACheckViewHelper daaCheckViewHelper) {
		this.daaCheckViewHelper = daaCheckViewHelper;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}
}
