package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirAgtBop01Service {
	/* mantis：FIR0266，處理人員：BJ085，需求單編號：FIR0266 板信資料交換處理作業 start */
	@SuppressWarnings("rawtypes")
	public Result findFirAgtBop01ByParams(Map params) throws SystemException, Exception;
	
	public Result findFirAgtBop01ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;


}
