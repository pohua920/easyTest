package com.tlg.xchg.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.xchg.dao.PrpcinsuredContactChk0NewDao;
import com.tlg.xchg.entity.PrpcinsuredContactChk0New;
/**mantis：OTH0184，處理人員：DP0706，需求單編號：APS要被保人通訊地址比對作業增加資料來源(XCHG)*/
public class PrpcinsuredContactChk0NewDaoImpl extends IBatisBaseDaoImpl<PrpcinsuredContactChk0New, BigDecimal> implements PrpcinsuredContactChk0NewDao {
	
	@Override
	public String getNameSpace() {
		return "PrpcinsuredContactChk0New";
	}
}