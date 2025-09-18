package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirAgtrnAs400DataErrDao;
import com.tlg.prpins.entity.FirAgtrnAs400DataErr;

/**mantis：FIR0388，處理人員：BJ085，需求單編號：FIR0388 AS400續保資料匯入  */
public class FirAgtrnAs400DataErrDaoImpl extends IBatisBaseDaoImpl<FirAgtrnAs400DataErr, BigDecimal> implements FirAgtrnAs400DataErrDao {
	@Override
	public String getNameSpace() {
		return "FirAgtrnAs400DataErr";
	}
}