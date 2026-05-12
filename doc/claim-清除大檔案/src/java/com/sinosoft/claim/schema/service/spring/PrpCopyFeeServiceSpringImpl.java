package com.sinosoft.claim.schema.service.spring;

/**
 * 保单保额保费接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCopyFee;
import com.sinosoft.claim.schema.model.PrpCopyFeeId;
import com.sinosoft.claim.schema.service.facade.PrpCopyFeeService;

public class PrpCopyFeeServiceSpringImpl extends GenericDaoHibernate<PrpCopyFee, PrpCopyFeeId> implements PrpCopyFeeService {

	@Override
	public void save(PrpCopyFee prpCopyFee) throws Exception {
		logger.info("保存保单保额保费信息");
		super.save(prpCopyFee);

	}

	@Override
	public void save(List<PrpCopyFee> list) throws Exception {
		logger.info("保存保单保额保费");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpCopyFeeId prpCopyFeeId) throws Exception {
		logger.info("删除保单保额保费编号为" + prpCopyFeeId + "的保单保额保费");
		super.deleteByPK(PrpCopyFee.class, prpCopyFeeId);
	}

	@Override
	public PrpCopyFee findPrpCopyFee(PrpCopyFeeId prpCopyFeeId) throws Exception {
		logger.info("查询保单保额保费编号为" + prpCopyFeeId + "的保单保额保费");
		return super.get(PrpCopyFee.class, prpCopyFeeId);
	}

	@Override
	public Page findPrpCopyFee(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取保单保额保费列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpCopyFee> findPrpCopyFee(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 根据赔款计算金额编号查询出保单保额保费
	 * @param certiNo ：传入的赔款计算金额编号
	 * @return 返回赔款计算金额
	 */
	public PrpCopyFee findPrpCopyFee(String certiNo) throws Exception {
		PrpCopyFee prpCopyFee = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpCopyFee> resultList = super.find(queryRule);
		if (resultList != null && resultList.size() > 0) {
			prpCopyFee = resultList.get(0);
		}
		return prpCopyFee;
	}
}
