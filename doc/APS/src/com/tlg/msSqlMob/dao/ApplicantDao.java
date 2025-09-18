package com.tlg.msSqlMob.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.msSqlMob.entity.Applicant;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
public interface ApplicantDao extends IBatisBaseDao<Applicant, BigDecimal>{
	
	/** mantis：MOB0002，處理人員：CC009，需求單編號：MOB0002 遠傳要保及批單資料作洗錢、利關人、黑名單檢核 */
	public List<Applicant> selectForFetPolicyCheck(Map<String,String> params) throws Exception;
}