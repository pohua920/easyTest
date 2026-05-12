package com.sinosoft.claim.schema.service.spring;
/**
 * 代赔数据转出确认接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLclaimApprov;
import com.sinosoft.claim.schema.service.facade.PrpLclaimApprovService;

public class PrpLclaimApprovServiceSpringImpl extends
GenericDaoHibernate<PrpLclaimApprov, String> implements PrpLclaimApprovService{

	@Override
	public void save(PrpLclaimApprov prpLclaimApprov) throws Exception {
		logger.info("保存代赔数据转出确认信息");
		super.save(prpLclaimApprov);
		
	}

	@Override
	public void save(List<PrpLclaimApprov> list) throws Exception {
		logger.info("保存代赔数据转出确认");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(String registno) throws Exception {
		logger.info("删除代赔数据转出确认编号为" + registno + "的代赔数据转出确认");
		super.deleteByPK(PrpLclaimApprov.class, registno);
	}

	@Override
	public PrpLclaimApprov findPrpLclaimApprov(String registno) throws Exception {
		logger.info("查询代赔数据转出确认编号为" + registno + "的代赔数据转出确认");
		return super.get(PrpLclaimApprov.class,registno);
	}
    
	@Override
	public Page findPrpLclaimApprov(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取代赔数据转出确认列表信息");
		return super.find(queryRule, pageNo, pageSize);
		
	}

	@Override
	public List<PrpLclaimApprov> findPrpLclaimApprov(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

}
