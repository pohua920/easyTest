package com.sinosoft.claim.workflow.service.spring;

import org.jbpm.task.event.DefaultTaskEventListener;
import org.jbpm.task.event.entity.TaskUserEvent;
import com.sinosoft.one.bpm.listener.BusinessTaskData;

public class JbpmBusinessTaskEventListener extends DefaultTaskEventListener {
	/**
	 * 任务完成后调用
	 */
	@Override
	public void taskCompleted(TaskUserEvent event) {
	}

	/**
	 * 任务完开始后调用
	 */
	@Override
	public void taskStarted(TaskUserEvent event) {
		BusinessTaskData businessTaskData = new BusinessTaskData(event.getUserId(), event.getTaskId());
		BusinessTaskData.getThreadlocal().set(businessTaskData);
	}

	/**
	 * 任务创建后调用
	 */
	@Override
	public void taskClaimed(TaskUserEvent event) {
		BusinessTaskData businessTaskData = BusinessTaskData.getThreadlocal().get();
		if (businessTaskData != null) {
			businessTaskData.addNextActorIds(event.getTaskId(), event.getUserId());
		}
	}
}
