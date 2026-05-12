package com.sinosoft.claim.schema.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.ClaimCompulsoryApplicant;
import com.sinosoft.claim.schema.service.facade.ClaimCompulsoryApplicantService;

/**
 * mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單
 */
public class ClaimCompulsoryApplicantServiceSpringImpl extends GenericDaoHibernate<ClaimCompulsoryApplicant, String> implements ClaimCompulsoryApplicantService {
	
	@Override
	public void save(ClaimCompulsoryApplicant claimCompulsoryApplicant) throws Exception {
		logger.info("保存claimCompulsoryApplicant信息");
		super.save(claimCompulsoryApplicant);
		
	}

	@Override
	public void save(List<ClaimCompulsoryApplicant> list) throws Exception {
		logger.info("保存claimCompulsoryApplicant信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(String Id) throws Exception {
		logger.info("删除claimCompulsoryApplicant信息编号为" + Id + "的oid信息");
		super.deleteByPK(ClaimCompulsoryApplicant.class, Id);
	}

	@Override
	public ClaimCompulsoryApplicant findClaimCompulsoryApplicant(String Id) throws Exception {
		logger.info("查询ClaimCompulsoryApplicant信息编号为" + Id + "的信息");
		return super.get(ClaimCompulsoryApplicant.class, Id);
	}

	@Override
	public Page findClaimCompulsoryApplicant(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取ClaimCompulsoryApplicant信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<ClaimCompulsoryApplicant> findClaimCompulsoryApplicant(QueryRule queryRule)
			throws Exception {
		// TODO Auto-generated method stub
		return super.find(queryRule);
	}

}
