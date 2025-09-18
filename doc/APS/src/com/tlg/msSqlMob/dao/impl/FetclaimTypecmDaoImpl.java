package com.tlg.msSqlMob.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlMob.dao.FetclaimTypecmDao;
import com.tlg.msSqlMob.entity.FetclaimTypecm;

/** mantis：MOB0020，處理人員：BI086，需求單編號：MOB0020 理賠盜打詐騙API及XML作業 */
public class FetclaimTypecmDaoImpl extends IBatisBaseDaoImpl<FetclaimTypecm, BigDecimal> implements FetclaimTypecmDao {
	@Override
	public String getNameSpace() {
		return "FetclaimTypecm";
	}
}