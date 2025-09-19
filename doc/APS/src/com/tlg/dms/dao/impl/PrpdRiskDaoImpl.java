package com.tlg.dms.dao.impl;

import java.math.BigDecimal;

import com.tlg.dms.dao.PrpdRiskDao;
import com.tlg.dms.entity.PrpdRisk;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;

public class PrpdRiskDaoImpl extends IBatisBaseDaoImpl<PrpdRisk, BigDecimal> implements PrpdRiskDao {
	/* mantis：OTH0087，處理人員：BJ085，需求單編號：OTH0087 AML手動登錄 start */
	@Override
	public String getNameSpace() {
		return "PrpdRisk";
	}

}