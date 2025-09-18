package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PbQueryDetailDao;
import com.tlg.prpins.entity.PbQueryDetail;

public class PbQueryDetailDaoImpl extends IBatisBaseDaoImpl<PbQueryDetail, BigDecimal> implements PbQueryDetailDao {
	
	@Override
	public String getNameSpace() {
		return "PbQueryDetail";
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
	
	@Override
	public List<Map> selectByPbScore(Map params) throws Exception {
		List<Map> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectByPbScore",params);
		return queryForList;
	}
	
	@Override
	public BigDecimal selectByPbResultScore(Map params) throws Exception {
		BigDecimal score = (BigDecimal) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".selectByPbResultScore", params);
		return score;
	}
}