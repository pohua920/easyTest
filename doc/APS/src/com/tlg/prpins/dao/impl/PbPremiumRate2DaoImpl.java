package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PbPremiumRate2Dao;
import com.tlg.prpins.entity.PbPremiumRate2;

public class PbPremiumRate2DaoImpl extends IBatisBaseDaoImpl<PbPremiumRate2, BigDecimal> implements PbPremiumRate2Dao {
	
	@Override
	public String getNameSpace() {
		return "PbPremiumRate2";
	}

}