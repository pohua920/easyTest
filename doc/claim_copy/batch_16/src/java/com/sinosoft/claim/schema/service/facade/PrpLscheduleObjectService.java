package com.sinosoft.claim.schema.service.facade;
/**
 * 调度对象接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLscheduleObject;

public interface PrpLscheduleObjectService {
	
	/**
	 * 调度对象信息
	 * @param PrpLscheduleObject ：传入的调度对象
	 */
	public void save(PrpLscheduleObject prpLscheduleObject) throws Exception;
	
	/**
	 * 保存调度对象信息
	 * @param list  :传入的调度对象信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLscheduleObject> list) throws Exception;
	
	/**
	 * 删除调度对象信息
	 * @param policyNo ：传入的调度对象编号
	 */
	public void delete(String scheduleObjectID) throws Exception;

	/**
	 * 更新调度对象信息
	 * @param PrpLscheduleObject :传入需要更新的调度对象
	 */
	public void update(PrpLscheduleObject prpLscheduleObject) throws Exception;

	/**
	 * 根据调度对象编号查询出调度对象信息
	 * @param policyNo ：传入的调度对象编号
	 * @return 返回调度对象
	 */
	public PrpLscheduleObject findPrpLscheduleObject(String scheduleObjectID) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的调度对象页面信息
	 */
	public Page findPrpLscheduleObject(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取  调度对象的集合
	 * @param queryRule 查询对象
	 * @return 包含的调度对象  的集合
	 */
	public List<PrpLscheduleObject> findPrpLscheduleObject(QueryRule queryRule) throws Exception;
}
