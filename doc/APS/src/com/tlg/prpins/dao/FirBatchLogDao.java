package com.tlg.prpins.dao;

import java.math.BigDecimal;

import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.iBatis.IBatisBaseDao;

public interface FirBatchLogDao extends IBatisBaseDao<FirBatchLog, BigDecimal> {
	
	/**
	 * 取得各項分數
	 */
	public String selectLastBatchNo()throws Exception;
	
}