package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.Fetclaimpay;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：MOB0018，處理人員：BJ085，需求單編號：MOB0018 理賠資料提交及列印作業 */
public interface FetclaimpayService {

	public int countFetclaimpay(Map params) throws SystemException, Exception;

	public Result findFetclaimpayByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findFetclaimpayByParams(Map params) throws SystemException, Exception;

	public Result insertFetclaimpay(Fetclaimpay fetclaimpay) throws SystemException, Exception;

	public Result updateFetclaimpay(Fetclaimpay fetclaimpay) throws SystemException, Exception;

	public Result removeFetclaimpay(BigDecimal oid) throws SystemException, Exception;
}
