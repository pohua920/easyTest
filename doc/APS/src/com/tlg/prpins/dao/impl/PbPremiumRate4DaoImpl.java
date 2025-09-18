package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PbPremiumRate4Dao;
import com.tlg.prpins.entity.PbPremiumRate4;

public class PbPremiumRate4DaoImpl extends IBatisBaseDaoImpl<PbPremiumRate4, BigDecimal> implements PbPremiumRate4Dao {
	
	@Override
	public String getNameSpace() {
		return "PbPremiumRate4";
	}

}