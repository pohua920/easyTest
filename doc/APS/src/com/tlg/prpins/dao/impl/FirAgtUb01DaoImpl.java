package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps041MainVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirAgtUb01Dao;
import com.tlg.prpins.entity.FirAgtUb01;
import com.tlg.util.PageInfo;

/* mantis：FIR0547，處理人員：CC009，需求單編號：FIR0547 聯邦新件資料交換查詢作業 */
public class FirAgtUb01DaoImpl extends IBatisBaseDaoImpl<FirAgtUb01, BigDecimal> implements FirAgtUb01Dao {
	@Override
	public String getNameSpace() {
		return "FirAgtUb01";
	}

	@Override
	public List<Aps041MainVo> selectForAps041(PageInfo pageInfo) throws Exception {
		List<Aps041MainVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForAps041",pageInfo.getFilter());
		return queryForList;
	}

	@Override
	public int countForAps041(Map<String, String> params) throws Exception {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countForAps041", params);
		return count;
	}
}