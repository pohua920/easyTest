package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirAgtBatchGenfileDao;
import com.tlg.prpins.entity.FirAgtBatchGenfile;

public class FirAgtBatchGenfileDaoImpl extends IBatisBaseDaoImpl<FirAgtBatchGenfile, BigDecimal> implements FirAgtBatchGenfileDao {
	/* mantis：FIR0266，處理人員：BJ085，需求單編號：FIR0266 板信資料交換處理作業 start */
	@Override
	public String getNameSpace() {
		return "FirAgtBatchGenfile";
	}
}