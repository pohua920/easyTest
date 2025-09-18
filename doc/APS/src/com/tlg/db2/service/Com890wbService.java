package com.tlg.db2.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/**
 * mantis：OTH0132，處理人員：BI086，需求單編號：OTH0132  保單存摺AS400資料寫入核心中介Table
 */
public interface Com890wbService {

	public int countCom890wb(Map<String, Object> params) throws SystemException, Exception;
	
	public Result findCom890wbByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findCom890wbByParams(Map<String, Object> params) throws SystemException, Exception;

	public Result findCom890wbByUK(String businessNo) throws SystemException, Exception;


}
