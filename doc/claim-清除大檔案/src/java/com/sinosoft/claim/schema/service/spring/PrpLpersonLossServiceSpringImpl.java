package com.sinosoft.claim.schema.service.spring;

/**
 * 人员赔付信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpLpersonLoss;
import com.sinosoft.claim.schema.model.PrpLpersonLossId;
import com.sinosoft.claim.schema.service.facade.PrpLpersonLossService;
import com.sinosoft.sysframework.common.util.StringUtils;

public class PrpLpersonLossServiceSpringImpl extends GenericDaoHibernate<PrpLpersonLoss, PrpLpersonLossId> implements PrpLpersonLossService {

	@Override
	public void save(PrpLpersonLoss prpLpersonLoss) throws Exception {
		logger.info("保存人员赔付信息信息");
		super.save(prpLpersonLoss);

	}

	@Override
	public void save(List<PrpLpersonLoss> list) throws Exception {
		logger.info("保存人员赔付信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLpersonLossId prpLpersonLossId) throws Exception {
		logger.info("删除人员赔付信息编号为" + prpLpersonLossId + "的人员赔付信息");
		super.deleteByPK(PrpLpersonLoss.class, prpLpersonLossId);
	}

	@Override
	public PrpLpersonLoss findPrpLpersonLoss(PrpLpersonLossId prpLpersonLossId) throws Exception {
		logger.info("查询人员赔付信息编号为" + prpLpersonLossId + "的人员赔付信息");
		return super.get(PrpLpersonLoss.class, prpLpersonLossId);
	}

	@Override
	public Page findPrpLpersonLoss(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取人员赔付信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLpersonLoss> findPrpLpersonLoss(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 根据人员赔付信息编号查询出人员赔付信息
	 * @param certiNo ：传入的人员赔付信息编号
	 * @return 返回人员赔付信息
	 */
	public PrpLpersonLoss findPrpLpersonLoss(String certiNo) throws Exception {
		PrpLpersonLoss prpLpersonLoss = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLpersonLoss> resultList = super.find(queryRule);
		if (resultList != null && resultList.size() > 0) {
			prpLpersonLoss = resultList.get(0);
		}
		return prpLpersonLoss;
	}

	@Override
	public void deleteByCompensateNo(String compensateNo) throws Exception {
		String sql = " DELETE FROM prpLpersonLoss Where compensateNo = '" + StringUtils.rightTrim(compensateNo) + "'";
		super.getSession().createSQLQuery(sql).executeUpdate();
	}
	public List<PrpLpersonLoss>findByConditions(String conditions)throws Exception{
		QueryRule queryRule = QueryRule.getInstance().addSql(conditions);
		return super.find(queryRule);
	}
	/**查询历史赔付人员信息
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public List<PrpLpersonLoss>findPersonHistory(String claimNo)throws Exception{
		String conditions = "compensateNo in (select compensateNo from prpLcompensate where claimNo='"+claimNo+"') order by compensateNo,personNo";
		QueryRule queryRule = QueryRule.getInstance().addSql(conditions);
		return super.find(queryRule);
	}
	
	/**
	 * 查询人员的历史赔付信息
	 * @param policyNo
	 * @param identifyNumber
	 * @return
	 * @throws Exception
	 */
	public List<PrpLpersonLoss>findPersonHisPaid(String policyNo,String identifyNumber)throws Exception{
		String sql = "select p.compensateNo,p.personno,sum(sumRealPay) from prplpersonloss p,prplcompensate c  where p.policyno='"+policyNo
		+"' and p.identifyNumber='"+identifyNumber+"' and p.compensateNo=c.compensateNo and c.underwriteflag in ('1','3') group by p.compensateNo,p.personno";
		List list = HibernateUtils.findbySql(super.getSession(), sql);
		List<PrpLpersonLoss> personLossList = new ArrayList<PrpLpersonLoss>();
		if(list!=null&&list.size()>0){
			PrpLpersonLoss prpLpersonLoss = null;
			Object[] objs = null;
			for(int i=0;i<list.size();i++){
				objs = (Object[])list.get(i);
				prpLpersonLoss = new PrpLpersonLoss();
				prpLpersonLoss.getId().setCompensateNo(String.valueOf(objs[0]));
				prpLpersonLoss.setPersonNo(((Number)objs[1]).intValue());
				prpLpersonLoss.setSumRealPay(((Number)objs[2]).doubleValue());
				personLossList.add(prpLpersonLoss);
			}
		}
		return personLossList;
		
	}

}
