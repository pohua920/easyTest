package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirAddrImportlistDao;
import com.tlg.prpins.entity.FirAddrImportlist;

public class FirAddrImportlistDaoImpl extends IBatisBaseDaoImpl<FirAddrImportlist, BigDecimal> implements FirAddrImportlistDao {
	/* mantis：FIR0183，處理人員：BJ085，需求單編號：FIR0183 火險地址資料匯入 start */

	@Override
	public String getNameSpace() {
		return "FirAddrImportlist";
	}
	
}