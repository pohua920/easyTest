package com.tlg.xchg.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.xchg.dao.CwpIpb905iDao;
import com.tlg.xchg.entity.CwpIpb905i;

public class CwpIpb905iDaoImpl extends IBatisBaseDaoImpl<CwpIpb905i, BigDecimal> implements CwpIpb905iDao {
	
	@Override
	public String getNameSpace() {
		return "CwpIpb905i";
	}
	
}