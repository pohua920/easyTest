package com.tlg.sales.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.sales.dao.PrpdAgreementDao;
import com.tlg.sales.entity.PrpdAgreement;

public class PrpdAgreementDaoImpl extends IBatisBaseDaoImpl<PrpdAgreement, BigDecimal> implements PrpdAgreementDao {
	/* mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程 start */
	@Override
	public String getNameSpace() {
		return "PrpdAgreement";
	}
	
	// mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程 
	public List<PrpdAgreement> findPrpdAgreementJoinDetail(Map params) throws Exception{
		List<PrpdAgreement> resultList = (List<PrpdAgreement>) getSqlMapClientTemplate().queryForList(getNameSpace() + ".selectCommissionJoinDetail",params);
		return resultList;
	}
}