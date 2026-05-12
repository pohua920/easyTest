package com.sinosoft.claim.schema.service.spring;

/**
 * 免赔信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLdeductible;
import com.sinosoft.claim.schema.model.PrpLdeductibleId;
import com.sinosoft.claim.schema.service.facade.PrpLdeductibleService;
import com.sinosoft.sysframework.common.util.StringUtils;

public class PrpLdeductibleServiceSpringImpl extends GenericDaoHibernate<PrpLdeductible, PrpLdeductibleId> implements PrpLdeductibleService {

	@Override
	public void save(PrpLdeductible prpLdeductible) throws Exception {
		logger.info("保存免赔信息");
		super.save(prpLdeductible);

	}

	@Override
	public void save(List<PrpLdeductible> list) throws Exception {
		logger.info("保存免赔信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLdeductibleId prpLdeductibleId) throws Exception {
		logger.info("删除免赔信息编号为" + prpLdeductibleId + "的免赔信息");
		super.deleteByPK(PrpLdeductible.class, prpLdeductibleId);
	}

	@Override
	public PrpLdeductible findPrpLdeductible(PrpLdeductibleId prpLdeductibleId) throws Exception {
		logger.info("查询免赔信息编号为" + prpLdeductibleId + "的免赔信息");
		return super.get(PrpLdeductible.class, prpLdeductibleId);
	}

	@Override
	public Page findPrpLdeductible(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取免赔信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLdeductible> findPrpLdeductible(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 根据免赔编号查询出免赔信息
	 * @param certiNo ：传入的免赔编号
	 * @return 返回免赔
	 */
	public PrpLdeductible findPrpLdeductible(String certiNo) throws Exception {
		PrpLdeductible prpLdeductible = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLdeductible> resultList = super.find(queryRule);
		if (resultList != null && resultList.size() > 0) {
			prpLdeductible = resultList.get(0);
		}
		return prpLdeductible;
	}

	@Override
	public void deleteByCompensateNo(String compensateNo) throws Exception {
		String sql = " DELETE FROM PrpLdeductible Where compensateNo = '" + StringUtils.rightTrim(compensateNo) + "'";
		super.getSession().createSQLQuery(sql).executeUpdate();
	}

}
