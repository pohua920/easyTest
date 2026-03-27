package com.sinosoft.claim.schema.service.spring;

/**
 * 财产险标的信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import java.util.List;
import com.sinosoft.claim.schema.model.PrpCitemProp;
import com.sinosoft.claim.schema.model.PrpCitemPropId;
import com.sinosoft.claim.schema.service.facade.PrpCitemPropService;

public class PrpCitemPropServiceSpringImpl extends GenericDaoHibernate<PrpCitemProp, PrpCitemPropId> implements PrpCitemPropService {

	public void save(PrpCitemProp prpCitemProp) throws Exception {
		logger.info("财产险标的信息信息");
		super.save(prpCitemProp);
	}

	public void save(List<PrpCitemProp> list) throws Exception {
		logger.info("财产险标的信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	public void delete(PrpCitemPropId prpCitemPropId) throws Exception {
		logger.info("删除财产险标的信息编号为" + prpCitemPropId + "的财产险标的信息");
		super.deleteByPK(PrpCitemProp.class, prpCitemPropId);
	}

	public PrpCitemProp findPrpCitemProp(PrpCitemPropId prpCitemPropId) throws Exception {
		logger.info("查询财产险标的信息编号为" + prpCitemPropId + "的财产险标的信息");
		return super.get(PrpCitemProp.class, prpCitemPropId);
	}

	public Page findPrpCitemProp(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取财产险标的信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	public List<PrpCitemProp> findPrpCitemProp(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
}
