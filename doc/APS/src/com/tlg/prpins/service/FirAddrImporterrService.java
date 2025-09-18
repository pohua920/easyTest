package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAddrImporterr;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirAddrImporterrService {
	/* mantis：FIR0183，處理人員：BJ085，需求單編號：FIR0183 火險地址資料匯入 start */

	public int countFirAddrImporterr(Map params) throws SystemException, Exception;

	public Result findFirAddrImporterrByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findFirAddrImporterrByParams(Map params) throws SystemException, Exception;

	public Result findFirAddrImporterrByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateFirAddrImporterr(FirAddrImporterr firAddrImporterr) throws SystemException, Exception;

	public Result insertFirAddrImporterr(FirAddrImporterr firAddrImporterr) throws SystemException, Exception;

	public Result removeFirAddrImporterr(BigDecimal oid) throws SystemException, Exception;
	

}
