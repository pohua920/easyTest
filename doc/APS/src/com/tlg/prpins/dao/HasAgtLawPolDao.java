package com.tlg.prpins.dao;

import java.math.BigDecimal;

import java.util.List;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.HasAgtLawPol;

public interface HasAgtLawPolDao extends IBatisBaseDao<HasAgtLawPol, BigDecimal> {
	public List<HasAgtLawPol> selectForGenFile(Map<String,String> params) throws Exception;
}
