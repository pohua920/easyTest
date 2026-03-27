package cn.com.sinosoft.saa.service.spring;

import java.util.List;

import ins.framework.dao.GenericDaoHibernate;
import cn.com.sinosoft.saa.model.SaaRisk;
import cn.com.sinosoft.saa.service.facade.SaaInsuranceService;

public class SaaInsuranceServiceSpringImpl extends GenericDaoHibernate<SaaRisk, Long>
implements SaaInsuranceService{

	public List<SaaRisk> findSaaInsuranceList() {
		String hql = "from SaaRisk";
		return super.findByHql(hql);
	}

}
