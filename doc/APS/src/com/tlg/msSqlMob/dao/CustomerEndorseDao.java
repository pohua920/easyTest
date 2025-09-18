package com.tlg.msSqlMob.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.msSqlMob.entity.CustomerEndorse;

public interface CustomerEndorseDao extends IBatisBaseDao<CustomerEndorse, BigDecimal>{
	/** mantis：MOB0026，處理人員：CE035，需求單編號：MOB0026 優化手機險對帳流程   ac檔補批單號 */
	public List<CustomerEndorse> selectCeToCorrectEndorseNo(Map<String, String> map)throws Exception;
}