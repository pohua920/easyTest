package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirCtbcRewNoshowwordDao;
import com.tlg.prpins.entity.FirCtbcRewNoshowword;

public class FirCtbcRewNoshowwordDaoImpl extends IBatisBaseDaoImpl<FirCtbcRewNoshowword, BigDecimal> implements FirCtbcRewNoshowwordDao {
	
	@Override
	public String getNameSpace() {
		return "FirCtbcRewNoshowword";
	}
	
}