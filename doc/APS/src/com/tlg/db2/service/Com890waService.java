package com.tlg.db2.service;

import java.util.Map;

import com.tlg.db2.entity.Com890wa;
import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/**
 * mantis：OTH0132，處理人員：BI086，需求單編號：OTH0132  保單存摺AS400資料寫入核心中介Table
 */
public interface Com890waService {

	public int countCom890wa(Map<String, Object> params) throws SystemException, Exception;
	
	public Result findCom890waByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findCom890waByParams(Map<String, Object> params) throws SystemException, Exception;

	public Result findCom890waByUK(String businessNo) throws SystemException, Exception;

	public Result updateCom890waForBatch(Com890wa com890wa) throws SystemException, Exception;
	
	public Result findUnsendCom890waData() throws SystemException, Exception;
}
