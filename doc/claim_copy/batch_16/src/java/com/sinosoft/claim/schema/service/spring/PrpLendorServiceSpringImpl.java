package com.sinosoft.claim.schema.service.spring;

/**
 * 理赔冲减保额信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLendor;
import com.sinosoft.claim.schema.model.PrpLendorId;
import com.sinosoft.claim.schema.service.facade.PrpLendorService;

public class PrpLendorServiceSpringImpl extends GenericDaoHibernate<PrpLendor, PrpLendorId> implements PrpLendorService {

	@Override
	public void save(PrpLendor prpLendor) throws Exception {
		logger.info("保存理赔冲减保额信息");
		super.save(prpLendor);

	}

	@Override
	public void save(List<PrpLendor> list) throws Exception {
		logger.info("保存理赔冲减保额信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLendorId prpLendorId) throws Exception {
		logger.info("删除理赔冲减保额信息编号为" + prpLendorId + "的理赔冲减保额信息");
		super.deleteByPK(PrpLendor.class, prpLendorId);
	}

	@Override
	public PrpLendor findPrpLendor(PrpLendorId prpLendorId) throws Exception {
		logger.info("查询理赔冲减保额信息编号为" + prpLendorId + "的理赔冲减保额信息");
		return super.get(PrpLendor.class, prpLendorId);
	}

	@Override
	public Page findPrpLendor(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取理赔冲减保额信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLendor> findPrpLendor(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 根据理赔冲减保额编号查询出理赔冲减保额信息
	 * @param certiNo ：传入的理赔冲减保额编号
	 * @return 返回理赔冲减保额
	 */
	public PrpLendor findPrpLendor(String certiNo) throws Exception {
		PrpLendor prpLendor = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLendor> resultList = super.find(queryRule);
		if (resultList != null && resultList.size() > 0) {
			prpLendor = resultList.get(0);
		}
		return prpLendor;
	}

	@Override
	public void deleteByCompensateNo(String compensateNo) throws Exception {
		String sql = " DELETE FROM prpLendor Where compensateNo = '" + compensateNo + "'";
		super.getSession().createSQLQuery(sql).executeUpdate();
	}

}
