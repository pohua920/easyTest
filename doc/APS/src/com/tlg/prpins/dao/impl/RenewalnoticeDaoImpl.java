package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.RenewalnoticeDao;
import com.tlg.prpins.entity.Renewalnotice;

/*mantis：CAR0417，處理人員：BJ085，需求單編號：CAR0417 機車強制車險重新投保發對接功能 */
public class RenewalnoticeDaoImpl extends IBatisBaseDaoImpl<Renewalnotice, BigDecimal> implements RenewalnoticeDao {
	
	@Override
	public String getNameSpace() {
		return "Renewalnotice";
	}

}