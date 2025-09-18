package com.tlg.sales.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程 start */
public interface PrpdAgreementService {
	public int countPrpdAgreement(Map params) throws SystemException, Exception;

	public Result findPrpdAgreementByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPrpdAgreementByParams(Map params) throws SystemException, Exception;
	
	//mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程
	public Result findPrpdAgreementJoinDetail(Map params) throws SystemException, Exception;
}
