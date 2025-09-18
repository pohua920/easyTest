package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.Fetclaimcomm;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：MOB0018，處理人員：BJ085，需求單編號：MOB0018 理賠資料提交及列印作業 */
public interface FetclaimcommService {

	public int countFetclaimcomm(Map params) throws SystemException, Exception;

	public Result findFetclaimcommByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findFetclaimcommByParams(Map params) throws SystemException, Exception;

	public Result insertFetclaimcomm(Fetclaimcomm fetclaimcomm) throws SystemException, Exception;
	
	public Result updateFetclaimcomm(Fetclaimcomm fetclaimcomm) throws SystemException, Exception;

	public Result removeFetclaimcomm(BigDecimal oid) throws SystemException, Exception;

}
