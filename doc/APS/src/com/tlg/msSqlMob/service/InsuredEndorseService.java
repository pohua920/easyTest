package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.InsuredEndorse;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface InsuredEndorseService {

	public int countInsuredEndorse(Map params) throws SystemException, Exception;
	
	public Result findInsuredEndorseByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findInsuredEndorseByParams(Map params) throws SystemException, Exception;

	public Result findInsuredEndorseByUK(String transactionId) throws SystemException, Exception;

	public Result updateInsuredEndorse(InsuredEndorse insuredEndorse) throws SystemException, Exception;

	public Result insertInsuredEndorse(InsuredEndorse insuredEndorse) throws SystemException, Exception;

	public Result removeInsuredEndorse(String transactionId) throws SystemException, Exception;
	
}
