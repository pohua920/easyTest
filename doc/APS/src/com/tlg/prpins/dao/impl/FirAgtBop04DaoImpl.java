package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirAgtBop04Dao;
import com.tlg.prpins.entity.FirAgtBop04;

/* mantis：FIR0494，處理人員：CC009，需求單編號：FIR0494_板信回饋檔產生排程規格_新版 */
public class FirAgtBop04DaoImpl extends IBatisBaseDaoImpl<FirAgtBop04, BigDecimal> implements FirAgtBop04Dao {
	@Override
	public String getNameSpace() {
		return "FirAgtBop04";
	}
	
	@Override
	public List<FirAgtBop04> selectForGenFile(Map<String, String> params) throws Exception {
		return  getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForGenFile",params);
	}
}