package com.tlg.db2.service;

import java.util.Map;

import com.tlg.db2.entity.Com880wk;
import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/**
 * mantis：OTH0132，處理人員：BI086，需求單編號：OTH0132  保單存摺AS400資料寫入核心中介Table
 */
public interface Com880wkService {

	public int countCom880wk(Map<String, Object> params) throws SystemException, Exception;
	
	public Result findCom880wkByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findCom880wkByParams(Map<String, Object> params) throws SystemException, Exception;

	public Result findCom880wkByUK(String businessNo) throws SystemException, Exception;

	public Result updateCom880wkForBatch(Com880wk com880wk) throws SystemException, Exception;
	
	public Result findUnsendCom880wkData() throws SystemException, Exception;
}
