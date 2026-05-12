package com.sinosoft.claim.schema.service.facade;
/**
 * 调度任务/查勘任务接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLscheduleMainWF;
import com.sinosoft.claim.schema.model.PrpLscheduleMainWFId;

public interface PrpLscheduleMainWFService {
	
	/**
	 * 保存调度任务/查勘任务信息
	 * @param prpLscheduleMainWF ：传入的调度任务/查勘任务
	 */
	public void save(PrpLscheduleMainWF prpLscheduleMainWF) throws Exception;
	
	/**
	 * 保存调度任务/查勘任务信息
	 * @param list:保存调度任务/查勘任务信息
	 */
	public void save(List<PrpLscheduleMainWF> list) throws Exception;
	
	/**
	 * 删除调度任务/查勘任务信息
	 * @param prpLscheduleMainWFId ：传入的调度任务/查勘任务编号
	 */
	public void delete(PrpLscheduleMainWFId prpLscheduleMainWFId) throws Exception;

	/**
	 * 更新调度任务/查勘任务信息
	 * @param prpLscheduleMainWF :传入需要更新的调度任务/查勘任务
	 */
	public void update(PrpLscheduleMainWF prpLscheduleMainWF) throws Exception;

	/**
	 * 根据调度任务/查勘任务编号查询出调度任务/查勘任务信息
	 * @param prpLscheduleMainWFId ：传入的调度任务/查勘任务编号
	 * @return 返回调度任务/查勘任务
	 */
	public PrpLscheduleMainWF findPrpLscheduleMainWF(PrpLscheduleMainWFId prpLscheduleMainWFId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的调度任务/查勘任务页面信息
	 */
	public Page findPrpLscheduleMainWF(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 调度任务/查勘任务信息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的  调度任务/查勘任务信息的集合
	 */
	public List<PrpLscheduleMainWF> findPrpLscheduleMainWF(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据调度任务/查勘任务编号查询出调度任务/查勘任务信息
	 * @param prpLscheduleMainWFId ：传入的调度任务/查勘任务编号
	 * @return 返回调度任务/查勘任务
	 */
	public PrpLscheduleMainWF findPrpLscheduleMainWF(int scheduleID,String registNo) throws Exception;
	/**
	 * 保存调度任务/查勘任务信息
	 * @param list:保存调度任务/查勘任务信息
	 */
	public void saveOrUpdate(List<PrpLscheduleMainWF> list) throws Exception;
	/**
	 * 保存调度任务/查勘任务信息
	 * @param list:保存调度任务/查勘任务信息
	 */
	public void saveOrUpdate(PrpLscheduleMainWF prpLscheduleMainWF) throws Exception;
}
