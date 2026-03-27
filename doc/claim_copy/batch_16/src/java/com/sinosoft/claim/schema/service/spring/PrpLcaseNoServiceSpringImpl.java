package com.sinosoft.claim.schema.service.spring;
/**
 * 赔案号信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLcaseNo;
import com.sinosoft.claim.schema.model.PrpLcaseNoId;
import com.sinosoft.claim.schema.service.facade.PrpLcaseNoService;

public class PrpLcaseNoServiceSpringImpl extends
GenericDaoHibernate<PrpLcaseNo, PrpLcaseNoId> implements PrpLcaseNoService{

	@Override
	public void save(PrpLcaseNo prpLcaseNo) throws Exception {
		logger.info("保存赔案号信息");
		super.save(prpLcaseNo);
		
	}

	@Override
	public void save(List<PrpLcaseNo> list) throws Exception {
		logger.info("保存赔案号信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLcaseNoId prpLcaseNoId) throws Exception {
		logger.info("删除赔案号信息编号为" + prpLcaseNoId + "的赔案号信息");
		super.deleteByPK(PrpLcaseNo.class, prpLcaseNoId);
	}

	@Override
	public PrpLcaseNo findPrpLcaseNo(PrpLcaseNoId prpLcaseNoId) throws Exception {
		logger.info("查询赔案号信息编号为" + prpLcaseNoId + "的赔案号信息");
		return super.get(PrpLcaseNo.class, prpLcaseNoId);
	}

	@Override
	public Page findPrpLcaseNo(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取赔案号信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLcaseNo> findPrpLcaseNo(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据赔案号编号查询出赔案号信息
	 * @param certiNo ：传入的赔案号编号
	 * @return 返回赔案号
	 */
	public PrpLcaseNo findPrpLcaseNo(String certiNo) throws Exception{
		PrpLcaseNo prpLcaseNo = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLcaseNo> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLcaseNo = resultList.get(0);
		}
		return prpLcaseNo;
	}

}
