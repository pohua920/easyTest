package com.tlg.xchg.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.xchg.entity.AssocRcvAncmt;

public interface AssocRcvAncmtService {

	public int countAssocRcvAncmt(Map params) throws SystemException, Exception;

	public Result findAssocRcvAncmtByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findAssocRcvAncmtByParams(Map params) throws SystemException, Exception;

	public Result findAssocRcvAncmtByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateAssocRcvAncmt(AssocRcvAncmt assocRcvAncmt) throws SystemException, Exception;

	public Result insertAssocRcvAncmt(AssocRcvAncmt assocRcvAncmt) throws SystemException, Exception;

	public Result removeAssocRcvAncmt(BigDecimal oid) throws SystemException, Exception;
	
	public Result findUnsendAssocRcvAncmtData() throws SystemException, Exception;
}
