package com.tlg.db2.dao;

import java.util.List;
import java.util.Map;

import com.tlg.db2.entity.As400FilToRptCoredata;
import com.tlg.iBatis.IBatisBaseDao;

/**
 *  mantis：OTH0175，處理人員：DP0706，需求單編號：OTH0175_APS-收件收件報備系統 已出單資料回拋B2B
 * @author dp0706
 *
 */
public interface As400FilCustDao  extends IBatisBaseDao<As400FilToRptCoredata, String> {

	public List<As400FilToRptCoredata> selectAs400FilByCustQueryStr(Map<String, Object> qryMap) throws Exception;
}
