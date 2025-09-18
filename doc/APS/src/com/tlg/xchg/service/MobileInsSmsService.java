package com.tlg.xchg.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.xchg.entity.MobileInsSms;

public interface MobileInsSmsService {

	public int countMobileInsSms(Map params) throws SystemException, Exception;

	public Result findMobileInsSmsByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findMobileInsSmsByParams(Map params) throws SystemException, Exception;

	public Result findMobileInsSmsByOid(String oid) throws SystemException, Exception;

	public Result updateMobileInsSms(MobileInsSms mobileInsSms) throws SystemException, Exception;

	public Result insertMobileInsSms(MobileInsSms mobileInsSms) throws SystemException, Exception;

	public Result findUnsendCancelNotice() throws SystemException, Exception;
	
	public Result findUnsendUnpaidNotice() throws SystemException, Exception;
	
}
