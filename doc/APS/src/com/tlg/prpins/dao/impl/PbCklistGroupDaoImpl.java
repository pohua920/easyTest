package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PbCklistGroupDao;
import com.tlg.prpins.entity.PbCklistGroup;

public class PbCklistGroupDaoImpl extends IBatisBaseDaoImpl<PbCklistGroup, BigDecimal> implements PbCklistGroupDao {
	
	@Override
	public String getNameSpace() {
		return "PbCklistGroup";
	}

}