package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.BatchRepairDetail;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface BatchRepairDetailService {

	@SuppressWarnings("rawtypes")
	public int countBatchRepairDetail(Map params) throws SystemException, Exception;
	
	public Result findBatchRepairDetailByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	@SuppressWarnings("rawtypes")
	public Result findBatchRepairDetailByParams(Map params) throws SystemException, Exception;

	public Result findBatchRepairDetailByUK(String transactionId) throws SystemException, Exception;

	public Result updateBatchRepairDetail(BatchRepairDetail batchRepairDetail) throws SystemException, Exception;

	public Result insertBatchRepairDetail(BatchRepairDetail batchRepairDetail) throws SystemException, Exception;

	public Result removeBatchRepairDetail(String transactionId) throws SystemException, Exception;
	
}
