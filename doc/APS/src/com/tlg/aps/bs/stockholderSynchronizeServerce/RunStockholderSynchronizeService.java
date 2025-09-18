package com.tlg.aps.bs.stockholderSynchronizeServerce;

import java.util.Date;

import com.tlg.util.Result;

/* mantis：OTH0101，處理人員：BJ085，需求單編號：OTH0101 取得金控利關人資料同步排程 */
public interface RunStockholderSynchronizeService {

	public Result stockholderDataSynchronize(Date executeTime) throws Exception;
	
}
