package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.ClaimCompulsoryStatePrices;
import com.tlg.util.Result;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
public interface ClaimCompulsoryStatePricesService {

	public int countClaimCompulsoryStatePrices(Map params) throws SystemException, Exception;

	public Result findClaimCompulsoryStatePricesByParams(Map params) throws SystemException, Exception;

	public Result findClaimCompulsoryStatePricesByUK(String oid) throws SystemException, Exception;

	public Result updateClaimCompulsoryStatePrices(ClaimCompulsoryStatePrices claimCompulsoryStatePrices) throws SystemException, Exception;

	public Result insertClaimCompulsoryStatePrices(ClaimCompulsoryStatePrices claimCompulsoryStatePrices) throws SystemException, Exception;

	public Result removeClaimCompulsoryStatePrices(String oid) throws SystemException, Exception;
	
}
