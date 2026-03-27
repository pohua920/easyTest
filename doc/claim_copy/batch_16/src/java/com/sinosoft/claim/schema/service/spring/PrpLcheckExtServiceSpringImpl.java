package com.sinosoft.claim.schema.service.spring;
/**
 * 查勘/代查勘扩展信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLcheckExt;
import com.sinosoft.claim.schema.model.PrpLcheckExtId;
import com.sinosoft.claim.schema.service.facade.PrpLcheckExtService;

public class PrpLcheckExtServiceSpringImpl extends
GenericDaoHibernate<PrpLcheckExt, PrpLcheckExtId> implements PrpLcheckExtService{

	@Override
	public void save(PrpLcheckExt prpLcheckExt) throws Exception {
		logger.info("保存查勘/代查勘扩展信息");
		super.save(prpLcheckExt);
		
	}

	@Override
	public void save(List<PrpLcheckExt> list) throws Exception {
		logger.info("保存查勘/代查勘扩展信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLcheckExtId prpLcheckExtId) throws Exception {
		logger.info("删除查勘/代查勘扩展信息编号为" + prpLcheckExtId + "的查勘/代查勘扩展信息");
		super.deleteByPK(PrpLcheckExt.class, prpLcheckExtId);
	}

	@Override
	public PrpLcheckExt findPrpLcheckExt(PrpLcheckExtId prpLcheckExtId) throws Exception {
		logger.info("查询查勘/代查勘扩展信息编号为" + prpLcheckExtId + "的查勘/代查勘扩展信息");
		return super.get(PrpLcheckExt.class, prpLcheckExtId);
	}

	@Override
	public Page findPrpLcheckExt(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取查勘/代查勘扩展信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLcheckExt> findPrpLcheckExt(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据查勘/代查勘扩展编号查询出查勘/代查勘扩展信息
	 * @param certiNo ：传入的查勘/代查勘扩展编号
	 * @return 返回查勘/代查勘扩展
	 */
	public PrpLcheckExt findPrpLcheckExt(String certiNo) throws Exception{
		PrpLcheckExt prpLcheckExt = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLcheckExt> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLcheckExt = resultList.get(0);
		}
		return prpLcheckExt;
	}

}
