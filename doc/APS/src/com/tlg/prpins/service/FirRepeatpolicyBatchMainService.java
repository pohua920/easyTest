package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirRepeatpolicyBatchMain;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：FIR0565，處理人員：CC009，需求單編號：FIR0565 住火_複保險通知轉檔作業 */
public interface FirRepeatpolicyBatchMainService {
	public Result findByParams(Map params) throws Exception;
	
	public Result removeFirRepeatpolicyBatchMain(FirRepeatpolicyBatchMain entity) throws SystemException,Exception;
	
	public Result insertFirRepeatpolicyBatchMain(FirRepeatpolicyBatchMain entity) throws SystemException,Exception;
	
	public Result findFirRepeatpolicyBatchMainByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result updateFirRepeatpolicyBatchMain(FirRepeatpolicyBatchMain entity) throws SystemException, Exception;
}
