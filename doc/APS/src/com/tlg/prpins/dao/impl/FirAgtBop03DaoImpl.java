package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirAgtBop03Dao;
import com.tlg.prpins.entity.FirAgtBop03;

/* mantis：FIR0494，處理人員：CC009，需求單編號：FIR0494_板信回饋檔產生排程規格_新版 */
public class FirAgtBop03DaoImpl extends IBatisBaseDaoImpl<FirAgtBop03, BigDecimal> implements FirAgtBop03Dao {
	@Override
	public String getNameSpace() {
		return "FirAgtBop03";
	}

	@Override
	public List<FirAgtBop03> selectForGenFile(Map<String, String> params) throws Exception {
		return  getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForGenFile",params);
	}
}