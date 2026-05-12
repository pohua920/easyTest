package com.sinosoft.claim.schema.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.ClaimCompulsoryCase;
import com.sinosoft.claim.schema.service.facade.ClaimCompulsoryCaseService;

/**
 * mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單
 */
public class ClaimCompulsoryCaseServiceSpringImpl extends GenericDaoHibernate<ClaimCompulsoryCase, String> implements ClaimCompulsoryCaseService {
	
	@Override
	public void save(ClaimCompulsoryCase claimCompulsoryCase) throws Exception {
		logger.info("保存ClaimCompulsoryCase信息");
		super.save(claimCompulsoryCase);
		
	}

	@Override
	public void save(List<ClaimCompulsoryCase> list) throws Exception {
		logger.info("保存ClaimCompulsoryCase信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(String Id) throws Exception {
		logger.info("删除ClaimCompulsoryCase信息编号为" + Id + "的信息");
		super.deleteByPK(ClaimCompulsoryCase.class, Id);
	}

	@Override
	public ClaimCompulsoryCase findClaimCompulsoryCase(String Id) throws Exception {
		logger.info("查询ClaimCompulsoryCase信息编号为" + Id + "的信息");
		return super.get(ClaimCompulsoryCase.class, Id);
	}

	@Override
	public Page findClaimCompulsoryCase(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取ClaimCompulsoryCase信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<ClaimCompulsoryCase> findClaimCompulsoryCase(QueryRule queryRule)
			throws Exception {
		// TODO Auto-generated method stub
		return super.find(queryRule);
	}

}
