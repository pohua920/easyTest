package com.tlg.db2.service;

import java.util.Map;

import com.tlg.db2.entity.Com704wa;
import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface Com704waService {

	public int countCom704wa(Map params) throws SystemException, Exception;
	
	public Result findCom704waByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findCom704waByParams(Map params) throws SystemException, Exception;

	public Result findCom704waByUK(String businessNo) throws SystemException, Exception;

	public Result updateCom704wa(Com704wa com704wa) throws SystemException, Exception;

	public Result insertCom704wa(Com704wa com704wa) throws SystemException, Exception;


}
