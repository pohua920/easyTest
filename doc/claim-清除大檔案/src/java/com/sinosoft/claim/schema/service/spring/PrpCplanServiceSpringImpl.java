package com.sinosoft.claim.schema.service.spring;

/**
 * 收费计划接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import java.util.List;
import com.sinosoft.claim.schema.model.PrpCplan;
import com.sinosoft.claim.schema.model.PrpCplanId;
import com.sinosoft.claim.schema.service.facade.PrpCplanService;

public class PrpCplanServiceSpringImpl extends GenericDaoHibernate<PrpCplan, PrpCplanId> implements PrpCplanService {

	public void save(PrpCplan prpCplan) throws Exception {
		logger.info("收费计划信息");
		super.save(prpCplan);
	}

	public void save(List<PrpCplan> list) throws Exception {
		logger.info("收费计划信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	public void delete(PrpCplanId prpCplanId) throws Exception {
		logger.info("删除收费计划编号为" + prpCplanId + "的收费计划");
		super.deleteByPK(PrpCplan.class, prpCplanId);
	}

	public PrpCplan findPrpCplan(PrpCplanId prpCplanId) throws Exception {
		logger.info("查询收费计划编号为" + prpCplanId + "的收费计划");
		return super.get(PrpCplan.class, prpCplanId);
	}

	public Page findPrpCplan(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取收费计划列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	public List<PrpCplan> findPrpCplan(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
}
