package com.sinosoft.claim.schema.service.spring;

/**
 * 外部机构信息表，包括银行帐号信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.DataUtils;

import java.util.List;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpLexternalAgency;
import com.sinosoft.claim.schema.model.PrpLexternalAgencyId;
import com.sinosoft.claim.schema.service.facade.PrpLexternalAgencyService;

public class PrpLexternalAgencyServiceSpringImpl extends GenericDaoHibernate<PrpLexternalAgency, PrpLexternalAgencyId> implements PrpLexternalAgencyService {

	@Override
	public void save(PrpLexternalAgency prpLexternalAgency) throws Exception {
		logger.info("保存外部机构信息表，包括银行帐号信息");
		super.save(prpLexternalAgency);

	}

	@Override
	public void save(List<PrpLexternalAgency> list) throws Exception {
		logger.info("保存外部机构信息表，包括银行帐号信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLexternalAgencyId prpLexternalAgencyId) throws Exception {
		logger.info("删除外部机构信息表，包括银行帐号信息编号为" + prpLexternalAgencyId + "的外部机构信息表，包括银行帐号信息");
		super.deleteByPK(PrpLexternalAgency.class, prpLexternalAgencyId);
	}

	@Override
	public PrpLexternalAgency findPrpLexternalAgency(PrpLexternalAgencyId prpLexternalAgencyId) throws Exception {
		logger.info("查询外部机构信息表，包括银行帐号信息编号为" + prpLexternalAgencyId + "的外部机构信息表，包括银行帐号信息");
		return super.get(PrpLexternalAgency.class, prpLexternalAgencyId);
	}

	@Override
	public Page findPrpLexternalAgency(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取外部机构信息表，包括银行帐号信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLexternalAgency> findPrpLexternalAgency(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 根据外部机构信息表，包括银行帐号信息编号查询出外部机构信息表，包括银行帐号信息
	 * @param comCode ：传入的外部机构信息表，包括银行帐号信息编号
	 * @return 返回外部机构信息表，包括银行帐号信息
	 */
	public PrpLexternalAgency findPrpLexternalAgency(String comCode) throws Exception {
		PrpLexternalAgency prpLexternalAgency = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.comCode", comCode);
		List<PrpLexternalAgency> resultList = super.find(queryRule);
		if (resultList != null && resultList.size() > 0) {
			prpLexternalAgency = resultList.get(0);
		}
		return prpLexternalAgency;
	}

	@Override
	public Page findByPage(String conditions, int pageNo, int pageSize) throws Exception {
		if (DataUtils.emptyToNull(conditions) == null) {
			conditions = " 1=1 ";
		}
		String sql = "select * from PrpLexternalAgency where " + conditions;
		return HibernateUtils.findPagebySql(super.getSession(), sql, pageNo, pageSize, PrpLexternalAgency.class);

	}
	@Override
	public List<?> findByConditions(String conditions) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule);
	}
}
