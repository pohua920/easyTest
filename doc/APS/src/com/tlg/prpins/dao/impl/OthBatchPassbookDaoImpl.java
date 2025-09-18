package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.OthBatchPassbookDao;
import com.tlg.prpins.entity.OthBatchPassbook;

/* mantis：OTH0127，處理人員：CC009，需求單編號：OTH0127 保發中心_保單存摺產生排程規格 */
public class OthBatchPassbookDaoImpl extends IBatisBaseDaoImpl<OthBatchPassbook, BigDecimal> implements OthBatchPassbookDao {

	@Override
	public String getNameSpace() {
		return "OthBatchPassbook";
	}
	
	
}