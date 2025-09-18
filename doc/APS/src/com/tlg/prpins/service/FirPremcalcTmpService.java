package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirPremcalcTmp;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirPremcalcTmpService {

	public int countFirPremcalcTmp(Map params) throws SystemException, Exception;

	public Result findFirPremcalcTmpByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findFirPremcalcTmpByParams(Map params) throws SystemException, Exception;

	public Result findFirPremcalcTmpByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateFirPremcalcTmp(FirPremcalcTmp firPremcalcTmp) throws SystemException, Exception;

	public Result insertFirPremcalcTmp(FirPremcalcTmp firPremcalcTmp) throws SystemException, Exception;

	public Result removeFirPremcalcTmp(BigDecimal oid) throws SystemException, Exception;
	

}
