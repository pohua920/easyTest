package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.FirPahsinRenewalVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.Prpcinsured;

public interface PrpcinsuredDao extends IBatisBaseDao<Prpcinsured, BigDecimal> {
	
	//mantis：FIR0589，處理人員：BJ085，需求單編號：FIR0589 住火_APS板信續保作業_第二年優化_資料接收排程
	public List<FirPahsinRenewalVo> findForPanhsinCoreInsured(Map params);
}