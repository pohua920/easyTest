package com.sinosoft.claim.workflow.service.spring;

import org.drools.event.DefaultProcessEventListener;
import org.drools.event.process.ProcessCompletedEvent;
import com.sinosoft.one.bpm.listener.BusinessTaskData;

public class JbpmBusinessProcesssEventListener extends DefaultProcessEventListener{

	@Override
	public void afterProcessCompleted(ProcessCompletedEvent event) {
		super.afterProcessCompleted(event);
		BusinessTaskData businessTaskData = BusinessTaskData.getThreadlocal().get();
		businessTaskData.setClose(true);
	}
//
//	@Override
//	public void beforeProcessCompleted(ProcessCompletedEvent event) {
//		System.err.println("流程开始结束之前的监听.************************");
//		super.beforeProcessCompleted(event);
//	}
//
//	@Override
//	public void afterProcessStarted(ProcessStartedEvent event) {
//		System.err.println("流程结束之后的监听.************************"+event.getProcessInstance().getId()+event.getProcessInstance().getProcessId());
//		super.afterProcessStarted(event);
//	}
//
//	@Override
//	public void beforeProcessStarted(ProcessStartedEvent event) {
//		super.beforeProcessStarted(event);
//	}

}
