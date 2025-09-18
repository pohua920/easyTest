package com.tlg.sales.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.sales.entity.Prpdagent;
import com.tlg.sales.entity.PrpdagentIdv;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：SALES0033 ，處理人員： DP0713 ，需求單編號：銷管系統-業務員換證日期更新排程及通知*/
public interface PrpdagentIdvService {
	public Result findPrpdagentByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPrpdagentByParams(Map params) throws SystemException, Exception;
	
	public Result findPrpdagentByUk(Map params) throws SystemException, Exception;
	
	public Result updatePrpdagent(PrpdagentIdv prpdagent) throws SystemException, Exception;
	
}
