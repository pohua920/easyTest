package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirBatchSendmailOkDao;
import com.tlg.prpins.entity.FirBatchSendmailOk;

public class FirBatchSendmailOkDaoImpl extends IBatisBaseDaoImpl<FirBatchSendmailOk, BigDecimal> implements FirBatchSendmailOkDao {
	
	@Override
	public String getNameSpace() {
		return "FirBatchSendmailOk";
	}
	
}