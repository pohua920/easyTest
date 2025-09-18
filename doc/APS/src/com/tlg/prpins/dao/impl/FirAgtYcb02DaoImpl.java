package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirAgtYcb02Dao;
import com.tlg.prpins.entity.FirAgtYcb02;

/**
 * mantis：FIR0680，處理人員：DP0714，住火_元大回饋檔產生排程規格
 */
public class FirAgtYcb02DaoImpl extends IBatisBaseDaoImpl<FirAgtYcb02, BigDecimal> implements FirAgtYcb02Dao {
	@Override
	public String getNameSpace() {
		return "FirAgtYcb02";
	}

	@Override
	public List<FirAgtYcb02> selectForGenFile(Map<String, String> params) throws Exception {
		return  getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForGenFile",params);
	}
}
