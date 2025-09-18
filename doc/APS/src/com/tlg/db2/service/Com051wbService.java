package com.tlg.db2.service;

import java.util.Map;

import com.tlg.db2.entity.Com051wb;
import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface Com051wbService {

	public int countCom051wb(Map params) throws SystemException, Exception;
	
	public Result findCom051wbByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findCom051wbByParams(Map params) throws SystemException, Exception;

	public Result updateCom051wb(Com051wb com051wb) throws SystemException, Exception;
	
	public Result updateCom051wbForWb60(Com051wb com051wb) throws SystemException, Exception;
	
	public Result updateCom051wbForWb60(Map params) throws SystemException, Exception;

	public Result insertCom051wb(Com051wb com051wb) throws SystemException, Exception;

	public Result findUnsendCom051wbData() throws SystemException, Exception;
}
