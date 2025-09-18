package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.UwblacklistDao;
import com.tlg.prpins.entity.Uwblacklist;

public class UwblacklistDaoImpl extends IBatisBaseDaoImpl<Uwblacklist, BigDecimal> implements UwblacklistDao {
	
	@Override
	public String getNameSpace() {
		return "Uwblacklist";
	}
	
}