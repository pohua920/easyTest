package com.sinosoft.one.bpm.service.facade;

import java.math.BigDecimal;
import java.util.List;

import com.sinosoft.one.bpm.model.ProcessInstanceBOInfo;
import com.sinosoft.one.bpm.model.TaskParamInfo;

public interface ProcessInstanceBOService {
	
	void createProcessInstanceBOInfo(ProcessInstanceBOInfo info);

    void insertTaskParamInfo(TaskParamInfo taskParamInfo);
	
	void removeProcessInstanceBOInfo(ProcessInstanceBOInfo info);
	
	void removeProcessInstanceBOInfo(final long piId);
	
	ProcessInstanceBOInfo getProcessInstanceBOInfo(String processId, String businessId);
	
	ProcessInstanceBOInfo getProcessInstanceBOInfo(long processInstanceId);
	
	List<ProcessInstanceBOInfo> getAllNormalProcessInstanceBOInfo();
	
	BigDecimal queryProcessInstanceIdByTaskId(long taskId);

    TaskParamInfo findTaskParamInfo(String processId, String businessId, long processInstanceId);

    List<TaskParamInfo> listTaskParamInfo(String processId, String businessId, long processInstanceId);

    void deleteTaskParamInfo(long taskId, String userId, String processId, String businessId, long processInstanceId);

    void deleteTaskParamInfo(TaskParamInfo taskParamInfo);

    void deleteProcesssInstanceBOInfo(String processId, long processInstanceId);

    void deleteProcesssInstanceBOInfo(ProcessInstanceBOInfo processsInstanceBOInfo);

    TaskParamInfo findTaskParamInfo(String userId, String processId, String businessId, long processInstanceId,Long taskId);
}
