package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirCtbcTmpStlDao;
import com.tlg.prpins.entity.FirCtbcTmpStl;

public class FirCtbcTmpStlDaoImpl extends IBatisBaseDaoImpl<FirCtbcTmpStl, BigDecimal> implements FirCtbcTmpStlDao {
	
	@Override
	public String getNameSpace() {
		return "FirCtbcTmpStl";
	}
	
}