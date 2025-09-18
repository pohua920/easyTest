package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.ClaimCompulsoryCaseDao;
import com.tlg.prpins.entity.ClaimCompulsoryCase;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
public class ClaimCompulsoryCaseDaoImpl extends IBatisBaseDaoImpl<ClaimCompulsoryCase, BigDecimal> implements ClaimCompulsoryCaseDao {
	
	@Override
	public String getNameSpace() {
		return "ClaimCompulsoryCase";
	}

}