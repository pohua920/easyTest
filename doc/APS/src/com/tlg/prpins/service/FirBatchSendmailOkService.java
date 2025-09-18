package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchSendmailOk;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirBatchSendmailOkService {
	
	@SuppressWarnings("rawtypes")
	public int countFirBatchSendmailOk(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findFirBatchSendmailOkByParams(Map params) throws SystemException, Exception;
	
	public Result findFirBatchSendmailOkByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result updateFirBatchSendmailOk(FirBatchSendmailOk firBatchSendmailOk) throws SystemException, Exception;

	public Result insertFirBatchSendmailOk(FirBatchSendmailOk firBatchSendmailOk) throws SystemException, Exception;

	public Result removeFirBatchSendmailOk(BigDecimal oid) throws SystemException, Exception;
	
	public Result findFirBatchSendmailOkByOid(BigDecimal oid) throws SystemException, Exception;
	
}
