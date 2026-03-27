package com.sinosoft.claim.schema.service.spring;
/**
 * 担保函（船舶）接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLassure;
import com.sinosoft.claim.schema.service.facade.PrpLassureService;

public class PrpLassureServiceSpringImpl extends
GenericDaoHibernate<PrpLassure, String> implements PrpLassureService{

	@Override
	public void save(PrpLassure prpLassure) throws Exception {
		logger.info("保存担保函（船舶）信息");
		super.save(prpLassure);
		
	}

	@Override
	public void save(List<PrpLassure> list) throws Exception {
		logger.info("保存担保函（船舶）");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(String assureNo) throws Exception {
		logger.info("删除担保函（船舶）编号为" + assureNo + "的担保函（船舶）");
		super.deleteByPK(PrpLassure.class, assureNo);
	}

	@Override
	public PrpLassure findPrpLassure(String assureNo) throws Exception {
		logger.info("查询担保函（船舶）编号为" + assureNo + "的担保函（船舶）");
		return super.get(PrpLassure.class,assureNo);
	}
    
	@Override
	public Page findPrpLassure(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取担保函（船舶）列表信息");
		return super.find(queryRule, pageNo, pageSize);
		
	}

	@Override
	public List<PrpLassure> findPrpLassure(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

}
