package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.HasBatchMatchFetDao;
import com.tlg.prpins.entity.HasBatchMatchFet;

/**
 * mantis：HAS0284，處理人員：DP0706，需求單編號：HAS0284_遠傳優化需求-比對同要保人但生日或姓名不同
 * @author dp0706
 *
 */
public class HasBatchMatchFetDaoImpl extends IBatisBaseDaoImpl<HasBatchMatchFet, BigDecimal> implements HasBatchMatchFetDao {
	
	@Override
	public String getNameSpace() {
		return "HasBatchMatchFet";
	}
	
}
