package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PbPremiumRate9Dao;
import com.tlg.prpins.entity.PbPremiumRate9;

public class PbPremiumRate9DaoImpl extends IBatisBaseDaoImpl<PbPremiumRate9, BigDecimal> implements PbPremiumRate9Dao {
	
	@Override
	public String getNameSpace() {
		return "PbPremiumRate9";
	}

}