package com.tlg.msSqlSh.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlSh.entity.CtbcStockholderTemp;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：OTH0101，處理人員：BJ085，需求單編號：OTH0101 取得金控利關人資料同步排程 */
public interface CtbcStockholderTempService {

	public int countCtbcStockholderTemp(Map params) throws SystemException, Exception;
	
	public Result findCtbcStockholderTempByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findCtbcStockholderTempByParams(Map params) throws SystemException, Exception;

	public Result insertCtbcStockholderTemp(CtbcStockholderTemp ctbcStockholderTemp) throws SystemException, Exception;

	public void truncate() throws Exception;
}
