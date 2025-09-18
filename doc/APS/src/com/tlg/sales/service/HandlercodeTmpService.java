package com.tlg.sales.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

/** mantis：SALES0007，處理人員：BJ085，需求單編號：SALES0007 新增業務人員所屬服務人員維護*/
public interface HandlercodeTmpService {

	public Result findHandlercodeTmpByParams(Map params) throws SystemException, Exception;
	
	public Result findHandlercodeIsValidByParams(Map params) throws SystemException, Exception;
}
