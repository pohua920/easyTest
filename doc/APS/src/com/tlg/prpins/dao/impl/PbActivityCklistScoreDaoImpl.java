package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PbActivityCklistScoreDao;
import com.tlg.prpins.entity.PbActivityCklistScore;

public class PbActivityCklistScoreDaoImpl extends IBatisBaseDaoImpl<PbActivityCklistScore, BigDecimal> implements PbActivityCklistScoreDao {
	
	@Override
	public String getNameSpace() {
		return "PbActivityCklistScore";
	}

}