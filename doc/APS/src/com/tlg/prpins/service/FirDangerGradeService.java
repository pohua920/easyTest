package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirDangerGrade;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirDangerGradeService {

	public int countFirDangerGrade(Map params) throws SystemException, Exception;

	public Result findFirDangerGradeByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findFirDangerGradeByParams(Map params) throws SystemException, Exception;

	public Result findFirDangerGradeByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateFirDangerGrade(FirDangerGrade firDangerGrade) throws SystemException, Exception;

	public Result insertFirDangerGrade(FirDangerGrade firDangerGrade) throws SystemException, Exception;

	public Result removeFirDangerGrade(BigDecimal oid) throws SystemException, Exception;
	

}
