package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirAgtrnBatchFbDao;
import com.tlg.prpins.entity.FirAgtrnBatchFb;

/** mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業  **/
public class FirAgtrnBatchFbDaoImpl extends IBatisBaseDaoImpl<FirAgtrnBatchFb, BigDecimal> implements FirAgtrnBatchFbDao {
	@Override
	public String getNameSpace() {
		return "FirAgtrnBatchFb";
	}
}