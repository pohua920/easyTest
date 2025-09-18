package com.tlg.prpins.dao;

import java.math.BigDecimal;

import java.util.List;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.HasAgtLawPolDetail;

public interface HasAgtLawPolDetailDao extends IBatisBaseDao<HasAgtLawPolDetail, BigDecimal> {
	public List<HasAgtLawPolDetail> selectForGenFile(Map<String,String> params) throws Exception;
}
