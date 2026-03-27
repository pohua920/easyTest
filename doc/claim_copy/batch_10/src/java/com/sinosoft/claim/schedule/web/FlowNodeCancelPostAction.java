package com.sinosoft.claim.schedule.web;

import java.util.Map;

import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schedule.service.facade.ScheduleService;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.claim.workflow.vo.JbpmDto;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;

@SuppressWarnings("serial")
public class FlowNodeCancelPostAction extends Struts2Action {
	/**工作流处理service*/
	private WorkFlowService workFlowService;
	/**调度逻辑分发service*/
	private ScheduleService scheduleService;
	/**工作流ViewHelper*/
	private WorkFlowViewHelper workFlowViewHelper;
	/**
	 * 註銷的定損任務
	 * @throws Exception
	 */
	public String flowNodeCancelPost() throws Exception {
		this.clearErrorsAndMessages();
		String forward = "";
		HttpServletRequest httpServletRequest = getRequest();
		String editType = httpServletRequest.getParameter("editType");
		String swfLogFlowID = httpServletRequest.getParameter("swfLogFlowID");
		String swfLogLogNo = httpServletRequest.getParameter("swfLogLogNo");
		String registNo = httpServletRequest.getParameter("RegistNo");
		
		if (swfLogFlowID == null || swfLogLogNo == null || swfLogFlowID.equals("null") || swfLogLogNo.equals("null")) {
			String msg = "案件'" + registNo + "'沒發現可以註銷的定損任務!";
			throw new UserException(1, 3, "定損任務註銷", msg);
		}
		SwfLog swfLogDtoDealNode = this.getWorkFlowService().findNodeByPrimaryKey(swfLogFlowID, Integer.parseInt(DataUtils.nullToZero(swfLogLogNo)));
		if (swfLogDtoDealNode == null) {
			String msg = "案件'" + registNo + "'沒發現可以註銷的定損任務!";
			throw new UserException(1, 3, "定損任務註銷", msg);
		}
		if ("ScheduleCancel".equals(editType)) {
			// 需要检查撤消的定损是不是调度中唯一的一个定损，如果是的话，不可以注销的。。
			// 检查边
			String strSql = "flowid='" + swfLogFlowID + "' and nodestatus<5 " + " and nodeType in ('certa','wound','propc')";
			int count = this.getWorkFlowService().findFlowNodeCountByConditon(strSql);
			if (count == 1) {
				// 如果是唯一调度出去的任务。判断调度中还存在没有调度出去的吗？
				strSql = " registno='" + registNo + "' and surveyTimes<1";
				int schedulecount = this.scheduleService.findScheduleItemCountByConditon(strSql);
				// 只有唯一的一个定损任务，所以就是不能让取消！！
				if (schedulecount < 1) {
					String msg = "案件'" + registNo + "'中，僅剩此唯一的定損任務，所以不能註銷!";
					throw new UserException(1, 3, "定損任務註銷", msg);

				}

			}
		}
		WorkFlowDto workFlowDto = null;
		String actorId = httpServletRequest.getParameter("swfLogActorId");
		if (WorkFlowDto.isWorkflowswitch() && DataUtils.emptyToNull(DataUtils.dbNullToEmpty(actorId)) != null) {
			// 新工作流引擎处理入口
			workFlowDto = this.getJbpmWorkFlowDto(swfLogDtoDealNode);
		} else {
			workFlowDto = this.getWorkFlowDto(swfLogDtoDealNode);
		}
		// 保存报案注销信息並查找工作流程
		if (workFlowViewHelper.checkDealDto(workFlowDto)) {
			this.getWorkFlowService().deal(workFlowDto);
		}
		this.addActionMessage("定損註銷儲存成功");
		forward = "success";

		return forward;
	}
	/**
	 * 新工作流处理定损任务注销
	 * @param swfLog
	 * @return
	 * @throws Exception
	 */
	private WorkFlowDto getJbpmWorkFlowDto(SwfLog swfLog)throws Exception{
		HttpServletRequest httpServletRequest = getRequest();
		WorkFlowDto workFlowDto = new WorkFlowDto();
		workFlowDto.setUpdate(true);
		workFlowDto.setSubmit(true);
		workFlowDto.setNewWorkFlow(true);
		Map<String,Object> paramMap = workFlowDto.getParamMap();
		paramMap.put("nodeStatus", "6");
//		swfLog.setNodeStatus("6");
		String context = httpServletRequest.getParameter("contextInnerHTML");
		// 取得当前用户信息
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		String tempStr = "@用戶:" + user.getUserName() + "執行了註銷操作，原因：" + context;
//		swfLog.setTitleStr(swfLog.getTitleStr() + tempStr);
		paramMap.put("titleStr", swfLog.getTitleStr() + tempStr);
		workFlowDto.setParamMap(paramMap);
		workFlowDto.setCurrSwfLog(swfLog);
		JbpmDto jbpmDto = new JbpmDto();
		jbpmDto.putParamsMap("cancelFlag", true);
		jbpmDto.addCertainLossNodeMap(swfLog.getNodeType(), swfLog.getLossItemCode());
		workFlowDto.setJbpmDto(jbpmDto);
		return workFlowDto;
	}
	/**
	 * 旧的工作流引擎处理定损任务注销
	 * @param swfLog
	 * @return
	 * @throws Exception
	 */
	private WorkFlowDto getWorkFlowDto(SwfLog swfLog)throws Exception{
		HttpServletRequest httpServletRequest = getRequest();
		WorkFlowDto workFlowDto = new WorkFlowDto();
		workFlowDto.setUpdate(true);
		swfLog.setNodeStatus("6");
		String context = httpServletRequest.getParameter("contextInnerHTML");
		// 取得当前用户信息
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		String tempStr = "@用戶:" + user.getUserName() + "執行了註銷操作，原因：" + context;
		swfLog.setTitleStr(swfLog.getTitleStr() + tempStr);
		workFlowDto.setUpdateSwfLog(swfLog);
		return workFlowDto;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public ScheduleService getScheduleService() {
		return scheduleService;
	}

	public void setScheduleService(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	public WorkFlowViewHelper getWorkFlowViewHelper() {
		return workFlowViewHelper;
	}

	public void setWorkFlowViewHelper(WorkFlowViewHelper workFlowViewHelper) {
		this.workFlowViewHelper = workFlowViewHelper;
	}

}
