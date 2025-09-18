package com.tlg.xchg.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.xchg.entity.LiaUndwrtAnnounceResult;

public interface LiaUndwrtAnnounceResultService {

	public int countLiaUndwrtAnnounceResult(Map params) throws SystemException, Exception;

	public Result findLiaUndwrtAnnounceResultByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findLiaUndwrtAnnounceResultByParams(Map params) throws SystemException, Exception;

	public Result findLiaUndwrtAnnounceResultByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateLiaUndwrtAnnounceResult(LiaUndwrtAnnounceResult liaUndwrtAnnounceResult) throws SystemException, Exception;

	public Result insertLiaUndwrtAnnounceResult(LiaUndwrtAnnounceResult liaUndwrtAnnounceResult) throws SystemException, Exception;

	public Result removeLiaUndwrtAnnounceResult(BigDecimal oid) throws SystemException, Exception;
	

}
