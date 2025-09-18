package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchSendmailDetail;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirBatchSendmailDetailService {
	
	@SuppressWarnings("rawtypes")
	public int countFirBatchSendmailDetail(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findFirBatchSendmailDetailByParams(Map params) throws SystemException, Exception;
	
	public Result findFirBatchSendmailDetailByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result updateFirBatchSendmailDetail(FirBatchSendmailDetail firBatchSendmailDetail) throws SystemException, Exception;

	public Result insertFirBatchSendmailDetail(FirBatchSendmailDetail firBatchSendmailDetail) throws SystemException, Exception;

	public Result removeFirBatchSendmailDetail(BigDecimal oid) throws SystemException, Exception;
	
	public Result findFirBatchSendmailDetailByOid(BigDecimal oid) throws SystemException, Exception;
	
	//mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險)
	public Result findRiskcodeForNoticeEmail() throws SystemException, Exception;
	
}
