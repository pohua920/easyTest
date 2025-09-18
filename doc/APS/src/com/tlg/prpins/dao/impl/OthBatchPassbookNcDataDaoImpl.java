package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.OthBatchPassbookNcDataDao;
import com.tlg.prpins.entity.OthBatchPassbookNcData;

/** mantis：OTH0131，處理人員：BJ085，需求單編號：OTH0131 保發中心-保單存摺各險寫入中介Table作業 */
public class OthBatchPassbookNcDataDaoImpl extends IBatisBaseDaoImpl<OthBatchPassbookNcData, BigDecimal> implements OthBatchPassbookNcDataDao {

	@Override
	public String getNameSpace() {
		return "OthBatchPassbookNcData";
	}
	
}