package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.PbCklistScore;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PbCklistScoreService {

	public int countPbCklistScore(Map params) throws SystemException, Exception;

	public Result findPbCklistScoreByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPbCklistScoreByParams(Map params) throws SystemException, Exception;

	public Result findPbCklistScoreByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updatePbCklistScore(PbCklistScore pbCklistScore) throws SystemException, Exception;

	public Result insertPbCklistScore(PbCklistScore pbCklistScore) throws SystemException, Exception;

	public Result removePbCklistScore(BigDecimal oid) throws SystemException, Exception;
	

}
