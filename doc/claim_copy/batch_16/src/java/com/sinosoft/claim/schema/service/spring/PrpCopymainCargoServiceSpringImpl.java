package com.sinosoft.claim.schema.service.spring;

/**
 * 货运险保单信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import java.util.List;
import com.sinosoft.claim.schema.model.PrpCopymainCargo;
import com.sinosoft.claim.schema.service.facade.PrpCopymainCargoService;

public class PrpCopymainCargoServiceSpringImpl extends GenericDaoHibernate<PrpCopymainCargo, String> implements PrpCopymainCargoService {

	public void save(PrpCopymainCargo prpCopymainCargo) throws Exception {
		logger.info("货运险保单信息信息");
		super.save(prpCopymainCargo);
	}

	public void save(List<PrpCopymainCargo> list) throws Exception {
		logger.info("货运险保单信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	public void delete(String endorseNo) throws Exception {
	}

	public PrpCopymainCargo findPrpCopymainCargo(String endorseNo) throws Exception {
		logger.info("查询货运险保单信息编号为" + endorseNo + "的货运险保单信息");
		return super.get(PrpCopymainCargo.class, endorseNo);
	}

	public Page findPrpCopymainCargo(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取货运险保单信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	public List<PrpCopymainCargo> findPrpCopymainCargo(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
}
