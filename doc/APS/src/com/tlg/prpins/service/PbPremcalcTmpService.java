package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.PbPremcalcTmp;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PbPremcalcTmpService {

	public int countPbPremcalcTmp(Map params) throws SystemException, Exception;

	public Result findPbPremcalcTmpByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPbPremcalcTmpByParams(Map params) throws SystemException, Exception;

	public Result findPbPremcalcTmpByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updatePbPremcalcTmp(PbPremcalcTmp pbPremcalcTmp) throws SystemException, Exception;

	public Result insertPbPremcalcTmp(PbPremcalcTmp pbPremcalcTmp) throws SystemException, Exception;

	public Result removePbPremcalcTmp(BigDecimal oid) throws SystemException, Exception;
	

}
