package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import java.util.List;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.HasAgtLawPolDetailDao;
import com.tlg.prpins.entity.HasAgtLawPolDetail;

public class HasAgtLawPolDetailDaoImpl extends IBatisBaseDaoImpl<HasAgtLawPolDetail, BigDecimal> implements HasAgtLawPolDetailDao {
	
	@Override
	public String getNameSpace() {
		return "HasAgtLawPolDetail";
	}
	
	@Override
	public List<HasAgtLawPolDetail> selectForGenFile(Map<String, String> params) throws Exception {
		return  getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForGenFile",params);
	}
}
