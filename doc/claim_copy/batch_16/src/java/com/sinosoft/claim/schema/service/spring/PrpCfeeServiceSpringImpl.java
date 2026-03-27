package com.sinosoft.claim.schema.service.spring;

/**
 * 保单保额保费接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCfee;
import com.sinosoft.claim.schema.model.PrpCfeeId;
import com.sinosoft.claim.schema.model.PrpLcfeeId;
import com.sinosoft.claim.schema.service.facade.PrpCfeeService;

public class PrpCfeeServiceSpringImpl extends GenericDaoHibernate<PrpCfee, PrpLcfeeId> implements PrpCfeeService {

	@Override
	public void save(PrpCfee prpCfee) throws Exception {
		logger.info("保存保单保额保费信息");
		super.save(prpCfee);

	}

	@Override
	public void save(List<PrpCfee> list) throws Exception {
		logger.info("保存保单保额保费");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpCfeeId prpCfeeId) throws Exception {
		logger.info("删除保单保额保费编号为" + prpCfeeId + "的保单保额保费");
		super.deleteByPK(PrpCfee.class, prpCfeeId);
	}

	@Override
	public PrpCfee findPrpCfee(PrpCfeeId prpCfeeId) throws Exception {
		logger.info("查询保单保额保费编号为" + prpCfeeId + "的保单保额保费");
		return super.get(PrpCfee.class, prpCfeeId);
	}

	@Override
	public Page findPrpCfee(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取保单保额保费列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpCfee> findPrpCfee(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 根据赔款计算金额编号查询出保单保额保费
	 * @param certiNo ：传入的赔款计算金额编号
	 * @return 返回赔款计算金额
	 */
	public PrpCfee findPrpCfee(String certiNo) throws Exception {
		PrpCfee prpCfee = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpCfee> resultList = super.find(queryRule);
		if (resultList != null && resultList.size() > 0) {
			prpCfee = resultList.get(0);
		}
		return prpCfee;
	}
}
