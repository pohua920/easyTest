package com.tlg.msSqlMob.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.ApplicantForEndorseCheckVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.msSqlMob.entity.ApplicantEndorse;

public interface ApplicantEndorseDao extends IBatisBaseDao<ApplicantEndorse, BigDecimal>{

	
	public List<ApplicantForEndorseCheckVo> findEndorseForCheck()throws Exception;
}