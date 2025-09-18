package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

public interface FirBatchInfoService {
	
	@SuppressWarnings("rawtypes")
	public Result findFirBatchInfoByParams(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findFirBatchInfoByUK(Map params) throws SystemException, Exception;
	
	//mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程
	public Result findHandler1code() throws Exception;

}
