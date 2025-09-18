package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.PrpcinsuredContactChk0;
import com.tlg.sales.entity.PrpdCompany;

/**mantis：OTH0106，處理人員：BJ085，需求單編號：OTH0106 要被保人通訊資料比對確認作業*/
/**mantis：OTH0184，處理人員：DP0706，需求單編號：APS要被保人通訊地址比對作業增加資料來源(XCHG)*/
public interface PrpcinsuredContactChk0Dao extends IBatisBaseDao<PrpcinsuredContactChk0, BigDecimal> {
	
	public List<PrpdCompany> findPrpdCompany(Map params) throws SystemException, Exception;
	
}