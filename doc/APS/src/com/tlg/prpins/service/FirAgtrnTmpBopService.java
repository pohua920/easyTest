package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtrnTmpBop;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirAgtrnTmpBopService {
	/* mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程 start */
	public Result findFirAgtrnTmpBopByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	@SuppressWarnings("rawtypes")
	public Result findFirAgtrnTmpBopByParams(Map params) throws SystemException, Exception;
	
	public int countFirAgtrnTmpBop(Map params) throws SystemException, Exception;
	
	public Result insertFirAgtrnTmpBop(FirAgtrnTmpBop firAgtrnTmpBop) throws SystemException, Exception;
	
	public Result updateFirAgtrnTmpBop(FirAgtrnTmpBop firAgtrnTmpBop) throws SystemException, Exception;
}
