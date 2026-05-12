package com.sinosoft.sys.platform.power.service.facade;

import java.util.List;

import com.sinosoft.sys.platform.power.model.SaaTask;



/**
 * 权限控制辅助方法接口�?
 * @author �п��� 
 *
 */

public interface SaaAuthTaskService{
	/**
	 * 保存�?有的功能ID;
	 * */
	public void saveSaaAuthTask(List<SaaTask> authList,String userCode);
	
}
