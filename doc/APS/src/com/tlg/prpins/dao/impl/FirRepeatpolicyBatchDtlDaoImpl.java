package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps042ImportVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirRepeatpolicyBatchDtlDao;
import com.tlg.prpins.entity.FirRepeatpolicyBatchDtl;

/* mantis：FIR0565，處理人員：CC009，需求單編號：FIR0565 住火_複保險通知轉檔作業 */
public class FirRepeatpolicyBatchDtlDaoImpl extends IBatisBaseDaoImpl<FirRepeatpolicyBatchDtl, BigDecimal> implements FirRepeatpolicyBatchDtlDao {
	@Override
	public String getNameSpace() {
		return "FirRepeatpolicyBatchDtl";
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<Aps042ImportVo> selectForAps042Import(Map<String, String> params) throws Exception {
		List<Aps042ImportVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForAps042Import",params);
		return queryForList;
	}
}