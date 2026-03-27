package com.sinosoft.claim.schema.service.spring;
/**
 * 共保信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpCcoins;
import com.sinosoft.claim.schema.model.PrpCcoinsId;
import com.sinosoft.claim.schema.service.facade.PrpCcoinsService;

public class PrpCcoinsServiceSpringImpl extends
GenericDaoHibernate<PrpCcoins, PrpCcoinsId> implements PrpCcoinsService{

	@Override
	public void save(PrpCcoins PrpCcoins) throws Exception {
		logger.info("保存共保信息信息");
		super.save(PrpCcoins);
		
	}

	@Override
	public void save(List<PrpCcoins> list) throws Exception {
		logger.info("保存共保信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpCcoinsId PrpCcoinsId) throws Exception {
		logger.info("删除共保信息编号为" + PrpCcoinsId + "的共保信息");
		super.deleteByPK(PrpCcoins.class, PrpCcoinsId);
	}

	@Override
	public PrpCcoins findPrpCcoins(PrpCcoinsId PrpCcoinsId) throws Exception {
		logger.info("查询共保信息编号为" + PrpCcoinsId + "的共保信息");
		return super.get(PrpCcoins.class, PrpCcoinsId);
	}

	@Override
	public Page findPrpCcoins(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取共保信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpCcoins> findPrpCcoins(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据人伤跟踪编号查询出共保信息
	 * @param certiNo ：传入的人伤跟踪编号
	 * @return 返回人伤跟踪
	 */
	public PrpCcoins findPrpCcoins(String certiNo) throws Exception{
		PrpCcoins PrpCcoins = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpCcoins> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			PrpCcoins = resultList.get(0);
		}
		return PrpCcoins;
	}
	public List<PrpCcoins> findByConditionsChiefFlag(String conditions)throws Exception{
		if(conditions == null||"".equals(conditions)){
			conditions = "1=1";
		}
		String sql = "select * from PrpCcoins where "+conditions;
		List<?> list = HibernateUtils.findbySql(super.getSession(), sql,PrpCcoins.class);
		List<PrpCcoins> prpCcoinsList = new ArrayList<PrpCcoins>();
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			PrpCcoins prpCcoins = (PrpCcoins) iterator.next();
			prpCcoinsList.add(prpCcoins);
		}
		return prpCcoinsList;
	}
	public List<PrpCcoins> findByConditions(String conditions)throws Exception{
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule);
	}

}
