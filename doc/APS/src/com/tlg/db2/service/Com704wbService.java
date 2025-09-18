package com.tlg.db2.service;

import java.util.Map;

import com.tlg.db2.entity.Com704wb;
import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface Com704wbService {

	public int countCom704wb(Map params) throws SystemException, Exception;
	
	public Result findCom704wbByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findCom704wbByParams(Map params) throws SystemException, Exception;

	public Result findCom704wbByUK(String businessNo, String serialNo) throws SystemException, Exception;

	public Result updateCom704wb(Com704wb com704wb) throws SystemException, Exception;

	public Result insertCom704wb(Com704wb com704wb) throws SystemException, Exception;


}
