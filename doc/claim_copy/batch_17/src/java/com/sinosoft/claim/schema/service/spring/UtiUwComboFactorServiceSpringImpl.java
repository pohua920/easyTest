package com.sinosoft.claim.schema.service.spring;
/**
 * 组合因子表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.UtiUwComboFactor;
import com.sinosoft.claim.schema.model.UtiUwComboFactorId;
import com.sinosoft.claim.schema.service.facade.UtiUwComboFactorService;

public class UtiUwComboFactorServiceSpringImpl extends GenericDaoHibernate<UtiUwComboFactor, UtiUwComboFactorId> implements UtiUwComboFactorService {

	@Override
	public List<?> findByConditions(String conditions, int pageNo, int rowsPerPage) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule, pageNo, rowsPerPage).getResult();
	}

	@Override
	public void delete(UtiUwComboFactorId utiUwComboFactorId) throws Exception {
		super.deleteByPK(utiUwComboFactorId);
		logger.info("删除组合因子编号为" + utiUwComboFactorId + "的组合因子信息");
	}

	@Override
	public UtiUwComboFactor findUtiUwComboFactor(
			UtiUwComboFactorId utiUwComboFactorId) throws Exception {
		logger.info("查询组合因子编号为" + utiUwComboFactorId + "的组合因子信息");
		return super.get(UtiUwComboFactor.class,utiUwComboFactorId);
	}

	@Override
	public Page findUtiUwComboFactor(QueryRule queryRule, int pageNo,
			int pageSize) throws Exception {
		logger.info("获取组合因子列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<UtiUwComboFactor> findUtiUwComboFactor(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}

	@Override
	public void save(UtiUwComboFactor utiUwComboFactor) throws Exception {
		logger.info("保存组合因子信息");
		super.save(utiUwComboFactor);
	}

	@Override
	public void save(List<UtiUwComboFactor> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

}
