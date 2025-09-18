package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.OthBatchPassbook;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：OTH0127，處理人員：CC009，需求單編號：OTH0127 保發中心_保單存摺產生排程規格 */
public interface OthBatchPassbookService {
	
	public Result findOthBatchPassbookByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public int countOthBatchPassbook(Map params) throws SystemException, Exception;
	
	public Result insertOthBatchPassbook(OthBatchPassbook othBatchPassbook) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findOthBatchPassbookByParams(Map params) throws SystemException, Exception;
	
	public Result updateOthBatchPassbook(OthBatchPassbook othBatchPassbook) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findOthBatchPassbookByUK(Map params) throws SystemException, Exception;

}
