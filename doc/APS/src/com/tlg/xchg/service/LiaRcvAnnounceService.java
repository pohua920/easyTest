package com.tlg.xchg.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.xchg.entity.LiaRcvAnnounce;

public interface LiaRcvAnnounceService {

	public int countLiaRcvAnnounce(Map params) throws SystemException, Exception;

	public Result findLiaRcvAnnounceByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findLiaRcvAnnounceByParams(Map params) throws SystemException, Exception;

	public Result findLiaRcvAnnounceByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateLiaRcvAnnounce(LiaRcvAnnounce liaRcvAnnounce) throws SystemException, Exception;

	public Result insertLiaRcvAnnounce(LiaRcvAnnounce liaRcvAnnounce) throws SystemException, Exception;

	public Result removeLiaRcvAnnounce(BigDecimal oid) throws SystemException, Exception;
	
	public Result findUnsendLiaRcvAnnounceData() throws SystemException, Exception;
}
