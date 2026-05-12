package com.sinosoft.sys.platform.power.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.exception.BusinessException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sinosoft.app.common.service.facade.IdGenService;
import com.sinosoft.app.common.util.HqlRulesUtil;
import com.sinosoft.sys.platform.power.model.SaaTask;
import com.sinosoft.sys.platform.power.service.facade.SaaTaskService;

public class SaaTaskServiceSpringImpl extends
		GenericDaoHibernate<SaaTask, Long> implements SaaTaskService {
	private IdGenService idGenService;

	public void addTask(SaaTask saaTask) throws Exception {
		Date date = new Date();

		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("taskCode", saaTask.getTaskCode());
		if (super.find(queryRule).size() > 0) {
			throw new BusinessException("已存在該功能配置", false);
		}
		String taskId = idGenService.getId(idGenService.SAA_TASKNO);
		long id = Long.parseLong(taskId);

		saaTask.setCreateTime(date);
		saaTask.setUpdateTime(date);
		saaTask.setId(id);
		super.getHibernateTemplate().merge(saaTask);

	}
	
	public void insertTaskTest(SaaTask saaTask) throws Exception{
		super.getHibernateTemplate().merge(saaTask);
	}

	public void deleteTask(Long id) {
		super.deleteByPK(id);
	}

	public Page findTask(SaaTask saaTask, int pageNo, int pageSize) {
		HqlRulesUtil hqlRulesUtil = new HqlRulesUtil();
		hqlRulesUtil.addLike("saaTask.taskCode", saaTask.getTaskCode());
		hqlRulesUtil.addLike("saaTask.parentCode", saaTask.getParentCode());
		hqlRulesUtil.addLike("saaTask.taskCName", saaTask.getTaskCName());
		hqlRulesUtil.addLike("saaTask.taskEName", saaTask.getTaskTName());
		hqlRulesUtil.addLike("saaTask.creatorCode", saaTask.getCreatorCode());
		hqlRulesUtil.addLike("saaTask.updaterCode", saaTask.getUpdaterCode());
		hqlRulesUtil.addLike("saaTask.validStatus", saaTask.getValidStatus());
		StringBuffer hql = new StringBuffer();
		hql.append(" from SaaTask saaTask");
		if (hqlRulesUtil.getHql().trim().length() != 0) {
			hql.append(" where  ").append(hqlRulesUtil.getHql());
		}
//		Page page = findByHql(hql.toString(), pageNo, pageSize);

		return null;
	}

	public SaaTask findTask(Map<String, Object> map) {
		return super.findUnique(map);
	}

	public List<SaaTask> findTask(String[] taskCodes) {
		List<SaaTask> tasks = new ArrayList<SaaTask>(0);
		for (String str : taskCodes) {
			SaaTask task = super.findUnique("taskCode", str);
			tasks.add(task);
		}
		return tasks;
	}

	public SaaTask findTaskById(Long id) {
		return super.get(id);
	}

	public List<String> getGradeTaskByUserCode(String userCode, String str) {
		List<String> taskCodes = this
				.findByHql(
						"select distinct task.taskCode from SaaUserGrade userGrade,SaaGradeTask gradeTask,SaaTask task where userGrade.userCode=? and userGrade.saaGrade.id=gradeTask.saaGrade.id and gradeTask.saaTask.id=task.id and task.taskCode like ?",
						userCode, "PNC_Workflow_" + str + "%");
		return taskCodes;
	}

	public Long getTaskIdByTaskCode(String taskCode) {
		Long taskId = new Long(0);
		List<Long> taskIds = this.findByHql(
				"select task.id from SaaTask task where task.taskCode=?",
				taskCode);
		if (taskIds.size() > 0) {
			taskId = taskIds.get(0);
		} else {
			throw new BusinessException( "系統中不存在該功能，功能號：" + taskCode,
					false);
		}
		return taskId;
	}
	

	public Page findTask(QueryRule queryRule, int pageNo, int pageSize){
		return super.find(queryRule, pageNo, pageSize);
	}
	

	public void updateTask(SaaTask saaTask) {
		Date date = new Date();
		saaTask.setUpdateTime(date);
		super.update(saaTask);

	}

	public SaaTask getTask(Map<String, Object> map) {
		return super.findUnique(map);
	}

	/**
	 * 获取全部功能
	 * */
	public List<SaaTask> getAllDate() {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addIsNotNull("taskCode");
		List<SaaTask> saataskList = super.find(SaaTask.class, queryRule);
		return saataskList;
	}

	public void setIdGenService(IdGenService idGenService) {
		this.idGenService = idGenService;
	}

}