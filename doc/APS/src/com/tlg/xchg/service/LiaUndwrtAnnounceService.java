package com.tlg.xchg.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.xchg.entity.LiaUndwrtAnnounce;

public interface LiaUndwrtAnnounceService {

	public int countLiaUndwrtAnnounce(Map params) throws SystemException, Exception;

	public Result findLiaUndwrtAnnounceByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findLiaUndwrtAnnounceByParams(Map params) throws SystemException, Exception;

	public Result findLiaUndwrtAnnounceByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateLiaUndwrtAnnounce(LiaUndwrtAnnounce liaUndwrtAnnounce) throws SystemException, Exception;

	public Result insertLiaUndwrtAnnounce(LiaUndwrtAnnounce liaUndwrtAnnounce) throws SystemException, Exception;

	public Result removeLiaUndwrtAnnounce(BigDecimal oid) throws SystemException, Exception;
	
	public Result findUnsendLiaUndwrtAnnounceData() throws SystemException, Exception;

}
