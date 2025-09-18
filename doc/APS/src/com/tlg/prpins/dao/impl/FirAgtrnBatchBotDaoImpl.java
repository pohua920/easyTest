package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirAgtrnBatchBotDao;
import com.tlg.prpins.entity.FirAgtrnBatchBot;

/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業  **/
public class FirAgtrnBatchBotDaoImpl extends IBatisBaseDaoImpl<FirAgtrnBatchBot, BigDecimal> implements FirAgtrnBatchBotDao {
	@Override
	public String getNameSpace() {
		return "FirAgtrnBatchBot";
	}
}