package com.sinosoft.claim.schema.service.spring;
/**
 * 车辆定损信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLcarLoss;
import com.sinosoft.claim.schema.model.PrpLcarLossId;
import com.sinosoft.claim.schema.service.facade.PrpLcarLossService;

public class PrpLcarLossServiceSpringImpl extends
GenericDaoHibernate<PrpLcarLoss, PrpLcarLossId> implements PrpLcarLossService{

	@Override
	public void save(PrpLcarLoss prpLcarLoss) throws Exception {
		logger.info("保存车辆定损信息");
		super.save(prpLcarLoss);
		
	}

	@Override
	public void save(List<PrpLcarLoss> list) throws Exception {
		logger.info("保存车辆定损信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLcarLossId prpLcarLossId) throws Exception {
		logger.info("删除车辆定损信息编号为" + prpLcarLossId + "的车辆定损信息");
		super.deleteByPK(PrpLcarLoss.class, prpLcarLossId);
	}

	@Override
	public PrpLcarLoss findPrpLcarLoss(PrpLcarLossId prpLcarLossId) throws Exception {
		logger.info("查询车辆定损信息编号为" + prpLcarLossId + "的车辆定损信息");
		return super.get(PrpLcarLoss.class, prpLcarLossId);
	}

	@Override
	public Page findPrpLcarLoss(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取车辆定损信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLcarLoss> findPrpLcarLoss(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据车辆定损编号查询出车辆定损信息
	 * @param certiNo ：传入的车辆定损编号
	 * @return 返回车辆定损
	 */
	public PrpLcarLoss findPrpLcarLoss(String certiNo) throws Exception{
		PrpLcarLoss prpLcarLoss = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLcarLoss> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLcarLoss = resultList.get(0);
		}
		return prpLcarLoss;
	}

}
