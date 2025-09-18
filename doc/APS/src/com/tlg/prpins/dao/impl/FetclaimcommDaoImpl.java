package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FetclaimcommDao;
import com.tlg.prpins.entity.Fetclaimcomm;

/** mantis：MOB0018，處理人員：BJ085，需求單編號：MOB0018 理賠資料提交及列印作業 */
public class FetclaimcommDaoImpl extends IBatisBaseDaoImpl<Fetclaimcomm, BigDecimal> implements FetclaimcommDao {
	
	@Override
	public String getNameSpace() {
		return "Fetclaimcomm";
	}

}