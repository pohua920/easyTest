package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.BankInfoVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PrpdbankinfoDao;
import com.tlg.prpins.entity.Prpdbankinfo;

public class PrpdbankinfoDaoImpl extends IBatisBaseDaoImpl<Prpdbankinfo, BigDecimal> implements PrpdbankinfoDao {
	
	@Override
	public String getNameSpace() {
		return "Prpdbankinfo";
	}
	
	@SuppressWarnings("unchecked")
	public List<BankInfoVo> findByParamsForWs(Map params)throws Exception {	
		List<BankInfoVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectByParamsForWs",params);
		return queryForList;
	}

}