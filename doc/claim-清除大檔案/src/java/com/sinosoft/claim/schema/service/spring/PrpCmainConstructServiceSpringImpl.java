package com.sinosoft.claim.schema.service.spring;

/**
 * 建安工险保单信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import java.util.List;
import com.sinosoft.claim.schema.model.PrpCmainConstruct;
import com.sinosoft.claim.schema.service.facade.PrpCmainConstructService;

public class PrpCmainConstructServiceSpringImpl extends GenericDaoHibernate<PrpCmainConstruct, String> implements PrpCmainConstructService {

	public void save(PrpCmainConstruct prpCmainConstruct) throws Exception {
		logger.info("建安工险保单信息信息");
		super.save(prpCmainConstruct);
	}

	public void save(List<PrpCmainConstruct> list) throws Exception {
		logger.info("建安工险保单信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	public void delete(String policyNo) throws Exception {
		logger.info("删除建安工险保单信息编号为" + policyNo + "的建安工险保单信息");
		super.deleteByPK(PrpCmainConstruct.class, policyNo);
	}

	public PrpCmainConstruct findPrpCmainConstruct(String policyNo) throws Exception {
		logger.info("查询建安工险保单信息编号为" + policyNo + "的建安工险保单信息");
		return super.get(PrpCmainConstruct.class, policyNo);
	}

	public Page findPrpCmainConstruct(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取建安工险保单信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	public List<PrpCmainConstruct> findPrpCmainConstruct(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
}
