package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirCtbcRewOriginal180Dao;
import com.tlg.prpins.entity.FirCtbcRewOriginal180;

public class FirCtbcRewOriginal180DaoImpl extends IBatisBaseDaoImpl<FirCtbcRewOriginal180, BigDecimal> implements FirCtbcRewOriginal180Dao {
	
	@Override
	public String getNameSpace() {
		return "FirCtbcRewOriginal180";
	}
	
}