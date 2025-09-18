package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PrpcinsuredContactChk0Dao;
import com.tlg.prpins.entity.PrpcinsuredContactChk0;
import com.tlg.sales.entity.PrpdCompany;
/**mantis：OTH0106，處理人員：BJ085，需求單編號：OTH0106 要被保人通訊資料比對確認作業*/
public class PrpcinsuredContactChk0DaoImpl extends IBatisBaseDaoImpl<PrpcinsuredContactChk0, BigDecimal> implements PrpcinsuredContactChk0Dao {
	
	@Override
	public String getNameSpace() {
		return "PrpcinsuredContactChk0";
	}
	
	//mantis：OTH0184，處理人員：DP0706，需求單編號：APS要被保人通訊地址比對作業增加資料來源(XCHG)
	@SuppressWarnings("unchecked")
	public List<PrpdCompany> findPrpdCompany(Map params) throws Exception {	
		
		List<PrpdCompany> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".queryCompanyInfo",params);
		
		return queryForList;
	}
	
}