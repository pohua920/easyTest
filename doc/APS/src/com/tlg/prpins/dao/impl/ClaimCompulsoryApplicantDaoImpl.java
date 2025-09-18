package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.ClaimCompulsoryApplicantDao;
import com.tlg.prpins.entity.ClaimCompulsoryApplicant;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
public class ClaimCompulsoryApplicantDaoImpl extends IBatisBaseDaoImpl<ClaimCompulsoryApplicant, BigDecimal> implements ClaimCompulsoryApplicantDao {
	
	@Override
	public String getNameSpace() {
		return "ClaimCompulsoryApplicant";
	}

}