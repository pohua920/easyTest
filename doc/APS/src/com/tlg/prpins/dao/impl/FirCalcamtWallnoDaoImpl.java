package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirCalcamtWallnoDao;
import com.tlg.prpins.entity.FirCalcamtWallno;

public class FirCalcamtWallnoDaoImpl extends IBatisBaseDaoImpl<FirCalcamtWallno, BigDecimal> implements FirCalcamtWallnoDao {
	
	@Override
	public String getNameSpace() {
		return "FirCalcamtWallno";
	}

}