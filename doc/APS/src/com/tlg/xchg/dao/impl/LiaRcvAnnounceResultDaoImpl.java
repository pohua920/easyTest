package com.tlg.xchg.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.xchg.dao.LiaRcvAnnounceResultDao;
import com.tlg.xchg.entity.LiaRcvAnnounceResult;

public class LiaRcvAnnounceResultDaoImpl extends IBatisBaseDaoImpl<LiaRcvAnnounceResult, BigDecimal> implements LiaRcvAnnounceResultDao {
	
	@Override
	public String getNameSpace() {
		return "LiaRcvAnnounceResult";
	}
	
}