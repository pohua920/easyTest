package com.tlg.sales.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.sales.entity.PrpdagentLoginDateReserve;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：SALES0033 ，處理人員： DP0713 ，需求單編號：銷管系統-業務員換證日期更新排程及通知*/
public interface PrpdagentLoginDateReserveService {
	public int countPrpdagentLoginDateReserve(Map params) throws SystemException, Exception;
	
	public Result findPrpdagentLoginDateReserveByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPrpdagentLoginDateReserveByParams(Map params) throws SystemException, Exception;
	
	public Result findPrpdagentLoginDateReserveByUk(Map params) throws SystemException, Exception;
	
	public Result updatePrpdagentLoginDateReserve(PrpdagentLoginDateReserve prpdagentLoginDateReserve) throws SystemException, Exception;

	public Result selectForAgentLoginDateReserve(Map<String, Object> params) throws SystemException, Exception;
	
}
