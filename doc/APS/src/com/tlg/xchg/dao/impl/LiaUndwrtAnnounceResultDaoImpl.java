package com.tlg.xchg.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.xchg.dao.LiaUndwrtAnnounceResultDao;
import com.tlg.xchg.entity.LiaUndwrtAnnounceResult;

public class LiaUndwrtAnnounceResultDaoImpl extends IBatisBaseDaoImpl<LiaUndwrtAnnounceResult, BigDecimal> implements LiaUndwrtAnnounceResultDao {
	
	@Override
	public String getNameSpace() {
		return "LiaUndwrtAnnounceResult";
	}
	
}