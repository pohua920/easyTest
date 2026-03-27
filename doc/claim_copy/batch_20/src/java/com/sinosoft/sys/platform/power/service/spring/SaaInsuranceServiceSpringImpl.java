package com.sinosoft.sys.platform.power.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.sys.platform.power.model.SaaRisk;
import com.sinosoft.sys.platform.power.service.facade.SaaInsuranceService;


public class SaaInsuranceServiceSpringImpl extends GenericDaoHibernate<SaaRisk, Long>
implements SaaInsuranceService{

	public List<SaaRisk> findSaaInsuranceList() {
		String hql = "from SaaRisk";
		return super.findByHql(hql);
	}

}
