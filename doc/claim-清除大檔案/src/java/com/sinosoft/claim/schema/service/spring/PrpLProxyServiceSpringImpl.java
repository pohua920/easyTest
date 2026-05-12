package com.sinosoft.claim.schema.service.spring;
/**
 * 理赔调派处理记录信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLProxy;
import com.sinosoft.claim.schema.model.PrpLProxyId;
import com.sinosoft.claim.schema.service.facade.PrpLProxyService;

public class PrpLProxyServiceSpringImpl extends
GenericDaoHibernate<PrpLProxy, PrpLProxyId> implements PrpLProxyService{

	@Override
	public void save(PrpLProxy prpLProxy) throws Exception {
		logger.info("保存理赔调派处理记录信息");
		super.save(prpLProxy);
		
	}

	@Override
	public void save(List<PrpLProxy> list) throws Exception {
		logger.info("保存理赔调派处理记录信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLProxyId prpLProxyId) throws Exception {
		logger.info("删除理赔调派处理记录信息编号为" + prpLProxyId + "的理赔调派处理记录信息");
		super.deleteByPK(PrpLProxy.class, prpLProxyId);
	}

	@Override
	public PrpLProxy findPrpLProxy(PrpLProxyId prpLProxyId) throws Exception {
		logger.info("查询理赔调派处理记录信息编号为" + prpLProxyId + "的理赔调派处理记录信息");
		return super.get(PrpLProxy.class, prpLProxyId);
	}

	@Override
	public Page findPrpLProxy(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取理赔调派处理记录信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLProxy> findPrpLProxy(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据理赔调派处理记录编号查询出理赔调派处理记录信息
	 * @param certiNo ：传入的理赔调派处理记录编号
	 * @return 返回理赔调派处理记录
	 */
	public PrpLProxy findPrpLProxy(String certiNo) throws Exception{
		PrpLProxy prpLProxy = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLProxy> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLProxy = resultList.get(0);
		}
		return prpLProxy;
	}

}
