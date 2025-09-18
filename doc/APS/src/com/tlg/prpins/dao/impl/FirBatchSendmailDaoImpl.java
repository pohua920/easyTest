package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirBatchSendmailDao;
import com.tlg.prpins.entity.FirBatchSendmail;

/*mantis：CAR0507，處理人員：BJ085，需求單編號：CAR0507 承保系統新增電子保單產生追蹤機制(車險&旅平險)*/
public class FirBatchSendmailDaoImpl extends IBatisBaseDaoImpl<FirBatchSendmail, BigDecimal> implements FirBatchSendmailDao {
	
	@Override
	public String getNameSpace() {
		return "FirBatchSendmail";
	}
	
}