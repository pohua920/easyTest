package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtBatchMain;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirAgtBatchMainService {
	/* mantis：FIR0265，處理人員：BJ085，需求單編號：FIR0265 板信受理檔產生排程 start */
	@SuppressWarnings("rawtypes")
	public Result findFirAgtBatchMainByParams(Map params) throws SystemException, Exception;
	
	public Result findFirAgtBatchMainByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	@SuppressWarnings("rawtypes")
	public Result findFirAgtBatchMainByUk(Map params) throws SystemException, Exception;
	
	public Result updateFirAgtBatchMain(FirAgtBatchMain firAgtBatchMain) throws SystemException, Exception;

	/* mantis：FIR0314，處理人員：BJ085，需求單編號：FIR0314 台銀保經-APS新件要保檔產生排程
	   mantis：FIR0315，處理人員：BJ085，需求單編號：FIR0315 台銀保經-APS保單檔產生排程 */
	public Result findBotMaxFilenameByParams(Map params) throws SystemException, Exception;
	
	/* mantis：FIR0547，處理人員：CC009，需求單編號：FIR0547 聯邦新件資料交換查詢作業 */
	public Result findAPS041ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	//mantis：FIR0545，處理人員：BJ085，需求單編號：FIR0545 火險_聯邦新件_APS要保資料接收排程
	public Result insertFirAgtBatchMain(FirAgtBatchMain firAgtBatchMain) throws SystemException, Exception;
}
