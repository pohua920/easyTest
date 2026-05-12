/**
 * 
 */
package com.sinosoft.claim.schema.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCopyCargoItem;
import com.sinosoft.claim.schema.model.PrpCopyCargoItemId;
import com.sinosoft.claim.schema.service.facade.PrpCopyCargoItemService;

/**
 * 货运险标的信息接口
 * @author 中科软
 */
public class PrpCopyCargoItemServiceSpringImpl extends GenericDaoHibernate<PrpCopyCargoItem, PrpCopyCargoItemId> implements PrpCopyCargoItemService {

	/**
	 * 保存货运险标的信息信息
	 * @param PrpCopyCargoItem ：传入的货运险标的信息
	 */
	@Override
	public void save(PrpCopyCargoItem prpCopyCargoItem) throws Exception {
		super.save(prpCopyCargoItem);
	}

	/**
	 * 货运险标的信息信息
	 * @param list  :传入的货运险标的信息信息集合
	 * @throws Exceptionuan
	 */
	@Override
	public void save(List<PrpCopyCargoItem> list) throws Exception {
		super.saveAll(list);
	}

	/**
	 * 删除货运险标的信息信息
	 * @param PrpCopyCargoItemId ：传入的货运险标的信息编号
	 */
	@Override
	public void delete(PrpCopyCargoItemId prpCopyCargoItemId) throws Exception {
		super.delete(prpCopyCargoItemId);
	}

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的货运险标的信息页面信息
	 */
	@Override
	public Page findPrpCopyCargoItem(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 根据查询对象获取货运险标的信息  的列表
	 * @param queryRule 查询对象
	 * @return 包含的货运险标的信息  的列表
	 */
	@Override
	public List<PrpCopyCargoItem> findPrpCopyCargoItem(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

}
