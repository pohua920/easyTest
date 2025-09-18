package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAddrImportlist;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirAddrImportlistService {
	/* mantis：FIR0183，處理人員：BJ085，需求單編號：FIR0183 火險地址資料匯入 start */

	public Result findFirAddrImportlistByPageInfo(PageInfo pageInfo)throws SystemException, Exception;
	
	public Result findFirAddrImportlistByParams(Map params) throws SystemException, Exception;
	
	public Result insertFirAddrImportlist(FirAddrImportlist firAddrImportlist) throws SystemException, Exception;
	
	public Result updateFirAddrImportlist(FirAddrImportlist firAddrImportlist) throws SystemException, Exception;
}
