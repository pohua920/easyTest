package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.TerminationNotice;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：MOB0024，處理人員：BJ016，需求單編號：MOB0024 產生終止通知書 */
public interface TerminationNoticeService {

	@SuppressWarnings("rawtypes")
	public int countTerminationNotice(Map params) throws SystemException, Exception;
	
	public Result findTerminationNoticeByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	@SuppressWarnings("rawtypes")
	public Result findTerminationNoticeByParams(Map params) throws SystemException, Exception;

	public Result findTerminationNoticeByUK(String transactionId) throws SystemException, Exception;

	public Result updateTerminationNotice(TerminationNotice entity) throws SystemException, Exception;

	public Result insertTerminationNotice(TerminationNotice entity) throws SystemException, Exception;

	public Result removeTerminationNotice(String transactionId) throws SystemException, Exception;
	
	public Result findTerminationNoticeForCancel(Map params) throws SystemException, Exception;
	
	public Result findTerminationNoticeForUnpaid1(Map params) throws SystemException, Exception;
	
	public Result findTerminationNoticeForUnpaid2(Map params) throws SystemException, Exception;
	
}
