package com.sinosoft.claim.schema.service.spring;
/**
 * 财产核定损明细清单信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLprop;
import com.sinosoft.claim.schema.model.PrpLpropId;
import com.sinosoft.claim.schema.service.facade.PrpLpropService;

public class PrpLpropServiceSpringImpl extends
GenericDaoHibernate<PrpLprop, PrpLpropId> implements PrpLpropService{

	@Override
	public void save(PrpLprop prpLprop) throws Exception {
		logger.info("保存财产核定损明细清单信息");
		super.save(prpLprop);
		
	}

	@Override
	public void save(List<PrpLprop> list) throws Exception {
		logger.info("保存财产核定损明细清单信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLpropId prpLpropId) throws Exception {
		logger.info("删除财产核定损明细清单信息编号为" + prpLpropId + "的财产核定损明细清单信息");
		super.deleteByPK(PrpLprop.class, prpLpropId);
	}

	@Override
	public PrpLprop findPrpLprop(PrpLpropId prpLpropId) throws Exception {
		logger.info("查询财产核定损明细清单信息编号为" + prpLpropId + "的财产核定损明细清单信息");
		return super.get(PrpLprop.class, prpLpropId);
	}

	@Override
	public Page findPrpLprop(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取财产核定损明细清单信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLprop> findPrpLprop(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据财产核定损明细清单编号查询出财产核定损明细清单信息
	 * @param certiNo ：传入的财产核定损明细清单编号
	 * @return 返回财产核定损明细清单
	 */
	public PrpLprop findPrpLprop(String certiNo) throws Exception{
		PrpLprop prpLprop = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLprop> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLprop = resultList.get(0);
		}
		return prpLprop;
	}

}
