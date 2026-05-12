package com.sinosoft.claim.schema.service.spring;
/**
 * 联共保赔付金额分摊信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLcfeecoins;
import com.sinosoft.claim.schema.model.PrpLcfeecoinsId;
import com.sinosoft.claim.schema.service.facade.PrpLcfeecoinsService;

public class PrpLcfeecoinsServiceSpringImpl extends
GenericDaoHibernate<PrpLcfeecoins, PrpLcfeecoinsId> implements PrpLcfeecoinsService{

	@Override
	public void save(PrpLcfeecoins prpLcfeecoins) throws Exception {
		logger.info("保存联共保赔付金额分摊信息");
		super.save(prpLcfeecoins);
		
	}

	@Override
	public void save(List<PrpLcfeecoins> list) throws Exception {
		logger.info("保存联共保赔付金额分摊信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLcfeecoinsId prpLcfeecoinsId) throws Exception {
		logger.info("删除联共保赔付金额分摊信息编号为" + prpLcfeecoinsId + "的联共保赔付金额分摊信息");
		super.deleteByPK(PrpLcfeecoins.class, prpLcfeecoinsId);
	}

	@Override
	public PrpLcfeecoins findPrpLcfeecoins(PrpLcfeecoinsId prpLcfeecoinsId) throws Exception {
		logger.info("查询联共保赔付金额分摊信息编号为" + prpLcfeecoinsId + "的联共保赔付金额分摊信息");
		return super.get(PrpLcfeecoins.class, prpLcfeecoinsId);
	}

	@Override
	public Page findPrpLcfeecoins(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取联共保赔付金额分摊信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLcfeecoins> findPrpLcfeecoins(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据联共保赔付金额分摊编号查询出联共保赔付金额分摊信息
	 * @param certiNo ：传入的联共保赔付金额分摊编号
	 * @return 返回联共保赔付金额分摊
	 */
	public PrpLcfeecoins findPrpLcfeecoins(String certiNo) throws Exception{
		PrpLcfeecoins prpLcfeecoins = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLcfeecoins> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLcfeecoins = resultList.get(0);
		}
		return prpLcfeecoins;
	}

}
