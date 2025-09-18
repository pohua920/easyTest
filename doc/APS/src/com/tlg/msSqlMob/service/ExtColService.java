package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.ExtCol;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
public interface ExtColService {

	public int countExtCol(Map params) throws SystemException, Exception;
	
	public Result findExtColByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findExtColByParams(Map params) throws SystemException, Exception;

	public Result findExtColByUK(String transactionId) throws SystemException, Exception;

	public Result updateExtCol(ExtCol extCol) throws SystemException, Exception;

	public Result insertExtCol(ExtCol extCol) throws SystemException, Exception;

	public Result removeExtCol(String transactionId) throws SystemException, Exception;
	
}
