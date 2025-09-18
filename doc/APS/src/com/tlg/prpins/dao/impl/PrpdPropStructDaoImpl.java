package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PrpdPropStructDao;
import com.tlg.prpins.entity.PrpdPropStruct;

public class PrpdPropStructDaoImpl extends IBatisBaseDaoImpl<PrpdPropStruct, BigDecimal> implements PrpdPropStructDao {
	
	@Override
	public String getNameSpace() {
		return "PrpdPropStruct";
	}

}