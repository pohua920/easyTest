package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirAddrImporterrDao;
import com.tlg.prpins.entity.FirAddrImporterr;

public class FirAddrImporterrDaoImpl extends IBatisBaseDaoImpl<FirAddrImporterr, BigDecimal> implements FirAddrImporterrDao {
	/* mantis：FIR0183，處理人員：BJ085，需求單編號：FIR0183 火險地址資料匯入 start */

	@Override
	public String getNameSpace() {
		return "FirAddrImporterr";
	}
	
}