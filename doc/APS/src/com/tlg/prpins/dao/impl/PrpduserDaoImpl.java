package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Ajax005PrpduserVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PrpduserDao;
import com.tlg.prpins.entity.Prpduser;

public class PrpduserDaoImpl extends IBatisBaseDaoImpl<Prpduser, BigDecimal> implements PrpduserDao {
	
	@Override
	public String getNameSpace() {
		return "Prpduser";
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<Ajax005PrpduserVo> selectForAjax005(Map<String, Object> params) throws Exception {
		List<Ajax005PrpduserVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForAjax005",params);
		return queryForList;
	}
	
}