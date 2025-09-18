package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirTmpDatacheckDao;
import com.tlg.prpins.entity.FirTmpDatacheck;

public class FirTmpDatacheckDaoImpl extends IBatisBaseDaoImpl<FirTmpDatacheck, BigDecimal> implements FirTmpDatacheckDao {
	/* mantis：FIR0225，處理人員：BJ085，需求單編號：FIR0225 稽核議題調整-過往資料檢核 start */
	@Override
	public String getNameSpace() {
		return "FirTmpDatacheck";
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<FirTmpDatacheck> findBatchesByParams(Map params)throws Exception {	
		List<FirTmpDatacheck> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectBatchesByParams",params);
		return queryForList;
	}

}