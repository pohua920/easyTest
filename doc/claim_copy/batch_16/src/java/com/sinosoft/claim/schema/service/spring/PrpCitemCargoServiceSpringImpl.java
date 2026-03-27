package com.sinosoft.claim.schema.service.spring;
/**
 * 货运险标的信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCitemCargo;
import com.sinosoft.claim.schema.model.PrpCitemCargoId;
import com.sinosoft.claim.schema.service.facade.PrpCitemCargoService;

public class PrpCitemCargoServiceSpringImpl extends
GenericDaoHibernate<PrpCitemCargo, PrpCitemCargoId> implements PrpCitemCargoService{

	@Override
	public void save(PrpCitemCargo PrpCitemCargo) throws Exception {
		logger.info("保存货运险标的信息信息");
		super.save(PrpCitemCargo);
		
	}

	@Override
	public void save(List<PrpCitemCargo> list) throws Exception {
		logger.info("保存货运险标的信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpCitemCargoId PrpCitemCargoId) throws Exception {
		logger.info("删除货运险标的信息编号为" + PrpCitemCargoId + "的货运险标的信息");
		super.deleteByPK(PrpCitemCargo.class, PrpCitemCargoId);
	}

	@Override
	public PrpCitemCargo findPrpCitemCargo(PrpCitemCargoId PrpCitemCargoId) throws Exception {
		logger.info("查询货运险标的信息编号为" + PrpCitemCargoId + "的货运险标的信息");
		return super.get(PrpCitemCargo.class, PrpCitemCargoId);
	}

	@Override
	public Page findPrpCitemCargo(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取货运险标的信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpCitemCargo> findPrpCitemCargo(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据人伤跟踪编号查询出货运险标的信息
	 * @param certiNo ：传入的人伤跟踪编号
	 * @return 返回人伤跟踪
	 */
	public PrpCitemCargo findPrpCitemCargo(String certiNo) throws Exception{
		PrpCitemCargo PrpCitemCargo = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpCitemCargo> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			PrpCitemCargo = resultList.get(0);
		}
		return PrpCitemCargo;
	}

}
