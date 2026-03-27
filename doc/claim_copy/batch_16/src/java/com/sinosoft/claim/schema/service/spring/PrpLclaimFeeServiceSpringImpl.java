package com.sinosoft.claim.schema.service.spring;
/**
 * 估损金额信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLclaimFee;
import com.sinosoft.claim.schema.model.PrpLclaimFeeId;
import com.sinosoft.claim.schema.service.facade.PrpLclaimFeeService;

public class PrpLclaimFeeServiceSpringImpl extends
GenericDaoHibernate<PrpLclaimFee, PrpLclaimFeeId> implements PrpLclaimFeeService{

	@Override
	public void save(PrpLclaimFee prpLclaimFee) throws Exception {
		logger.info("保存估损金额信息");
		super.save(prpLclaimFee);
		
	}

	@Override
	public void save(List<PrpLclaimFee> list) throws Exception {
		logger.info("保存估损金额信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLclaimFeeId prpLclaimFeeId) throws Exception {
		logger.info("删除估损金额信息编号为" + prpLclaimFeeId + "的估损金额信息");
		super.deleteByPK(PrpLclaimFee.class, prpLclaimFeeId);
	}
	/**
	 * @param claimNo
	 * @throws Exception
	 * 根据立案号删除信息
	 */
	public void deleteByClaimNo(String claimNo) throws Exception {
		logger.info("删除估损金额信息编号为" + claimNo + "的估损金额信息");
		String sql = "delete from PrpLclaimFee where claimNo=?";
		super.getSession().createSQLQuery(sql).setString(0, claimNo).executeUpdate();
	}
	/**
	 * @param list
	 * @throws Exception
	 * 保存或者修改方法
	 */
	public void saveOrUpdate(PrpLclaimFee prpLclaimFee) throws Exception {
		logger.info("保存估损金额信息");
		super.getSession().saveOrUpdate(prpLclaimFee);
		
	}

	/**
	 * @param list
	 * @throws Exception
	 * 保存或者修改方法
	 */
	public void saveOrUpdate(List<PrpLclaimFee> list) throws Exception {
		logger.info("保存估损金额信息");
		for(int i=0;i<list.size();i++){
			super.getSession().saveOrUpdate(list.get(i));
		}
	}

	@Override
	public PrpLclaimFee findPrpLclaimFee(PrpLclaimFeeId prpLcallCenterId) throws Exception {
		logger.info("查询估损金额信息编号为" + prpLcallCenterId + "的估损金额信息");
		return super.get(PrpLclaimFee.class, prpLcallCenterId);
	}

	@Override
	public Page findPrpLclaimFee(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取估损金额信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLclaimFee> findPrpLclaimFee(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}

}
