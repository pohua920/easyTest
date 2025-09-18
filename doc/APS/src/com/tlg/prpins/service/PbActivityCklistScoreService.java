package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.PbActivityCklistScore;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PbActivityCklistScoreService {

	public int countPbActivityCklistScore(Map params) throws SystemException, Exception;

	public Result findPbActivityCklistScoreByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPbActivityCklistScoreByParams(Map params) throws SystemException, Exception;

	public Result findPbActivityCklistScoreByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updatePbActivityCklistScore(PbActivityCklistScore pbActivityCklistScore) throws SystemException, Exception;

	public Result insertPbActivityCklistScore(PbActivityCklistScore pbActivityCklistScore) throws SystemException, Exception;

	public Result removePbActivityCklistScore(BigDecimal oid) throws SystemException, Exception;
	

}
