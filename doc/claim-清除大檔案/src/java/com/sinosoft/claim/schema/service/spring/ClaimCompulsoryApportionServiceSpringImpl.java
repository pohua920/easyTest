package com.sinosoft.claim.schema.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.ClaimCompulsoryApportion;
import com.sinosoft.claim.schema.service.facade.ClaimCompulsoryApportionService;

/**
 * mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單
 */
public class ClaimCompulsoryApportionServiceSpringImpl extends GenericDaoHibernate<ClaimCompulsoryApportion, String> implements ClaimCompulsoryApportionService {
	
	@Override
	public void save(ClaimCompulsoryApportion claimCompulsoryApportion) throws Exception {
		logger.info("保存ClaimCompulsoryApportion信息");
		super.save(claimCompulsoryApportion);
		
	}

	@Override
	public void save(List<ClaimCompulsoryApportion> list) throws Exception {
		logger.info("保存ClaimCompulsoryApportion信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(String Id) throws Exception {
		logger.info("删除ClaimCompulsoryApportion信息编号为" + Id + "的信息");
		super.deleteByPK(ClaimCompulsoryApportion.class, Id);
	}

	@Override
	public ClaimCompulsoryApportion findClaimCompulsoryApportion(String Id) throws Exception {
		logger.info("查询ClaimCompulsoryApportion信息编号为" + Id + "的信息");
		return super.get(ClaimCompulsoryApportion.class, Id);
	}

	@Override
	public Page findClaimCompulsoryApportion(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取ClaimCompulsoryApportion信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<ClaimCompulsoryApportion> findClaimCompulsoryApportion(QueryRule queryRule)
			throws Exception {
		// TODO Auto-generated method stub
		return super.find(queryRule);
	}

}
