package com.tlg.dms.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PrpdRiskService {
	/* mantis：OTH0087，處理人員：BJ085，需求單編號：OTH0087 AML手動登錄 start */
	public int countPrpdRisk(Map params) throws SystemException, Exception;

	public Result findPrpdRiskByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPrpdRiskByParams(Map params) throws SystemException, Exception;

}
