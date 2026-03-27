package com.sinosoft.claim.schema.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.ClaimCompulsoryApplicant;

/**
 * mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單
 */
public interface ClaimCompulsoryApplicantService {
	/**
	 * 
	 */
	public void save(ClaimCompulsoryApplicant claimCompulsoryApplicant) throws Exception;
	
	/**
	 * @throws Exceptionuan
	 */
	public void save(List<ClaimCompulsoryApplicant> list) throws Exception;
	
	/**
	 */
	public void delete(String id) throws Exception;

	/**
	 */
	public void update(ClaimCompulsoryApplicant claimCompulsoryApplicant) throws Exception;

	/**
	 */
	public ClaimCompulsoryApplicant findClaimCompulsoryApplicant(String id) throws Exception;
	
	/**
	 */
	public Page findClaimCompulsoryApplicant(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 */
	public List<ClaimCompulsoryApplicant> findClaimCompulsoryApplicant(QueryRule queryRule) throws Exception;
	
}
