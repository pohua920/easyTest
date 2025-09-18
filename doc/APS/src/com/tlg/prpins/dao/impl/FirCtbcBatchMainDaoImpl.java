package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirCtbcBatchMainDao;
import com.tlg.prpins.entity.FirCtbcBatchMain;

public class FirCtbcBatchMainDaoImpl extends IBatisBaseDaoImpl<FirCtbcBatchMain, BigDecimal> implements FirCtbcBatchMainDao {
	
	@Override
	public String getNameSpace() {
		return "FirCtbcBatchMain";
	}
	
}