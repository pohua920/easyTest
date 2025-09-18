package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.PrpdhighareaMc;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PrpdhighareaMcService {
	/* mantis：MAR0037，處理人員：BJ085，需求單編號：MAR0037 高風險地區新增維護檔 start*/
	
	@SuppressWarnings("rawtypes")
	public int countPrpdhighareaMc(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findPrpdhighareaMcByParams(Map params) throws SystemException, Exception;
	
	public Result findPrpdhighareaMcByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result updatePrpdhighareaMc(PrpdhighareaMc prpdhighareaMc) throws SystemException, Exception;

	public Result insertPrpdhighareaMc(PrpdhighareaMc prpdhighareaMc) throws SystemException, Exception;

	public Result removePrpdhighareaMc(PrpdhighareaMc prpdhighareaMc) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findPrpdhighareaMcByUK(Map params) throws SystemException, Exception;
	
}
