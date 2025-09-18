package com.tlg.msSqlMob.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import com.tlg.aps.vo.ApplicantForEndorseCheckVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlMob.dao.ApplicantEndorseDao;
import com.tlg.msSqlMob.entity.ApplicantEndorse;

public class ApplicantEndorseDaoImpl extends IBatisBaseDaoImpl<ApplicantEndorse, BigDecimal> implements ApplicantEndorseDao {

	@Override
	public String getNameSpace() {
		return "ApplicantEndorse";
	}

	@Override
	public List<ApplicantForEndorseCheckVo> findEndorseForCheck() throws Exception {
		List<ApplicantForEndorseCheckVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForCheck");
		return queryForList;
	}
	

}