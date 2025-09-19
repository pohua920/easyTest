package com.tlg.msSqlSms.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlSms.dao.ReceiptDao;
import com.tlg.msSqlSms.entity.Receipt;
import com.tlg.msSqlSms.entity.SMSRequest;

public class ReceiptDaoImpl extends IBatisBaseDaoImpl<Receipt, BigDecimal> implements ReceiptDao {
	
	@Override
	public String getNameSpace() {
		return "Receipt";
	}
	
	@SuppressWarnings("unchecked")
	public List<Receipt> findTopByParams(Map params)throws Exception {	
		
		List<Receipt> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectTopForReceipt",params);
		
		return queryForList;
	}

}