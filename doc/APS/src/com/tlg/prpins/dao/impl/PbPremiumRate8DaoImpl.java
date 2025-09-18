package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PbPremiumRate8Dao;
import com.tlg.prpins.entity.PbPremiumRate8;

public class PbPremiumRate8DaoImpl extends IBatisBaseDaoImpl<PbPremiumRate8, BigDecimal> implements PbPremiumRate8Dao {
	
	@Override
	public String getNameSpace() {
		return "PbPremiumRate8";
	}

}