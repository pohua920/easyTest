package com.tlg.xchg.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.xchg.entity.LiaRcvAnnounceResult;

public interface LiaRcvAnnounceResultService {

	public int countLiaRcvAnnounceResult(Map params) throws SystemException, Exception;

	public Result findLiaRcvAnnounceResultByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findLiaRcvAnnounceResultByParams(Map params) throws SystemException, Exception;

	public Result findLiaRcvAnnounceResultByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateLiaRcvAnnounceResult(LiaRcvAnnounceResult liaRcvAnnounceResult) throws SystemException, Exception;

	public Result insertLiaRcvAnnounceResult(LiaRcvAnnounceResult liaRcvAnnounceResult) throws SystemException, Exception;

	public Result removeLiaRcvAnnounceResult(BigDecimal oid) throws SystemException, Exception;
	

}
