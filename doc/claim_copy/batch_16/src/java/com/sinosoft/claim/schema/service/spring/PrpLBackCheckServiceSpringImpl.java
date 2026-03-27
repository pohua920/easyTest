package com.sinosoft.claim.schema.service.spring;
/**
 * PRPLBACKCHECK接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLBackCheck;
import com.sinosoft.claim.schema.service.facade.PrpLBackCheckService;

public class PrpLBackCheckServiceSpringImpl extends
GenericDaoHibernate<PrpLBackCheck, String> implements PrpLBackCheckService{

	@Override
	public void save(PrpLBackCheck prpLBackCheck) throws Exception {
		logger.info("保存PRPLBACKCHECK信息");
		super.save(prpLBackCheck);
		
	}

	@Override
	public void save(List<PrpLBackCheck> list) throws Exception {
		logger.info("保存PRPLBACKCHECK");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(String registno) throws Exception {
		logger.info("删除PRPLBACKCHECK编号为" + registno + "的PRPLBACKCHECK");
		super.deleteByPK(PrpLBackCheck.class, registno);
	}

	@Override
	public PrpLBackCheck findPrpLBackCheck(String registno) throws Exception {
		logger.info("查询PRPLBACKCHECK编号为" + registno + "的PRPLBACKCHECK");
		return super.get(PrpLBackCheck.class,registno);
	}
    
	@Override
	public Page findPrpLBackCheck(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取PRPLBACKCHECK列表信息");
		return super.find(queryRule, pageNo, pageSize);
		
	}

	@Override
	public List<PrpLBackCheck> findPrpLBackCheck(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

}
