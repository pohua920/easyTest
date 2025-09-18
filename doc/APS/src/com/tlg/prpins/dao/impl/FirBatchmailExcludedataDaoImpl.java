package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirBatchmailExcludedataDao;
import com.tlg.prpins.entity.FirBatchmailExcludedata;
/* mantis：FIR0369，處理人員：BJ085，需求單編號：FIR0369 增加電子保單不寄送單位業務來源設定 */
public class FirBatchmailExcludedataDaoImpl extends IBatisBaseDaoImpl<FirBatchmailExcludedata, BigDecimal> implements FirBatchmailExcludedataDao {
	
	@Override
	public String getNameSpace() {
		return "FirBatchmailExcludedata";
	}
}