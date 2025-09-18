package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirCtbcTmpPblDao;
import com.tlg.prpins.entity.FirCtbcTmpPbl;

public class FirCtbcTmpPblDaoImpl extends IBatisBaseDaoImpl<FirCtbcTmpPbl, BigDecimal> implements FirCtbcTmpPblDao {
	
	@Override
	public String getNameSpace() {
		return "FirCtbcTmpPbl";
	}
	
}