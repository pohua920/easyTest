package com.sinosoft.claim.schema.service.spring;
/**
 * 逾款欠款清单信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLarrearage;
import com.sinosoft.claim.schema.model.PrpLarrearageId;
import com.sinosoft.claim.schema.service.facade.PrpLarrearageService;

public class PrpLarrearageServiceSpringImpl extends
GenericDaoHibernate<PrpLarrearage, PrpLarrearageId> implements PrpLarrearageService{

	@Override
	public void save(PrpLarrearage prpLarrearage) throws Exception {
		logger.info("保存逾款欠款清单信息");
		super.save(prpLarrearage);
		
	}

	@Override
	public void save(List<PrpLarrearage> list) throws Exception {
		logger.info("保存逾款欠款清单信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLarrearageId prpLarrearageId) throws Exception {
		logger.info("删除逾款欠款清单信息编号为" + prpLarrearageId + "的逾款欠款清单信息");
		super.deleteByPK(PrpLarrearage.class, prpLarrearageId);
	}

	@Override
	public PrpLarrearage findPrpLarrearage(PrpLarrearageId prpLarrearageId) throws Exception {
		logger.info("查询逾款欠款清单信息编号为" + prpLarrearageId + "的逾款欠款清单信息");
		return super.get(PrpLarrearage.class, prpLarrearageId);
	}

	@Override
	public Page findPrpLarrearage(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取逾款欠款清单信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLarrearage> findPrpLarrearage(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据逾款欠款清单编号查询出逾款欠款清单信息
	 * @param certiNo ：传入的逾款欠款清单编号
	 * @return 返回逾款欠款清单
	 */
	public PrpLarrearage findPrpLarrearage(String certiNo) throws Exception{
		PrpLarrearage prpLarrearage = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLarrearage> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLarrearage = resultList.get(0);
		}
		return prpLarrearage;
	}

}
