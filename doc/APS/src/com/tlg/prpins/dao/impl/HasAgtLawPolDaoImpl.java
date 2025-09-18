package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import java.util.List;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.HasAgtLawPolDao;
import com.tlg.prpins.entity.HasAgtLawPol;

public class HasAgtLawPolDaoImpl extends IBatisBaseDaoImpl<HasAgtLawPol, BigDecimal> implements HasAgtLawPolDao {
	
	@Override
	public String getNameSpace() {
		return "HasAgtLawPol";
	}
	
	@Override
	public List<HasAgtLawPol> selectForGenFile(Map<String, String> params) throws Exception {
		return  getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForGenFile",params);
	}
}
