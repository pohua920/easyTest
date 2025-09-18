package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.EpolicyPrpdrationclausekindVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PrpdrationclausekindDao;
import com.tlg.prpins.entity.Prpdrationclausekind;

public class PrpdrationclausekindDaoImpl extends IBatisBaseDaoImpl<Prpdrationclausekind, BigDecimal> implements PrpdrationclausekindDao {
	
	@Override
	public String getNameSpace() {
		return "Prpdrationclausekind";
	}

	@Override
	public List<EpolicyPrpdrationclausekindVo> selectForEpolicy(Map params) throws Exception {
		List<EpolicyPrpdrationclausekindVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForEpolicy",params);
		return queryForList;
	}
	
}