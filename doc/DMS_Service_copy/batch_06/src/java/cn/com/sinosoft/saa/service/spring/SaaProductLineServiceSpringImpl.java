package cn.com.sinosoft.saa.service.spring;

import java.util.List;

import ins.framework.dao.GenericDaoHibernate;
import cn.com.sinosoft.saa.model.SaaBusinessline;
import cn.com.sinosoft.saa.service.facade.SaaProductLineService;

public class SaaProductLineServiceSpringImpl extends GenericDaoHibernate<SaaBusinessline, String>
implements SaaProductLineService {

	public List<SaaBusinessline> findSaaProductLineList() {
		String hql = "select saaBusinessline from SaaBusinessline saaBusinessline";
		return super.findByHql(hql);
	}

}
