/**
 * 
 */
package com.sinosoft.claim.schema.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCCargoItem;
import com.sinosoft.claim.schema.model.PrpCCargoItemId;
import com.sinosoft.claim.schema.service.facade.PrpCCargoItemService;

/**
 * 货运险标的信息接口
 * @author 中科软
 */
public class PrpCCargoItemServiceSpringImpl extends GenericDaoHibernate<PrpCCargoItem, PrpCCargoItemId> implements PrpCCargoItemService {

	/**
	 * 保存货运险标的信息信息
	 * @param PrpCCargoItem ：传入的货运险标的信息
	 */
	@Override
	public void save(PrpCCargoItem prpCCargoItem) throws Exception {
		super.save(prpCCargoItem);
	}

	/**
	 * 货运险标的信息信息
	 * @param list  :传入的货运险标的信息信息集合
	 * @throws Exceptionuan
	 */
	@Override
	public void save(List<PrpCCargoItem> list) throws Exception {
		super.saveAll(list);
	}

	/**
	 * 删除货运险标的信息信息
	 * @param PrpCCargoItemId ：传入的货运险标的信息编号
	 */
	@Override
	public void delete(PrpCCargoItem prpCCargoItem) throws Exception {
		super.delete(prpCCargoItem);
	}

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的货运险标的信息页面信息
	 */
	@Override
	public Page findPrpCCargoItem(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 根据查询对象获取货运险标的信息  的列表
	 * @param queryRule 查询对象
	 * @return 包含的货运险标的信息  的列表
	 */
	@Override
	public List<PrpCCargoItem> findPrpCCargoItem(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

}
