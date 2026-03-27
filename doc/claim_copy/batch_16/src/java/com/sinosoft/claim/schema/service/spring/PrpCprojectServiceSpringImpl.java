package com.sinosoft.claim.schema.service.spring;
/**
 * 预约协议缴费计划接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCproject;
import com.sinosoft.claim.schema.model.PrpCprojectId;
import com.sinosoft.claim.schema.service.facade.PrpCprojectService;

public class PrpCprojectServiceSpringImpl extends
GenericDaoHibernate<PrpCproject, PrpCprojectId> implements PrpCprojectService{

	@Override
	public void save(PrpCproject PrpCproject) throws Exception {
		logger.info("保存预约协议缴费计划信息");
		super.save(PrpCproject);
		
	}

	@Override
	public void save(List<PrpCproject> list) throws Exception {
		logger.info("保存预约协议缴费计划");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpCprojectId PrpCprojectId) throws Exception {
		logger.info("删除预约协议缴费计划编号为" + PrpCprojectId + "的预约协议缴费计划");
		super.deleteByPK(PrpCproject.class, PrpCprojectId);
	}

	@Override
	public PrpCproject findPrpCproject(PrpCprojectId PrpCprojectId) throws Exception {
		logger.info("查询预约协议缴费计划编号为" + PrpCprojectId + "的预约协议缴费计划");
		return super.get(PrpCproject.class, PrpCprojectId);
	}

	@Override
	public Page findPrpCproject(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取预约协议缴费计划列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpCproject> findPrpCproject(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据人伤跟踪编号查询出预约协议缴费计划
	 * @param certiNo ：传入的人伤跟踪编号
	 * @return 返回人伤跟踪
	 */
	public PrpCproject findPrpCproject(String certiNo) throws Exception{
		PrpCproject PrpCproject = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpCproject> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			PrpCproject = resultList.get(0);
		}
		return PrpCproject;
	}

}
