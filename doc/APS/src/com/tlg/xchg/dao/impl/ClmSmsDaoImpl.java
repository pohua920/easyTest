package com.tlg.xchg.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.xchg.dao.ClmSmsDao;
import com.tlg.xchg.entity.ClmSms;

public class ClmSmsDaoImpl extends IBatisBaseDaoImpl<ClmSms, BigDecimal> implements ClmSmsDao {
	
	@Override
	public String getNameSpace() {
		return "ClmSms";
	}


}