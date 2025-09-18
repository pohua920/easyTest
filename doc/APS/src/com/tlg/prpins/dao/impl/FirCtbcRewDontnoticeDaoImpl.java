package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirCtbcRewDontnoticeDao;
import com.tlg.prpins.entity.FirCtbcRewDontnotice;

public class FirCtbcRewDontnoticeDaoImpl extends IBatisBaseDaoImpl<FirCtbcRewDontnotice, BigDecimal> implements FirCtbcRewDontnoticeDao {
	
	@Override
	public String getNameSpace() {
		return "FirCtbcRewDontnotice";
	}
	
}