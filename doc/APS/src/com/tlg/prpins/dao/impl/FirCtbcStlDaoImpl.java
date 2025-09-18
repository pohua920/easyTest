package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirCtbcStlDao;
import com.tlg.prpins.entity.FirCtbcStl;

public class FirCtbcStlDaoImpl extends IBatisBaseDaoImpl<FirCtbcStl, BigDecimal> implements FirCtbcStlDao {
	
	@Override
	public String getNameSpace() {
		return "FirCtbcStl";
	}
	
}