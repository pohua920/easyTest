package com.tlg.prpins.service;

import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：FIR0547，處理人員：CC009，需求單編號：FIR0547 聯邦新件資料交換查詢作業 */
public interface FirAgtUb02Service {
	public Result findAPS041Main3ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
}
