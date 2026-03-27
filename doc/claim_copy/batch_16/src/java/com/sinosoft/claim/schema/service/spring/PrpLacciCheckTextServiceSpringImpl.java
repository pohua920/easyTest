package com.sinosoft.claim.schema.service.spring;
/**
 * 意健险调查信息描述信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLacciCheckText;
import com.sinosoft.claim.schema.model.PrpLacciCheckTextId;
import com.sinosoft.claim.schema.service.facade.PrpLacciCheckTextService;

public class PrpLacciCheckTextServiceSpringImpl extends
GenericDaoHibernate<PrpLacciCheckText, PrpLacciCheckTextId> implements PrpLacciCheckTextService{

	@Override
	public void save(PrpLacciCheckText prpLacciCheckText) throws Exception {
		logger.info("保存意健险调查信息描述信息");
		super.save(prpLacciCheckText);
		
	}

	@Override
	public void save(List<PrpLacciCheckText> list) throws Exception {
		logger.info("保存意健险调查信息描述信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLacciCheckTextId prpLacciCheckTextId) throws Exception {
		logger.info("删除意健险调查信息描述信息编号为" + prpLacciCheckTextId + "的意健险调查信息描述信息");
		super.deleteByPK(PrpLacciCheckText.class, prpLacciCheckTextId);
	}

	@Override
	public PrpLacciCheckText findPrpLacciCheckText(PrpLacciCheckTextId prpLacciCheckTextId) throws Exception {
		logger.info("查询意健险调查信息描述信息编号为" + prpLacciCheckTextId + "的意健险调查信息描述信息");
		return super.get(PrpLacciCheckText.class, prpLacciCheckTextId);
	}

	@Override
	public Page findPrpLacciCheckText(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取意健险调查信息描述信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLacciCheckText> findPrpLacciCheckText(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据意健险调查信息描述编号查询出意健险调查信息描述信息
	 * @param certiNo ：传入的意健险调查信息描述编号
	 * @return 返回意健险调查信息描述
	 */
	public PrpLacciCheckText findPrpLacciCheckText(String certiNo) throws Exception{
		PrpLacciCheckText prpLacciCheckText = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLacciCheckText> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLacciCheckText = resultList.get(0);
		}
		return prpLacciCheckText;
	}
	
	@Override
	public List<PrpLacciCheckText> findByConditions(String conditions) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule);
	}
	
}
