package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtrnBatchYcb;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業  **/
public interface FirAgtrnBatchYcbService {
	
	public Result findFirAgtrnBatchYcbByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	@SuppressWarnings("rawtypes")
	public Result findFirAgtrnBatchYcbByParams(Map params) throws SystemException, Exception;
	
	public int countFirAgtrnBatchYcb(Map params) throws SystemException, Exception;
	
	public Result insertFirAgtrnBatchYcb(FirAgtrnBatchYcb firAgtrnBatchYcb) throws SystemException, Exception;
	
	public Result updateFirAgtrnBatchYcb(FirAgtrnBatchYcb firAgtrnBatchYcb) throws SystemException, Exception;
	
	public Result removeFirAgtrnBatchYcb(FirAgtrnBatchYcb firAgtrnBatchYcb) throws SystemException, Exception;
}
