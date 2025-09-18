package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.PbPremcalcClause;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PbPremcalcClauseService {

	public int countPbPremcalcClause(Map params) throws SystemException, Exception;

	public Result findPbPremcalcClauseByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPbPremcalcClauseByParams(Map params) throws SystemException, Exception;

	public Result findPbPremcalcClauseByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updatePbPremcalcClause(PbPremcalcClause pbPremcalcClause) throws SystemException, Exception;

	public Result insertPbPremcalcClause(PbPremcalcClause pbPremcalcClause) throws SystemException, Exception;

	public Result removePbPremcalcClause(BigDecimal oid) throws SystemException, Exception;

}
