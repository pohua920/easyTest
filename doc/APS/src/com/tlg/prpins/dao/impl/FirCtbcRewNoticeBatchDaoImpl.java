package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirCtbcRewNoticeBatchDao;
import com.tlg.prpins.entity.FirCtbcRewNoticeBatch;

public class FirCtbcRewNoticeBatchDaoImpl extends IBatisBaseDaoImpl<FirCtbcRewNoticeBatch, BigDecimal> implements FirCtbcRewNoticeBatchDao {
	
	@Override
	public String getNameSpace() {
		return "FirCtbcRewNoticeBatch";
	}
	
}