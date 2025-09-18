package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.PbPremcalcAdditionTerm;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PbPremcalcAdditionTermService {

	public int countPbPremcalcAdditionTerm(Map params) throws SystemException, Exception;

	public Result findPbPremcalcAdditionTermByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPbPremcalcAdditionTermByParams(Map params) throws SystemException, Exception;

	public Result findPbPremcalcAdditionTermByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updatePbPremcalcAdditionTerm(PbPremcalcAdditionTerm pbPremcalcAdditionTerm) throws SystemException, Exception;

	public Result insertPbPremcalcAdditionTerm(PbPremcalcAdditionTerm pbPremcalcAdditionTerm) throws SystemException, Exception;

	public Result removePbPremcalcAdditionTerm(BigDecimal oid) throws SystemException, Exception;
	

}
