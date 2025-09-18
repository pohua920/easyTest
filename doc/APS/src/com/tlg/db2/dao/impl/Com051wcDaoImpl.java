package com.tlg.db2.dao.impl;

import java.math.BigDecimal;

import com.tlg.db2.dao.Com051wcDao;
import com.tlg.db2.entity.Com051wc;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;

public class Com051wcDaoImpl extends IBatisBaseDaoImpl<Com051wc, BigDecimal> implements Com051wcDao {
	
	@Override
	public String getNameSpace() {
		return "Com051wc";
	}

}