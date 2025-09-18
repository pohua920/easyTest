package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirCtbcRewMatchLogDao;
import com.tlg.prpins.entity.FirCtbcRewMatchLog;

public class FirCtbcRewMatchLogDaoImpl extends IBatisBaseDaoImpl<FirCtbcRewMatchLog, BigDecimal> implements FirCtbcRewMatchLogDao {
	
	@Override
	public String getNameSpace() {
		return "FirCtbcRewMatchLog";
	}

	@Override
	public BigDecimal getOid() throws Exception {
		BigDecimal oid = (BigDecimal) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".getOid");
		return oid;
	}
	
}