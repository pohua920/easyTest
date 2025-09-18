package com.tlg.xchg.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.xchg.entity.PrpcinsuredContactChk0New;
/**mantis：OTH0184，處理人員：DP0706，需求單編號：APS要被保人通訊地址比對作業增加資料來源(XCHG)*/
public interface PrpcinsuredContactChk0NewService {
	
	public Result findPrpcinsuredContactChk0ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	@SuppressWarnings("rawtypes")
	public Result findPrpcinsuredContactChk0ByParams(Map params) throws SystemException, Exception;
	
	public Result findPrpcinsuredContactChk0ByUk(Map params) throws SystemException, Exception;
	
	public Result updatePrpcinsuredContactChk0(PrpcinsuredContactChk0New prpcinsuredContactChk0) throws SystemException, Exception;
}
