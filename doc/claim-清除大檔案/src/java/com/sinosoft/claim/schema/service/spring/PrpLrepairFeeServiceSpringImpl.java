package com.sinosoft.claim.schema.service.spring;
/**
 * 修理费用清单信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLrepairFee;
import com.sinosoft.claim.schema.model.PrpLrepairFeeId;
import com.sinosoft.claim.schema.service.facade.PrpLrepairFeeService;

public class PrpLrepairFeeServiceSpringImpl extends
GenericDaoHibernate<PrpLrepairFee, PrpLrepairFeeId> implements PrpLrepairFeeService{

	@Override
	public void save(PrpLrepairFee prpLrepairFee) throws Exception {
		logger.info("保存修理费用清单信息");
		super.save(prpLrepairFee);
		
	}

	@Override
	public void save(List<PrpLrepairFee> list) throws Exception {
		logger.info("保存修理费用清单信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLrepairFeeId prpLrepairFeeId) throws Exception {
		logger.info("删除修理费用清单信息编号为" + prpLrepairFeeId + "的修理费用清单信息");
		super.deleteByPK(PrpLrepairFee.class, prpLrepairFeeId);
	}

	@Override
	public PrpLrepairFee findPrpLrepairFee(PrpLrepairFeeId prpLrepairFeeId) throws Exception {
		logger.info("查询修理费用清单信息编号为" + prpLrepairFeeId + "的修理费用清单信息");
		return super.get(PrpLrepairFee.class, prpLrepairFeeId);
	}

	@Override
	public Page findPrpLrepairFee(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取修理费用清单信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLrepairFee> findPrpLrepairFee(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据修理费用清单编号查询出修理费用清单信息
	 * @param certiNo ：传入的修理费用清单编号
	 * @return 返回修理费用清单
	 */
	public PrpLrepairFee findPrpLrepairFee(String certiNo) throws Exception{
		PrpLrepairFee prpLrepairFee = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLrepairFee> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLrepairFee = resultList.get(0);
		}
		return prpLrepairFee;
	}

}
