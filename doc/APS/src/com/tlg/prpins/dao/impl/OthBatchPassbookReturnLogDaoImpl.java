package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.OthBatchPassbookReturnLogDao;
import com.tlg.prpins.entity.OthBatchPassbookReturnLog;

/* mantis：OTH0138，處理人員：CC009，需求單編號：OTH0138 保發中心_保單存摺回饋檔回存資料庫規格 */
public class OthBatchPassbookReturnLogDaoImpl extends IBatisBaseDaoImpl<OthBatchPassbookReturnLog, BigDecimal> implements OthBatchPassbookReturnLogDao {
	
	@Override
	public String getNameSpace() {
		return "OthBatchPassbookReturnLog";
	}
}