package com.sinosoft.claim.common.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sinosoft.claim.common.service.facade.PrpDcodeRiskService;
import com.sinosoft.claim.schema.model.PrpDcodeRisk;
import com.sinosoft.claim.schema.model.PrpDcodeRiskId;

public class PrpDcodeRiskServiceServiceImpl extends GenericDaoHibernate<PrpDcodeRisk, PrpDcodeRiskId> implements PrpDcodeRiskService {

	/**
	 * 更具条件查询prpdcoderisk
	 * @param conditions 查询条件
	 * @return 返回数据集
	 * @throws Exception
	 */
	@Override
	public List<PrpDcodeRisk> findByConditions(String conditions) throws Exception {
		return findByConditions(conditions, 0, 0);
	}
	/**
	 * 更具条件查询prpdcoderisk
	 * @param conditions 查询条件
	 * @param pageNo 起始页
	 * @param rowsPerPage 每页显示的条数
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<PrpDcodeRisk> findByConditions(String conditions, int pageNo, int rowsPerPage) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		Page page = super.find(queryRule,pageNo,rowsPerPage);
		List<PrpDcodeRisk> list = new ArrayList<PrpDcodeRisk>();
		for (Iterator<?> iterator = page.getResult().iterator(); iterator.hasNext();) {
			PrpDcodeRisk prpDcodeRisk = (PrpDcodeRisk) iterator.next();
			list.add(prpDcodeRisk);
		}
		return list;
	}

}
