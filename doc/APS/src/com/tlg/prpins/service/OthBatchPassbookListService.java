package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.OthBatchPassbookList;
import com.tlg.util.Result;

/* mantis：OTH0127，處理人員：CC009，需求單編號：OTH0127 保發中心_保單存摺產生排程規格 */
public interface OthBatchPassbookListService {
	
	@SuppressWarnings("rawtypes")
	public Result findOthBatchPassbookListByParams(Map params) throws SystemException, Exception;
	
	public Result updateOthBatchPassbookList(OthBatchPassbookList othBatchPassbookList) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result updateBatchNoByTmpBno(Map params) throws SystemException, Exception;
	
	/**
	 * mantis：OTH0132，處理人員：BI086，需求單編號：OTH0132  保單存摺AS400資料寫入核心中介Table
	 */
	public Result insertOthBatchPassbookList(OthBatchPassbookList othBatchPassbookList) throws SystemException, Exception;
	
}
