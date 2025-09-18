package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchmailExcludedata;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
/* mantis：FIR0369，處理人員：BJ085，需求單編號：FIR0369 增加電子保單不寄送單位業務來源設定 */
public interface FirBatchmailExcludedataService {
	
	public Result findFirBatchmailExcludedataByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	@SuppressWarnings("rawtypes")
	public Result findFirBatchmailExcludedataByParams(Map params) throws SystemException, Exception;
	
	public Result findFirBatchmailExcludedataByUk(Map params) throws SystemException, Exception;
	
	public Result insertFirBatchmailExcludedata(FirBatchmailExcludedata firBatchmailExcludedata) throws SystemException, Exception;
	
	public Result updateFirBatchmailExcludedata(FirBatchmailExcludedata firBatchmailExcludedata) throws SystemException, Exception;
}
