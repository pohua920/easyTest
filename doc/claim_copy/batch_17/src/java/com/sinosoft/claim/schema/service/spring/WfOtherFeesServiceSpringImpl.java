package com.sinosoft.claim.schema.service.spring;
/**
 * 其他费用信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.WfOtherFees;
import com.sinosoft.claim.schema.model.WfOtherFeesId;
import com.sinosoft.claim.schema.service.facade.WfOtherFeesService;

public class WfOtherFeesServiceSpringImpl extends
		GenericDaoHibernate<WfOtherFees, WfOtherFeesId> implements WfOtherFeesService {

	@Override
	public void insert(WfOtherFees wfotherfees) throws Exception {
		super.save(wfotherfees);
	}

	@Override
	public void delete(String businessNo, int serialNo, int lineNo)
			throws Exception {
		WfOtherFeesId wfOtherFeesId = new WfOtherFeesId(businessNo,serialNo,lineNo);
		super.deleteByPK(wfOtherFeesId);
	}

	@Override
	public void deleteByConditions(String conditions) throws Exception {

	}

	@Override
	public void update(WfOtherFees wfotherfees) {

	}

	public WfOtherFees findByPrimaryKey(String businessNo, int serialNo,
			int lineNo) throws Exception {
		WfOtherFeesId wfOtherFeesId = new WfOtherFeesId(businessNo,serialNo,lineNo);
		return super.get(wfOtherFeesId);
	}

	@Override
	public Page findByConditions(String conditions, int pageNo,
			int rowsPerPage) throws Exception {
		return null;
	}

	@Override
	public List<?> findByConditions(String conditions) throws Exception {
		return null;
	}

	@Override
	public int getCount(String conditions) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule).size();
	}

}
