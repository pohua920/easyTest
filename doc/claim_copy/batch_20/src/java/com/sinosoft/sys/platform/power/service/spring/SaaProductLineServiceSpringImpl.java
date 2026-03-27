package com.sinosoft.sys.platform.power.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.sys.platform.power.model.SaaBusinessline;
import com.sinosoft.sys.platform.power.service.facade.SaaProductLineService;


public class SaaProductLineServiceSpringImpl extends GenericDaoHibernate<SaaBusinessline, String>
implements SaaProductLineService {

	public List<SaaBusinessline> findSaaProductLineList() {
		String hql = "select saaBusinessline from SaaBusinessline saaBusinessline";
		return super.findByHql(hql);
	}

}
