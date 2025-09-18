package com.tlg.prpins.dao;

import java.math.BigDecimal;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.Prpcpayment;

/*mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程 */
public interface PrpcpaymentDao extends IBatisBaseDao<Prpcpayment, BigDecimal> {
	
}