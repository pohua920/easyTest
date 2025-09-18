package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirDoubleQuakeQlist;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirDoubleQuakeQlistService {

	public int countFirDoubleQuakeQlist(Map params) throws SystemException, Exception;

	public Result findFirDoubleQuakeQlistByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findFirDoubleQuakeQlistByParams(Map params) throws SystemException, Exception;

	public Result findFirDoubleQuakeQlistByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateFirDoubleQuakeQlist(FirDoubleQuakeQlist firDoubleQuakeQlist) throws SystemException, Exception;

	public Result insertFirDoubleQuakeQlist(FirDoubleQuakeQlist firDoubleQuakeQlist) throws SystemException, Exception;

	public Result removeFirDoubleQuakeQlist(BigDecimal oid) throws SystemException, Exception;
	

}
