package com.sinosoft.claim.schema.service.spring;
/**
 * 理赔业务权限信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLclaimGrade;
import com.sinosoft.claim.schema.model.PrpLclaimGradeId;
import com.sinosoft.claim.schema.service.facade.PrpLclaimGradeService;

public class PrpLclaimGradeServiceSpringImpl extends
GenericDaoHibernate<PrpLclaimGrade, PrpLclaimGradeId> implements PrpLclaimGradeService{

	@Override
	public void save(PrpLclaimGrade prpLCitemKind) throws Exception {
		logger.info("保存理赔业务权限信息");
		super.save(prpLCitemKind);
		
	}

	@Override
	public void save(List<PrpLclaimGrade> list) throws Exception {
		logger.info("保存理赔业务权限信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLclaimGradeId prpLCitemKindId) throws Exception {
		logger.info("删除理赔业务权限信息编号为" + prpLCitemKindId + "的理赔业务权限信息");
		super.deleteByPK(PrpLclaimGrade.class, prpLCitemKindId);
	}

	@Override
	public PrpLclaimGrade findPrpLclaimGrade(PrpLclaimGradeId prpLCitemKindId) throws Exception {
		logger.info("查询理赔业务权限信息编号为" + prpLCitemKindId + "的理赔业务权限信息");
		return super.get(PrpLclaimGrade.class, prpLCitemKindId);
	}

	@Override
	public Page findPrpLclaimGrade(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取理赔业务权限信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLclaimGrade> findPrpLclaimGrade(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据理赔业务权限编号查询出理赔业务权限信息
	 * @param certiNo ：传入的理赔业务权限编号
	 * @return 返回理赔业务权限
	 */
	public PrpLclaimGrade findPrpLclaimGrade(String certiNo) throws Exception{
		PrpLclaimGrade prpLCitemKind = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLclaimGrade> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLCitemKind = resultList.get(0);
		}
		return prpLCitemKind;
	}

}
