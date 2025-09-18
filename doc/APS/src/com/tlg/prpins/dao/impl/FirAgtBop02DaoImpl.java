package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirAgtBop02Dao;
import com.tlg.prpins.entity.FirAgtBop02;

public class FirAgtBop02DaoImpl extends IBatisBaseDaoImpl<FirAgtBop02, BigDecimal> implements FirAgtBop02Dao {
	/* mantis：FIR0266，處理人員：BJ085，需求單編號：FIR0266 板信資料交換處理作業_新增保單處理 start */
	@Override
	public String getNameSpace() {
		return "FirAgtBop02";
	}
}