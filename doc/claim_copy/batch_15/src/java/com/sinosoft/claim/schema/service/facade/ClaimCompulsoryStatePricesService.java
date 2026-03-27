package com.sinosoft.claim.schema.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.ClaimCompulsoryCharges;
import com.sinosoft.claim.schema.model.ClaimCompulsoryStatePrices;

/**
 * mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單
 */
public interface ClaimCompulsoryStatePricesService {
	/**
	 * 
	 */
	public void save(ClaimCompulsoryStatePrices claimCompulsoryStatePrices) throws Exception;
	
	/**
	 * @throws Exceptionuan
	 */
	public void save(List<ClaimCompulsoryStatePrices> list) throws Exception;
	
	/**
	 */
	public void delete(String id) throws Exception;

	/**
	 */
	public void update(ClaimCompulsoryStatePrices claimCompulsoryStatePrices) throws Exception;

	/**
	 */
	public ClaimCompulsoryStatePrices findClaimCompulsoryStatePrices(String id) throws Exception;
	
	/**
	 */
	public Page findClaimCompulsoryStatePrices(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 */
	public List<ClaimCompulsoryStatePrices> findClaimCompulsoryStatePrices(QueryRule queryRule) throws Exception;
	
}
