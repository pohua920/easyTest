package com.sinosoft.claim.schema.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.ClaimCompulsoryCase;
import com.sinosoft.claim.schema.model.ClaimCompulsoryCharges;
import com.sinosoft.claim.schema.model.ClaimCompulsoryStatePrices;
import com.sinosoft.claim.schema.service.facade.ClaimCompulsoryCaseService;
import com.sinosoft.claim.schema.service.facade.ClaimCompulsoryChargesService;
import com.sinosoft.claim.schema.service.facade.ClaimCompulsoryStatePricesService;

/**
 * mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單
 */
public class ClaimCompulsoryStatePricesServiceSpringImpl extends GenericDaoHibernate<ClaimCompulsoryStatePrices, String> implements ClaimCompulsoryStatePricesService {
	
	@Override
	public void save(ClaimCompulsoryStatePrices claimCompulsoryStatePrices) throws Exception {
		logger.info("保存ClaimCompulsoryStatePrices信息");
		super.save(claimCompulsoryStatePrices);
		
	}

	@Override
	public void save(List<ClaimCompulsoryStatePrices> list) throws Exception {
		logger.info("保存ClaimCompulsoryStatePrices信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(String Id) throws Exception {
		logger.info("删除ClaimCompulsoryStatePrices信息编号为" + Id + "的信息");
		super.deleteByPK(ClaimCompulsoryStatePrices.class, Id);
	}

	@Override
	public ClaimCompulsoryStatePrices findClaimCompulsoryStatePrices(String Id) throws Exception {
		logger.info("查询ClaimCompulsoryStatePrices信息编号为" + Id + "的信息");
		return super.get(ClaimCompulsoryStatePrices.class, Id);
	}

	@Override
	public Page findClaimCompulsoryStatePrices(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取ClaimCompulsoryStatePrices信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<ClaimCompulsoryStatePrices> findClaimCompulsoryStatePrices(QueryRule queryRule)
			throws Exception {
		// TODO Auto-generated method stub
		return super.find(queryRule);
	}

}
