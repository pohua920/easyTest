package com.tlg.msSqlRdcB2b.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlRdcB2b.dao.RptCoredataDao;
import com.tlg.msSqlRdcB2b.entity.RptCoredata;
//mantis：OTH0175，處理人員：DP0706，需求單編號：OTH0175_APS-收件收件報備系統 已出單資料回拋B2B
public class RptCoredataDaoImpl extends IBatisBaseDaoImpl<RptCoredata, BigDecimal> implements RptCoredataDao {

	
	@Override
	public String getNameSpace() {
		return "RptCoredata";
	}

	
	
	
    
}