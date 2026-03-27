package com.sinosoft.claim.schema.service.spring;
/**
 * PRPLMENU接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLmenu;
import com.sinosoft.claim.schema.service.facade.PrpLmenuService;

public class PrpLmenuServiceSpringImpl extends
GenericDaoHibernate<PrpLmenu, String> implements PrpLmenuService{

	@Override
	public void save(PrpLmenu prpLmenu) throws Exception {
		logger.info("保存PRPLMENU信息");
		super.save(prpLmenu);
		
	}

	@Override
	public void save(List<PrpLmenu> list) throws Exception {
		logger.info("保存PRPLMENU");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(String funcID) throws Exception {
		logger.info("删除PRPLMENU编号为" + funcID + "的PRPLMENU");
		super.deleteByPK(PrpLmenu.class, funcID);
	}

	@Override
	public PrpLmenu findPrpLmenu(String funcID) throws Exception {
		logger.info("查询PRPLMENU编号为" + funcID + "的PRPLMENU");
		return super.get(PrpLmenu.class,funcID);
	}
    
	@Override
	public Page findPrpLmenu(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取PRPLMENU列表信息");
		return super.find(queryRule, pageNo, pageSize);
		
	}

	@Override
	public List<PrpLmenu> findPrpLmenu(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

}
