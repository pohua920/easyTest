package com.sinosoft.claim.verifyLoss.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.util.BusinessRuleUtil;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.ui.control.action.UIQuickCaseAction;
import com.sinosoft.claim.verifyLoss.util.DAAVerifyLossViewHelper;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;
import ins.framework.web.Struts2Action;

/**
 * @ClassName VerifyLossBeforeEditAction
 * @Description 车险理赔核损前查询保单信息
 * @author 中科软
 */
@SuppressWarnings("serial")
public class VerifyLossBeforeEditAction extends Struts2Action {
	/**险种*/
	private String riskCode;
	/**主车标志*/
	private String insureCarFlag;
	/**损失标的序号*/
	private String lossItemCode;
	/**业务类型：ADD-新增 EDIT-修改 SHOW-显示*/
	private String editType;
	/**定损报案号*/
	private String prpLverifyLossRegistNo;
	/**报案号*/
	private String RegistNo;
	/**操作执行後，显示的信息*/
	private String message;
	/**节点*/
	private String nodeType;
	/**保单号*/
	private String policyNo;
	/**核损viewhelper*/
	private DAAVerifyLossViewHelper daaVerifyLossViewHelper;
	/**保单service*/
	private PolicyService policyService;
	/**工作流service*/
	private WorkFlowService workFlowService;


	/**
	 * 核算前信息处理
	 * @return
	 * @throws Exception
	 */
	public String verifyLossBeforeEdit() throws Exception {
		HttpServletRequest httpServletRequest = super.getRequest();
		HttpServletResponse httpServletResponse = super.getResponse();
		String registNo = ""; // 报案号
		String forward = ""; // 向前
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user"); // 用户信息
		String tempStatus = httpServletRequest.getParameter("status");
		int intLossItemCode = Integer.parseInt(DataUtils.nullToZero(lossItemCode));// 险种
		httpServletRequest.setAttribute("editType", editType);
		try {
			// 尚未加入type异常处理{}、其它必须参数异常处理{}
			if ("ADD".equals(editType)) {
				// 查询立案信息,整理输入，用於初始界面显示
				registNo = RegistNo; // 报案号
				// reason:增加对简易赔案的判断
				UIQuickCaseAction uiQuickCaseAction = new UIQuickCaseAction();
				// 後续程序是否执行，赠加控制
				boolean blFwd = uiQuickCaseAction.checkQuickCaseAndForwadToSHOW(registNo, httpServletResponse);
				if (blFwd) {
					return NONE;
				}
				String conditions = "POLICYNO = '" + policyNo + "'";
				int payFee = this.getPolicyService().checkPay(conditions);
				httpServletRequest.setAttribute("payFee", String.valueOf(payFee));
				// 进行占号分析，只有当有工作流号码和LogNo的时候才能使用。
				// 如果没有flowID和logNo则不进行判断。
				String flowID = httpServletRequest.getParameter("swfLogFlowID");
				String logNo = httpServletRequest.getParameter("swfLogLogNo");
				String msg = "";
				if (!"".equals(DataUtils.dbNullToEmpty(flowID)) && !"".equals(DataUtils.dbNullToEmpty(logNo))) {
					SwfLog swfLogDto = this.getWorkFlowService().holdNode(flowID, Integer.parseInt(logNo), user.getUserCode(), user.getUserName());
					if (swfLogDto.getHoldNode() == false) {
						msg = "案件'" + registNo + "'已經被代碼:'" + swfLogDto.getHandlerCode() + "',名稱:'" + swfLogDto.getHandlerName() + "'的用戶所佔用,請選擇其它案件進行處理!";
						throw new UserException(1, 3, "工作流", msg);
					}
				}
				this.daaVerifyLossViewHelper.certainLossDtoToView(httpServletRequest, registNo, editType);
				forward = editType + riskCode;
			}
			if (editType.equals("EDIT") || editType.equals("SHOW")) {
				// 查询核损信息,整理输入，用於初始界面显示
				registNo = prpLverifyLossRegistNo; // 报案号
				// reason:增加对简易赔案的判断
				UIQuickCaseAction uiQuickCaseAction = new UIQuickCaseAction();
				// 後续程序是否执行，赠加控制
				boolean blFwd = uiQuickCaseAction.checkQuickCaseAndForwadToSHOW(registNo, httpServletResponse);
				if (blFwd) {
					return NONE;
				}
				String policyNo = (String) httpServletRequest.getParameter("policyNo");
				String conditions = "POLICYNO = '" + policyNo + "'";
				int payFee = this.getPolicyService().checkPay(conditions);
				httpServletRequest.setAttribute("payFee", String.valueOf(payFee));
				forward = editType + riskCode;
				this.daaVerifyLossViewHelper.verifyLossDtoView(httpServletRequest, registNo, editType, tempStatus);
			}
			// reason:区分回勘
			// 取得forward
			String nodeType = httpServletRequest.getParameter("nodeType");
			if (nodeType != null && nodeType.equals("backc")) {
				forward = "BackCDAA";
			} else {
				forward = BusinessRuleUtil.getForward(httpServletRequest, riskCode, nodeType, editType, intLossItemCode);
			}
			if (editType.equals("SelectLossType")) {
				forward = "SelectLossType";
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
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
		RegistNo = registNo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public DAAVerifyLossViewHelper getDaaVerifyLossViewHelper() {
		return daaVerifyLossViewHelper;
	}

	public void setDaaVerifyLossViewHelper(DAAVerifyLossViewHelper daaVerifyLossViewHelper) {
		this.daaVerifyLossViewHelper = daaVerifyLossViewHelper;
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
