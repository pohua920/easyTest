package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PbPremiumRate7Dao;
import com.tlg.prpins.entity.PbPremiumRate7;

public class PbPremiumRate7DaoImpl extends IBatisBaseDaoImpl<PbPremiumRate7, BigDecimal> implements PbPremiumRate7Dao {
	
	@Override
	public String getNameSpace() {
		return "PbPremiumRate7";
	}

}