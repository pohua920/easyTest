package com.tlg.aps.bs.stockholderSynchronizeServerce.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.stockholderSynchronizeServerce.StockholderSynchronizeService;
import com.tlg.msSqlSh.entity.CtbcStockholderTemp;
import com.tlg.msSqlSh.service.CtbcStockholderTempService;

/* mantis：OTH0101，處理人員：BJ085，需求單編號：OTH0101 取得金控利關人資料同步排程 */
@Transactional(value = "msSqlShTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class StockholderSynchronizeServiceImpl implements StockholderSynchronizeService {

	private CtbcStockholderTempService ctbcStockholderTempService;

	@Override
	public void insertCtbcStockholderTemp(CtbcStockholderTemp ctbcStockholderTemp) throws Exception {
		ctbcStockholderTempService.insertCtbcStockholderTemp(ctbcStockholderTemp);
	}

	@Override
	public void truncateCtbcStockholderTemp() throws Exception {
		ctbcStockholderTempService.truncate();
	}
	
	public CtbcStockholderTempService getCtbcStockholderTempService() {
		return ctbcStockholderTempService;
	}

	public void setCtbcStockholderTempService(CtbcStockholderTempService ctbcStockholderTempService) {
		this.ctbcStockholderTempService = ctbcStockholderTempService;
	}

}
