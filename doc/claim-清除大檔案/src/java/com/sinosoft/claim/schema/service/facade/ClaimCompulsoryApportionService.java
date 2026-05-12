package com.sinosoft.claim.schema.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.ClaimCompulsoryApportion;

/**
 * mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單
 */
public interface ClaimCompulsoryApportionService {
	/**
	 * 
	 */
	public void save(ClaimCompulsoryApportion claimCompulsoryApportion) throws Exception;
	
	/**
	 * @throws Exceptionuan
	 */
	public void save(List<ClaimCompulsoryApportion> list) throws Exception;
	
	/**
	 */
	public void delete(String id) throws Exception;

	/**
	 */
	public void update(ClaimCompulsoryApportion claimCompulsoryApportion) throws Exception;

	/**
	 */
	public ClaimCompulsoryApportion findClaimCompulsoryApportion(String id) throws Exception;
	
	/**
	 */
	public Page findClaimCompulsoryApportion(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 */
	public List<ClaimCompulsoryApportion> findClaimCompulsoryApportion(QueryRule queryRule) throws Exception;
	
}
