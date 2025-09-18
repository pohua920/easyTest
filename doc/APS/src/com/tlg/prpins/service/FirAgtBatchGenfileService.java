package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtBatchGenfile;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirAgtBatchGenfileService {
	/* mantis：FIR0266，處理人員：BJ085，需求單編號：FIR0266 板信資料交換處理作業 start */
	@SuppressWarnings("rawtypes")
	public int countFirAgtBatchGenfile(Map params) throws SystemException, Exception;

	public Result findFirAgtBatchGenfileByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	@SuppressWarnings("rawtypes")
	public Result findFirAgtBatchGenfileByParams(Map params) throws SystemException, Exception;

	public Result findFirAgtBatchGenfileByOid(BigDecimal oid) throws SystemException, Exception;

	public Result insertFirAgtBatchGenfile(FirAgtBatchGenfile firAgtBatchGenfile) throws SystemException, Exception;

}
