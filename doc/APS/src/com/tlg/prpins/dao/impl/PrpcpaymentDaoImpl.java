package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PrpcpaymentDao;
import com.tlg.prpins.entity.Prpcpayment;

/*mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程 */
public class PrpcpaymentDaoImpl extends IBatisBaseDaoImpl<Prpcpayment, BigDecimal> implements PrpcpaymentDao {
	
	@Override
	public String getNameSpace() {
		return "Prpcpayment";
	}
	
}