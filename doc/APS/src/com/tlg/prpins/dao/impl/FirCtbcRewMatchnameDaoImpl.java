package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirCtbcRewMatchnameDao;
import com.tlg.prpins.entity.FirCtbcRewMatchname;

public class FirCtbcRewMatchnameDaoImpl extends IBatisBaseDaoImpl<FirCtbcRewMatchname, BigDecimal> implements FirCtbcRewMatchnameDao {
	
	@Override
	public String getNameSpace() {
		return "FirCtbcRewMatchname";
	}
	
}