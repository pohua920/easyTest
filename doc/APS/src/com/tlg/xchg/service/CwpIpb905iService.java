package com.tlg.xchg.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.xchg.entity.CwpIpb905i;

public interface CwpIpb905iService {

	public int countCwpIpb905i(Map params) throws SystemException, Exception;

	public Result findCwpIpb905iByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findCwpIpb905iByParams(Map params) throws SystemException, Exception;
	
	public Result findCwpIpb905iByUK(String cmptype, String cmpno, String idno, String birdate, String insno, String insclass, String inskind, String insitem ) throws SystemException, Exception;

	public Result findCwpIpb905iByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateCwpIpb905i(CwpIpb905i cwpIpb905i) throws SystemException, Exception;

	public Result insertCwpIpb905i(CwpIpb905i cwpIpb905i) throws SystemException, Exception;

	public Result removeCwpIpb905i(BigDecimal oid) throws SystemException, Exception;
	
}
