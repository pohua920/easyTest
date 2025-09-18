package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtBotFh;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：FIR0315，處理人員：BJ085，需求單編號：FIR0315 台銀保經-APS保單檔產生排程 */
public interface FirAgtBotFhService {
	
	public Result findFirAgtBotFhByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	@SuppressWarnings("rawtypes")
	public Result findFirAgtBotFhByParams(Map params) throws SystemException, Exception;
	
	public int countFirAgtBotFh(Map params) throws SystemException, Exception;
	
	public Result insertFirAgtBotFh(FirAgtBotFh firAgtBotFh) throws SystemException, Exception;
	
	public Result updateFirAgtBotFh(FirAgtBotFh firAgtBotFh) throws SystemException, Exception;


}
