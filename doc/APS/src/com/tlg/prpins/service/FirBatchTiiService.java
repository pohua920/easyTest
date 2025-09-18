package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchTii;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：FIR0579，處理人員：BJ085，需求單編號：FIR0579 保發中心-住火保批資料產生排程規格 */
public interface FirBatchTiiService {

	public int countFirBatchTii(Map params) throws SystemException, Exception;

	public Result findFirBatchTiiByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findFirBatchTiiByParams(Map params) throws SystemException, Exception;
	
	public Result findFirBatchTiiByUk(String batchNo) throws SystemException,Exception;

	public Result insertFirBatchTii(FirBatchTii firBatchTii) throws SystemException, Exception;

	public Result updateFirBatchTii(FirBatchTii firBatchTii) throws SystemException, Exception;

	public Result removeFirBatchTii(FirBatchTii firBatchTii) throws SystemException, Exception;
	
}
