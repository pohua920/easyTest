package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirPremiumRate2Dao;
import com.tlg.prpins.entity.FirPremiumRate2;

public class FirPremiumRate2DaoImpl extends IBatisBaseDaoImpl<FirPremiumRate2, BigDecimal> implements FirPremiumRate2Dao {
	
	@Override
	public String getNameSpace() {
		return "FirPremiumRate2";
	}

}