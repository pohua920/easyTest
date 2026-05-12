package com.sinosoft.claim.common.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.common.service.facade.PrpPitemCarService;
import com.sinosoft.claim.schema.model.PrpPitemCar;
import com.sinosoft.claim.schema.model.PrpPitemCarId;

public class PrpPitemCarServiceSpringImpl extends GenericDaoHibernate<PrpPitemCar, PrpPitemCarId> implements PrpPitemCarService {
	/**
	 * 按条件查询多条数据
	 * @param conditions 查询条件
	 * @param pageNo 页号
	 * @param rowsPerPage 每页的行数
	 * @return Collection
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PrpPitemCar> findByConditions(String conditions, int pageNo, int rowsPerPage) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule, pageNo, rowsPerPage).getResult();
	}

	/**
	 * 插入一条数据
	 * @param prpPitem_carDto prpPitem_carDto
	 * @throws Exception
	 */
	@Override
	public void insert(PrpPitemCar prpPitemcar) throws Exception {
		super.save(prpPitemcar);
	}

}
