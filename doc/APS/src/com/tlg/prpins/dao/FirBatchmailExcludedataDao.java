package com.tlg.prpins.dao;

import java.math.BigDecimal;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.FirBatchmailExcludedata;
/* mantis：FIR0369，處理人員：BJ085，需求單編號：FIR0369 增加電子保單不寄送單位業務來源設定 */
public interface FirBatchmailExcludedataDao extends IBatisBaseDao<FirBatchmailExcludedata, BigDecimal> {
	
}