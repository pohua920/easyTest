package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PrpdBaseRateDao;
import com.tlg.prpins.entity.PrpdBaseRate;

public class PrpdBaseRateDaoImpl extends IBatisBaseDaoImpl<PrpdBaseRate, BigDecimal> implements PrpdBaseRateDao {
	
	@Override
	public String getNameSpace() {
		return "PrpdBaseRate";
	}

}