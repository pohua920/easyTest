package com.sinosoft.claim.schema.service.facade;
/**
 * 货运险标的信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCopyCargoItem;
import com.sinosoft.claim.schema.model.PrpCopyCargoItemId;

public interface PrpCopyCargoItemService {
	
	/**
	 * 保存货运险标的信息信息
	 * @param PrpCopyCargoItem ：传入的货运险标的信息
	 */
	public void save(PrpCopyCargoItem prpCopyCargoItem) throws Exception;
	
	/**
	 * 货运险标的信息信息
	 * @param list  :传入的货运险标的信息信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCopyCargoItem> list) throws Exception;
	
	/**
	 * 删除货运险标的信息信息
	 * @param PrpCopyCargoItemId ：传入的货运险标的信息编号
	 */
	public void delete(PrpCopyCargoItemId prpCopyCargoItemId) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的货运险标的信息页面信息
	 */
	public Page findPrpCopyCargoItem(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取货运险标的信息  的列表
	 * @param queryRule 查询对象
	 * @return 包含的货运险标的信息  的列表
	 */
	public List<PrpCopyCargoItem> findPrpCopyCargoItem(QueryRule queryRule) throws Exception;
	
}
