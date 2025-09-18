package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.FetCancelNotification;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FetCancelNotificationService {

	public int countFetCancelNotification(Map params) throws SystemException, Exception;
	
	public Result findFetCancelNotificationByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findFetCancelNotificationByParams(Map params) throws SystemException, Exception;

	public Result findFetCancelNotificationByUK(String transactionId) throws SystemException, Exception;

	public Result updateFetCancelNotification(FetCancelNotification entity) throws SystemException, Exception;

	public Result insertFetCancelNotification(FetCancelNotification entity) throws SystemException, Exception;

	public Result removeFetCancelNotification(String transactionId) throws SystemException, Exception;
	
}
