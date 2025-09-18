package com.tlg.xchg.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.xchg.dao.LiaMiEpolicyDao;
import com.tlg.xchg.entity.LiaMiEpolicy;

public class LiaMiEpolicyDaoImpl extends IBatisBaseDaoImpl<LiaMiEpolicy, BigDecimal> implements LiaMiEpolicyDao {
	
	@Override
	public String getNameSpace() {
		return "LiaMiEpolicy";
	}
	
}