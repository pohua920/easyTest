package com.sinosoft.claim.schema.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.ClaimCompulsoryCase;

/**
 * mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單
 */
public interface ClaimCompulsoryCaseService {
	/**
	 * 
	 */
	public void save(ClaimCompulsoryCase claimCompulsoryCase) throws Exception;
	
	/**
	 * @throws Exceptionuan
	 */
	public void save(List<ClaimCompulsoryCase> list) throws Exception;
	
	/**
	 */
	public void delete(String id) throws Exception;

	/**
	 */
	public void update(ClaimCompulsoryCase claimCompulsoryCase) throws Exception;

	/**
	 */
	public ClaimCompulsoryCase findClaimCompulsoryCase(String id) throws Exception;
	
	/**
	 */
	public Page findClaimCompulsoryCase(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 */
	public List<ClaimCompulsoryCase> findClaimCompulsoryCase(QueryRule queryRule) throws Exception;
	
}
