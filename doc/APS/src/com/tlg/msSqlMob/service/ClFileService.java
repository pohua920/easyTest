package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.ClFile;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：MOB0008，處理人員：BJ016，需求單編號：MOB0008 將安達提供的理賠回饋檔記錄到CL_FILE資料表 */
public interface ClFileService {

	public int countClFile(Map params) throws SystemException, Exception;
	
	public Result findClFileByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findClFileByParams(Map params) throws SystemException, Exception;

	public Result findClFileByUK(String transactionId) throws SystemException, Exception;

	public Result updateClFile(ClFile entity) throws SystemException, Exception;

	public Result insertClFile(ClFile entity) throws SystemException, Exception;

	public Result removeClFile(String transactionId) throws SystemException, Exception;
	
}
