package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtBotFd;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程 **/
public interface FirAgtBotFdService {
	
	@SuppressWarnings("rawtypes")
	public Result findFirAgtBotFdByParams(Map params) throws SystemException, Exception;
	
	public int countFirAgtBotFd(Map params) throws SystemException, Exception;
	
	public Result insertFirAgtBotFd(FirAgtBotFd firAgtBotFd) throws SystemException, Exception;
	
	public Result updateFirAgtBotFd(FirAgtBotFd firAgtBotFd) throws SystemException, Exception;

	/* mantis：FIR0624，處理人員：BJ085，需求單編號：FIR0624 住火_臺銀續保作業_臺銀FD檔查詢作業 */
	public Result findAPS057Main1ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

}
