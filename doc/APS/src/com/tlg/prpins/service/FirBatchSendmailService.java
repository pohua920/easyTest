package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchSendmail;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/*mantis：CAR0507，處理人員：BJ085，需求單編號：CAR0507 承保系統新增電子保單產生追蹤機制(車險&旅平險)*/
public interface FirBatchSendmailService {
	
	@SuppressWarnings("rawtypes")
	public int countFirBatchSendmail(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findFirBatchSendmailByParams(Map params) throws SystemException, Exception;
	
	public Result findFirBatchSendmailByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result updateFirBatchSendmail(FirBatchSendmail firBatchSendmail) throws SystemException, Exception;

	public Result insertFirBatchSendmail(FirBatchSendmail firBatchSendmail) throws SystemException, Exception;

	public Result removeFirBatchSendmail(FirBatchSendmail firBatchSendmail) throws SystemException, Exception;
	
}
