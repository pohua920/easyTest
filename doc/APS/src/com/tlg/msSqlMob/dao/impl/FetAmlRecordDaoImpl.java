package com.tlg.msSqlMob.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlMob.dao.FetAmlRecordDao;
import com.tlg.msSqlMob.entity.FetAmlRecord;
/** mantis：MOB0022，處理人員：CE035，需求單編號：MOB0022 洗錢檢核條件記錄到FET_AML_RECORD資料表 */
public class FetAmlRecordDaoImpl extends IBatisBaseDaoImpl<FetAmlRecord, BigDecimal> implements FetAmlRecordDao {

	@Override
	public String getNameSpace() {
		return "FetAmlRecord";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<FetAmlRecord> selectByContractId(Map params) throws Exception {
		
		return getSqlMapClientTemplate().queryForList(getNameSpace()+".selectByContractId", params);
	}
}
