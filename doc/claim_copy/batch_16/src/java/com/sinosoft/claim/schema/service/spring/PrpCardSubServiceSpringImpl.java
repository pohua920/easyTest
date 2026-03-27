package com.sinosoft.claim.schema.service.spring;
/**
 * 刷卡子信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCardSub;
import com.sinosoft.claim.schema.model.PrpCardSubId;
import com.sinosoft.claim.schema.service.facade.PrpCardSubService;

public class PrpCardSubServiceSpringImpl extends
GenericDaoHibernate<PrpCardSub, PrpCardSubId> implements PrpCardSubService{

	@Override
	public void save(PrpCardSub PrpCardSub) throws Exception {
		logger.info("保存刷卡子信息信息");
		super.save(PrpCardSub);
		
	}

	@Override
	public void save(List<PrpCardSub> list) throws Exception {
		logger.info("保存刷卡子信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpCardSubId PrpCardSubId) throws Exception {
		logger.info("删除刷卡子信息编号为" + PrpCardSubId + "的刷卡子信息");
		super.deleteByPK(PrpCardSub.class, PrpCardSubId);
	}

	@Override
	public PrpCardSub findPrpCardSub(PrpCardSubId PrpCardSubId) throws Exception {
		logger.info("查询刷卡子信息编号为" + PrpCardSubId + "的刷卡子信息");
		return super.get(PrpCardSub.class, PrpCardSubId);
	}

	@Override
	public Page findPrpCardSub(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取刷卡子信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpCardSub> findPrpCardSub(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据人伤跟踪编号查询出刷卡子信息
	 * @param certiNo ：传入的人伤跟踪编号
	 * @return 返回人伤跟踪
	 */
	public PrpCardSub findPrpCardSub(String certiNo) throws Exception{
		PrpCardSub PrpCardSub = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpCardSub> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			PrpCardSub = resultList.get(0);
		}
		return PrpCardSub;
	}

}
