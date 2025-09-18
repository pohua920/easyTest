package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirAgtBotApDao;
import com.tlg.prpins.entity.FirAgtBotAp;

/* mantis：FIR0314，處理人員：BJ085，需求單編號：FIR0314 台銀保經-APS新件要保檔產生排程 */
public class FirAgtBotApDaoImpl extends IBatisBaseDaoImpl<FirAgtBotAp, BigDecimal> implements FirAgtBotApDao {
	@Override
	public String getNameSpace() {
		return "FirAgtBotAp";
	}
}