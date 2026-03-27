package com.sinosoft.claim.schema.service.spring;
/**
 * 船舶险标的信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCitemShip;
import com.sinosoft.claim.schema.model.PrpCitemShipId;
import com.sinosoft.claim.schema.service.facade.PrpCitemShipService;

public class PrpCitemShipServiceSpringImpl extends
GenericDaoHibernate<PrpCitemShip, PrpCitemShipId> implements PrpCitemShipService{

	@Override
	public void save(PrpCitemShip PrpCitemShip) throws Exception {
		logger.info("保存船舶险标的信息信息");
		super.save(PrpCitemShip);
		
	}

	@Override
	public void save(List<PrpCitemShip> list) throws Exception {
		logger.info("保存船舶险标的信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpCitemShipId PrpCitemShipId) throws Exception {
		logger.info("删除船舶险标的信息编号为" + PrpCitemShipId + "的船舶险标的信息");
		super.deleteByPK(PrpCitemShip.class, PrpCitemShipId);
	}

	@Override
	public PrpCitemShip findPrpCitemShip(PrpCitemShipId PrpCitemShipId) throws Exception {
		logger.info("查询船舶险标的信息编号为" + PrpCitemShipId + "的船舶险标的信息");
		return super.get(PrpCitemShip.class, PrpCitemShipId);
	}

	@Override
	public Page findPrpCitemShip(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取船舶险标的信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpCitemShip> findPrpCitemShip(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据人伤跟踪编号查询出船舶险标的信息
	 * @param certiNo ：传入的人伤跟踪编号
	 * @return 返回人伤跟踪
	 */
	public PrpCitemShip findPrpCitemShip(String certiNo) throws Exception{
		PrpCitemShip PrpCitemShip = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpCitemShip> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			PrpCitemShip = resultList.get(0);
		}
		return PrpCitemShip;
	}

}
