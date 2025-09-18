package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PrpdhighareaMcDao;
import com.tlg.prpins.entity.PrpdhighareaMc;

public class PrpdhighareaMcDaoImpl extends IBatisBaseDaoImpl<PrpdhighareaMc, BigDecimal> implements PrpdhighareaMcDao {
	/* mantis：MAR0037，處理人員：BJ085，需求單編號：MAR0037 高風險地區新增維護檔 start*/
	@Override
	public String getNameSpace() {
		return "PrpdhighareaMc";
	}
	
}