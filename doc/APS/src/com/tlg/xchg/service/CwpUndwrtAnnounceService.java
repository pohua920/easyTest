package com.tlg.xchg.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.xchg.entity.CwpUndwrtAnnounce;

public interface CwpUndwrtAnnounceService {
	/* mantis：OTH0093，處理人員：BJ085，需求單編號：OTH0093 傷害險通報查詢、重送介面 start */
	public int countCwpUndwrtAnnounce(Map params) throws SystemException, Exception;

	public Result findCwpUndwrtAnnounceByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findCwpUndwrtAnnounceByParams(Map params) throws SystemException, Exception;

	public Result findCwpUndwrtAnnounceByUK(String checkno, String keyidno, String dataserno) throws SystemException, Exception;
	
	public Result findCwpUndwrtAnnounceByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateCwpUndwrtAnnounce(CwpUndwrtAnnounce cwpUndwrtAnnounce) throws SystemException, Exception;

	public Result insertCwpUndwrtAnnounce(CwpUndwrtAnnounce cwpUndwrtAnnounce) throws SystemException, Exception;

	public Result removeCwpUndwrtAnnounce(BigDecimal oid) throws SystemException, Exception;
	
	public Result findDistinctCwpUndwrtAnnounceData(PageInfo pageInfo) throws SystemException, Exception;

	public Result findDistinctCwpUndwrtAnnounceByParams(Map params) throws SystemException, Exception;
	
	public Result findUnsendUndwrtData(Map params) throws SystemException, Exception;
}
