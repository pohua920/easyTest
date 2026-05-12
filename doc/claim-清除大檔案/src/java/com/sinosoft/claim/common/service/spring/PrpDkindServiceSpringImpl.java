package com.sinosoft.claim.common.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.common.service.facade.PrpDkindService;
import com.sinosoft.claim.schema.model.PrpDkind;
import com.sinosoft.claim.schema.model.PrpDkindId;

public class PrpDkindServiceSpringImpl extends GenericDaoHibernate<PrpDkind, PrpDkindId> implements PrpDkindService {

	@Override
	public String translateCode(String riskCode, String kindCode, boolean isChinese) {
		String codeName = "";
		PrpDkindId prpDkindId = new PrpDkindId();
		prpDkindId.setKindCode(kindCode);
		prpDkindId.setRiskCode(riskCode);
		PrpDkind prpDkind = super.get(PrpDkind.class, prpDkindId);
		if (isChinese) {
			codeName = prpDkind.getKindCName();
		} else {
			codeName = prpDkind.getKindEName();
		}
		return codeName;
	}

	/**
	 * PrpDkind表代码服务<br>
	 * 支持的代码类型有：<br>
	 * @param codeType 代码类型
	 * @param codeCode 代码
	 * @return 代码名称
	 */
	public PrpDkind findPrpDkindById(PrpDkindId id) throws Exception {

		PrpDkind prpDkind = super.get(PrpDkind.class, id);
		return prpDkind;
	}

	@Override
	public List<PrpDkind> findByConditions(String conditions) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule);
	}

}
