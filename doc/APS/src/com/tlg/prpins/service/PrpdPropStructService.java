package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PrpdPropStructService {

	public int countPrpdPropStruct(Map params) throws SystemException, Exception;

	public Result findPrpdPropStructByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPrpdPropStructByParams(Map params) throws SystemException, Exception;


}
