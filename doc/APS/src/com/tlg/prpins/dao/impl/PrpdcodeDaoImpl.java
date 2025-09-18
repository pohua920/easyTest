package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PrpdcodeDao;
import com.tlg.prpins.entity.Prpdcode;

public class PrpdcodeDaoImpl extends IBatisBaseDaoImpl<Prpdcode, BigDecimal> implements PrpdcodeDao {
	
	@Override
	public String getNameSpace() {
		return "Prpdcode";
	}
	
}