package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.OthBatchPassbookKindDao;
import com.tlg.prpins.entity.OthBatchPassbookKind;

/* mantis：OTH0127，處理人員：CC009，需求單編號：OTH0127 保發中心_保單存摺產生排程規格 */
public class OthBatchPassbookKindDaoImpl extends IBatisBaseDaoImpl<OthBatchPassbookKind, BigDecimal> implements OthBatchPassbookKindDao {

	@Override
	public String getNameSpace() {
		return "OthBatchPassbookKind";
	}
	
}