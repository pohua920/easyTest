package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PbPremiumRate1Dao;
import com.tlg.prpins.entity.PbPremiumRate1;

public class PbPremiumRate1DaoImpl extends IBatisBaseDaoImpl<PbPremiumRate1, BigDecimal> implements PbPremiumRate1Dao {
	
	@Override
	public String getNameSpace() {
		return "PbPremiumRate1";
	}

}