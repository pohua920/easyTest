package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtBotAp;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：FIR0314，處理人員：BJ085，需求單編號：FIR0314 台銀保經-APS新件要保檔產生排程 */
public interface FirAgtBotApService {
	
	public Result findFirAgtBotApByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	@SuppressWarnings("rawtypes")
	public Result findFirAgtBotApByParams(Map params) throws SystemException, Exception;
	
	public int countFirAgtBotAp(Map params) throws SystemException, Exception;
	
	public Result insertFirAgtBotAp(FirAgtBotAp firAgtBotAp) throws SystemException, Exception;
	
	public Result updateFirAgtBotAp(FirAgtBotAp firAgtBotAp) throws SystemException, Exception;


}
