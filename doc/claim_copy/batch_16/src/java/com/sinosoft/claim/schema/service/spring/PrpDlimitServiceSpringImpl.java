package com.sinosoft.claim.schema.service.spring;
/**
 * @Description 限额免赔代码表的数据访问
 * @author 中科软
 * @date Feb 24, 2013 3:07:39 AM
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sinosoft.claim.schema.model.PrpDlimit;
import com.sinosoft.claim.schema.model.PrpDlimitId;
import com.sinosoft.claim.schema.service.facade.PrpDlimitService;

public class PrpDlimitServiceSpringImpl extends GenericDaoHibernate<PrpDlimit, PrpDlimitId> implements PrpDlimitService {

	@Override
	public void delete(String riskCode, String limitCode) throws Exception {
		super.deleteByPK(new PrpDlimitId(riskCode,limitCode));
	}

	@Override
	public List<PrpDlimit> findByConditions(QueryRule queryRule, int pageNo,
			int rowsPerPage) throws Exception {
		Page page=super.find(queryRule,pageNo,rowsPerPage);
		List<PrpDlimit> list = new ArrayList<PrpDlimit>();
		for (Iterator<?> iterator = page.getResult().iterator(); iterator.hasNext();) {
			PrpDlimit prpDlimit = (PrpDlimit) iterator.next();
			list.add(prpDlimit);
		}
		return list;
	}

	@Override
	public List<PrpDlimit> findByConditions(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	@Override
	public void save(PrpDlimit prpDlimit) throws Exception {
		super.save(prpDlimit);
	}

	@Override
	public void update(PrpDlimit prpDlimit) {
		super.update(prpDlimit);
	}
	
	/**
	 * PrpDlimit表代码服务<br>
	 * 支持的代码类型有：<br>
	 * @param codeType 代码类型
	 * @param codeCode 代码
	 * @return 代码名称
	 */
	public PrpDlimit findPrpDlimitById(PrpDlimitId id) throws Exception {
		
		PrpDlimit prpDlimit = super.get(PrpDlimit.class, id);
		return prpDlimit;
	}

}
