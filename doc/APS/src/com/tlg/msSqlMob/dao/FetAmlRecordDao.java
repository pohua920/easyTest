package com.tlg.msSqlMob.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.msSqlMob.entity.FetAmlRecord;
/** mantis：MOB0022，處理人員：CE035，需求單編號：MOB0022 洗錢檢核條件記錄到FET_AML_RECORD資料表 */
public interface FetAmlRecordDao extends IBatisBaseDao<FetAmlRecord, BigDecimal>{
	
	@SuppressWarnings("rawtypes")
	public List<FetAmlRecord> selectByContractId(Map params)throws Exception;
}
