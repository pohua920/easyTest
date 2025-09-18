package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirBatchTiiDao;
import com.tlg.prpins.entity.FirBatchTii;

/** mantis：FIR0579，處理人員：BJ085，需求單編號：FIR0579 保發中心-住火保批資料產生排程規格 */
public class FirBatchTiiDaoImpl extends IBatisBaseDaoImpl<FirBatchTii, BigDecimal> implements FirBatchTiiDao {
	
	@Override
	public String getNameSpace() {
		return "FirBatchTii";
	}
	
}