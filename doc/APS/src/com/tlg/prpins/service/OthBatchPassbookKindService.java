package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.OthBatchPassbookKind;
import com.tlg.util.Result;

/* mantis：OTH0127，處理人員：CC009，需求單編號：OTH0127 保發中心_保單存摺產生排程規格 */
public interface OthBatchPassbookKindService {
	
	public Result findOthBatchPassbookKindByParams(Map params) throws SystemException, Exception;
	
	public Result updateOthBatchPassbookKind(OthBatchPassbookKind othBatchPassbookKind) throws SystemException, Exception;
	
	public Result insertOthBatchPassbookKind(OthBatchPassbookKind othBatchPassbookKind) throws SystemException, Exception;
	
}
