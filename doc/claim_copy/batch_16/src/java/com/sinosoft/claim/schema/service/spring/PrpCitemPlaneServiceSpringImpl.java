package com.sinosoft.claim.schema.service.spring;
/**
 * 标的信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCitemPlane;
import com.sinosoft.claim.schema.model.PrpCitemPlaneId;
import com.sinosoft.claim.schema.service.facade.PrpCitemPlaneService;

public class PrpCitemPlaneServiceSpringImpl extends
GenericDaoHibernate<PrpCitemPlane, PrpCitemPlaneId> implements PrpCitemPlaneService{

	@Override
	public void save(PrpCitemPlane PrpCitemPlane) throws Exception {
		logger.info("保存标的信息信息");
		super.save(PrpCitemPlane);
		
	}

	@Override
	public void save(List<PrpCitemPlane> list) throws Exception {
		logger.info("保存标的信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpCitemPlaneId PrpCitemPlaneId) throws Exception {
		logger.info("删除标的信息编号为" + PrpCitemPlaneId + "的标的信息");
		super.deleteByPK(PrpCitemPlane.class, PrpCitemPlaneId);
	}

	@Override
	public PrpCitemPlane findPrpCitemPlane(PrpCitemPlaneId PrpCitemPlaneId) throws Exception {
		logger.info("查询标的信息编号为" + PrpCitemPlaneId + "的标的信息");
		return super.get(PrpCitemPlane.class, PrpCitemPlaneId);
	}

	@Override
	public Page findPrpCitemPlane(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取标的信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpCitemPlane> findPrpCitemPlane(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据标的编号查询出标的信息
	 * @param certiNo ：传入的标的编号
	 * @return 返回标的
	 */
	public PrpCitemPlane findPrpCitemPlane(String certiNo) throws Exception{
		PrpCitemPlane PrpCitemPlane = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpCitemPlane> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			PrpCitemPlane = resultList.get(0);
		}
		return PrpCitemPlane;
	}

}
