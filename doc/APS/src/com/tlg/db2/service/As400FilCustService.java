package com.tlg.db2.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

/**
 * mantis：OTH0175，處理人員：DP0706，需求單編號：OTH0175_APS-收件收件報備系統 已出單資料回拋B2B
 * @author dp0706
 *
 */
public interface As400FilCustService {
	
	public Result findAs400FilByCustQueryStr ( Map<String, Object> qryMap) throws SystemException, Exception;

}
