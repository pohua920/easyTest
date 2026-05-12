package com.sinosoft.claim.workflow.web;

import ins.framework.utils.DataUtils;
import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.util.PayRefRecViewHelper;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;

/**
 * <p>
 * Title: 工作流流程查询
 * </p>
 * <p>
 * Description: 工作流流程查询
 * </p>
 * @author 中科软
 */
public class WorkFlowBeforeQueryAction extends Struts2Action {

	/**
	 * @Fields serialVersionUID:
	 */
	private static final long serialVersionUID = 1L;
	/**代码翻译服务*/
	private CodeService codeService;
	/**工作流服务*/
	private WorkFlowService workFlowService;
	/**工作流ViewHelper*/
	private WorkFlowViewHelper workFlowViewHelper;
	/**赔款支付情况ViewHelper*/
	private PayRefRecViewHelper payRefRecViewHelper;

	/**
	 * 查询工作流
	 * @return
	 * @throws Exception
	 */
	public String workFlowBeforeQuery() throws Exception {
		try {
			// 业务类型：ADD-新增 EDIT-修改 SHOW-显示
			HttpServletRequest httpServletRequest = super.getRequest();
			String swfLogFlowID = httpServletRequest.getParameter("swfLogFlowID"); // 业务号
			String registNo = httpServletRequest.getParameter("registNo"); // 这个是报案号码
			String claimNo = httpServletRequest.getParameter("claimNo"); // 这个是报案号码
			String editType = httpServletRequest.getParameter("editType");
			// 根据报案号查询工作流程信息 DataUtils.emptyToNull(registNo)!=null &&
			// (DataUtils.emptyToNull(swfLogFlowID)== null)
			if (DataUtils.emptyToNull(registNo) != null && DataUtils.emptyToNull(swfLogFlowID) == null || DataUtils.emptyToNull(claimNo) != null) {
				// 按报案号码进行流程的查询。
				if (DataUtils.emptyToNull(claimNo) != null) {
					registNo = this.codeService.translateBusinessCode(claimNo, false);
				}
				swfLogFlowID = this.workFlowService.findFlowIDByRegistNo(registNo);
				if (DataUtils.emptyToNull(swfLogFlowID) == null) {
					swfLogFlowID = this.workFlowService.findViewFlowIDBybusinessNo(registNo);
				}
			}
			this.workFlowViewHelper.setFlowDtoToView(httpServletRequest, swfLogFlowID);
			// 显示核赔通过计算书支付情况
			this.payRefRecViewHelper.dtoToView(httpServletRequest, swfLogFlowID);
			if ("taskView".equals(editType)) {
				return "taskView";
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
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

	public WorkFlowViewHelper getWorkFlowViewHelper() {
		return workFlowViewHelper;
	}

	public void setWorkFlowViewHelper(WorkFlowViewHelper workFlowViewHelper) {
		this.workFlowViewHelper = workFlowViewHelper;
	}

	public PayRefRecViewHelper getPayRefRecViewHelper() {
		return payRefRecViewHelper;
	}

	public void setPayRefRecViewHelper(PayRefRecViewHelper payRefRecViewHelper) {
		this.payRefRecViewHelper = payRefRecViewHelper;
	}
}
