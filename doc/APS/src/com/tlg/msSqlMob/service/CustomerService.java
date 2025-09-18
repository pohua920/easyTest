package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.Customer;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
public interface CustomerService {

	@SuppressWarnings("rawtypes")
	public int countCustomer(Map params) throws SystemException, Exception;
	
	public Result findCustomerByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	@SuppressWarnings("rawtypes")
	public Result findCustomerByParams(Map params) throws SystemException, Exception;

	public Result findCustomerByUK(String transactionId) throws SystemException, Exception;

	public Result updateCustomer(Customer customer) throws SystemException, Exception;

	public Result insertCustomer(Customer customer) throws SystemException, Exception;

	public Result removeCustomer(String transactionId) throws SystemException, Exception;
	
	public Result selectForAps050(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result selectForAps050Detail(String transactionId) throws SystemException, Exception;
	/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 回傳保單號給數開 */
	public Result returnPolicyNoToG10(String rptBatchNo) throws SystemException, Exception;
	
}
