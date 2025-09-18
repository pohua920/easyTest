package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirBatchLogDao;
import com.tlg.prpins.entity.FirBatchLog;

public class FirBatchLogDaoImpl extends IBatisBaseDaoImpl<FirBatchLog, BigDecimal> implements FirBatchLogDao {
	
	@Override
	public String getNameSpace() {
		return "FirBatchLog";
	}

	@Override
	public String selectLastBatchNo() throws Exception {
		String batchNo = (String) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".selectLastBatchNo");
		return batchNo;
	}
	
}