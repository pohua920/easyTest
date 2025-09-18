package com.tlg.db2.dao.impl;

import java.util.List;
import java.util.Map;

import com.tlg.db2.dao.As400FilCustDao;
import com.tlg.db2.entity.As400FilToRptCoredata;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;

/**
 *  mantis：OTH0175，處理人員：DP0706，需求單編號：OTH0175_APS-收件收件報備系統 已出單資料回拋B2B
 * @author dp0706
 *
 */
public class As400FilCustDaoImpl extends IBatisBaseDaoImpl<As400FilToRptCoredata, String> implements As400FilCustDao {
	
	@Override
	public String getNameSpace() {
		return "As400FilCust";
	}

	@Override
	public List<As400FilToRptCoredata> selectAs400FilByCustQueryStr(Map<String, Object> qryMap) throws Exception {
		List<As400FilToRptCoredata> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectAs400FilByCustQueryStr", qryMap);
		return queryForList;
	}

}
