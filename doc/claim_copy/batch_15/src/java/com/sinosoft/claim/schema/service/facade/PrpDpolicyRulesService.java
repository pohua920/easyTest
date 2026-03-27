package com.sinosoft.claim.schema.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpDpolicyRules;

/**
 * mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則
 */
public interface PrpDpolicyRulesService {
	/**
	 */
	public List<PrpDpolicyRules> findByConditions(String conditions) throws Exception;

	
}
