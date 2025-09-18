package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.EpolicyPrpdrationclausekindVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.Prpdrationclausekind;

public interface PrpdrationclausekindDao extends IBatisBaseDao<Prpdrationclausekind, BigDecimal> {
	public List<EpolicyPrpdrationclausekindVo> selectForEpolicy(Map params)throws Exception;
}