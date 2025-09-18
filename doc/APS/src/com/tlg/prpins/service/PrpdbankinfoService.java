package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PrpdbankinfoService {

	public int countPrpdbankinfo(Map params) throws SystemException, Exception;

	public Result findPrpdbankinfoByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPrpdbankinfoByParams(Map params) throws SystemException, Exception;

	public Result findPrpdbankinfoByParamsForWs(Map params) throws SystemException, Exception;
}
