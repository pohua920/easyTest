package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

public interface PrpcinsuredService {
	
	@SuppressWarnings("rawtypes")
	public Result findPrpcinsuredByParams(Map params) throws SystemException, Exception;
	
	/*mantis：FIR0589，處理人員：BJ085，需求單編號：FIR0589 住火_APS板信續保作業_第二年優化_資料接收排程 */
	@SuppressWarnings("rawtypes")
	public Result findForPanhsinCoreInsured(Map params) throws SystemException, Exception;

}
