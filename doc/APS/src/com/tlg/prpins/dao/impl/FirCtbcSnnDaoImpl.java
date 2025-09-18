package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirCtbcSnnDao;
import com.tlg.prpins.entity.FirCtbcSnn;

public class FirCtbcSnnDaoImpl extends IBatisBaseDaoImpl<FirCtbcSnn, BigDecimal> implements FirCtbcSnnDao {
	
	@Override
	public String getNameSpace() {
		return "FirCtbcSnn";
	}
	
}