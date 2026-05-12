package com.sinosoft.claim.schema.service.spring;
/**
 * 调度任务信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLscheduleMain;
import com.sinosoft.claim.schema.model.PrpLscheduleMainId;
import com.sinosoft.claim.schema.service.facade.PrpLscheduleMainService;

public class PrpLscheduleMainServiceSpringImpl extends
GenericDaoHibernate<PrpLscheduleMain, PrpLscheduleMainId> implements PrpLscheduleMainService{

	@Override
	public void save(PrpLscheduleMain prpLscheduleMain) throws Exception {
		logger.info("保存调度任务信息");
		super.save(prpLscheduleMain);
		
	}

	@Override
	public void save(List<PrpLscheduleMain> list) throws Exception {
		logger.info("保存调度任务信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLscheduleMainId prpLscheduleMainId) throws Exception {
		logger.info("删除调度任务信息编号为" + prpLscheduleMainId + "的调度任务信息");
		super.deleteByPK(PrpLscheduleMain.class, prpLscheduleMainId);
	}

	@Override
	public PrpLscheduleMain findPrpLscheduleMain(PrpLscheduleMainId prpLscheduleMainId) throws Exception {
		logger.info("查询调度任务信息编号为" + prpLscheduleMainId + "的调度任务信息");
		return super.get(PrpLscheduleMain.class, prpLscheduleMainId);
	}

	@Override
	public Page findPrpLscheduleMain(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取调度任务信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLscheduleMain> findPrpLscheduleMain(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据调度任务编号查询出调度任务信息
	 * @param certiNo ：传入的调度任务编号
	 * @return 返回调度任务
	 */
	public PrpLscheduleMain findPrpLscheduleMain(String certiNo) throws Exception{
		PrpLscheduleMain prpLscheduleMain = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLscheduleMain> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLscheduleMain = resultList.get(0);
		}
		return prpLscheduleMain;
	}

}
