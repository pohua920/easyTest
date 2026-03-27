package com.sinosoft.one.bpm.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.alibaba.fastjson.JSON;
import com.sinosoft.one.bpm.model.ProcessInstanceBOInfo;
import com.sinosoft.one.bpm.model.TaskParamInfo;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.task.Status;
import org.jbpm.task.TaskService;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.ContentData;
import org.jbpm.task.utils.ContentMarshallerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sinosoft.one.bpm.aspect.GlobalVariable;
import com.sinosoft.one.bpm.service.facade.BpmService;
import com.sinosoft.one.bpm.support.BpmServiceSupport;
import com.sinosoft.one.bpm.variable.VariableHandler;
import com.sinosoft.one.bpm.variable.VariableHandlerFactory;

public class BpmServiceImplement implements BpmService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private BpmServiceSupport bpmServiceSupport;

    private ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
	
/*	public void init() {
		TaskAspect.setBpmSerivce(this);
	}*/

	/*
	 * 
	 * @see
	 * com.sinosoft.ebusiness.bpm.service.spring.BpmService#createProcess(java
	 * .lang.String, java.util.Map)
	 */
	public long createProcess(String processId, Map<String, Object> params, String businessId)
			throws Exception {
//		TransactionStatus transaction  = bpmServiceSupport.beginTransaction();
		//todo add paramInfo
//        System.out.println("start createProcess");
//        System.out.println(JSON.toJSONString(taskParamInfo));
//        System.out.println("end createProcess");
//        bpmServiceSupport.commit(transaction);
//        String paramData = JSON.toJSONString(params);
//        TaskParamInfo taskParamInfo = new TaskParamInfo(processId,businessId,paramData);
//        bpmServiceSupport.saveTaskParamInfo(taskParamInfo);
		StatefulKnowledgeSession ksession = bpmServiceSupport.getSession();
		ProcessInstance pi = ksession.startProcess(processId, params);
		ksession.fireAllRules();
        long processInstanceId = pi.getId();
		return processInstanceId;
	}

	/*
	 * 
	 * @see
	 * com.sinosoft.ebusiness.bpm.service.spring.BpmService#getTasks(java.lang .String)
	 */
	public List<TaskSummary> getTasks(String user, String processId, String businessId) throws Exception {
		TaskService taskService = bpmServiceSupport.getTaskService(businessId);
		long processInstanceId = bpmServiceSupport.getProcessInstanceId(processId, businessId);
		System.out.println("*******************user:"+user+"*****processId:"+processId+"*****businessid:"+businessId+"*******");
		System.out.println("*******************processInstanceId:"+processInstanceId+"******************");
		List<Status> status = new ArrayList<Status>();
		status.add(Status.Reserved);
		List<TaskSummary> tasks = taskService.getTasksByStatusByProcessId(processInstanceId, status, "en-UK");
		return tasks;
	}

	/*
	 * 
	 * @see
	 * com.sinosoft.ebusiness.bpm.service.spring.BpmService#submitTask(long,
	 * java.lang.String, java.util.HashMap)
	 */
	public void submitTask(long taskId, String user, Map<String, Object> data, String businessId, String processId)
			throws Exception {
//        TransactionStatus transaction  = bpmServiceSupport.beginTransaction();
        String paramData = JSON.toJSONString(data);
        Long processInstanceId = bpmServiceSupport.getProcessInstanceId(processId, businessId);
        TaskParamInfo taskParamInfo = new TaskParamInfo(taskId,user,businessId,paramData,processInstanceId,processId);
        //add paramInfo
        bpmServiceSupport.saveTaskParamInfo(taskParamInfo);
		ContentData contentData = null;
		if (data != null) {
			try {
				contentData = ContentMarshallerHelper.marshal(data, bpmServiceSupport.getEnvironment());
			} catch (Exception e) {
				logger.error("submit task exception. task id : " + taskId, e);
                throw new Exception(e);
			}
		}
		TaskService taskService = bpmServiceSupport.getTaskService(businessId);
		taskService.complete(taskId, user, contentData);

//        bpmServiceSupport.commit(transaction);
	}

    //todo give the rollbackTask method
    public synchronized void rollbackTask(final String processId,final String businessId,final String _userId,final Long taskId) throws Exception{
        Future future = executorService.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
//                TransactionStatus transactionStatus = bpmServiceSupport.beginTransaction();
                long processInstanceId = bpmServiceSupport.getProcessInstanceId(processId, businessId);
                TaskParamInfo taskParamInfo1 = bpmServiceSupport.getTaskParamInfo(_userId,processId,businessId,processInstanceId,taskId);
                if (taskParamInfo1!=null){
                    bpmServiceSupport.deleteTaskParamInfo(taskParamInfo1);
                }
                removeProcess(processId,businessId,processInstanceId);
//                bpmServiceSupport.commit(transactionStatus);
//                TaskParamInfo processParam = bpmServiceSupport.getTaskParamInfo(processId,businessId,processInstanceId);
//                String params = processParam.getParamData();
//                Map<String,Object> paramsMap = JSON.parseObject(params);
                Map<String,Object> params = new HashMap<String,Object>();
                params.put("businessId", businessId);
                createProcess(processId, params, businessId);
                List<TaskParamInfo> taskParamInfoList =  bpmServiceSupport.listTaskParamInfo(processId,businessId,processInstanceId);
                if (taskParamInfoList!=null){
                    for (TaskParamInfo taskParamInfo:taskParamInfoList){
                        String userId = taskParamInfo.getUserId();
                        long taskId = getTaskId(userId, processId, businessId);
                        String paramStr = taskParamInfo.getParamData();
                        Map<String,Object> paramData = JSON.parseObject(paramStr);
                        try {
                            startTask(taskId, userId, businessId);
                        } catch (Exception e) {
                            releaseTask(taskId, userId, businessId);
                            logger.info("releaseTask taskId=" + taskId + "  userId=" + userId);
                            throw new RuntimeException(e);
                        }
                        submitTask(taskId, userId, paramData, businessId, processId);
                    }
                }
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        future.get();
    }

    private void removeProcess(String processId, String businessId, long processInstanceId) {
//        StatefulKnowledgeSession ksession = bpmServiceSupport.getSession();
//        ksession.abortProcessInstance(processInstanceId);
        bpmServiceSupport.clearProcessInstanceInfo(processId,processInstanceId);
//        bpmServiceSupport.deleteProcesssInstanceBOInfo(processId,processInstanceId);
//        ProcessInstanceBOInfo processInstanceBOInfo = bpmServiceSupport.findProcessInstanceBOInfo(processInstanceId);
//        if (processInstanceBOInfo!=null){
//            bpmServiceSupport.deleteProcesssInstanceBOInfo(processInstanceBOInfo);
//        }
    }

    /*
     *
     * @see com.sinosoft.ebusiness.bpm.service.spring.BpmService#startTask(long,
     * java.lang.String)
     */
	public void startTask(long taskId, String userId, String businessId) throws Exception {
		TaskService taskService = bpmServiceSupport.getTaskService(businessId);
		taskService.start(taskId, userId);
	}

	/*
	 * 
	 * @see
	 * com.sinosoft.ebusiness.bpm.service.spring.BpmService#releaseTask(long,
	 * java.lang.String)
	 */
	public void releaseTask(long taskId, String userId, String businessId) throws Exception {
		TaskService taskService = bpmServiceSupport.getTaskService(businessId);
		taskService.release(taskId, userId);
	}

	/*
	 * 
	 * @see
	 * com.sinosoft.ebusiness.bpm.service.spring.BpmService#getBusinessId(long)
	 */
	public String getBusinessId(long processInstanceId) throws Exception {
		return bpmServiceSupport.getBusinessId(processInstanceId);
	}

	/*
	 * @see
	 * com.sinosoft.ebusiness.bpm.service.spring.BpmService#getBusinessId(java
	 * .lang.String,java.lang.String)
	 */
	public long getTaskId(String userId, String processId, String businessId) throws Exception {
		long taskId = -1;
		List<TaskSummary> tasks = this.getTasks(userId, processId, businessId);
		if("compp".equals(userId)||"veric".equals(userId)){
			System.out.println("*******************task:"+tasks.size()+"*****************************");
		}
		logger.debug("tasks : " + tasks);
		for (TaskSummary task : tasks) {
			if("compp".equals(userId)||"veric".equals(userId)){
				System.out.println("********************************userId:"+task.getActualOwner().getId()+"******************");
				System.out.println("**********************************processinstanceid:"+task.getProcessInstanceId()+"*********");
			}
			if (task.getActualOwner().getId().equals(userId)) {
				taskId = task.getId();
				break;
			}
		}
		if(taskId == -1) {
			throw new RuntimeException("No any task find for userId : " + userId + ", businessId : " + businessId );
		}
		return taskId;
	}
	
	public void doVariable(Object[] args, GlobalVariable variable) throws Exception {
		VariableHandler variableHandler = VariableHandlerFactory.buildVariableHandler(variable.type(), bpmServiceSupport);
		variableHandler.handler(args, variable);
	}

    
    public void doVariables(List<GlobalVariable> variableList, Object[] args) throws Exception {
    	for(GlobalVariable aVariable : variableList) {
    		doVariable(args, aVariable);
    	}
    }

	public void setBpmServiceSupport(BpmServiceSupport bpmServiceSupport) {
		this.bpmServiceSupport = bpmServiceSupport;
	}
}
