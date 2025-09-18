package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PbPremcalcCklistDao;
import com.tlg.prpins.entity.PbPremcalcCklist;

public class PbPremcalcCklistDaoImpl extends IBatisBaseDaoImpl<PbPremcalcCklist, BigDecimal> implements PbPremcalcCklistDao {
	
	@Override
	public String getNameSpace() {
		return "PbPremcalcCklist";
	}

	@Override
	public List<Map> selectByScore(Map params) throws Exception {
		List<Map> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectByScore",params);
		return queryForList;
	}
	
	@Override
	public BigDecimal selectByResultScore(Map params) throws Exception {
		BigDecimal score = (BigDecimal) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".selectByResultScore", params);
		return score;
	}

}