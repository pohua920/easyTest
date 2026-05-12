package com.sinosoft.sys.platform.power.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;
import java.util.Map;

import com.sinosoft.sys.platform.power.model.SaaTask;



public interface SaaTaskService {
	public SaaTask findTaskById(Long id);

	public Page findTask(SaaTask saaTask, int pageNo, int pageSize);

	public void addTask(SaaTask saaTask) throws Exception;

	public void updateTask(SaaTask saaTask);

	public void deleteTask(Long id);

	public SaaTask findTask(Map<String, Object> map);

	public List<SaaTask> findTask(String[] taskCodes);
	
	public SaaTask getTask(Map<String, Object> map);
	
	public List<String> getGradeTaskByUserCode(String userCode, String str);

	public Long getTaskIdByTaskCode(String taskCode);
	/** 查询出所有的有效功能ID */
	public List<SaaTask> getAllDate();
	
	public void insertTaskTest(SaaTask saaTask) throws Exception;
	
	public Page findTask(QueryRule queryRule, int pageNo, int pageSize);
	
}
