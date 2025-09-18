package com.tlg.msSqlMob.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.FetclaimTypecm;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：MOB0020，處理人員：BI086，需求單編號：MOB0020 理賠盜打詐騙API及XML作業 */
public interface FetclaimTypecmService {

	public int countFetclaimTypecm(Map params) throws SystemException, Exception;
	
	public Result findFetclaimTypecmByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findFetclaimTypecmByParams(Map params) throws SystemException, Exception;

	public Result findFetclaimTypecmByUK(String mtnNo) throws SystemException, Exception;
	
	public Result findFetclaimTypecmByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateFetclaimTypecm(FetclaimTypecm entity) throws SystemException, Exception;

	public Result insertFetclaimTypecm(FetclaimTypecm entity) throws SystemException, Exception;

	public Result removeFetclaimTypecm(String mtnNo, String datatype) throws SystemException, Exception;
	
}
