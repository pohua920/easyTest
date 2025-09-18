package com.tlg.msSqlMob.dao.impl;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlMob.dao.ProposalInsufficientSmsDao;
import com.tlg.msSqlMob.entity.ProposalInsufficientSms;

import java.math.BigDecimal;

public class ProposalInsufficientSmsDaoImpl extends IBatisBaseDaoImpl<ProposalInsufficientSms, BigDecimal> implements ProposalInsufficientSmsDao {
	@Override
	public String getNameSpace() {
		return "ProposalInsufficientSms";
	}
}