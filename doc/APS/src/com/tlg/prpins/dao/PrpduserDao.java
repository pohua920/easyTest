package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.prpins.entity.Prpduser;
import com.tlg.aps.vo.Ajax005PrpduserVo;
import com.tlg.iBatis.IBatisBaseDao;

public interface PrpduserDao extends IBatisBaseDao<Prpduser, BigDecimal> {
	public List<Ajax005PrpduserVo> selectForAjax005(Map<String,Object> params)throws Exception;
}