package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirDangerGradeDao;
import com.tlg.prpins.entity.FirDangerGrade;

public class FirDangerGradeDaoImpl extends IBatisBaseDaoImpl<FirDangerGrade, BigDecimal> implements FirDangerGradeDao {
	
	@Override
	public String getNameSpace() {
		return "FirDangerGrade";
	}

}