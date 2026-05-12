package com.sinosoft.claim.schema.service.facade;

/**
 * 查勘任务处理接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLcheckItem;
import com.sinosoft.claim.schema.model.PrpLcheckItemId;

public interface PrpLcheckItemService {

	/**
	 * 保存查勘任务处理信息
	 * @param prpLcheckItem ：传入的查勘任务处理
	 */
	public void save(PrpLcheckItem prpLcheckItem) throws Exception;

	/**
	 * 查勘任务处理信息
	 * @param list :传入的查勘任务处理信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLcheckItem> list) throws Exception;

	/**
	 * 删除查勘任务处理信息
	 * @param prpLcheckItemId ：传入的查勘任务处理编号
	 */
	public void delete(PrpLcheckItemId prpLcheckItemId) throws Exception;

	/**
	 * 更新查勘任务处理信息
	 * @param prpLcheckItem :传入需要更新的查勘任务处理
	 */
	public void update(PrpLcheckItem prpLcheckItem) throws Exception;

	/**
	 * 根据查勘任务处理编号查询出查勘任务处理信息
	 * @param prpLcheckItemId ：传入的查勘任务处理编号
	 * @return 返回查勘任务处理
	 */
	public PrpLcheckItem findPrpLcheckItem(PrpLcheckItemId prpLcheckItemId) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的查勘任务处理页面信息
	 */
	public Page findPrpLcheckItem(QueryRule queryRule, int pageNo, int pageSize) throws Exception;

	/**
	 * 根据查询对象获取 查勘任务处理页面信息 的列表
	 * @param queryRule 查询对象
	 * @return 包含的 查勘任务处理页面信息的列表
	 */
	public List<PrpLcheckItem> findPrpLcheckItem(QueryRule queryRule);

	/**
	 * 根据查勘任务处理编号查询出查勘任务处理信息
	 * @param certiNo ：传入的查勘任务处理编号
	 * @return 返回查勘任务处理
	 */
	public PrpLcheckItem findPrpLcheckItem(String certiNo) throws Exception;
}
