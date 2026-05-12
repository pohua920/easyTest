package com.sinosoft.claim.claim.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import com.sinosoft.claim.claim.service.facade.ExcludeClaimService;
import com.sinosoft.claim.schema.model.Prplexcludeclaim;
import com.sinosoft.claim.schema.service.facade.PrplexcludeclaimService;
import com.sinosoft.sysframework.common.datatype.PageRecord;

public class ExcludeClaimServiceSpringImpl extends GenericDaoHibernate<Prplexcludeclaim, String> implements ExcludeClaimService {
	/**立案除外service*/
	private PrplexcludeclaimService prplexcludeclaimService;
	/**
	 * 立案除外历史查询
	 * @param conditions 查询条件
	 * @param intPageNo 起始页
	 * @param intRecordPerPage 每页显示条数
	 * @return 查询结果集
	 * @throws Exception
	 */
	@Override
	public PageRecord historyQuery(String conditions, int intPageNo,
			int intRecordPerPage) throws Exception {
//		int count = prplexcludeclaimService.getCount(SqlUtils.getWherePartForGetCount(conditions));
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		Page page = prplexcludeclaimService.findPrplexcludeclaim(queryRule, intPageNo, intRecordPerPage);
		PageRecord pageRecord = new PageRecord((int)page.getTotalCount(),intPageNo,(int)page.getTotalPageCount(),intRecordPerPage,page.getResult());
		return pageRecord;
	}
	public PrplexcludeclaimService getPrplexcludeclaimService() {
		return prplexcludeclaimService;
	}
	public void setPrplexcludeclaimService(
			PrplexcludeclaimService prplexcludeclaimService) {
		this.prplexcludeclaimService = prplexcludeclaimService;
	}

	
	
}
