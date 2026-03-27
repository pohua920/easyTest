package com.sinosoft.claim.schema.service.spring;
/**
 * 定核损意见信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLverifyLossExt;
import com.sinosoft.claim.schema.model.PrpLverifyLossExtId;
import com.sinosoft.claim.schema.service.facade.PrpLverifyLossExtService;

public class PrpLverifyLossExtServiceSpringImpl extends
GenericDaoHibernate<PrpLverifyLossExt, PrpLverifyLossExtId> implements PrpLverifyLossExtService{

	@Override
	public void save(PrpLverifyLossExt prpLverifyLossExt) throws Exception {
		logger.info("保存PRPLVERIFYLOSSEXT信息");
		super.save(prpLverifyLossExt);
		
	}

	@Override
	public void save(List<PrpLverifyLossExt> list) throws Exception {
		logger.info("保存PRPLVERIFYLOSSEXT信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLverifyLossExtId prpLverifyLossExtId) throws Exception {
		logger.info("删除PRPLVERIFYLOSSEXT信息编号为" + prpLverifyLossExtId + "的PRPLVERIFYLOSSEXT信息");
		super.deleteByPK(PrpLverifyLossExt.class, prpLverifyLossExtId);
	}

	@Override
	public PrpLverifyLossExt findPrpLverifyLossExt(PrpLverifyLossExtId prpLverifyLossExtId) throws Exception {
		logger.info("查询PRPLVERIFYLOSSEXT信息编号为" + prpLverifyLossExtId + "的PRPLVERIFYLOSSEXT信息");
		return super.get(PrpLverifyLossExt.class, prpLverifyLossExtId);
	}

	@Override
	public Page findPrpLverifyLossExt(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取PRPLVERIFYLOSSEXT信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLverifyLossExt> findPrpLverifyLossExt(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据PRPLVERIFYLOSSEXT编号查询出PRPLVERIFYLOSSEXT信息
	 * @param certiNo ：传入的PRPLVERIFYLOSSEXT编号
	 * @return 返回PRPLVERIFYLOSSEXT
	 */
	public PrpLverifyLossExt findPrpLverifyLossExt(String certiNo) throws Exception{
		PrpLverifyLossExt prpLverifyLossExt = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLverifyLossExt> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLverifyLossExt = resultList.get(0);
		}
		return prpLverifyLossExt;
	}

}
