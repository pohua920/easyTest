package com.sinosoft.claim.workflow.web;

import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.WfLogService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * @ClassName ProcessWorkFlowAction
 * @Description 车险工作流收回模块
 * @author 中科软
 */

public class ProcessWorkflowAction extends Struts2Action {
	private static final long serialVersionUID = 1L;
	/**核赔工作流服务*/
	private WfLogService wfLogService;
	/**工作流服务*/
	private WorkFlowService workFlowService;

	/**
	 * 处理退回工作流
	 * @return
	 * @throws Exception
	 */
	public String processWorkflow() throws Exception {
		String forward = "";// 界面流转
		try {
			// 业务类型：ADD-新增 EDIT-修改 SHOW-显示
			String editType = ""; // 编辑类型
			HttpServletRequest httpServletRequest = getRequest();
			editType = httpServletRequest.getParameter("editType");

			WorkFlowDto workFlowDto = null;
			if (editType.equals("recycle")) {
				workFlowDto = new WorkFlowDto();
				workFlowDto.setRecycle(true);
				SwfLog swfLogDto = new SwfLog();
				swfLogDto.getId().setFlowID(httpServletRequest.getParameter("flowID"));
				swfLogDto.getId().setLogNo(Integer.parseInt(httpServletRequest.getParameter("logNo")));
				workFlowDto.setUpdateSwfLog(swfLogDto);
				this.getWorkFlowService().deal(workFlowDto);
				wfLogService.recycleWflog(swfLogDto);
			}
			this.clearErrorsAndMessages();
			this.addActionMessage(getText("claim.back.success"));
			forward = "success";
		} catch (UserException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(1, 3, "工作流", e.getMessage());
		}
		return forward;
	}

	public WfLogService getWfLogService() {
		return wfLogService;
	}

	public void setWfLogService(WfLogService wfLogService) {
		this.wfLogService = wfLogService;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

}
