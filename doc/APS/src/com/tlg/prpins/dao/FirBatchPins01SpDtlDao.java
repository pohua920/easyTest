package com.tlg.prpins.dao;

import java.math.BigDecimal;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.FirBatchPins01SpDtl;

/* mantis：FIR0436，處理人員：BJ085，需求單編號：FIR0436 住火-APS保單通訊資料產生及下載作業批次作業 */
public interface FirBatchPins01SpDtlDao extends IBatisBaseDao<FirBatchPins01SpDtl, BigDecimal> {

	public void truncate() throws Exception;
}