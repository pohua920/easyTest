package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchTiiAddr;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：FIR0579，處理人員：BJ085，需求單編號：FIR0579 保發中心-住火保批資料產生排程規格 */
public interface FirBatchTiiAddrService {

	public int countFirBatchTiiAddr(Map params) throws SystemException, Exception;

	public Result findFirBatchTiiAddrByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findFirBatchTiiAddrByParams(Map params) throws SystemException, Exception;

	public Result insertFirBatchTiiAddr(FirBatchTiiAddr firBatchTiiAddr) throws SystemException, Exception;

	public Result updateFirBatchTiiAddr(FirBatchTiiAddr firBatchTiiAddr) throws SystemException, Exception;

	public Result removeFirBatchTiiAddr(BigDecimal oid) throws SystemException, Exception;
}
