package com.tlg.aps.bs.stockholderSynchronizeServerce;

import com.tlg.msSqlSh.entity.CtbcStockholderTemp;

/* mantis：OTH0101，處理人員：BJ085，需求單編號：OTH0101 取得金控利關人資料同步排程 */
public interface StockholderSynchronizeService {
	
	public void insertCtbcStockholderTemp(CtbcStockholderTemp ctbcStockholderTemp) throws Exception;
	
	public void truncateCtbcStockholderTemp() throws Exception;
}
