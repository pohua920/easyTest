package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.PbPremcalcTmpdtl;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PbPremcalcTmpdtlService {

	public int countPbPremcalcTmpdtl(Map params) throws SystemException, Exception;

	public Result findPbPremcalcTmpdtlByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPbPremcalcTmpdtlByParams(Map params) throws SystemException, Exception;

	public Result findPbPremcalcTmpdtlByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updatePbPremcalcTmpdtl(PbPremcalcTmpdtl pbPremcalcTmpdtl) throws SystemException, Exception;

	public Result insertPbPremcalcTmpdtl(PbPremcalcTmpdtl pbPremcalcTmpdtl) throws SystemException, Exception;

	public Result removePbPremcalcTmpdtl(BigDecimal oid) throws SystemException, Exception;
	

}
