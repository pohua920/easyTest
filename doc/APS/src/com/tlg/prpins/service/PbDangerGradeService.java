package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.PbDangerGrade;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PbDangerGradeService {

	public int countPbDangerGrade(Map params) throws SystemException, Exception;

	public Result findPbDangerGradeByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPbDangerGradeByParams(Map params) throws SystemException, Exception;

	public Result findPbDangerGradeByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updatePbDangerGrade(PbDangerGrade pbDangerGrade) throws SystemException, Exception;

	public Result insertPbDangerGrade(PbDangerGrade pbDangerGrade) throws SystemException, Exception;

	public Result removePbDangerGrade(BigDecimal oid) throws SystemException, Exception;
	

}
