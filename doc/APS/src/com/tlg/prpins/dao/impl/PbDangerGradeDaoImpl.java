package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PbDangerGradeDao;
import com.tlg.prpins.entity.PbDangerGrade;

public class PbDangerGradeDaoImpl extends IBatisBaseDaoImpl<PbDangerGrade, BigDecimal> implements PbDangerGradeDao {
	
	@Override
	public String getNameSpace() {
		return "PbDangerGrade";
	}

}