package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.ChFile;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：MOB0008，處理人員：BJ016，需求單編號：MOB0008 將安達提供的保批回饋檔記錄到CH_FILE資料表 */
public interface ChFileService {

	public int countChFile(Map params) throws SystemException, Exception;
	
	public Result findChFileByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findChFileByParams(Map params) throws SystemException, Exception;

	public Result findChFileByUK(String transactionId) throws SystemException, Exception;

	public Result updateChFile(ChFile entity) throws SystemException, Exception;

	public Result insertChFile(ChFile entity) throws SystemException, Exception;

	public Result removeChFile(String transactionId) throws SystemException, Exception;
	
}
