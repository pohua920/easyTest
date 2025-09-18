package com.tlg.sales.service;

import java.util.Map;

import com.tlg.sales.entity.Prpdagentsub;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：SALES0008，處理人員：BJ085，需求單編號：SALES0008 保經代分支機構維護程式需求 */
public interface PrpdagentsubService{
	public Result findPrpdagentsubByPageInfo(PageInfo pageInfo) throws Exception;

	public Result findPrpdagentsubByParams(Map params) throws Exception;
	
	public Result findPrpdagentsubByUk(Map params) throws Exception;
	
	public Result updatePrpdagentsub(Prpdagentsub prpdagentsub) throws Exception;
	
	public Result insertPrpdagentsub(Prpdagentsub prpdagentsub) throws Exception;
	
	public int countMaxAgentsubcode() throws Exception;
	
	public boolean removePrpdagentsub(Map<String,String> params) throws Exception;
	
	public Result findPrpdagentsubForAps028ByPageInfo(PageInfo pageInfo) throws Exception;
}
