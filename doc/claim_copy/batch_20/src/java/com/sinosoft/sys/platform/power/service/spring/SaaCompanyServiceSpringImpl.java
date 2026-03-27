package com.sinosoft.sys.platform.power.service.spring;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import com.sinosoft.app.common.util.TimeUtil;
import com.sinosoft.sys.platform.power.model.SaaCompany;
import com.sinosoft.sys.platform.power.service.facade.SaaCompanyService;
import com.sinosoft.sys.platform.power.service.facade.SaaPowerHelpService;

public class SaaCompanyServiceSpringImpl extends GenericDaoHibernate<SaaCompany,String> implements SaaCompanyService {
	/**
	 * 通过状态获得公司 
	 * @author 中科软
	 */
	public List<SaaCompany> getComByValidstatus(String Validstatus){
		//String hql = "select comCName,comCode from PrpDcompany where validstatus = ?";
		QueryRule rule = QueryRule.getInstance();
		rule.addEqual("validStatus", Validstatus);
		return this.find(rule);
	}
	
	public Page findCompany(QueryRule queryRule, int pageNo, int pageSize){
		return super.find(queryRule, pageNo, pageSize);
	}
	
	/**
	 * 得到当前机构名称
	 * @param comCname
	 * @return
	 */
	@Override
	public String getComCname(String comCode) {
		//String hql = "select comCName from PrpDcompany where comCode = ?";
		String hql = "select comCName from 	SaaCompany where comCode = ?";

		List list=this.findByHql(hql, comCode);
		if(list.size()>0){
		    return list.get(0).toString();
		}
		return null;
	}
}
