package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtUb01;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：FIR0547，處理人員：CC009，需求單編號：FIR0547 聯邦新件資料交換查詢作業 */
public interface FirAgtUb01Service {
	public Result findAPS041Main2ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	/* mantis：FIR0545，處理人員：BJ085，需求單編號：FIR0545 火險_聯邦新件_APS要保資料接收排程 start*/
	@SuppressWarnings("rawtypes")
	public Result findFirAgtUb01ByParams(Map params) throws SystemException, Exception;
	
	public Result findFirAgtUb01ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result insertFirAgtUb01(FirAgtUb01 firAgtUb01) throws SystemException, Exception;
	
	public Result updateFirAgtUb01(FirAgtUb01 firAgtUb01) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public int countFirAgtUb01(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findFirAgtUb01ByUK(Map params) throws SystemException, Exception;
	/* mantis：FIR0545，處理人員：BJ085，需求單編號：FIR0545 火險_聯邦新件_APS要保資料接收排程 end*/
}
