package com.tlg.xchg.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;
import com.tlg.xchg.entity.LiaMiNoticeCancle;

public interface LiaMiNoticeCancleService {
	
	public Result insertLiaMiNoticeCancle(LiaMiNoticeCancle liaMiNoticeCancle) throws SystemException, Exception;
	
	public Result updateLiaMiNoticeCancle(LiaMiNoticeCancle liaMiNoticeCancle) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findLiaMiNoticeCancleByParams(Map params) throws SystemException, Exception;

}
