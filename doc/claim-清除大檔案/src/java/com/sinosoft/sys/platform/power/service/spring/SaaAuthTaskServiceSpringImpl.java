package com.sinosoft.sys.platform.power.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.sys.platform.power.model.SaaAuthTask;
import com.sinosoft.sys.platform.power.model.SaaTask;
import com.sinosoft.sys.platform.power.service.facade.SaaAuthTaskService;




/**
 * 权限控制辅助方法接口�?
 * @author �п��� 
 *
 */

public class SaaAuthTaskServiceSpringImpl extends GenericDaoHibernate<SaaAuthTask,Long>
implements SaaAuthTaskService{
	/** 保存�?有的功能ID */
	public void saveSaaAuthTask(List<SaaTask> authList,String userCode){
		List<SaaAuthTask> authTaskList = new ArrayList<SaaAuthTask>(0);
		for(SaaTask saaTask:authList){
			SaaAuthTask saaAuthTask=new SaaAuthTask();
			saaAuthTask.setUserCode(userCode);
			saaAuthTask.setSaaTask(saaTask);
			authTaskList.add(saaAuthTask);
		}
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("userCode", userCode);
		List<SaaAuthTask> saataskList = super.find(SaaAuthTask.class,queryRule);
		super.deleteAll(saataskList);
		super.saveAll(authTaskList);
	}
}
