package com.tlg.db2.service;

import java.util.Map;

import com.tlg.db2.entity.Com051wa;
import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface Com051waService {

	public int countCom051wa(Map params) throws SystemException, Exception;
	
	public Result findCom051waByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findCom051waByParams(Map params) throws SystemException, Exception;

	public Result updateCom051wa(Com051wa com051wa) throws SystemException, Exception;
	
	public Result updateCom051waForWa60(Com051wa com051wa) throws SystemException, Exception;
	
	public Result updateCom051waForWa60(Map params) throws SystemException, Exception;

	public Result insertCom051wa(Com051wa com051wa) throws SystemException, Exception;

	public Result findUnsendCom051waData() throws SystemException, Exception;
}
