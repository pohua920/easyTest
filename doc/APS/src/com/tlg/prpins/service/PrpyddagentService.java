package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

public interface PrpyddagentService {
	/*mantis：FIR0295，處理人員：BJ085，需求單編號：FIR0295 外銀板信續件移回新核心-維護作業*/
	@SuppressWarnings("rawtypes")
	public Result findPrpyddagentByParams(Map params) throws SystemException, Exception;

}
