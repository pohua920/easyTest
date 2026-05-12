package com.sinosoft.claim.schema.service.spring;
/**
 *定核损标的信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLverifyLossItem;
import com.sinosoft.claim.schema.model.PrpLverifyLossItemId;
import com.sinosoft.claim.schema.service.facade.PrpLverifyLossItemService;

public class PrpLverifyLossItemServiceSpringImpl extends
GenericDaoHibernate<PrpLverifyLossItem, PrpLverifyLossItemId> implements PrpLverifyLossItemService{

	@Override
	public void save(PrpLverifyLossItem prpLverifyLossItem) throws Exception {
		logger.info("保存PRPLVERIFYLOSSITEM信息");
		super.save(prpLverifyLossItem);
		
	}

	@Override
	public void save(List<PrpLverifyLossItem> list) throws Exception {
		logger.info("保存PRPLVERIFYLOSSITEM信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLverifyLossItemId prpLverifyLossItemId) throws Exception {
		logger.info("删除PRPLVERIFYLOSSITEM信息编号为" + prpLverifyLossItemId + "的PRPLVERIFYLOSSITEM信息");
		super.deleteByPK(PrpLverifyLossItem.class, prpLverifyLossItemId);
	}

	@Override
	public PrpLverifyLossItem findPrpLverifyLossItem(PrpLverifyLossItemId prpLverifyLossItemId) throws Exception {
		logger.info("查询PRPLVERIFYLOSSITEM信息编号为" + prpLverifyLossItemId + "的PRPLVERIFYLOSSITEM信息");
		return super.get(PrpLverifyLossItem.class, prpLverifyLossItemId);
	}

	@Override
	public Page findPrpLverifyLossItem(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取PRPLVERIFYLOSSITEM信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLverifyLossItem> findPrpLverifyLossItem(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据PRPLVERIFYLOSSITEM编号查询出PRPLVERIFYLOSSITEM信息
	 * @param certiNo ：传入的PRPLVERIFYLOSSITEM编号
	 * @return 返回PRPLVERIFYLOSSITEM
	 */
	public PrpLverifyLossItem findPrpLverifyLossItem(String certiNo) throws Exception{
		PrpLverifyLossItem prpLverifyLossItem = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLverifyLossItem> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLverifyLossItem = resultList.get(0);
		}
		return prpLverifyLossItem;
	}

}
