package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirPremiumRateDao;
import com.tlg.prpins.entity.FirPremiumRate;

public class FirPremiumRateDaoImpl extends IBatisBaseDaoImpl<FirPremiumRate, BigDecimal> implements FirPremiumRateDao {
	
	@Override
	public String getNameSpace() {
		return "FirPremiumRate";
	}

}