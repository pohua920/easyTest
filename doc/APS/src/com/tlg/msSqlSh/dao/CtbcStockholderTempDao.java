package com.tlg.msSqlSh.dao;

import java.math.BigDecimal;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.msSqlSh.entity.CtbcStockholderTemp;

/* mantis：OTH0101，處理人員：BJ085，需求單編號：OTH0101 取得金控利關人資料同步排程 */
public interface CtbcStockholderTempDao extends IBatisBaseDao<CtbcStockholderTemp, BigDecimal> {
	
	public void truncate() throws Exception;
}