package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchTiiList;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：FIR0579，處理人員：BJ085，需求單編號：FIR0579 保發中心-住火保批資料產生排程規格 */
public interface FirBatchTiiListService {

	public int countFirBatchTiiList(Map params) throws SystemException, Exception;

	public Result findFirBatchTiiListByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findFirBatchTiiListByParams(Map params) throws SystemException, Exception;

	public Result insertFirBatchTiiList(FirBatchTiiList firBatchTiiList) throws SystemException, Exception;

	public Result updateFirBatchTiiList(FirBatchTiiList firBatchTiiList) throws SystemException, Exception;

	public Result removeFirBatchTiiList(BigDecimal oid) throws SystemException, Exception;
	
	public Result findForCountProcTypeByParams(Map params) throws SystemException, Exception;
}
