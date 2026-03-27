package com.sinosoft.claim.schema.service.spring;
/**
 * 人伤跟踪信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLptext;
import com.sinosoft.claim.schema.model.PrpLptextId;
import com.sinosoft.claim.schema.service.facade.PrpLptextService;

public class PrpLptextServiceSpringImpl extends
GenericDaoHibernate<PrpLptext, PrpLptextId> implements PrpLptextService{

	@Override
	public void save(PrpLptext prpLptext) throws Exception {
		logger.info("保存人伤跟踪信息");
		super.save(prpLptext);
		
	}

	@Override
	public void save(List<PrpLptext> list) throws Exception {
		logger.info("保存人伤跟踪信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLptextId prpLptextId) throws Exception {
		logger.info("删除人伤跟踪信息编号为" + prpLptextId + "的人伤跟踪信息");
		super.deleteByPK(PrpLptext.class, prpLptextId);
	}

	@Override
	public PrpLptext findPrpLptext(PrpLptextId prpLptextId) throws Exception {
		logger.info("查询人伤跟踪信息编号为" + prpLptextId + "的人伤跟踪信息");
		return super.get(PrpLptext.class, prpLptextId);
	}

	@Override
	public Page findPrpLptext(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取人伤跟踪信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLptext> findPrpLptext(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据人伤跟踪编号查询出人伤跟踪信息
	 * @param certiNo ：传入的人伤跟踪编号
	 * @return 返回人伤跟踪
	 */
	public PrpLptext findPrpLptext(String certiNo) throws Exception{
		PrpLptext prpLptext = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLptext> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLptext = resultList.get(0);
		}
		return prpLptext;
	}

}
