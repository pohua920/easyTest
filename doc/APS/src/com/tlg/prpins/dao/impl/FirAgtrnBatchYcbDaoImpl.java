package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirAgtrnBatchYcbDao;
import com.tlg.prpins.entity.FirAgtrnBatchYcb;

/** mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 */
public class FirAgtrnBatchYcbDaoImpl extends IBatisBaseDaoImpl<FirAgtrnBatchYcb, BigDecimal> implements FirAgtrnBatchYcbDao {
	@Override
	public String getNameSpace() {
		return "FirAgtrnBatchYcb";
	}
	
}