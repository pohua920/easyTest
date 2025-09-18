package com.tlg.db2.service;

import java.util.Map;

import com.tlg.db2.entity.Com051we;
import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface Com051weService {

	public int countCom051we(Map params) throws SystemException, Exception;
	
	public Result findCom051weByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findCom051weByParams(Map params) throws SystemException, Exception;

	public Result updateCom051we(Com051we com051we) throws SystemException, Exception;
	
	public Result updateCom051weForWe20(Com051we com051we) throws SystemException, Exception;
	
	public Result updateCom051weForWe20(Map params) throws SystemException, Exception;

	public Result insertCom051we(Com051we com051we) throws SystemException, Exception;

	public Result findUnsendCom051weData() throws SystemException, Exception;
}
