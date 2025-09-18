package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirTmpDatacheck;
import com.tlg.util.Result;

public interface FirTmpDatacheckService {
	/* mantis：FIR0225，處理人員：BJ085，需求單編號：FIR0225 稽核議題調整-過往資料檢核 start */
	public int countFirTmpDatacheck(Map params) throws SystemException, Exception;
	
	public Result findFirTmpDatacheckByParams(Map params) throws SystemException, Exception;
	
	public Result findBatchesFirTmpDatacheckByParams(Map params) throws SystemException, Exception;
	
	public Result updateFirTmpDatacheckByOid(FirTmpDatacheck firTmpDatacheck) throws SystemException, Exception;
	
}
