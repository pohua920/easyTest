package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirPremiumRate1Dao;
import com.tlg.prpins.entity.FirPremiumRate1;

public class FirPremiumRate1DaoImpl extends IBatisBaseDaoImpl<FirPremiumRate1, BigDecimal> implements FirPremiumRate1Dao {
	
	@Override
	public String getNameSpace() {
		return "FirPremiumRate1";
	}

}