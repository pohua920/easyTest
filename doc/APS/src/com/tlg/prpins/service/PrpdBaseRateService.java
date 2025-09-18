package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PrpdBaseRateService {

	public int countPrpdBaseRate(Map params) throws SystemException, Exception;

	public Result findPrpdBaseRateByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPrpdBaseRateByParams(Map params) throws SystemException, Exception;

}
