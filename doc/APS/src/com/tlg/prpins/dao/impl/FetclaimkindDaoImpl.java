package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FetclaimkindDao;
import com.tlg.prpins.entity.Fetclaimkind;

/** mantis：MOB0018，處理人員：BJ085，需求單編號：MOB0018 理賠資料提交及列印作業 */
public class FetclaimkindDaoImpl extends IBatisBaseDaoImpl<Fetclaimkind, BigDecimal> implements FetclaimkindDao {
	
	@Override
	public String getNameSpace() {
		return "Fetclaimkind";
	}

}