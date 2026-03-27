package com.sinosoft.claim.schema.service.spring;
/**
 * 人伤跟踪信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLinvestigate;
import com.sinosoft.claim.schema.model.PrpLinvestigateId;
import com.sinosoft.claim.schema.service.facade.PrpLinvestigateService;

public class PrpLinvestigateServiceSpringImpl extends
GenericDaoHibernate<PrpLinvestigate, PrpLinvestigateId> implements PrpLinvestigateService{

	@Override
	public void save(PrpLinvestigate prpLinvestigate) throws Exception {
		logger.info("保存人伤跟踪信息");
		super.save(prpLinvestigate);
		
	}

	@Override
	public void save(List<PrpLinvestigate> list) throws Exception {
		logger.info("保存人伤跟踪信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLinvestigateId prpLinvestigateId) throws Exception {
		logger.info("删除人伤跟踪信息编号为" + prpLinvestigateId + "的人伤跟踪信息");
		super.deleteByPK(PrpLinvestigate.class, prpLinvestigateId);
	}

	@Override
	public PrpLinvestigate findPrpLinvestigate(PrpLinvestigateId prpLinvestigateId) throws Exception {
		logger.info("查询人伤跟踪信息编号为" + prpLinvestigateId + "的人伤跟踪信息");
		return super.get(PrpLinvestigate.class, prpLinvestigateId);
	}

	@Override
	public Page findPrpLinvestigate(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取人伤跟踪信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLinvestigate> findPrpLinvestigate(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据人伤跟踪编号查询出人伤跟踪信息
	 * @param certiNo ：传入的人伤跟踪编号
	 * @return 返回人伤跟踪
	 */
	public PrpLinvestigate findPrpLinvestigate(String certiNo) throws Exception{
		PrpLinvestigate prpLinvestigate = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLinvestigate> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLinvestigate = resultList.get(0);
		}
		return prpLinvestigate;
	}

}
