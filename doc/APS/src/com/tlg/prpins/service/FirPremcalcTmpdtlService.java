package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirPremcalcTmpdtl;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirPremcalcTmpdtlService {

	public int countFirPremcalcTmpdtl(Map params) throws SystemException, Exception;
	
	public int countDiscountNum(BigDecimal oidFirPremcalcTmp, String calcDate, String joinPremRate) throws SystemException, Exception;

	public Result findFirPremcalcTmpdtlByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findFirPremcalcTmpdtlByParams(Map params) throws SystemException, Exception;

	public Result findFirPremcalcTmpdtlByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateFirPremcalcTmpdtl(FirPremcalcTmpdtl firPremcalcTmpdtl) throws SystemException, Exception;

	public Result insertFirPremcalcTmpdtl(FirPremcalcTmpdtl firPremcalcTmpdtl) throws SystemException, Exception;

	public Result removeFirPremcalcTmpdtl(BigDecimal oid) throws SystemException, Exception;
	

}
