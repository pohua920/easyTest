package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.BatchRepairMain;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface BatchRepairMainService {

	@SuppressWarnings("rawtypes")
	public int countBatchRepairMain(Map params) throws SystemException, Exception;
	
	public Result findBatchRepairMainByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	@SuppressWarnings("rawtypes")
	public Result findBatchRepairMainByParams(Map params) throws SystemException, Exception;

	public Result findBatchRepairMainByUK(String transactionId) throws SystemException, Exception;

	public Result updateBatchRepairMain(BatchRepairMain batchRepairMain) throws SystemException, Exception;

	public Result insertBatchRepairMain(BatchRepairMain batchRepairMain) throws SystemException, Exception;

	public Result removeBatchRepairMain(String transactionId) throws SystemException, Exception;
	
}
