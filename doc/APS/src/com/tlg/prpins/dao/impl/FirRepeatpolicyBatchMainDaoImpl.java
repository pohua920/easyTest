package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirRepeatpolicyBatchMainDao;
import com.tlg.prpins.entity.FirRepeatpolicyBatchMain;

/* mantis：FIR0565，處理人員：CC009，需求單編號：FIR0565 住火_複保險通知轉檔作業 */
public class FirRepeatpolicyBatchMainDaoImpl extends IBatisBaseDaoImpl<FirRepeatpolicyBatchMain, BigDecimal> implements FirRepeatpolicyBatchMainDao {
	
	@Override
	public String getNameSpace() {
		return "FirRepeatpolicyBatchMain";
	}
}