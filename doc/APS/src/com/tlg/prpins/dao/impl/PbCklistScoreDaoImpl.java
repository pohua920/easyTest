package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PbCklistScoreDao;
import com.tlg.prpins.entity.PbCklistScore;

public class PbCklistScoreDaoImpl extends IBatisBaseDaoImpl<PbCklistScore, BigDecimal> implements PbCklistScoreDao {
	
	@Override
	public String getNameSpace() {
		return "PbCklistScore";
	}

}