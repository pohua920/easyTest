package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PrpyddagentDao;
import com.tlg.prpins.entity.Prpyddagent;

public class PrpyddagentDaoImpl extends IBatisBaseDaoImpl<Prpyddagent, BigDecimal> implements PrpyddagentDao {
	/*mantis：FIR0295，處理人員：BJ085，需求單編號：FIR0295 外銀板信續件移回新核心-維護作業*/
	@Override
	public String getNameSpace() {
		return "Prpyddagent";
	}
	
}