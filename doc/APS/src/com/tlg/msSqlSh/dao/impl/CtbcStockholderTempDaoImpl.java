package com.tlg.msSqlSh.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlSh.dao.CtbcStockholderTempDao;
import com.tlg.msSqlSh.entity.CtbcStockholderTemp;

/* mantis：OTH0101，處理人員：BJ085，需求單編號：OTH0101 取得金控利關人資料同步排程 */
public class CtbcStockholderTempDaoImpl extends IBatisBaseDaoImpl<CtbcStockholderTemp, BigDecimal> implements CtbcStockholderTempDao {
	
	@Override
	public String getNameSpace() {
		return "CtbcStockholderTemp";
	}
	
	@Override
	public void truncate() throws Exception {
		getSqlMapClientTemplate().delete(getNameSpace()+".truncate");
	}

}