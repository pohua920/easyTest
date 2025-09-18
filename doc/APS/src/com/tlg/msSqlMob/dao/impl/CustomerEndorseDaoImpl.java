package com.tlg.msSqlMob.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlMob.dao.CustomerEndorseDao;
import com.tlg.msSqlMob.entity.CustomerEndorse;

public class CustomerEndorseDaoImpl extends IBatisBaseDaoImpl<CustomerEndorse, BigDecimal> implements CustomerEndorseDao {

	@Override
	public String getNameSpace() {
		return "CustomerEndorse";
	}
	/** mantis：MOB0026，處理人員：CE035，需求單編號：MOB0026 優化手機險對帳流程   ac檔補批單號 START*/
	@Override
	public List<CustomerEndorse> selectCeToCorrectEndorseNo(Map<String, String> map) throws Exception {
		List<CustomerEndorse> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectCeToCorrectEndorseNo");
		return queryForList;
	}
	/** mantis：MOB0026，處理人員：CE035，需求單編號：MOB0026 優化手機險對帳流程   ac檔補批單號 END*/
}