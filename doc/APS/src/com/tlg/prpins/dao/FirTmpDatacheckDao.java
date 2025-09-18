package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.FirTmpDatacheck;

/* mantis：FIR0225，處理人員：BJ085，需求單編號：FIR0225 稽核議題調整-過往資料檢核 start */
public interface FirTmpDatacheckDao extends IBatisBaseDao<FirTmpDatacheck, BigDecimal> {
	
	List<FirTmpDatacheck> findBatchesByParams(Map params)throws Exception;
}