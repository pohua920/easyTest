package com.sinosoft.claim.endcase.web;

import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.dto.domain.PrpLquickCaseDto;
import com.sinosoft.claim.endcase.util.DAAEndcaseViewHelper;
import com.sinosoft.claim.endcase.util.ReCaseViewHelper;
import com.sinosoft.claim.schema.service.facade.PrpLverifyLossService;
import com.sinosoft.claim.ui.control.action.UIQuickCaseAction;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.sysframework.exceptionlog.UserException;

public class ReCaseBeforeEditAction extends Struts2Action {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**结案数据收集*/
	private DAAEndcaseViewHelper daaEndcaseViewHelper;
	/**重开赔案数据收集*/
	private ReCaseViewHelper reCaseViewHelper;
	/**定损服务*/
	private PrpLverifyLossService prpLverifyLossService;
	/**代码翻译服务*/
	private CodeService codeService;
	
	private WorkFlowService workFlowService;

	/**
	 * 重开赔案的处理
	 * @return
	 * @throws Exception
	 */
	public String reCaseBeforeEdit() throws Exception {
		HttpServletRequest httpServletRequest = getRequest();
		// 业务类型：ADD-新增 EDIT-修改 SHOW-显示
		String claimNo = httpServletRequest.getParameter("reCaseClaimNo").trim(); // 赔案号
		String forward = ""; // 向前
		// 重开赔案只允许做一次,且应该是曾经生成过赔案号的案件才需要重开赔案
		boolean isGenrateCaseNo = daaEndcaseViewHelper.isGenrateCaseNo(claimNo);
		if (isGenrateCaseNo == false) {
			throw new UserException(0, -1, "重開賠案", "案件還未結案，不需要重開賠案!");
		}
		// 简易赔案结案後不允许重开
		String registNo = "";
		registNo = codeService.translateBusinessCode(claimNo, false);
		UIQuickCaseAction uiQuickCaseAction = new UIQuickCaseAction();
		PrpLquickCaseDto prpLquickCase = uiQuickCaseAction.findPrpLquickCaseByPrimaryKey(registNo);
		if (prpLquickCase != null && "1".equals(prpLquickCase.getValidStatus())) {
			prpLverifyLossService.update(prpLquickCase.getRegistNo());
		}
		reCaseViewHelper.claimNoDtoToView(httpServletRequest, claimNo);
		reCaseViewHelper.recasemaxDtoToView(httpServletRequest, claimNo);
		String flowId = this.getWorkFlowService().findViewFlowIDBybusinessNo(claimNo); // 获取flowId
		String compeStr = "flowid='" + flowId + "' and businessno='" + claimNo + "' and nodeType='compe' and flowstatus>0 and nodestatus<4";
		int compeCount = this.getWorkFlowService().findFlowNodeCountByConditon(compeStr);
		String recaseend = "y";//重开赔案已结束
		if (compeCount > 0) {
			recaseend = "n";//
		}
		httpServletRequest.setAttribute("recaseend", recaseend);
		forward = "success";
		return forward;
	}

	public DAAEndcaseViewHelper getDaaEndcaseViewHelper() {
		return daaEndcaseViewHelper;
	}

	public void setDaaEndcaseViewHelper(DAAEndcaseViewHelper daaEndcaseViewHelper) {
		this.daaEndcaseViewHelper = daaEndcaseViewHelper;
	}

	public ReCaseViewHelper getReCaseViewHelper() {
		return reCaseViewHelper;
	}

	public void setReCaseViewHelper(ReCaseViewHelper reCaseViewHelper) {
		this.reCaseViewHelper = reCaseViewHelper;
	}

	public PrpLverifyLossService getPrpLverifyLossService() {
		return prpLverifyLossService;
	}

	public void setPrpLverifyLossService(PrpLverifyLossService prpLverifyLossService) {
		this.prpLverifyLossService = prpLverifyLossService;
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
