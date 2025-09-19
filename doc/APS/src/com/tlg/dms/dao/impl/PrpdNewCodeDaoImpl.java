package com.tlg.dms.dao.impl;

import java.math.BigDecimal;

import com.tlg.dms.dao.PrpdNewCodeDao;
import com.tlg.dms.entity.PrpdNewCode;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;

public class PrpdNewCodeDaoImpl extends IBatisBaseDaoImpl<PrpdNewCode, BigDecimal> implements PrpdNewCodeDao {
	
	@Override
	public String getNameSpace() {
		return "PrpdNewCode";
	}

}