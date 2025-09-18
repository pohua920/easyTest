package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtTocoreInsured;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirAgtTocoreInsuredService {
	/* mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程 start */
	public Result findFirAgtTocoreInsuredByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	@SuppressWarnings("rawtypes")
	public Result findFirAgtTocoreInsuredByParams(Map params) throws SystemException, Exception;
	
	public int countFirAgtTocoreInsured(Map params) throws SystemException, Exception;
	
	public Result insertFirAgtTocoreInsured(FirAgtTocoreInsured firAgtTocoreInsured) throws SystemException, Exception;
	
	public Result updateFirAgtTocoreInsured(FirAgtTocoreInsured firAgtTocoreInsured) throws SystemException, Exception;
	
	//mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業
	public Result removeFirAgtTocoreInsured(BigDecimal oid) throws Exception;
}
