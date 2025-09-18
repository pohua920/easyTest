package com.tlg.msSqlSh.service.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.msSqlSh.dao.MsSqlSHSpDao;
import com.tlg.msSqlSh.service.MsSqlSHSpService;

/* mantis：OTH0101，處理人員：BJ085，需求單編號：OTH0101 取得金控利關人資料同步排程 */
@Transactional(value = "msSqlShTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class MsSqlSHSpServiceImpl implements MsSqlSHSpService{

	private MsSqlSHSpDao msSqlSHSpDao;

	@Override
	public void runSpCtbcStockholder() throws Exception {
		msSqlSHSpDao.runSpCtbcStockholder();
	}

	public MsSqlSHSpDao getMsSqlSHSpDao() {
		return msSqlSHSpDao;
	}

	public void setMsSqlSHSpDao(MsSqlSHSpDao msSqlSHSpDao) {
		this.msSqlSHSpDao = msSqlSHSpDao;
	}
	
}
