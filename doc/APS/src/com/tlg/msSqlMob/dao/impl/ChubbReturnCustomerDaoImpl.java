package com.tlg.msSqlMob.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlMob.dao.ChubbReturnCustomerDao;
import com.tlg.msSqlMob.entity.ChubbReturnCustomer;

/** mantis：MOB0010，處理人員：BJ085，需求單編號：MOB0010 安達回傳保單及批單處理結果狀態更新 */
public class ChubbReturnCustomerDaoImpl extends IBatisBaseDaoImpl<ChubbReturnCustomer, BigDecimal> implements ChubbReturnCustomerDao {
	
	@Override
	public String getNameSpace() {
		return "ChubbReturnCustomer";
	}
}