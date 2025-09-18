package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.ClaimCompulsoryCase;
import com.tlg.util.Result;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
public interface ClaimCompulsoryCaseService {

	public int countClaimCompulsoryCase(Map params) throws SystemException, Exception;

	public Result findClaimCompulsoryCaseByParams(Map params) throws SystemException, Exception;

	public Result findClaimCompulsoryCaseByUK(String oid) throws SystemException, Exception;

	public Result updateClaimCompulsoryCase(ClaimCompulsoryCase claimCompulsoryCase) throws SystemException, Exception;

	public Result insertClaimCompulsoryCase(ClaimCompulsoryCase claimCompulsoryCase) throws SystemException, Exception;

	public Result removeClaimCompulsoryCase(String oid) throws SystemException, Exception;
	
}
