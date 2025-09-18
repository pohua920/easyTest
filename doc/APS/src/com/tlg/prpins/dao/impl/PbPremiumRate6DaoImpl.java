package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PbPremiumRate6Dao;
import com.tlg.prpins.entity.PbPremiumRate6;

public class PbPremiumRate6DaoImpl extends IBatisBaseDaoImpl<PbPremiumRate6, BigDecimal> implements PbPremiumRate6Dao {
	
	@Override
	public String getNameSpace() {
		return "PbPremiumRate6";
	}

}