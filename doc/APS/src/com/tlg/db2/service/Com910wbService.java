package com.tlg.db2.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/**

 */
public interface Com910wbService {

	public int countCom910wb(Map<String, Object> params) throws SystemException, Exception;
	
	public Result findCom910wbByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findCom910wbByParams(Map<String, Object> params) throws SystemException, Exception;

	public Result findCom910wbByUK(String businessNo) throws SystemException, Exception;


}
