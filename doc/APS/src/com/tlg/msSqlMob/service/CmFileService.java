package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.CmFile;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：MOB0008，處理人員：BJ016，需求單編號：MOB0008 將安達提供的佣金回饋檔記錄到CM_FILE資料表 */
public interface CmFileService {

	public int countCmFile(Map params) throws SystemException, Exception;
	
	public Result findCmFileByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findCmFileByParams(Map params) throws SystemException, Exception;

	public Result findCmFileByUK(String transactionId) throws SystemException, Exception;

	public Result updateCmFile(CmFile entity) throws SystemException, Exception;

	public Result insertCmFile(CmFile entity) throws SystemException, Exception;

	public Result removeCmFile(String transactionId) throws SystemException, Exception;
	
}
