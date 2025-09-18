package com.tlg.xchg.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;
import com.tlg.xchg.entity.LiaMiNoticeUnpaid;

public interface LiaMiNoticeUnpaidService {
	
	public Result insertLiaMiNoticeUnpaid(LiaMiNoticeUnpaid liaMiNoticeUnpaid) throws SystemException, Exception;
	public Result updateLiaMiNoticeUnpaid(LiaMiNoticeUnpaid liaMiNoticeUnpaid) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findLiaMiNoticeUnpaidByParams(Map params) throws SystemException, Exception;

}
