package com.tlg.prpins.dao;

import java.math.BigDecimal;

import com.tlg.prpins.entity.FirCtbcRewMatchLog;
import com.tlg.iBatis.IBatisBaseDao;

public interface FirCtbcRewMatchLogDao extends IBatisBaseDao<FirCtbcRewMatchLog, BigDecimal> {
	public BigDecimal getOid() throws Exception;
}