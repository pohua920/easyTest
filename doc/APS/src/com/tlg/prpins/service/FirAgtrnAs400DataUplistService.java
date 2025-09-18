package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtrnAs400DataUplist;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/**mantis：FIR0388，處理人員：BJ085，需求單編號：FIR0388 AS400續保資料匯入 */
public interface FirAgtrnAs400DataUplistService {
	public Result findFirAgtrnAs400DataUplistByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	@SuppressWarnings("rawtypes")
	public Result findFirAgtrnAs400DataUplistByParams(Map params) throws SystemException, Exception;
	
	public int countFirAgtrnAs400DataUplist(Map params) throws SystemException, Exception;
	
	public Result insertFirAgtrnAs400DataUplist(FirAgtrnAs400DataUplist firAgtrnAs400DataUplist) throws SystemException, Exception;
	
	public Result updateFirAgtrnAs400DataUplist(FirAgtrnAs400DataUplist firAgtrnAs400DataUplist) throws SystemException, Exception;

	public Result findFirAgtrnAs400DataUplistByUk(Map params) throws SystemException, Exception;
}
