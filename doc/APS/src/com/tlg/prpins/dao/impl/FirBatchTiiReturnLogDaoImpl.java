package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirBatchTiiReturnLogDao;
import com.tlg.prpins.entity.FirBatchTiiReturnLog;

/** mantis：FIR0580，處理人員：BJ085，需求單編號：FIR0580 保發中心-住火保批資料回饋檔回存資料庫 */
public class FirBatchTiiReturnLogDaoImpl extends IBatisBaseDaoImpl<FirBatchTiiReturnLog, BigDecimal> implements FirBatchTiiReturnLogDao {
	
	@Override
	public String getNameSpace() {
		return "FirBatchTiiReturnLog";
	}
}