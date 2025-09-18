package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.FirBatchPins01Detail;

/* mantis：FIR0436，處理人員：BJ085，需求單編號：FIR0436 住火-APS保單通訊資料產生及下載作業批次作業 */
public interface FirBatchPins01DetailDao extends IBatisBaseDao<FirBatchPins01Detail, BigDecimal> {

	public Integer selectSumRiskcodecount(Map params) throws Exception;
	
}