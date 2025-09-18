package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps002DetailVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirCtbcBatchDtlDao;
import com.tlg.prpins.entity.FirCtbcBatchDtl;
import com.tlg.prpins.entity.FirCtbcRst;
import com.tlg.util.PageInfo;

public class FirCtbcBatchDtlDaoImpl extends IBatisBaseDaoImpl<FirCtbcBatchDtl, BigDecimal> implements FirCtbcBatchDtlDao {
	
	@Override
	public String getNameSpace() {
		return "FirCtbcBatchDtl";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FirCtbcRst> selectForFeedback(Map<String,Object> params) throws Exception {
		List<FirCtbcRst> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForFeedback",params);
		return queryForList;
	}

	@Override
	public Aps002DetailVo selectForAps002Detail(Map<String, Object> params) throws Exception {
		Aps002DetailVo result = (Aps002DetailVo)getSqlMapClientTemplate().queryForObject(getNameSpace()+".selectForAps002Detail",params);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	//mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢)
	// mantis：FIR0708，處理人員：DP0714，住火_APS_中信新件轉入查詢作業增加多個單位邏輯 -- start
	public List<Aps002DetailVo> selectJoinStl(Map<String,Object> params) throws Exception {
		//mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢)
		List<Aps002DetailVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectJoinStl",params);
		return queryForList;
	}
	// mantis：FIR0708，處理人員：DP0714，住火_APS_中信新件轉入查詢作業增加多個單位邏輯 -- end

	@Override
	public int countJoinStl(Map<String, Object> params) throws Exception {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countJoinStl", params);
		return count;
	}
	
}