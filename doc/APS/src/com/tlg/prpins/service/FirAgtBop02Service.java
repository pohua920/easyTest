package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirAgtBop02Service {
	/* mantis：FIR0266，處理人員：BJ085，需求單編號：FIR0266 板信資料交換處理作業_增加保單處理 start */
	@SuppressWarnings("rawtypes")
	public Result findFirAgtBop02ByParams(Map params) throws SystemException, Exception;
	
	public Result findFirAgtBop02ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;


}
