package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirRuleAddrExcpDao;
import com.tlg.prpins.entity.FirRuleAddrExcp;

public class FirRuleAddrExcpDaoImpl extends IBatisBaseDaoImpl<FirRuleAddrExcp, BigDecimal> implements FirRuleAddrExcpDao {
	/* mantis：FIR0238，處理人員：BJ085，需求單編號：FIR0238 稽核議題檢核-例外地址維護作業 start */
	
	@Override
	public String getNameSpace() {
		return "FirRuleAddrExcp";
	}

}