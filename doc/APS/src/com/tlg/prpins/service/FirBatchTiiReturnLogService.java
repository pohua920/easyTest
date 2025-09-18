package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchTiiReturnLog;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：FIR0580，處理人員：BJ085，需求單編號：FIR0580 保發中心-住火保批資料回饋檔回存資料庫 */
public interface FirBatchTiiReturnLogService {

	public int countFirBatchTiiReturnLog(Map params) throws SystemException, Exception;

	public Result findFirBatchTiiReturnLogByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findFirBatchTiiReturnLogByParams(Map params) throws SystemException, Exception;

	public Result insertFirBatchTiiReturnLog(FirBatchTiiReturnLog firBatchTiiReturnLog) throws SystemException, Exception;

	public Result updateFirBatchTiiReturnLog(FirBatchTiiReturnLog firBatchTiiReturnLog) throws SystemException, Exception;

	public Result removeFirBatchTiiReturnLog(BigDecimal oid) throws SystemException, Exception;
}
