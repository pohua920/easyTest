package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.ClaimCompulsoryChargesDao;
import com.tlg.prpins.entity.ClaimCompulsoryCharges;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
public class ClaimCompulsoryChargesDaoImpl extends IBatisBaseDaoImpl<ClaimCompulsoryCharges, BigDecimal> implements ClaimCompulsoryChargesDao {
	
	@Override
	public String getNameSpace() {
		return "ClaimCompulsoryCharges";
	}

}