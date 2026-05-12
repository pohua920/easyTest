package com.sinosoft.claim.schema.service.facade;
/**
 * 调度任务接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLscheduleMain;
import com.sinosoft.claim.schema.model.PrpLscheduleMainId;

public interface PrpLscheduleMainService {
	
	/**
	 * 保存调度任务信息
	 * @param prpLscheduleMain ：传入的调度任务
	 */
	public void save(PrpLscheduleMain prpLscheduleMain) throws Exception;
	
	/**
	 * 调度任务信息
	 * @param list  :传入的调度任务信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLscheduleMain> list) throws Exception;
	
	/**
	 * 删除调度任务信息
	 * @param prpLscheduleMainId ：传入的调度任务编号
	 */
	public void delete(PrpLscheduleMainId prpLscheduleMainId) throws Exception;

	/**
	 * 更新调度任务信息
	 * @param prpLscheduleMain :传入需要更新的调度任务
	 */
	public void update(PrpLscheduleMain prpLscheduleMain) throws Exception;

	/**
	 * 根据调度任务编号查询出调度任务信息
	 * @param prpLscheduleMainId ：传入的调度任务编号
	 * @return 返回调度任务
	 */
	public PrpLscheduleMain findPrpLscheduleMain(PrpLscheduleMainId prpLscheduleMainId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的调度任务页面信息
	 */
	public Page findPrpLscheduleMain(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 调度任务页面信息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的 调度任务页面信息 的集合
	 */
	public List<PrpLscheduleMain> findPrpLscheduleMain(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据调度任务编号查询出调度任务信息
	 * @param certiNo ：传入的调度任务编号
	 * @return 返回调度任务
	 */
	public PrpLscheduleMain findPrpLscheduleMain(String certiNo) throws Exception;
}
