package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirCtbcSigDao;
import com.tlg.prpins.entity.FirCtbcSig;

public class FirCtbcSigDaoImpl extends IBatisBaseDaoImpl<FirCtbcSig, BigDecimal> implements FirCtbcSigDao {
	
	@Override
	public String getNameSpace() {
		return "FirCtbcSig";
	}
	
}