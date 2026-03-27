package com.sinosoft.claim.schema.service.spring;
/**
 * 赔款计算金额信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLcfee;
import com.sinosoft.claim.schema.model.PrpLcfeeId;
import com.sinosoft.claim.schema.service.facade.PrpLcfeeService;

public class PrpLcfeeServiceSpringImpl extends
GenericDaoHibernate<PrpLcfee, PrpLcfeeId> implements PrpLcfeeService{

	@Override
	public void save(PrpLcfee prpLcfee) throws Exception {
		logger.info("保存赔款计算金额信息");
		super.save(prpLcfee);
		
	}

	@Override
	public void save(List<PrpLcfee> list) throws Exception {
		logger.info("保存赔款计算金额信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLcfeeId prpLcfeeId) throws Exception {
		logger.info("删除赔款计算金额信息编号为" + prpLcfeeId + "的赔款计算金额信息");
		super.deleteByPK(PrpLcfee.class, prpLcfeeId);
	}

	@Override
	public PrpLcfee findPrpLcfee(PrpLcfeeId prpLcfeeId) throws Exception {
		logger.info("查询赔款计算金额信息编号为" + prpLcfeeId + "的赔款计算金额信息");
		return super.get(PrpLcfee.class, prpLcfeeId);
	}

	@Override
	public Page findPrpLcfee(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取赔款计算金额信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLcfee> findPrpLcfee(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据赔款计算金额编号查询出赔款计算金额信息
	 * @param certiNo ：传入的赔款计算金额编号
	 * @return 返回赔款计算金额
	 */
	public PrpLcfee findPrpLcfee(String certiNo) throws Exception{
		PrpLcfee prpLcfee = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLcfee> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLcfee = resultList.get(0);
		}
		return prpLcfee;
	}

	@Override
	public void deleteByCompensateNo(String compensateNo) throws Exception {
		String sql = " DELETE FROM prpLcfee Where compensateNo = '" + compensateNo + "'";
		super.getSession().createSQLQuery(sql).executeUpdate();
	}

}
