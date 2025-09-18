package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirBatchPins01SpDtlDao;
import com.tlg.prpins.entity.FirBatchPins01SpDtl;

/* mantis：FIR0436，處理人員：BJ085，需求單編號：FIR0436 住火-APS保單通訊資料產生及下載作業批次作業 */
public class FirBatchPins01SpDtlDaoImpl extends IBatisBaseDaoImpl<FirBatchPins01SpDtl, BigDecimal> implements FirBatchPins01SpDtlDao {
	
	@Override
	public String getNameSpace() {
		return "FirBatchPins01SpDtl";
	}
	
	@Override
	public void truncate() throws Exception {
		getSqlMapClientTemplate().update(getNameSpace()+".truncate");
	}
}