package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PbPremiumRate10Dao;
import com.tlg.prpins.entity.PbPremiumRate10;

public class PbPremiumRate10DaoImpl extends IBatisBaseDaoImpl<PbPremiumRate10, BigDecimal> implements PbPremiumRate10Dao {
	
	@Override
	public String getNameSpace() {
		return "PbPremiumRate10";
	}

}