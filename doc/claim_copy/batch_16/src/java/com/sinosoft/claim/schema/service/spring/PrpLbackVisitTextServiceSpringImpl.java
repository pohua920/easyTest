package com.sinosoft.claim.schema.service.spring;
/**
 * PRPLBACKVISITTEXT信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLbackVisitText;
import com.sinosoft.claim.schema.model.PrpLbackVisitTextId;
import com.sinosoft.claim.schema.service.facade.PrpLbackVisitTextService;

public class PrpLbackVisitTextServiceSpringImpl extends
GenericDaoHibernate<PrpLbackVisitText, PrpLbackVisitTextId> implements PrpLbackVisitTextService{

	@Override
	public void save(PrpLbackVisitText prpLbackVisitText) throws Exception {
		logger.info("保存PRPLBACKVISITTEXT信息");
		super.save(prpLbackVisitText);
		
	}

	@Override
	public void save(List<PrpLbackVisitText> list) throws Exception {
		logger.info("保存PRPLBACKVISITTEXT信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLbackVisitTextId prpLbackVisitTextId) throws Exception {
		logger.info("删除PRPLBACKVISITTEXT信息编号为" + prpLbackVisitTextId + "的PRPLBACKVISITTEXT信息");
		super.deleteByPK(PrpLbackVisitText.class, prpLbackVisitTextId);
	}

	@Override
	public PrpLbackVisitText findPrpLbackVisitText(PrpLbackVisitTextId prpLbackVisitTextId) throws Exception {
		logger.info("查询PRPLBACKVISITTEXT信息编号为" + prpLbackVisitTextId + "的PRPLBACKVISITTEXT信息");
		return super.get(PrpLbackVisitText.class, prpLbackVisitTextId);
	}

	@Override
	public Page findPrpLbackVisitText(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取PRPLBACKVISITTEXT信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLbackVisitText> findPrpLbackVisitText(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据PRPLBACKVISITTEXT编号查询出PRPLBACKVISITTEXT信息
	 * @param certiNo ：传入的PRPLBACKVISITTEXT编号
	 * @return 返回PRPLBACKVISITTEXT
	 */
	public PrpLbackVisitText findPrpLbackVisitText(String certiNo) throws Exception{
		PrpLbackVisitText prpLbackVisitText = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLbackVisitText> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLbackVisitText = resultList.get(0);
		}
		return prpLbackVisitText;
	}

}
