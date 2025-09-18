package com.tlg.xchg.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.xchg.entity.CwpRcvAnnounce;

public interface CwpRcvAnnounceService {
	/* mantis：OTH0093，處理人員：BJ085，需求單編號：OTH0093 傷害險通報查詢、重送介面 start */
	public int countCwpRcvAnnounce(Map params) throws SystemException, Exception;

	public Result findCwpRcvAnnounceByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findCwpRcvAnnounceByParams(Map params) throws SystemException, Exception;
	
	public Result findCwpRcvAnnounceByUK(String checkno, String keyidno, String dataserno) throws SystemException, Exception;

	public Result findCwpRcvAnnounceByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateCwpRcvAnnounce(CwpRcvAnnounce cwpRcvAnnounce) throws SystemException, Exception;

	public Result insertCwpRcvAnnounce(CwpRcvAnnounce cwpRcvAnnounce) throws SystemException, Exception;

	public Result removeCwpRcvAnnounce(BigDecimal oid) throws SystemException, Exception;

	public Result findDistinctCwpRcvAnnounceData(PageInfo pageInfo) throws SystemException, Exception;

	public Result findDistinctCwpRcvAnnounceByParams(Map params) throws SystemException, Exception;
	
	public Result findUnsendRcvData(Map params) throws SystemException, Exception;
}
