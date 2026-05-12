package com.sinosoft.claim.schema.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.ClaimCompulsoryCase;
import com.sinosoft.claim.schema.model.ClaimCompulsoryCharges;
import com.sinosoft.claim.schema.service.facade.ClaimCompulsoryCaseService;
import com.sinosoft.claim.schema.service.facade.ClaimCompulsoryChargesService;

/**
 * mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單
 */
public class ClaimCompulsoryChargesServiceSpringImpl extends GenericDaoHibernate<ClaimCompulsoryCharges, String> implements ClaimCompulsoryChargesService {
	
	@Override
	public void save(ClaimCompulsoryCharges claimCompulsoryCharges) throws Exception {
		logger.info("保存ClaimCompulsoryCharges信息");
		super.save(claimCompulsoryCharges);
		
	}

	@Override
	public void save(List<ClaimCompulsoryCharges> list) throws Exception {
		logger.info("保存ClaimCompulsoryCharges信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(String Id) throws Exception {
		logger.info("删除ClaimCompulsoryCharges信息编号为" + Id + "的信息");
		super.deleteByPK(ClaimCompulsoryCharges.class, Id);
	}

	@Override
	public ClaimCompulsoryCharges findClaimCompulsoryCharges(String Id) throws Exception {
		logger.info("查询ClaimCompulsoryCharges信息编号为" + Id + "的信息");
		return super.get(ClaimCompulsoryCharges.class, Id);
	}

	@Override
	public Page findClaimCompulsoryCharges(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取ClaimCompulsoryCharges信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<ClaimCompulsoryCharges> findClaimCompulsoryCharges(QueryRule queryRule)
			throws Exception {
		// TODO Auto-generated method stub
		return super.find(queryRule);
	}

}
