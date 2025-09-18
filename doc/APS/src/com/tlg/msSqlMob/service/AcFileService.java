package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.AcFile;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：MOB0008，處理人員：BJ016，需求單編號：MOB0008 將安達提供的銷帳回饋檔記錄到AC_FILE資料表 */
public interface AcFileService {

	public int countAcFile(Map params) throws SystemException, Exception;
	
	public Result findAcFileByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findAcFileByParams(Map params) throws SystemException, Exception;

	public Result findAcFileByUK(String transactionId) throws SystemException, Exception;

	public Result updateAcFile(AcFile entity) throws SystemException, Exception;

	public Result insertAcFile(AcFile entity) throws SystemException, Exception;

	public Result removeAcFile(String transactionId) throws SystemException, Exception;
	
}
