package com.sinosoft.claim.schema.service.spring;
/**
 * 查勘事故估损金额信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.schema.model.PrpLcheckLoss;
import com.sinosoft.claim.schema.model.PrpLcheckLossId;
import com.sinosoft.claim.schema.service.facade.PrpLcheckLossService;

public class PrpLcheckLossServiceSpringImpl extends
GenericDaoHibernate<PrpLcheckLoss, PrpLcheckLossId> implements PrpLcheckLossService{

	@Override
	public void save(PrpLcheckLoss prpLcheckLoss) throws Exception {
		logger.info("保存查勘事故估损金额信息");
		super.save(prpLcheckLoss);
		
	}

	@Override
	public void save(List<PrpLcheckLoss> list) throws Exception {
		logger.info("保存查勘事故估损金额信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLcheckLossId prpLcheckLossId) throws Exception {
		logger.info("删除查勘事故估损金额信息编号为" + prpLcheckLossId + "的查勘事故估损金额信息");
		super.deleteByPK(PrpLcheckLoss.class, prpLcheckLossId);
	}

	@Override
	public PrpLcheckLoss findPrpLcheckLoss(PrpLcheckLossId prpLcheckLossId) throws Exception {
		logger.info("查询查勘事故估损金额信息编号为" + prpLcheckLossId + "的查勘事故估损金额信息");
		return super.get(PrpLcheckLoss.class, prpLcheckLossId);
	}

	@Override
	public Page findPrpLcheckLoss(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取查勘事故估损金额信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLcheckLoss> findPrpLcheckLoss(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据查勘事故估损金额编号查询出查勘事故估损金额信息
	 * @param certiNo ：传入的查勘事故估损金额编号
	 * @return 返回查勘事故估损金额
	 */
	public PrpLcheckLoss findPrpLcheckLoss(String certiNo) throws Exception{
		PrpLcheckLoss prpLcheckLoss = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLcheckLoss> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLcheckLoss = resultList.get(0);
		}
		return prpLcheckLoss;
	}

	@Override
	public void deleteByRegistNo(String registNo) throws Exception {
		String sql = "delete from PrpLcheckLoss where registNo=?";
		super.getSession().createSQLQuery(sql).setString(0, registNo).executeUpdate();
	}

	@Override
	public void insertAll(List<PrpLcheckLoss> list) {
		if(list!=null&&list.size()>0){
			Session session = super.getSession();
			for(int i=0;i<list.size();i++){
				session.saveOrUpdate(list.get(i));
			}
		}
	}

}
