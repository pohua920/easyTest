package com.tlg.xchg.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.xchg.dao.CtbcPetWs10704Dao;
import com.tlg.xchg.entity.CtbcPetWs10704;

public class CtbcPetWs10704DaoImpl extends IBatisBaseDaoImpl<CtbcPetWs10704, BigDecimal> implements CtbcPetWs10704Dao {
	
	@Override
	public String getNameSpace() {
		return "CtbcPetWs10704";
	}
	
}