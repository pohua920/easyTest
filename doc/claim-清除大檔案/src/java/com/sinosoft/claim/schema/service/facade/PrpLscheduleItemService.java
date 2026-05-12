package com.sinosoft.claim.schema.service.facade;
/**
 * 调度任务标的接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLscheduleItem;
import com.sinosoft.claim.schema.model.PrpLscheduleItemId;

public interface PrpLscheduleItemService {
	
	/**
	 * 保存调度任务标的信息
	 * @param prpLscheduleItem ：传入的调度任务标的
	 */
	public void save(PrpLscheduleItem prpLscheduleItem) throws Exception;
	
	/**
	 * 删除调度任务标的信息
	 * @param prpLscheduleItemId ：传入的调度任务标的编号
	 */
	public void delete(PrpLscheduleItemId prpLscheduleItemId) throws Exception;

	/**
	 * 更新调度任务标的信息
	 * @param prpLscheduleItem :传入需要更新的调度任务标的
	 */
	public void update(PrpLscheduleItem prpLscheduleItem) throws Exception;

	/**
	 * 根据调度任务标的编号查询出调度任务标的信息
	 * @param prpLscheduleItemId ：传入的调度任务标的编号
	 * @return 返回调度任务标的
	 */
	public PrpLscheduleItem findPrpLscheduleItem(PrpLscheduleItemId prpLscheduleItemId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的调度任务标的页面信息
	 */
	public Page findPrpLscheduleItem(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 调度任务标的信息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的  调度任务标的信息的集合
	 */
	public List<PrpLscheduleItem> findPrpLscheduleItem(QueryRule queryRule) throws Exception;
	/**
	 * 保存调度任务标的信息
	 * @param list:保存调度任务标的信息集合
	 */
	public void insertAll(List<PrpLscheduleItem> list) throws Exception;
	
	/**
	 * @param prpLverifyLoss
	 * @throws Exception
	 * 保存或者修改，如果对象在数据库中不存在就保存对象，如果存在就更新对象
	 */
    public void saveOrUpdate(PrpLscheduleItem prpLscheduleItem) throws Exception;
    /**
	 * 删除调度任务标的信息
	 * @param prpLscheduleItemId ：传入的调度任务标的编号
	 */
	public void deleteAll(List<PrpLscheduleItem> prpLscheduleItemList);
	
	/**
	 * 先删除后插入，放在一个方法中
	 * @param list
	 * @throws Exception
	 */
	public void saveAndDelete(List<PrpLscheduleItem> list)throws Exception;
}
