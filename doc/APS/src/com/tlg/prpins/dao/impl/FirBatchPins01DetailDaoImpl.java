package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirBatchPins01DetailDao;
import com.tlg.prpins.entity.FirBatchPins01Detail;

/* mantis：FIR0436，處理人員：BJ085，需求單編號：FIR0436 住火-APS保單通訊資料產生及下載作業批次作業 */
public class FirBatchPins01DetailDaoImpl extends IBatisBaseDaoImpl<FirBatchPins01Detail, BigDecimal> implements FirBatchPins01DetailDao {
	
	@Override
	public String getNameSpace() {
		return "FirBatchPins01Detail";
	}
	
	@Override
	public Integer selectSumRiskcodecount(Map params) throws Exception {
		int count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".selectSumRiskcodecount",params);
		return count;
	}
}