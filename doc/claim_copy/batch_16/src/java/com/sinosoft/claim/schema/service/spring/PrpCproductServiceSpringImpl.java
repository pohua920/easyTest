package com.sinosoft.claim.schema.service.spring;

/**
 * 收费计划接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import java.util.List;
import com.sinosoft.claim.schema.model.PrpCproduct;
import com.sinosoft.claim.schema.model.PrpCproductId;
import com.sinosoft.claim.schema.service.facade.PrpCproductService;

public class PrpCproductServiceSpringImpl extends GenericDaoHibernate<PrpCproduct, PrpCproductId> implements PrpCproductService {

	public void save(PrpCproduct prpCproduct) throws Exception {
		logger.info("收费计划信息");
		super.save(prpCproduct);
	}

	public void save(List<PrpCproduct> list) throws Exception {
		logger.info("收费计划信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	public void delete(PrpCproductId prpCproductId) throws Exception {
		logger.info("删除收费计划编号为" + prpCproductId + "的收费计划");
		super.deleteByPK(PrpCproduct.class, prpCproductId);
	}

	public PrpCproduct findPrpCproduct(PrpCproductId prpCproductId) throws Exception {
		logger.info("查询收费计划编号为" + prpCproductId + "的收费计划");
		return super.get(PrpCproduct.class, prpCproductId);
	}

	public Page findPrpCproduct(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取收费计划列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	public List<PrpCproduct> findPrpCproduct(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
}
