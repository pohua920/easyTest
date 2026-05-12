package com.sinosoft.claim.common.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sinosoft.claim.common.service.facade.PrpPprofitService;
import com.sinosoft.claim.schema.model.PrpPprofit;
import com.sinosoft.claim.schema.model.PrpPprofitId;

public class PrpPprofitServiceSpringImpl  extends GenericDaoHibernate<PrpPprofit, PrpPprofitId> implements PrpPprofitService {

	@Override
	public List<PrpPprofit> findByConditions(String conditions, int pageNo, int rowsPerPage) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		Page page = super.find(queryRule, pageNo, rowsPerPage);
		List<PrpPprofit> list = new ArrayList<PrpPprofit>();
		Iterator<?> it = page.getResult().iterator();
		while (it.hasNext()) {
			PrpPprofit prpPprofit = (PrpPprofit) it.next();
			list.add(prpPprofit);
		}
		return list;
	}

}
