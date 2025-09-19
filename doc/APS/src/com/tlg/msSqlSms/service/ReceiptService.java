package com.tlg.msSqlSms.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlSms.entity.Receipt;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface ReceiptService {

	public int countReceipt(Map params) throws SystemException, Exception;
	
	public Result findReceiptByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findReceiptByParams(Map params) throws SystemException, Exception;
	
	public Result findTopReceiptByParams(Map params) throws SystemException, Exception;

	public Result findReceiptByUK(String messagId, String destAddress, String seq) throws SystemException, Exception;

	public Result updateReceipt(Receipt receipt) throws SystemException, Exception;

	public Result insertReceipt(Receipt receipt) throws SystemException, Exception;
	

}
