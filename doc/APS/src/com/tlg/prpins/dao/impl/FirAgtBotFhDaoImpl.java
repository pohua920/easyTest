package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirAgtBotFhDao;
import com.tlg.prpins.entity.FirAgtBotFh;

/* mantis：FIR0315，處理人員：BJ085，需求單編號：FIR0315 台銀保經-APS保單檔產生排程 */
public class FirAgtBotFhDaoImpl extends IBatisBaseDaoImpl<FirAgtBotFh, BigDecimal> implements FirAgtBotFhDao {
	@Override
	public String getNameSpace() {
		return "FirAgtBotFh";
	}
}