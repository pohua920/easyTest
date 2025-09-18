package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirRepeatpolicyBatchDtl;
import com.tlg.util.Result;

/* mantis：FIR0565，處理人員：CC009，需求單編號：FIR0565 住火_複保險通知轉檔作業 */
public interface FirRepeatpolicyBatchDtlService {
	public Result findByParams(Map params) throws Exception;
	
	public Result removeFirRepeatpolicyBatchDtl(FirRepeatpolicyBatchDtl entity) throws SystemException,Exception;
	
	public Result insertFirRepeatpolicyBatchDtl(FirRepeatpolicyBatchDtl entity) throws SystemException,Exception;
	
	public Result selectForAps042Import(Map<String,String> params) throws SystemException,Exception;
}
