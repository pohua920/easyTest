package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PrpdcarmodelextDao;
import com.tlg.prpins.entity.Prpdcarmodelext;
/**mantis：CAR0563，處理人員：CD078，需求單編號：CAR0563 廠牌車型代號外部資料單筆維護查詢作業 */
public class PrpdcarmodelextDaoImpl extends IBatisBaseDaoImpl<Prpdcarmodelext, BigDecimal> implements PrpdcarmodelextDao{
	@Override
	public String getNameSpace() {
		return "Prpdcarmodelext";
	}
}
