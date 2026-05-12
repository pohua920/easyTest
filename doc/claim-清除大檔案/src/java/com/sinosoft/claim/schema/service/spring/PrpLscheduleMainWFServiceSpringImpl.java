package com.sinosoft.claim.schema.service.spring;

/**
 * 调度任务/查勘任务接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLscheduleMainWF;
import com.sinosoft.claim.schema.model.PrpLscheduleMainWFId;
import com.sinosoft.claim.schema.service.facade.PrpLscheduleMainWFService;

public class PrpLscheduleMainWFServiceSpringImpl extends
		GenericDaoHibernate<PrpLscheduleMainWF, PrpLscheduleMainWFId> implements
		PrpLscheduleMainWFService {
	
	/**
	 * 保存调度任务/查勘任务信息
	 * @param prpLscheduleMainWF ：传入的调度任务/查勘任务
	 */
	@Override
	public void save(PrpLscheduleMainWF prpLscheduleMainWF) throws Exception {
		logger.info("保存调度任务/查勘任务信息");
		super.save(prpLscheduleMainWF);
	}
	
	/**
	 * 保存调度任务/查勘任务信息
	 * @param list:保存调度任务/查勘任务信息
	 */
	@Override
	public void save(List<PrpLscheduleMainWF> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}
	/**
	 * 保存调度任务/查勘任务信息
	 * @param prpLscheduleMainWF ：传入的调度任务/查勘任务
	 */
	public void saveOrUpdate(PrpLscheduleMainWF prpLscheduleMainWF) throws Exception {
		logger.info("保存调度任务/查勘任务信息");
		super.getSession().saveOrUpdate(prpLscheduleMainWF);
	}
	/**
	 * 保存调度任务/查勘任务信息
	 * @param list:保存调度任务/查勘任务信息
	 */
	public void saveOrUpdate(List<PrpLscheduleMainWF> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.getSession().saveOrUpdate(list.get(i));
		}
	}
	/**
	 * 删除调度任务/查勘任务信息
	 * @param prpLscheduleMainWFId ：传入的调度任务/查勘任务编号
	 */
	@Override
	public void delete(PrpLscheduleMainWFId prpLscheduleMainWFId) throws Exception{
		super.deleteByPK(prpLscheduleMainWFId);
		logger.info("删除调度任务/查勘任务编号为" + prpLscheduleMainWFId + "的调度任务/查勘任务信息");
	}
	
	/**
	 * @description: 调度任务/查勘任务修改
	 * @param PrpLscheduleMainWF prpLscheduleMainWF
	 * @throws Exception 
	 */
	@Override
	public void update(PrpLscheduleMainWF prpLscheduleMainWF){
		logger.info("修改调度任务/查勘任务信息开始");
		super.getHibernateTemplate().merge(prpLscheduleMainWF);
		logger.info("修改调度任务/查勘任务信息结束");
	}
	
	/**
	 * 根据调度任务/查勘任务编号查询出调度任务/查勘任务信息
	 * @param prpLscheduleMainWFId ：传入的调度任务/查勘任务编号
	 * @return 返回调度任务/查勘任务
	 */
	@Override
	public PrpLscheduleMainWF findPrpLscheduleMainWF(PrpLscheduleMainWFId prpLscheduleMainWFId) throws Exception{
		logger.info("查询调度任务/查勘任务编号为" + prpLscheduleMainWFId + "的调度任务/查勘任务信息");
		return super.get(PrpLscheduleMainWF.class,prpLscheduleMainWFId);
	}
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的调度任务/查勘任务页面信息
	 */
	@Override
	public Page findPrpLscheduleMainWF(QueryRule queryRule, int pageNo, int pageSize) throws Exception{
		logger.info("获取调度任务/查勘任务列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLscheduleMainWF> findPrpLscheduleMainWF(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据调度任务/查勘任务编号查询出调度任务/查勘任务信息
	 * @param prpLscheduleMainWFId ：传入的调度任务/查勘任务编号
	 * @return 返回调度任务/查勘任务
	 */
	public PrpLscheduleMainWF findPrpLscheduleMainWF(int scheduleID,String registNo) throws Exception{
		PrpLscheduleMainWF prpLscheduleMainWF = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.scheduleID", scheduleID);
		queryRule.addEqual("id.registNo", registNo);
		List<PrpLscheduleMainWF> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLscheduleMainWF = resultList.get(0);
		}
		return prpLscheduleMainWF;
	}
}