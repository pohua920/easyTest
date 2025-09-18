package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PbPremiumRate0Dao;
import com.tlg.prpins.entity.PbPremiumRate0;

public class PbPremiumRate0DaoImpl extends IBatisBaseDaoImpl<PbPremiumRate0, BigDecimal> implements PbPremiumRate0Dao {
	
	@Override
	public String getNameSpace() {
		return "PbPremiumRate0";
	}

}