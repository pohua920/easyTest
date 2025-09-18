package com.tlg.db2.service;

import java.util.Map;

import com.tlg.db2.entity.Com910wa;
import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/**
 * 
 */
public interface Com910waService {

	public int countCom910wa(Map<String, Object> params) throws SystemException, Exception;
	
	public Result findCom910waByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findCom910waByParams(Map<String, Object> params) throws SystemException, Exception;

	public Result findCom910waByUK(String businessNo) throws SystemException, Exception;

	public Result updateCom910waForBatch(Com910wa com910wa) throws SystemException, Exception;
	
	public Result findUnsendCom910waData() throws SystemException, Exception;
}
