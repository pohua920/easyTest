package com.sinosoft.claim.schema.service.spring;
/**
 * 换件项目清单信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLcomponent;
import com.sinosoft.claim.schema.model.PrpLcomponentId;
import com.sinosoft.claim.schema.service.facade.PrpLcomponentService;

public class PrpLcomponentServiceSpringImpl extends
GenericDaoHibernate<PrpLcomponent, PrpLcomponentId> implements PrpLcomponentService{

	@Override
	public void save(PrpLcomponent prpLcomponent) throws Exception {
		logger.info("保存换件项目清单信息");
		super.save(prpLcomponent);
		
	}

	@Override
	public void save(List<PrpLcomponent> list) throws Exception {
		logger.info("保存换件项目清单信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLcomponentId prpLcomponentId) throws Exception {
		logger.info("删除换件项目清单信息编号为" + prpLcomponentId + "的换件项目清单信息");
		super.deleteByPK(PrpLcomponent.class, prpLcomponentId);
	}

	@Override
	public PrpLcomponent findPrpLcomponent(PrpLcomponentId prpLcomponentId) throws Exception {
		logger.info("查询换件项目清单信息编号为" + prpLcomponentId + "的换件项目清单信息");
		return super.get(PrpLcomponent.class, prpLcomponentId);
	}

	@Override
	public Page findPrpLcomponent(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取换件项目清单信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLcomponent> findPrpLcomponent(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据换件项目清单编号查询出换件项目清单信息
	 * @param certiNo ：传入的换件项目清单编号
	 * @return 返回换件项目清单
	 */
	public PrpLcomponent findPrpLcomponent(String certiNo) throws Exception{
		PrpLcomponent prpLcomponent = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLcomponent> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLcomponent = resultList.get(0);
		}
		return prpLcomponent;
	}

}
