package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.FetPolicyImportError;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
public interface FetPolicyImportErrorService {

	public int countFetPolicyImportError(Map params) throws SystemException, Exception;
	
	public Result findFetPolicyImportErrorByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findFetPolicyImportErrorByParams(Map params) throws SystemException, Exception;

	public Result findFetPolicyImportErrorByUK(String transactionId) throws SystemException, Exception;

	public Result updateFetPolicyImportError(FetPolicyImportError fetPolicyImportError) throws SystemException, Exception;

	public Result insertFetPolicyImportError(FetPolicyImportError fetPolicyImportError) throws SystemException, Exception;

	public Result removeFetPolicyImportError(String transactionId) throws SystemException, Exception;
	
}
