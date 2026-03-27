package com.sinosoft.claim.schema.service.spring;
/**
 * PRPLBACKVISITQUE信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLbackVisitQue;
import com.sinosoft.claim.schema.model.PrpLbackVisitQueId;
import com.sinosoft.claim.schema.service.facade.PrpLbackVisitQueService;

public class PrpLbackVisitQueServiceSpringImpl extends
GenericDaoHibernate<PrpLbackVisitQue, PrpLbackVisitQueId> implements PrpLbackVisitQueService{

	@Override
	public void save(PrpLbackVisitQue prpLbackVisitQue) throws Exception {
		logger.info("保存PRPLBACKVISITQUE信息");
		super.save(prpLbackVisitQue);
		
	}

	@Override
	public void save(List<PrpLbackVisitQue> list) throws Exception {
		logger.info("保存PRPLBACKVISITQUE信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLbackVisitQueId prpLbackVisitQueId) throws Exception {
		logger.info("删除PRPLBACKVISITQUE信息编号为" + prpLbackVisitQueId + "的PRPLBACKVISITQUE信息");
		super.deleteByPK(PrpLbackVisitQue.class, prpLbackVisitQueId);
	}

	@Override
	public PrpLbackVisitQue findPrpLbackVisitQue(PrpLbackVisitQueId prpLbackVisitQueId) throws Exception {
		logger.info("查询PRPLBACKVISITQUE信息编号为" + prpLbackVisitQueId + "的PRPLBACKVISITQUE信息");
		return super.get(PrpLbackVisitQue.class, prpLbackVisitQueId);
	}

	@Override
	public Page findPrpLbackVisitQue(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取PRPLBACKVISITQUE信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLbackVisitQue> findPrpLbackVisitQue(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据PRPLBACKVISITQUE编号查询出PRPLBACKVISITQUE信息
	 * @param certiNo ：传入的PRPLBACKVISITQUE编号
	 * @return 返回PRPLBACKVISITQUE
	 */
	public PrpLbackVisitQue findPrpLbackVisitQue(String certiNo) throws Exception{
		PrpLbackVisitQue prpLbackVisitQue = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLbackVisitQue> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLbackVisitQue = resultList.get(0);
		}
		return prpLbackVisitQue;
	}

}
