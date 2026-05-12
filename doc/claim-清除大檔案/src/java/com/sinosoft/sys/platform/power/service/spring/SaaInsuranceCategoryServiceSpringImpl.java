package com.sinosoft.sys.platform.power.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.sys.platform.power.model.SaaClass;
import com.sinosoft.sys.platform.power.service.facade.SaaInsuranceCategoryService;


public class SaaInsuranceCategoryServiceSpringImpl extends GenericDaoHibernate<SaaClass,String>
		implements SaaInsuranceCategoryService {

	public List<SaaClass> findSaaInsuranceCategoryList() {
		String hql = "from SaaClass";
		return super.findByHql(hql);
	}
	public SaaClass findSaaClassByRiskCode(String riskCode){
		String hql = "select cla from SaaClass cla where cla.classCode in (select risk.classCode from SaaRisk risk where risk.riskCode=?)";
		return (SaaClass)super.findByHql(hql,riskCode).get(0);
	}
}
