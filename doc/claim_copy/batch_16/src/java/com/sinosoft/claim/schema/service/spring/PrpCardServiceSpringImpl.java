package com.sinosoft.claim.schema.service.spring;
/**
 * 刷卡信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCard;
import com.sinosoft.claim.schema.service.facade.PrpCardService;

public class PrpCardServiceSpringImpl extends
GenericDaoHibernate<PrpCard, String> implements PrpCardService{

	@Override
	public void save(PrpCard PrpCard) throws Exception {
		logger.info("保存刷卡信息信息");
		super.save(PrpCard);
		
	}

	@Override
	public void save(List<PrpCard> list) throws Exception {
		logger.info("保存刷卡信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(String claimNo) throws Exception {
		logger.info("删除刷卡信息编号为" + claimNo + "的刷卡信息");
		super.deleteByPK(PrpCard.class, claimNo);
	}

	@Override
	public PrpCard findPrpCard(String claimNo) throws Exception {
		logger.info("查询刷卡信息编号为" + claimNo + "的刷卡信息");
		return super.get(PrpCard.class,claimNo);
	}
    
	@Override
	public Page findPrpCard(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取刷卡信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
		
	}

	@Override
	public List<PrpCard> findPrpCard(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

}
