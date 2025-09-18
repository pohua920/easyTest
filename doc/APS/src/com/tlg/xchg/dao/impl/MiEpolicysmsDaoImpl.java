package com.tlg.xchg.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.xchg.dao.MiEpolicysmsDao;
import com.tlg.xchg.entity.MiEpolicysms;

public class MiEpolicysmsDaoImpl extends IBatisBaseDaoImpl<MiEpolicysms, BigDecimal> implements MiEpolicysmsDao {
	
	@Override
	public String getNameSpace() {
		return "MiEpolicysms";
	}
	
}