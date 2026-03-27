package com.sinosoft.claim.workflow.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import com.sinosoft.claim.workflow.service.facade.JbpmBusinessService;
import com.sinosoft.claim.workflow.vo.JbpmDto;
import com.sinosoft.one.bpm.aspect.BackTask;
import com.sinosoft.one.bpm.aspect.BackTasks;
import com.sinosoft.one.bpm.aspect.ProcessTask;
import com.sinosoft.one.bpm.aspect.StartProcess;
import com.sinosoft.one.bpm.aspect.TaskParam;
import com.sinosoft.one.bpm.aspect.TaskParams;

@SuppressWarnings("rawtypes")
public class JbpmBusinessServiceSpringImpl extends GenericDaoHibernate implements JbpmBusinessService {

	/**
	 * 启动工作流，处理第一个节点
	 * @param JbpmDto
	 * @param objs
	 * @return
	 * @throws Exception
	 */
	@StartProcess(processIdBeanOffset = 0, processIdAttributeName = "processId", businessBeanOffset = 0, businessIdAttributeName = "businessId")
	@ProcessTask(processIdBeanOffset = 0, processIdAttributeName = "processId",userIdBeanOffset = 0, userIdAttributeName = "actorId", businessBeanOffset = 0, businessIdAttributeName = "businessId")
	@TaskParam(key = "paramsMap", paramValueBeanOffset = 0, paramValueAttributeName = "paramsMap")
	@BackTasks(backTasks = { @BackTask(userBeanOffset = 0, userAttributeName = "processInstanceId"), @BackTask(userBeanOffset = 0, userAttributeName = "taskId"),
			@BackTask(userBeanOffset = 0, userAttributeName = "currentTask", businessAttributeName = "task"),
			@BackTask(userBeanOffset = 0, userAttributeName = "nextTaskList", businessAttributeName = "nextTasks") })
	public Object startProcess(JbpmDto jbpmDto, Object... objs) throws Exception {
		return null;
	}

	/**
	 * 处理当前节点的信息
	 * @param JbpmDto
	 * @param objs
	 * @return
	 * @throws Exception
	 */
	@ProcessTask(processIdBeanOffset = 0, processIdAttributeName = "processId",userIdBeanOffset = 0, userIdAttributeName = "actorId",
			businessBeanOffset = 0, businessIdAttributeName = "businessId")
	@TaskParams(taskParams = { @TaskParam(key = "paramsMap", paramValueBeanOffset = 0, paramValueAttributeName = "paramsMap"), 
			   @TaskParam(key = "certaNodeList", paramValueBeanOffset = 0, paramValueAttributeName = "certaNodeList"),
			   @TaskParam(key = "woundNodeList", paramValueBeanOffset = 0, paramValueAttributeName = "woundNodeList"),
			   @TaskParam(key = "propcNodeList", paramValueBeanOffset = 0, paramValueAttributeName = "propcNodeList")})
	@BackTasks(backTasks = { @BackTask(userBeanOffset = 0, userAttributeName = "processInstanceId"), 
			@BackTask(userBeanOffset = 0, userAttributeName = "taskId"),
			@BackTask(userBeanOffset = 0, userAttributeName = "currentTask", businessAttributeName = "task"),
			@BackTask(userBeanOffset = 0, userAttributeName = "nextTaskList", businessAttributeName = "nextTasks"), 
			@BackTask(userBeanOffset = 0, userAttributeName = "close", businessAttributeName = "close") })
	public Object processTask(JbpmDto jbpmDto, Object... objs) throws Exception {
		return null;
	}

}
