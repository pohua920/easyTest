package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtrnBatchFb;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業  **/
public interface FirAgtrnBatchFbService {
	public Result findFirAgtrnBatchFbByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	@SuppressWarnings("rawtypes")
	public Result findFirAgtrnBatchFbByParams(Map params) throws SystemException, Exception;
	
	public int countFirAgtrnBatchFb(Map params) throws SystemException, Exception;
	
	public Result insertFirAgtrnBatchFb(FirAgtrnBatchFb firAgtrnBatchFb) throws SystemException, Exception;
	
	public Result updateFirAgtrnBatchFb(FirAgtrnBatchFb firAgtrnBatchFb) throws SystemException, Exception;
}
