package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.MobileInsBatchInfo;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
public interface MobileInsBatchInfoService {

	public int countMobileInsBatchInfo(Map params) throws SystemException, Exception;
	
	public Result findMobileInsBatchInfoByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findMobileInsBatchInfoByParams(Map params) throws SystemException, Exception;

	public Result findMobileInsBatchInfoByUK(String oid) throws SystemException, Exception;

	public Result updateMobileInsBatchInfo(MobileInsBatchInfo mobileInsBatchInfo) throws SystemException, Exception;

	public Result insertMobileInsBatchInfo(MobileInsBatchInfo mobileInsBatchInfo) throws SystemException, Exception;

	public Result removeMobileInsBatchInfo(String oid) throws SystemException, Exception;
	
}
