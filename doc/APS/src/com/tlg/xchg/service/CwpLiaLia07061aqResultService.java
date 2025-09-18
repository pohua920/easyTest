package com.tlg.xchg.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.xchg.entity.CwpLiaLia07061aqResult;

public interface CwpLiaLia07061aqResultService {

	public int countCwpLiaLia07061aqResult(Map params) throws SystemException, Exception;

	public Result findCwpLiaLia07061aqResultByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findCwpLiaLia07061aqResultByParams(Map params) throws SystemException, Exception;
	
	public Result findCwpLiaLia07061aqResultByUK(String checkno, String dataserno) throws SystemException, Exception;

	public Result findCwpLiaLia07061aqResultByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateCwpLiaLia07061aqResult(CwpLiaLia07061aqResult cwpLiaLia07061aqResult) throws SystemException, Exception;

	public Result insertCwpLiaLia07061aqResult(CwpLiaLia07061aqResult cwpLiaLia07061aqResult) throws SystemException, Exception;
	
	public void batchInsertCwpLiaLia07061aqResult(List<CwpLiaLia07061aqResult> list) throws SystemException,Exception;

	public Result removeCwpLiaLia07061aqResult(BigDecimal oid) throws SystemException, Exception;
	
}
