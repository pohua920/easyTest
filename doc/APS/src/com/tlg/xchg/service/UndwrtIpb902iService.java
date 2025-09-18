package com.tlg.xchg.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.xchg.entity.UndwrtIpb902i;

public interface UndwrtIpb902iService {

	public int countUndwrtIpb902i(Map params) throws SystemException, Exception;

	public Result findUndwrtIpb902iByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findUndwrtIpb902iByParams(Map params) throws SystemException, Exception;

	public Result findUndwrtIpb902iByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateUndwrtIpb902i(UndwrtIpb902i undwrtIpb902i) throws SystemException, Exception;
	
	public Result updateUndwrtIpb902iSendtime(UndwrtIpb902i undwrtIpb902i) throws SystemException, Exception;

	public Result insertUndwrtIpb902i(UndwrtIpb902i undwrtIpb902i) throws SystemException, Exception;

	public Result removeUndwrtIpb902i(BigDecimal oid) throws SystemException, Exception;
	
	public Result findUnsendUndwrtIpb902iData() throws SystemException, Exception;
}
