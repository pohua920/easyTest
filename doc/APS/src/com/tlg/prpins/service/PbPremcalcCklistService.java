package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.PbPremcalcCklist;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PbPremcalcCklistService {

	public int countPbPremcalcCklist(Map params) throws SystemException, Exception;

	public Result findPbPremcalcCklistByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPbPremcalcCklistByParams(Map params) throws SystemException, Exception;
	
	public Result findPbPremcalcCklistForScore(Map params) throws SystemException, Exception;
	
	public BigDecimal findPbPremcalcCklistForResultScore(Map params) throws SystemException, Exception;

	public Result findPbPremcalcCklistByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updatePbPremcalcCklist(PbPremcalcCklist pbPremcalcCklist) throws SystemException, Exception;

	public Result insertPbPremcalcCklist(PbPremcalcCklist pbPremcalcCklist) throws SystemException, Exception;

	public Result removePbPremcalcCklist(BigDecimal oid) throws SystemException, Exception;
	

}
