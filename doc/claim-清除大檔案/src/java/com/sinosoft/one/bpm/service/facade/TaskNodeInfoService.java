package com.sinosoft.one.bpm.service.facade;

import java.util.List;

import com.sinosoft.one.bpm.model.TaskNodeEntity;

public interface TaskNodeInfoService {
	/**
	 * @param taskNodeInfos
	 */
	void saveOrUpdateTaskNodeEntities(List<TaskNodeEntity> taskNodeEntities);
	/**
	 * @param processId 
	 * @param actorId
	 * @return
	 */
	TaskNodeEntity queryTaskNodeEntity(String processId, String actorId);

}
