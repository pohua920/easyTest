package com.tlg.db2.dao.impl;

import java.math.BigDecimal;

import com.tlg.db2.dao.Com704waDao;
import com.tlg.db2.entity.Com704wa;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;

public class Com704waDaoImpl extends IBatisBaseDaoImpl<Com704wa, BigDecimal> implements Com704waDao {
	
	@Override
	public String getNameSpace() {
		return "Com704wa";
	}

}