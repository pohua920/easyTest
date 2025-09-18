package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.ChubbProductEndorse;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：，處理人員：BJ016，需求單編號：  行動裝置險安達線下批單資料檔下載*/
public interface ChubbProductEndorseService {

	@SuppressWarnings("rawtypes")
	public int countChubbProductEndorse(Map params) throws SystemException, Exception;
	
	public Result findChubbProductEndorseByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	@SuppressWarnings("rawtypes")
	public Result findChubbProductEndorseByParams(Map params) throws SystemException, Exception;

	public Result findChubbProductEndorseByUK(String transactionId) throws SystemException, Exception;

	public Result updateChubbProductEndorse(ChubbProductEndorse entity) throws SystemException, Exception;

	public Result insertChubbProductEndorse(ChubbProductEndorse entity) throws SystemException, Exception;

	public Result removeChubbProductEndorse(String transactionId) throws SystemException, Exception;
	
}
