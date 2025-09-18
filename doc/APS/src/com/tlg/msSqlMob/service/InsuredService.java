package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.Insured;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
public interface InsuredService {

	public int countInsured(Map params) throws SystemException, Exception;
	
	public Result findInsuredByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findInsuredByParams(Map params) throws SystemException, Exception;

	public Result findInsuredByUK(String transactionId) throws SystemException, Exception;

	public Result updateInsured(Insured insured) throws SystemException, Exception;

	public Result insertInsured(Insured insured) throws SystemException, Exception;

	public Result removeInsured(String transactionId) throws SystemException, Exception;
	
}
