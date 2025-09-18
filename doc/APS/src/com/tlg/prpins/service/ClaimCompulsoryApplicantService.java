package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.ClaimCompulsoryApplicant;
import com.tlg.util.Result;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
public interface ClaimCompulsoryApplicantService {

	public int countClaimCompulsoryApplicant(Map params) throws SystemException, Exception;

	public Result findClaimCompulsoryApplicantByParams(Map params) throws SystemException, Exception;

	public Result findClaimCompulsoryApplicantByUK(String oid) throws SystemException, Exception;

	public Result updateClaimCompulsoryApplicant(ClaimCompulsoryApplicant claimCompulsoryApplicant) throws SystemException, Exception;

	public Result insertClaimCompulsoryApplicant(ClaimCompulsoryApplicant claimCompulsoryApplicant) throws SystemException, Exception;

	public Result removeClaimCompulsoryApplicant(String oid) throws SystemException, Exception;
	
}
