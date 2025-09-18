package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.FirPahsinRenewalVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PrpcinsuredDao;
import com.tlg.prpins.entity.Prpcinsured;

public class PrpcinsuredDaoImpl extends IBatisBaseDaoImpl<Prpcinsured, BigDecimal> implements PrpcinsuredDao {
	
	@Override
	public String getNameSpace() {
		return "Prpcinsured";
	}
	
	/*mantis：FIR0589，處理人員：BJ085，需求單編號：FIR0589 住火_APS板信續保作業_第二年優化_資料接收排程 */
	public List<FirPahsinRenewalVo> findForPanhsinCoreInsured(Map params) {
 		List<FirPahsinRenewalVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForPanhsinCoreInsured",params);
 		return queryForList;
 	}
	
}