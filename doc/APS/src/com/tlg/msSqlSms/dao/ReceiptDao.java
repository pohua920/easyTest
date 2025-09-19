package com.tlg.msSqlSms.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.msSqlSms.entity.Receipt;

public interface ReceiptDao extends IBatisBaseDao<Receipt, BigDecimal> {
	
	

	public List<Receipt> findTopByParams(Map params)throws Exception;
}