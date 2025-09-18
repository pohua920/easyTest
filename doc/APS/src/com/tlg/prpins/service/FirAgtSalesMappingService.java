package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtSalesMapping;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirAgtSalesMappingService {
	/* mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程 start */
	public Result findFirAgtSalesMappingByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	@SuppressWarnings("rawtypes")
	public Result findFirAgtSalesMappingByParams(Map params) throws SystemException, Exception;
	@SuppressWarnings("rawtypes")
	public int countFirAgtSalesMapping(Map params) throws SystemException, Exception;
	
	public Result insertFirAgtSalesMapping(FirAgtSalesMapping firAgtSalesMapping) throws SystemException, Exception;
	
	public Result updateFirAgtSalesMapping(FirAgtSalesMapping firAgtSalesMapping) throws SystemException, Exception;
	@SuppressWarnings("rawtypes")
	public Result findFirAgtSalesMappingByBranchNo(Map params) throws SystemException, Exception;
	
	/* mantis：FIR0310，處理人員：BJ016，需求單編號：FIR0310 住火保經代分行服務人員對照表 START */
	public Result findFirAgtSalesMappingForAps017ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result selectByOidForAps017(BigDecimal oid) throws SystemException, Exception;
	
	public Result findFirAgtSalesMappingByOid(BigDecimal oid) throws SystemException, Exception;
	/* mantis：FIR0310，處理人員：BJ016，需求單編號：FIR0310 住火保經代分行服務人員對照表 END */
	
}
