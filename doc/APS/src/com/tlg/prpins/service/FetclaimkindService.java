package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.Fetclaimkind;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：MOB0018，處理人員：BJ085，需求單編號：MOB0018 理賠資料提交及列印作業 */
public interface FetclaimkindService {

	public int countFetclaimkind(Map params) throws SystemException, Exception;

	public Result findFetclaimkindByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findFetclaimkindByParams(Map params) throws SystemException, Exception;

	public Result insertFetclaimkind(Fetclaimkind fetclaimkind) throws SystemException, Exception;
	
	public Result updateFetclaimkind(Fetclaimkind fetclaimkind) throws SystemException, Exception;

	public Result removeFetclaimkind(BigDecimal oid) throws SystemException, Exception;

}
