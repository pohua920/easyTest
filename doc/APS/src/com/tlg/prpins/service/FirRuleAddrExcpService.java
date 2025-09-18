package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirRuleAddrExcp;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirRuleAddrExcpService {
	/* mantis：FIR0238，處理人員：BJ085，需求單編號：FIR0238 稽核議題檢核-例外地址維護作業 start */

	public int countFirRuleAddrExcp(Map params) throws SystemException, Exception;

	public Result findFirRuleAddrExcpByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findFirRuleAddrExcpByParams(Map params) throws SystemException, Exception;

	public Result findFirRuleAddrExcpByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateFirRuleAddrExcp(FirRuleAddrExcp firRuleAddrExcp) throws SystemException, Exception;

	public Result insertFirRuleAddrExcp(FirRuleAddrExcp firRuleAddrExcp) throws SystemException, Exception;

	public Result removeFirRuleAddrExcp(BigDecimal oid) throws SystemException, Exception;
	

}
