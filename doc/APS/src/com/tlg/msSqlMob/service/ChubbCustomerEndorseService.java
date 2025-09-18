package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.ChubbCustomerEndorse;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：，處理人員：BJ016，需求單編號：  行動裝置險安達線下批單資料檔下載*/
public interface ChubbCustomerEndorseService {

	@SuppressWarnings("rawtypes")
	public int countChubbCustomerEndorse(Map params) throws SystemException, Exception;
	
	public Result findChubbCustomerEndorseByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	@SuppressWarnings("rawtypes")
	public Result findChubbCustomerEndorseByParams(Map params) throws SystemException, Exception;

	public Result findChubbCustomerEndorseByUK(String transactionId) throws SystemException, Exception;

	public Result updateChubbCustomerEndorse(ChubbCustomerEndorse entity) throws SystemException, Exception;

	public Result insertChubbCustomerEndorse(ChubbCustomerEndorse entity) throws SystemException, Exception;

	public Result removeChubbCustomerEndorse(String transactionId) throws SystemException, Exception;
	
}
