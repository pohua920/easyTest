package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps003DetailVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirCtbcRstDao;
import com.tlg.prpins.entity.FirCtbcRst;
import com.tlg.util.PageInfo;

public class FirCtbcRstDaoImpl extends IBatisBaseDaoImpl<FirCtbcRst, BigDecimal> implements FirCtbcRstDao {
	
	@Override
	public String getNameSpace() {
		return "FirCtbcRst";
	}

	/*mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢)*/
	@Override
	public int countForAps003Detail(Map<String, Object> params) throws Exception {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countForAps003", params);
		return count;
	}

	/*mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢)*/
	@SuppressWarnings("unchecked")
	@Override
	public List<Aps003DetailVo> selectForAps003Detail(PageInfo pageInfo) throws Exception {
		List<Aps003DetailVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForAps003",pageInfo.getFilter());
		return queryForList;
	}
	
}