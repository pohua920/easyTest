package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirBatchPins01Dao;
import com.tlg.prpins.entity.FirBatchPins01;

/* mantis：FIR0427，處理人員：BJ085，需求單編號：FIR0427 個人險-保單通訊資料產生及下載作業 */
public class FirBatchPins01DaoImpl extends IBatisBaseDaoImpl<FirBatchPins01, BigDecimal> implements FirBatchPins01Dao {
	
	@Override
	public String getNameSpace() {
		return "FirBatchPins01";
	}
	
}