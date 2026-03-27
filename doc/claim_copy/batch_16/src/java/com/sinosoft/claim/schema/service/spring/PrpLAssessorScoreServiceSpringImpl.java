package com.sinosoft.claim.schema.service.spring;
/**
 * PRPLASSESSORSCORE信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpLAssessorScore;
import com.sinosoft.claim.schema.model.PrpLAssessorScoreId;
import com.sinosoft.claim.schema.service.facade.PrpLAssessorScoreService;

public class PrpLAssessorScoreServiceSpringImpl extends
GenericDaoHibernate<PrpLAssessorScore, PrpLAssessorScoreId> implements PrpLAssessorScoreService{

	@Override
	public void save(PrpLAssessorScore prpLAssessorScore) throws Exception {
		logger.info("保存PRPLASSESSORSCORE信息");
		super.save(prpLAssessorScore);
		
	}

	@Override
	public void save(List<PrpLAssessorScore> list) throws Exception {
		logger.info("保存PRPLASSESSORSCORE信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLAssessorScoreId prpLAssessorScoreId) throws Exception {
		logger.info("删除PRPLASSESSORSCORE信息编号为" + prpLAssessorScoreId + "的PRPLASSESSORSCORE信息");
		super.deleteByPK(PrpLAssessorScore.class, prpLAssessorScoreId);
	}

	@Override
	public PrpLAssessorScore findPrpLAssessorScore(PrpLAssessorScoreId prpLAssessorScoreId) throws Exception {
		logger.info("查询PRPLASSESSORSCORE信息编号为" + prpLAssessorScoreId + "的PRPLASSESSORSCORE信息");
		return super.get(PrpLAssessorScore.class, prpLAssessorScoreId);
	}

	@Override
	public Page findPrpLAssessorScore(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取PRPLASSESSORSCORE信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLAssessorScore> findPrpLAssessorScore(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据PRPLASSESSORSCORE编号查询出PRPLASSESSORSCORE信息
	 * @param certiNo ：传入的PRPLASSESSORSCORE编号
	 * @return 返回PRPLASSESSORSCORE
	 */
	public PrpLAssessorScore findPrpLAssessorScore(String certiNo) throws Exception{
		PrpLAssessorScore prpLAssessorScore = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLAssessorScore> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLAssessorScore = resultList.get(0);
		}
		return prpLAssessorScore;
	}

	@Override
	public int getCount(String conditions) throws Exception {
        StringBuffer buffer = new StringBuffer(100);
        buffer.append("SELECT count(*) FROM PrpLAssessorScore a,PrpLInsuranceSurveyor b,Prplexternalagency c WHERE a.COMCODE=b.COMCODE AND a.COMCODE1=c.COMCODE AND ");
        buffer.append(conditions);
        int	count = (int) HibernateUtils.getCountbyCountSql(this.getSession(), buffer.toString());
        return count;
	}

}
