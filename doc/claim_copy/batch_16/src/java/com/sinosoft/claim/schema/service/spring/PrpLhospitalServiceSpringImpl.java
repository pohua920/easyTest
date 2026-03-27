package com.sinosoft.claim.schema.service.spring;

/**
 * 车辆驾驶员关系接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLhospital;
import com.sinosoft.claim.schema.service.facade.PrpLhospitalService;

public class PrpLhospitalServiceSpringImpl extends GenericDaoHibernate<PrpLhospital, String> implements PrpLhospitalService {

	public void save(PrpLhospital prpLhospital) throws Exception {
		logger.info("保存医院信息");
		super.save(prpLhospital);
	}

	public void save(List<PrpLhospital> list) throws Exception {
		logger.info("保存医院信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	public void delete(String hospitalCode) throws Exception {
		super.deleteByPK(PrpLhospital.class, hospitalCode);
	}

	public PrpLhospital findPrpLhospital(String hospitalCode) throws Exception {
		return super.get(PrpLhospital.class, hospitalCode);
	}

	public Page findPrpLhospital(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		return super.find(queryRule, pageNo, pageSize);
	}
	/**
	 * @param hospitalCode
	 * @param hospitalName
	 * @return
	 * @throws Exception
	 * 更具名称和编码查询医院的条数,模糊查询
	 */
	public long findCount(String hospitalCode,String hospitalName)throws Exception{
		StringBuffer hql = new StringBuffer("select count(1) from PrpLhospital where 1=1");
		if(hospitalCode!=null&&!"".equals(hospitalCode)){
			hql.append(" and hospitalCode like '"+hospitalCode+"%'");
		}
		if(hospitalName!=null&&!"".equals(hospitalName)){
			hql.append(" and hospitalName like '"+hospitalName+"%'");
		}
		return super.getCount(hql.toString());
	}
	/**
	 * @param hospitalCode
	 * @param hospitalName
	 * @return
	 * @throws Exception
	 * 更具名称和编码查询医院的条数，精确查询
	 */
	public long getCount(String hospitalCode,String hospitalName)throws Exception{
		StringBuffer hql = new StringBuffer("select count(1) from PrpLhospital where 1=1");
		if(hospitalCode!=null&&!"".equals(hospitalCode)){
			hql.append(" and hospitalCode='"+hospitalCode+"'");
		}
		if(hospitalName!=null&&!"".equals(hospitalName)){
			hql.append(" and hospitalName='"+hospitalName+"'");
		}
		return super.getCount(hql.toString());
	}
	/**
	 * @param hospitalCode
	 * @param hospitalName
	 * @return
	 * @throws Exception
	 * 更具名称和编码查询医院
	 */
	public Page findPrpLhospital(String hospitalCode,String hospitalName, int pageNo, int pageSize) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		if(hospitalCode!=null&&!"".equals(hospitalCode)){
			queryRule.addLike("hospitalCode", hospitalCode+"%");
		}
		if(hospitalName!=null&&!"".equals(hospitalName)){
			queryRule.addLike("hospitalName", hospitalName+"%");
		}
		queryRule.addAscOrder("hospitalCode");
		return super.find(queryRule, pageNo, pageSize);
	}

	public List<PrpLhospital> findPrpLhospital(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
}
