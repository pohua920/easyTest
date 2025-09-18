package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirApsCtbcHandlerDao;
import com.tlg.prpins.entity.FirApsCtbcHandler;

/* mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢)
 * 調整角色權限控管功能，後USER決定取消此功能*/
public class FirApsCtbcHandlerDaoImpl extends IBatisBaseDaoImpl<FirApsCtbcHandler, BigDecimal> implements FirApsCtbcHandlerDao {
	
	@Override
	public String getNameSpace() {
		return "FirApsCtbcHandler";
	}

	@Override
	public String selectByUpperComcode(String upperComcode) throws Exception {
		String coreComcodes = (String) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".selectByUpperComocode");
		return coreComcodes;
	}
	
}