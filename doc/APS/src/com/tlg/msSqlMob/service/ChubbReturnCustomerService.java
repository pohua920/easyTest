package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.ChubbReturnCustomer;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：MOB0010，處理人員：BJ085，需求單編號：MOB0010 安達回傳保單及批單處理結果狀態更新 */
public interface ChubbReturnCustomerService {

	public int countChubbReturnCustomer(Map params) throws SystemException, Exception;
	
	public Result findChubbReturnCustomerByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findChubbReturnCustomerByParams(Map params) throws SystemException, Exception;

	public Result updateChubbReturnCustomer(ChubbReturnCustomer chubbReturnCustomer) throws SystemException, Exception;

	public Result insertChubbReturnCustomer(ChubbReturnCustomer chubbReturnCustomer) throws SystemException, Exception;

	public Result removeChubbReturnCustomer(String oid) throws SystemException, Exception;
	
}
