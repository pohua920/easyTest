package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.AccountFile;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：MOB0023，處理人員：BJ016，需求單編號：MOB0023 將安達提供的財務用銷帳回饋檔記錄到ACCOUNT_FILE資料表 */
public interface AccountFileService {

	@SuppressWarnings("rawtypes")
	public int countAccountFile(Map params) throws SystemException, Exception;
	
	public Result findAccountFileByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	@SuppressWarnings("rawtypes")
	public Result findAccountFileByParams(Map params) throws SystemException, Exception;

	public Result findAccountFileByUK(String transactionId) throws SystemException, Exception;

	public Result updateAccountFile(AccountFile entity) throws SystemException, Exception;

	public Result insertAccountFile(AccountFile entity) throws SystemException, Exception;

	public Result removeAccountFile(String transactionId) throws SystemException, Exception;
	
}
