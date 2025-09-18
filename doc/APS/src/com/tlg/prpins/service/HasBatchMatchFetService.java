package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

/**
 * mantis：HAS0284，處理人員：DP0706，需求單編號：HAS0284_遠傳優化需求-比對同要保人但生日或姓名不同
 * @author dp0706
 *
 */
@SuppressWarnings("rawtypes")
public interface HasBatchMatchFetService {
	
	public Result findHasBatchMatchFetByParams(Map params) throws SystemException, Exception;

}
