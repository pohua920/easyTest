package com.tlg.db2.dao.impl;

import java.math.BigDecimal;

import com.tlg.db2.dao.Com704wbDao;
import com.tlg.db2.entity.Com704wb;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;

public class Com704wbDaoImpl extends IBatisBaseDaoImpl<Com704wb, BigDecimal> implements Com704wbDao {
	
	@Override
	public String getNameSpace() {
		return "Com704wb";
	}

}