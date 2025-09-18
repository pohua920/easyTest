package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PbPremiumRate5Dao;
import com.tlg.prpins.entity.PbPremiumRate5;

public class PbPremiumRate5DaoImpl extends IBatisBaseDaoImpl<PbPremiumRate5, BigDecimal> implements PbPremiumRate5Dao {
	
	@Override
	public String getNameSpace() {
		return "PbPremiumRate5";
	}

}