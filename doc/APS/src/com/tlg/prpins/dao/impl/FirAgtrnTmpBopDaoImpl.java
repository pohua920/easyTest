package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirAgtrnTmpBopDao;
import com.tlg.prpins.entity.FirAgtrnTmpBop;

public class FirAgtrnTmpBopDaoImpl extends IBatisBaseDaoImpl<FirAgtrnTmpBop, BigDecimal> implements FirAgtrnTmpBopDao {
	/* mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程 start */
	@Override
	public String getNameSpace() {
		return "FirAgtrnTmpBop";
	}
}