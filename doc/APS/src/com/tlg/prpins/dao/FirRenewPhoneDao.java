package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.FirRenewPhone;

/** mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 */
public interface FirRenewPhoneDao extends IBatisBaseDao<FirRenewPhone, BigDecimal> {
	
	public FirRenewPhone selectPhoneByHandler1code(Map params)throws Exception;
}