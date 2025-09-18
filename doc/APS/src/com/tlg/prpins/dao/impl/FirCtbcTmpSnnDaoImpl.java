package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirCtbcTmpSnnDao;
import com.tlg.prpins.entity.FirCtbcTmpSnn;

public class FirCtbcTmpSnnDaoImpl extends IBatisBaseDaoImpl<FirCtbcTmpSnn, BigDecimal> implements FirCtbcTmpSnnDao {
	
	@Override
	public String getNameSpace() {
		return "FirCtbcTmpSnn";
	}
	
}