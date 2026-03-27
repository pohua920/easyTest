package com.sinosoft.claim.schema.service.spring;

/**
 * 代码信息接口实现类
 * @author 中科软
 *
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpDpolicyRules;
import com.sinosoft.claim.schema.model.PrpDpolicyRulesId;
import com.sinosoft.claim.schema.service.facade.PrpDpolicyRulesService;

/**
 * mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則
 */
public class PrpDpolicyRulesServiceSpringImpl extends GenericDaoHibernate<PrpDpolicyRules, PrpDpolicyRulesId> implements PrpDpolicyRulesService {

	/**
	 * 根据查询条件获取通用代码的列表
	 * @param condition 查询条件
	 * @return 包含的 通用代码 的列表
	 */
	@Override
	public List<PrpDpolicyRules> findByConditions(String conditions) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule);
	}

}
