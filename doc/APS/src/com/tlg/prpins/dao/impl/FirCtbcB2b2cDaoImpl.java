package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.aps.vo.Aps039DetailVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirCtbcB2b2cDao;
import com.tlg.prpins.entity.FirCtbcB2b2c;

/* mantis：FIR0497，處理人員：BJ085，需求單編號：FIR0497 中信保代網投_新件回饋檔產生排程規格_新增保經代網投 */
public class FirCtbcB2b2cDaoImpl extends IBatisBaseDaoImpl<FirCtbcB2b2c, BigDecimal> implements FirCtbcB2b2cDao {

	@Override
	public String getNameSpace() {
		return "FirCtbcB2b2c";
	}

	/* mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業 */
	@Override
	public Aps039DetailVo selectForAps039Detail(Map params) throws Exception {
		Aps039DetailVo queryForObject = (Aps039DetailVo) getSqlMapClientTemplate().queryForObject(getNameSpace()+".selectForAps039Detail",params);
		return queryForObject;
	}

}