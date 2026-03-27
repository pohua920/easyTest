package com.sinosoft.claim.schema.service.spring;

/**
 * 货运险保单信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import java.util.List;
import com.sinosoft.claim.schema.model.PrpCmainCargo;
import com.sinosoft.claim.schema.service.facade.PrpCmainCargoService;

public class PrpCmainCargoServiceSpringImpl extends GenericDaoHibernate<PrpCmainCargo, String> implements PrpCmainCargoService {

	public void save(PrpCmainCargo prpCmainCargo) throws Exception {
		logger.info("货运险保单信息信息");
		super.save(prpCmainCargo);
	}

	public void save(List<PrpCmainCargo> list) throws Exception {
		logger.info("货运险保单信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	public void delete(String policyNo) throws Exception {
	}

	public PrpCmainCargo findPrpCmainCargo(String policyNo) throws Exception {
		logger.info("查询货运险保单信息编号为" + policyNo + "的货运险保单信息");
		return super.get(PrpCmainCargo.class, policyNo);
	}

	public Page findPrpCmainCargo(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取货运险保单信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	public List<PrpCmainCargo> findPrpCmainCargo(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
}
