package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

/*mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程 */
public interface PrpcpaymentService {
	
	@SuppressWarnings("rawtypes")
	public Result findPrpcpaymentByParams(Map params) throws SystemException, Exception;

}
