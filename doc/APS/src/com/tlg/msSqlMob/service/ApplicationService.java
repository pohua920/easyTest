package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.Application;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
public interface ApplicationService {

	public int countApplication(Map params) throws SystemException, Exception;
	
	public Result findApplicationByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findApplicationByParams(Map params) throws SystemException, Exception;

	public Result findApplicationByUK(String transactionId) throws SystemException, Exception;

	public Result updateApplication(Application application) throws SystemException, Exception;

	public Result insertApplication(Application application) throws SystemException, Exception;

	public Result removeApplication(String transactionId) throws SystemException, Exception;
	/** mantis：MOB0029，處理人員：CE035，需求單編號：MOB0029 行動裝置險未完成審核通過通知險部人員  */
	public Result selectUncheckApplications(Map params) throws Exception;
	
}
