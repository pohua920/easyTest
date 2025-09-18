package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirCtbcDeptinfoDao;
import com.tlg.prpins.entity.FirCtbcDeptinfo;

public class FirCtbcDeptinfoDaoImpl extends IBatisBaseDaoImpl<FirCtbcDeptinfo, BigDecimal> implements FirCtbcDeptinfoDao {
	
	@Override
	public String getNameSpace() {
		return "FirCtbcDeptinfo";
	}
	
}