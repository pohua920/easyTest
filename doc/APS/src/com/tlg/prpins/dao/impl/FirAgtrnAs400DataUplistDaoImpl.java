package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirAgtrnAs400DataUplistDao;
import com.tlg.prpins.entity.FirAgtrnAs400DataUplist;

/**mantis：FIR0388，處理人員：BJ085，需求單編號：FIR0388 AS400續保資料匯入  */
public class FirAgtrnAs400DataUplistDaoImpl extends IBatisBaseDaoImpl<FirAgtrnAs400DataUplist, BigDecimal> implements FirAgtrnAs400DataUplistDao {
	@Override
	public String getNameSpace() {
		return "FirAgtrnAs400DataUplist";
	}
}