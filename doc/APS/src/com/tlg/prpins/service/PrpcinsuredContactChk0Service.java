package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.PrpcinsuredContactChk0;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
/**mantis：OTH0106，處理人員：BJ085，需求單編號：OTH0106 要被保人通訊資料比對確認作業*/
public interface PrpcinsuredContactChk0Service {
	
	public Result findPrpcinsuredContactChk0ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	@SuppressWarnings("rawtypes")
	public Result findPrpcinsuredContactChk0ByParams(Map params) throws SystemException, Exception;
	
	public Result findPrpcinsuredContactChk0ByUk(Map params) throws SystemException, Exception;
	
	public Result updatePrpcinsuredContactChk0(PrpcinsuredContactChk0 prpcinsuredContactChk0) throws SystemException, Exception;
	
	//mantis：OTH0184，處理人員：DP0706，需求單編號：APS要被保人通訊地址比對作業增加資料來源(XCHG)
	public Result findPrpdCompany(Map params) throws SystemException, Exception;
}
