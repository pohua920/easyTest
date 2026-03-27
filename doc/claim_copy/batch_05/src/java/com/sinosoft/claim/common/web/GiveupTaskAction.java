package com.sinosoft.claim.common.web;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;

import ins.framework.web.Struts2Action;

/**
 * @ClassName GiveupTaskAction
 * @Description 放弃任务处理Action
 * @author 中科软
 */
@SuppressWarnings("serial")
public class GiveupTaskAction extends Struts2Action {
	/** 工作流服务接口 */
	private WorkFlowService workFlowService;

	public String giveupTask() throws Exception {
		HttpServletRequest request = super.getRequest();
		String editType = request.getParameter("editType");
		// 未处理任务的放弃处理
		if ("GIVUP".equals(editType)) {
			String FlowID = request.getParameter("swfLogFlowID");
			int LogNo = Integer.parseInt((String) request.getParameter("swfLogLogNo"));
			SwfLog swfLogDto = this.getWorkFlowService().findNodeByPrimaryKey(FlowID, LogNo);
			swfLogDto.setHandlerCode("");
			swfLogDto.setHandlerName("");
			swfLogDto.setFlowStatus("1");
			this.getWorkFlowService().updateFlow(swfLogDto);
			this.clearErrorsAndMessages();
			this.addActionMessage(this.getText("prompt.compensate.giveup"));
		}
		return SUCCESS;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}
}
