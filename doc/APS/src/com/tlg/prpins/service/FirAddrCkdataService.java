package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAddrCkdata;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirAddrCkdataService {
	/* mantis：FIR0183，處理人員：BJ085，需求單編號：FIR0183 火險地址資料匯入 start */

	public int countFirAddrCkdata(Map params) throws SystemException, Exception;

	public Result findFirAddrCkdataByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findFirAddrCkdataByParams(Map params) throws SystemException, Exception;

	public Result findFirAddrCkdataByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateFirAddrCkdata(FirAddrCkdata firdrCkdata) throws SystemException, Exception;

	public Result insertFirAddrCkdata(FirAddrCkdata firAddrCkdata) throws SystemException, Exception;

	public Result removeFirAddrCkdata(BigDecimal oid) throws SystemException, Exception;

	public void truncate() throws SystemException, Exception;
	
	/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 start */
	public Result findAps024ExportData(Map params) throws SystemException, Exception;
	public int findAps024ExportDataCount(Map params) throws SystemException, Exception;
	/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 end */

}
