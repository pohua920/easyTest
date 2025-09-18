package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.IntfprpjpayrefrecDao;
import com.tlg.prpins.entity.Intfprpjpayrefrec;

public class IntfprpjpayrefrecDaoImpl extends IBatisBaseDaoImpl<Intfprpjpayrefrec, BigDecimal> implements IntfprpjpayrefrecDao {
	
	@Override
	public String getNameSpace() {
		return "Intfprpjpayrefrec";
	}
	
}