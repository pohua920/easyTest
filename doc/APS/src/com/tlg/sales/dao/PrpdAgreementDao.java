package com.tlg.sales.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.sales.entity.PrpdAgreement;

/* mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程 start */
public interface PrpdAgreementDao extends IBatisBaseDao<PrpdAgreement, BigDecimal> {
	
	//mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程
	public List<PrpdAgreement> findPrpdAgreementJoinDetail(Map params) throws Exception;
}