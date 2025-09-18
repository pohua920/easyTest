package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirCalcamtWallno;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirCalcamtWallnoService {

	public int countFirCalcamtWallno(Map params) throws SystemException, Exception;

	public Result findFirCalcamtWallnoByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findFirCalcamtWallnoByParams(Map params) throws SystemException, Exception;

	public Result findFirCalcamtWallnoByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateFirCalcamtWallno(FirCalcamtWallno firCalcamtWallno) throws SystemException, Exception;

	public Result insertFirCalcamtWallno(FirCalcamtWallno firCalcamtWallno) throws SystemException, Exception;

	public Result removeFirCalcamtWallno(BigDecimal oid) throws SystemException, Exception;
	

}
