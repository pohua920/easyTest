package com.tlg.db2.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface Com580WKService {

	public int countCom580WK(Map params) throws SystemException, Exception;

	public Result findCom580WKByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findCom580WKByParams(Map params) throws SystemException, Exception;

}
