package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirDoubleQuakeQlistDao;
import com.tlg.prpins.entity.FirDoubleQuakeQlist;

public class FirDoubleQuakeQlistDaoImpl extends IBatisBaseDaoImpl<FirDoubleQuakeQlist, BigDecimal> implements FirDoubleQuakeQlistDao{
	
	@Override
	public String getNameSpace() {
		return "FirDoubleQuakeQlist";
	}

}