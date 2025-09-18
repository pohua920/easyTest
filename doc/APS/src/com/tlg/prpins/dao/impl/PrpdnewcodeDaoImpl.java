package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PrpdnewcodeDao;
import com.tlg.prpins.entity.Prpdnewcode;

public class PrpdnewcodeDaoImpl extends IBatisBaseDaoImpl<Prpdnewcode, BigDecimal> implements PrpdnewcodeDao {
	
	@Override
	public String getNameSpace() {
		return "Prpdnewcode";
	}
	
}