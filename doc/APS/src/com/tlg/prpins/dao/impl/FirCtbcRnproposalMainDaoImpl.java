package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirCtbcRnproposalMainDao;
import com.tlg.prpins.entity.FirCtbcRnproposalMain;

/* mantis：FIR0459，處理人員：CC009，需求單編號：FIR0459 住火-APS中信續件要保書-排程查詢作業 */
public class FirCtbcRnproposalMainDaoImpl extends IBatisBaseDaoImpl<FirCtbcRnproposalMain, BigDecimal> implements FirCtbcRnproposalMainDao {
	@Override
	public String getNameSpace() {
		return "FirCtbcRnproposalMain";
	}

}