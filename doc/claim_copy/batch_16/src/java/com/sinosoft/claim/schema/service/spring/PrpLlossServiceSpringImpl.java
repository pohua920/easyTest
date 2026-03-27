package com.sinosoft.claim.schema.service.spring;
/**
 * 人伤跟踪信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpLloss;
import com.sinosoft.claim.schema.model.PrpLlossId;
import com.sinosoft.claim.schema.service.facade.PrpLlossService;
import com.sinosoft.sysframework.common.util.StringUtils;

public class PrpLlossServiceSpringImpl extends
GenericDaoHibernate<PrpLloss, PrpLlossId> implements PrpLlossService{

	@Override
	public void save(PrpLloss prpLloss) throws Exception {
		logger.info("保存人伤跟踪信息");
		super.save(prpLloss);
		
	}

	@Override
	public void save(List<PrpLloss> list) throws Exception {
		logger.info("保存人伤跟踪信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLlossId prpLlossId) throws Exception {
		logger.info("删除人伤跟踪信息编号为" + prpLlossId + "的人伤跟踪信息");
		super.deleteByPK(PrpLloss.class, prpLlossId);
	}

	@Override
	public PrpLloss findPrpLloss(PrpLlossId prpLlossId) throws Exception {
		logger.info("查询人伤跟踪信息编号为" + prpLlossId + "的人伤跟踪信息");
		return super.get(PrpLloss.class, prpLlossId);
	}

	@Override
	public Page findPrpLloss(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取人伤跟踪信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLloss> findPrpLloss(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据人伤跟踪编号查询出人伤跟踪信息
	 * @param certiNo ：传入的人伤跟踪编号
	 * @return 返回人伤跟踪
	 */
	public PrpLloss findPrpLloss(String certiNo) throws Exception{
		PrpLloss prpLloss = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLloss> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLloss = resultList.get(0);
		}
		return prpLloss;
	}

	@Override
	public void deleteByCompensateNo(String compensateNo) throws Exception {
		String sql = " DELETE FROM prpLloss Where compensateNo = '" + StringUtils.rightTrim(compensateNo) + "'";
		super.getSession().createSQLQuery(sql).executeUpdate();
	}
	public List<PrpLloss> findByConditions(String conditions)throws Exception{
		QueryRule queryRule = QueryRule.getInstance().addSql(conditions);
		return super.find(queryRule);
		
	}
	/**
	 * 查询保单的最大赔付额
	 * @param policyNo
	 * @param kindCode
	 * @param itemCode
	 * @return
	 * @throws Exception
	 */
	public Double findLossHisPaid(PrpLloss prpLloss)throws Exception{
		String sql = "select sum(sumDefPay) from prpLloss where policyNo='"+prpLloss.getPolicyNo()
					+"' and kindCode ='"+prpLloss.getKindCode()+"' and itemCode='"+prpLloss.getItemCode()+"'";
		List<?> list = HibernateUtils.findbySql(super.getSession(), sql);
		if(list.size()>0&&list.get(0)!=null){
			return ((Number)list.get(0)).doubleValue();
		}
		return 0D;
	}

}
