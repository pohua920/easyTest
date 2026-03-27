package com.sinosoft.claim.schema.service.spring;
/**
 * PRPLREFERLAW接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLreferlaw;
import com.sinosoft.claim.schema.model.PrpLreferlawId;
import com.sinosoft.claim.schema.service.facade.PrpLreferlawService;

public class PrpLreferlawServiceSpringImpl extends
GenericDaoHibernate<PrpLreferlaw, PrpLreferlawId> implements PrpLreferlawService{

	@Override
	public void save(PrpLreferlaw prpLreferlaw) throws Exception {
		logger.info("保存PRPLREFERLAW");
		super.save(prpLreferlaw);
		
	}

	@Override
	public void save(List<PrpLreferlaw> list) throws Exception {
		logger.info("保存PRPLREFERLAW");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLreferlawId prpLreferlawId) throws Exception {
		logger.info("删除PRPLREFERLAW编号为" + prpLreferlawId + "的PRPLREFERLAW");
		super.deleteByPK(PrpLreferlaw.class, prpLreferlawId);
	}

	@Override
	public PrpLreferlaw findPrpLreferlaw(PrpLreferlawId prpLreferlawId) throws Exception {
		logger.info("查询PRPLREFERLAW编号为" + prpLreferlawId + "的PRPLREFERLAW");
		return super.get(PrpLreferlaw.class, prpLreferlawId);
	}

	@Override
	public Page findPrpLreferlaw(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取PRPLREFERLAW列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLreferlaw> findPrpLreferlaw(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据PRPLREFERLAW编号查询出PRPLREFERLAW
	 * @param certiNo ：传入的PRPLREFERLAW编号
	 * @return 返回PRPLREFERLAW
	 */
	public PrpLreferlaw findPrpLreferlaw(String certiNo) throws Exception{
		PrpLreferlaw prpLreferlaw = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLreferlaw> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLreferlaw = resultList.get(0);
		}
		return prpLreferlaw;
	}

}
