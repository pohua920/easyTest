package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PbPremiumRateDao;
import com.tlg.prpins.entity.PbPremiumRate;

public class PbPremiumRateDaoImpl extends IBatisBaseDaoImpl<PbPremiumRate, BigDecimal> implements PbPremiumRateDao {
	
	@Override
	public String getNameSpace() {
		return "PbPremiumRate";
	}

}