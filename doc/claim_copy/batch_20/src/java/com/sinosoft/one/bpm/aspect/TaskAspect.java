package com.sinosoft.one.bpm.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.*;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.jbpm.task.Task;
import org.jbpm.task.TaskService;
import org.jbpm.task.query.TaskSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;

import com.sinosoft.one.bpm.listener.BusinessTaskData;
import com.sinosoft.one.bpm.service.facade.BpmService;
import com.sinosoft.one.bpm.support.BpmServiceSupport;
import com.sinosoft.one.bpm.util.BpmCommonUtils;

/**
 *
 * @author carvin
 *
 */
//@Aspect
public class TaskAspect implements Ordered{
    @Autowired
	private BpmService bpmService;
    @Autowired
    private BpmServiceSupport bpmServiceSupport;
    
    private Logger logger = Logger.getLogger(TaskAspect.class);

    private static int  bpmExecutorSize = 1;

    /**
     */
    private static ExecutorService executorService = null;

    /**
     * @param pjp
     * @return
     * @throws Throwable
     */
//    @Around("execution(@GetTask * com.sinosoft.one.bpm.test.service.spring.*.*(..))")
    public Object getTask(final ProceedingJoinPoint pjp) throws Throwable {
			Object result = pjp.proceed();
	    	
	    	logger.info("into getTask aspect");
	        GetTask getTask = parserAnnotation(pjp, GetTask.class);
	        Object[] args = pjp.getArgs();
	        String userId = getTask.userId();
	        if(StringUtils.isBlank(userId)) {
	        	int userIdBeanOffset = getTask.userIdBeanOffset();
	        	if(userIdBeanOffset == -1) {
	        		throw new IllegalArgumentException("getTask annotation must assign userId or userIdBeanOffset.");
	        	}
	        	userId = (String)BpmCommonUtils.parseAttributeValue(pjp.getArgs()[userIdBeanOffset], getTask.userIdAttributeName());
	        }
	        String businessIdAttributeName = getTask.businessIdAttributeName();
	        if(StringUtils.isBlank(businessIdAttributeName)) {
	        	throw new IllegalArgumentException("@getTask's property[businessIdAttributeName]  can't be empty .");
	        }
	        Object bean = args[getTask.businessBeanOffset()];
	        String businessId = parserBusinessId(bean,
	        		getTask.businessIdAttributeName());
	        String processId = getTask.processId();
            if(StringUtils.isBlank(processId)){
                int processBeanOffset = getTask.processIdBeanOffset();
                if(processBeanOffset == -1) {
                    throw new IllegalArgumentException("getTask annotation must assign processId and processBeanOffset.");
                }
                processId = (String)BpmCommonUtils.parseAttributeValue(pjp.getArgs()[processBeanOffset], getTask.processIdAttributeName());
            }

	        List<TaskSummary> tasks = bpmService.getTasks(userId, processId, businessId);
	        HashMap<String, String> taskAndBusiness = new HashMap<String, String>();
	        for (TaskSummary task : tasks) {
	            String tempBusinessId = bpmService.getBusinessId(task
	                    .getProcessInstanceId());
	            if (StringUtils.isNotBlank(tempBusinessId)) {
	                taskAndBusiness.put(tempBusinessId, String.valueOf(task.getId()));
	            }
	        }
	        
	        Iterator<?> it = getIterator(result);
	        String realBusinessIdAttributeName = businessIdAttributeName;
	        if(it == null) {
	        	String[] attributeNames = businessIdAttributeName.split("\\.");
	        	int len = attributeNames.length;
	        	realBusinessIdAttributeName = attributeNames[len-1].trim();
	        	Object tempResult = result;
	        	String currentAttributeName = "";
	        	for(int i=0; i<len-1; i++) {
	        		currentAttributeName = attributeNames[i];
	        		tempResult = PropertyUtils.getProperty(tempResult, currentAttributeName);
	        	}
	        	it = getIterator(tempResult);
	    		if(it == null) {
	    			throw new IllegalArgumentException("the property [" + currentAttributeName + "]' value must be Collection or Map.");
	    		}
	        }
	        
	        while (it.hasNext()) {
	            Object tempBean = it.next();
	            String tempBusinessId = parserBusinessId(tempBean, realBusinessIdAttributeName);
	            if (!taskAndBusiness.containsKey(tempBusinessId)) {
	                it.remove();
	            }
	        }
	        logger.info("out getTask aspect");
	        return result;
    }
    
    private Iterator<?> getIterator(Object target) {
    	Iterator<?> it = null;
    	 if(target instanceof List) {
         	it = ((List<?>) target).iterator();
         } else if(target instanceof Set) {
         	it = ((Set<?>) target).iterator();
         } else if(target instanceof Map) {
         	Collection<?> values = ((Map<?, ?>) target).values();
         	it = values != null ? values.iterator() : null;
         } 
    	 return it;
    }

    /**
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
//    @Around("execution(@ProcessTask * com.sinosoft.one.bpm.test.service.spring.*.*(..))")
    public Object processTask(final ProceedingJoinPoint pjp) throws Throwable {
        StartProcess startProcess = parserAnnotation(pjp, StartProcess.class);
        if (startProcess != null) {
            return pjp.proceed();
        }
        final Object result = pjp.proceed();


        Future future = getExecutorService().submit(new Callable<Object>() {

            @Override
            public Object call() throws Exception {

                GlobalVariables variablesAnnotation = parserAnnotation(pjp, GlobalVariables.class);
                GlobalVariable variableAnnotation = parserAnnotation(pjp, GlobalVariable.class);

                doVariables(variablesAnnotation, variableAnnotation, pjp.getArgs());

                try {
                    processTaskHandler(pjp);
                } catch (Throwable throwable) {
                    throw new RuntimeException(throwable);
                }

                return result;
            }
        });
        return future.get();
    }
    
	private void processTaskHandler(ProceedingJoinPoint pjp) throws Throwable {
		try {
			logger.info("into processTask aspect");
			ProcessTask processTask = parserAnnotation(pjp, ProcessTask.class);
			if (processTask == null)
				return;
			Object[] args = pjp.getArgs();
			Object bean = args[processTask.businessBeanOffset()];
			String businessId = parserBusinessId(bean, processTask.businessIdAttributeName());
			String userId = processTask.userId();
			if (StringUtils.isBlank(userId)) {
				int userIdBeanOffset = processTask.userIdBeanOffset();
				if (userIdBeanOffset == -1) {
					throw new IllegalArgumentException("processTask annotation must assign userId or userIdBeanOffset.");
				}
				userId = (String) BpmCommonUtils.parseAttributeValue(pjp.getArgs()[userIdBeanOffset], processTask.userIdAttributeName());
			}
			Map<String, Object> paramData = new HashMap<String, Object>();
			TaskParams taskParamsAnnotation = parserAnnotation(pjp, TaskParams.class);
			if (taskParamsAnnotation != null) {
				TaskParam[] taskParams = taskParamsAnnotation.taskParams();
				if (taskParams != null) {
					for (TaskParam taskParam : taskParams) {
						addParam(paramData, taskParam, args);
					}
				}
			} else {
				TaskParam taskParam = parserAnnotation(pjp, TaskParam.class);
				if (taskParam != null) {
					addParam(paramData, taskParam, args);
				}
			}
			String processId = processTask.processId();
			if (StringUtils.isBlank(processId)) {
				int processBeanOffset = processTask.processIdBeanOffset();
				if (processBeanOffset == -1) {
					throw new IllegalArgumentException("getTask annotation must assign processId and processBeanOffset.");
				}
				processId = (String) BpmCommonUtils.parseAttributeValue(pjp.getArgs()[processBeanOffset], processTask.processIdAttributeName());
			}
			long taskId = bpmService.getTaskId(userId, processId, businessId);
			try {
				bpmService.startTask(taskId, userId, businessId);
			} catch (Exception e) {
				bpmService.releaseTask(taskId, userId, businessId);
				logger.info("releaseTask taskId=" + taskId + "  userId=" + userId);
				throw new RuntimeException(e);
			}
			bpmService.submitTask(taskId, userId, paramData, businessId,processId);
			try {
				BusinessTaskData businessTaskData = BusinessTaskData.getThreadlocal().get();
				if (businessTaskData != null) {
					BackTask backTask = parserAnnotation(pjp, BackTask.class);
					BackTasks backTasksAnnotation = parserAnnotation(pjp, BackTasks.class);
					BackTask[] backTasks = backTasksAnnotation.backTasks();
					if (backTask != null || backTasks != null) {
						TaskService taskService = bpmServiceSupport.getTaskService(businessId);
						Task task = taskService.getTask(businessTaskData.getTaskId());
						businessTaskData.setProcessInstanceId(task.getTaskData().getProcessInstanceId());
						businessTaskData.setTask(task);
						for (Long id : businessTaskData.getNextActorIds().keySet()) {
							businessTaskData.addNextTasks(taskService.getTask(id));
						}
					}
					if (backTask != null) {
						addBackTask(backTask, businessTaskData, args);
					}
					if (backTasks != null) {
						for (BackTask back : backTasks) {
							addBackTask(back, businessTaskData, args);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("out processTask aspect");
		} finally {
			BusinessTaskData.getThreadlocal().remove();
		}
	}

    private void addParam(Map<String, Object> paramData, TaskParam taskParam, Object[] args) throws Exception {
    	String key = taskParam.key();
		if(StringUtils.isNotBlank(key)) {
			paramData.put(key, BpmCommonUtils.parseAttributeValue(args[taskParam.paramValueBeanOffset()], taskParam.paramValueAttributeName()));
		}
    }
    
    /**
     * @param backTask
     * @param businessTaskData
     * @param args
     * @throws Exception
     */
    private void addBackTask(BackTask backTask,BusinessTaskData businessTaskData,Object[] args) throws Exception {
        String userAttributeName = backTask.userAttributeName();
    	if(backTask.userBeanOffset()!=-1&&StringUtils.isNotBlank(userAttributeName)){
    		String	businessAttributeName = backTask.businessAttributeName();
    		if(StringUtils.isBlank(businessAttributeName)){
				businessAttributeName = userAttributeName;
			}
			Object value = BpmCommonUtils.parseAttributeValue(businessTaskData, businessAttributeName);
			BpmCommonUtils.setAttributeValue(args,backTask.userBeanOffset(), userAttributeName, value);
    	}
    }

    private void doVariables(GlobalVariables variables, GlobalVariable variable, Object[] args) throws Exception {
    	
    	List<GlobalVariable> variableList = new ArrayList<GlobalVariable>();
    	if(variables != null) {
    		variableList.addAll(Arrays.asList(variables.variables()));
    	}
    	if(variable != null) {
    		variableList.add(variable);
    	}
    	doVariables(variableList, args);
    	
    }
    
    private void doVariables(List<GlobalVariable> variableList, Object[] args)  throws Exception {
    	for(GlobalVariable aVariable : variableList) {
    		bpmService.doVariable(args, aVariable);
    	}
    }

    
    /**
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
//    @Around("execution(@StartProcess * com.sinosoft.one.bpm.test.service.spring.*.*(..))")
    public Object startProcess(final ProceedingJoinPoint pjp) throws Throwable {
    	final Object result = pjp.proceed();

        Future future = getExecutorService().submit(new Callable<Object>(){

            @Override
            public Object call() throws Exception {
                logger.info("into startProcess aspect");

                GlobalVariables variablesAnnotation = parserAnnotation(pjp, GlobalVariables.class);
                GlobalVariable variableAnnotation = parserAnnotation(pjp, GlobalVariable.class);

                Object[] args = pjp.getArgs();
                doVariables(variablesAnnotation, variableAnnotation, args);

                StartProcess startProcess = parserAnnotation(pjp, StartProcess.class);
                Object bean = pjp.getArgs()[startProcess.businessBeanOffset()];
                String businessId = parserBusinessId(bean,
                        startProcess.businessIdAttributeName());
                Map<String, Object> paramData = new HashMap<String, Object>();
                TaskParams taskParamsAnnotation = parserAnnotation(pjp, TaskParams.class);
                if(taskParamsAnnotation != null) {
                    TaskParam[] taskParams = taskParamsAnnotation.taskParams();
                    if(taskParams != null) {
                        for(TaskParam taskParam : taskParams) {
                            addParam(paramData, taskParam, args);
                        }
                    }
                } else {
                    TaskParam taskParam = parserAnnotation(pjp, TaskParam.class);
                    if(taskParam != null) {
                        addParam(paramData, taskParam, args);
                    }
                }
                paramData.put("businessId", businessId);

                String processId = startProcess.processId();
                if(StringUtils.isBlank(processId)){
                    int processBeanOffset = startProcess.processIdBeanOffset();
                    if(processBeanOffset == -1) {
                        throw new IllegalArgumentException("getTask annotation must assign processId and processBeanOffset.");
                    }
                    processId = (String)BpmCommonUtils.parseAttributeValue(pjp.getArgs()[processBeanOffset], startProcess.processIdAttributeName());
                }
                bpmService.createProcess(processId, paramData, businessId);

                try {
                    processTaskHandler(pjp);
                } catch (Throwable throwable) {
                    throw new RuntimeException(throwable);
                }
                logger.info("out startProcess aspect");
                return result;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        return future.get();
    }

    /**
     *
     * @param bean
     * @param attributeName
     * @return
     * @throws Exception
     */
    public String parserBusinessId(Object bean, String attributeName)
            throws Exception {
        return (String)BpmCommonUtils.parseAttributeValue(bean, attributeName);
    }
    
    /**
     *
     * @param <T>
     * @param pjp
     * @param annotationClass
     * @return
     * @throws Exception
     */
    public <T extends Annotation> T parserAnnotation(ProceedingJoinPoint pjp,
                                                     Class<T> annotationClass) throws Exception {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method m = signature.getMethod();
        if (Proxy.isProxyClass(pjp.getThis().getClass())) {
            m = pjp.getTarget().getClass()
                    .getMethod(m.getName(), m.getParameterTypes());
        }
        return (T) m.getAnnotation(annotationClass);
    }

	public int getOrder() {
		return -9999;
	}
	
	public ExecutorService getExecutorService(){
        if (executorService==null){
            executorService = Executors.newScheduledThreadPool(TaskAspect.bpmExecutorSize);
        }
        return executorService;
    }

    public static int getBpmExecutorSize() {
        return bpmExecutorSize;
    }

    public void setBpmExecutorSize(int bpmExecutorSize) {
    	TaskAspect.bpmExecutorSize = bpmExecutorSize;
    }
}
