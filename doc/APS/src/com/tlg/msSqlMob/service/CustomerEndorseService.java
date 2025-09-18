package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.CustomerEndorse;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface CustomerEndorseService {

	public int countCustomerEndorse(Map params) throws SystemException, Exception;
	
	public Result findCustomerEndorseByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findCustomerEndorseByParams(Map params) throws SystemException, Exception;

	public Result findCustomerEndorseByUK(String transactionId) throws SystemException, Exception;

	public Result updateCustomerEndorse(CustomerEndorse customerEndorse) throws SystemException, Exception;

	public Result insertCustomerEndorse(CustomerEndorse customerEndorse) throws SystemException, Exception;

	public Result removeCustomerEndorse(String transactionId) throws SystemException, Exception;
	/** mantis：MOB0026，處理人員：CE035，需求單編號：MOB0026 優化手機險對帳流程   ac檔補批單號 */
	public Result selectCeToCorrectEndorseNo(Map params) throws Exception;
	
}
