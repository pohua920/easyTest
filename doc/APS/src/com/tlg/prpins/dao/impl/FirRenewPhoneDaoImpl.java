package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirRenewPhoneDao;
import com.tlg.prpins.entity.FirRenewPhone;

/** mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 */
public class FirRenewPhoneDaoImpl extends IBatisBaseDaoImpl<FirRenewPhone, BigDecimal> implements FirRenewPhoneDao {
	
	@Override
	public String getNameSpace() {
		return "FirRenewPhone";
	}
	
	@Override
	public FirRenewPhone selectPhoneByHandler1code(Map params) throws Exception {
		return  (FirRenewPhone) getSqlMapClientTemplate().queryForObject(getNameSpace()+".selectPhoneByHandler1code",params);
	}
}