package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtrnBatchBot;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業  **/
public interface FirAgtrnBatchBotService {
	
	public Result findFirAgtrnBatchBotByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	@SuppressWarnings("rawtypes")
	public Result findFirAgtrnBatchBotByParams(Map params) throws SystemException, Exception;
	
	public int countFirAgtrnBatchBot(Map params) throws SystemException, Exception;
	
	public Result insertFirAgtrnBatchBot(FirAgtrnBatchBot firAgtrnBatchBot) throws SystemException, Exception;
	
	public Result updateFirAgtrnBatchBot(FirAgtrnBatchBot firAgtrnBatchBot) throws SystemException, Exception;
	
	public Result removeFirAgtrnBatchBot(FirAgtrnBatchBot firAgtrnBatchBot) throws SystemException, Exception;
}
