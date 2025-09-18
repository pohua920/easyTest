package com.tlg.db2.dao.impl;

import java.math.BigDecimal;

import com.tlg.db2.dao.Com580WKDao;
import com.tlg.db2.entity.Com580WK;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;

public class Com580WKDaoImpl extends IBatisBaseDaoImpl<Com580WK, BigDecimal> implements Com580WKDao {
	
	@Override
	public String getNameSpace() {
		return "Com580WK";
	}

}