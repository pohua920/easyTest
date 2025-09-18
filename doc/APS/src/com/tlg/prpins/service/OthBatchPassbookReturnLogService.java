package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.OthBatchPassbookReturnLog;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：OTH0138，處理人員：CC009，需求單編號：OTH0138 保發中心_保單存摺回饋檔回存資料庫規格 */
public interface OthBatchPassbookReturnLogService {
	
	public Result findOthBatchPassbookReturnLogByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public int countOthBatchPassbookReturnLog(Map params) throws SystemException, Exception;
	
	public Result insertOthBatchPassbookReturnLog(OthBatchPassbookReturnLog othBatchPassbookReturnLog) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findOthBatchPassbookReturnLogByParams(Map params) throws SystemException, Exception;
	
	public Result updateOthBatchPassbookReturnLog(OthBatchPassbookReturnLog othBatchPassbookReturnLog) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findOthBatchPassbookReturnLogByUK(Map params) throws SystemException, Exception;

}
