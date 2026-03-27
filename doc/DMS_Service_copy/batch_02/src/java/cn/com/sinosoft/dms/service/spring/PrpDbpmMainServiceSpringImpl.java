package cn.com.sinosoft.dms.service.spring;

import ins.framework.common.Page;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import cn.com.sinosoft.dms.model.PrpdRegulation;
import cn.com.sinosoft.dms.model.PrpdbpmMain;
import cn.com.sinosoft.dms.service.facade.PrpDbpmMainService;

public class PrpDbpmMainServiceSpringImpl extends
	GenericDaoHibernate<PrpdRegulation, String>implements PrpDbpmMainService{

	public void passPrpdbpmMain(String id) {
		
	}

	public PrpdbpmMain findByPrimaryKey(String id) {
		return null;
	}


	public Page prpdbpmMainList(PrpdbpmMain prpdbpmMain, int pageNo, int pageSize) {
		return null;
	}

	public void updatePrpdbpmMain(PrpdbpmMain prpdbpmMain) {
		// TODO Auto-generated method stub
		
	}

	public PrpdbpmMain findByPropertyName(String PropertyName, String propertyValue) {
		String hql = "select o from PrpdbpmMain o where o."+PropertyName+"=?";
		List<PrpdbpmMain> PrpdbpmMains = super.findByHql(hql,propertyValue);
		if(PrpdbpmMains.size()>0){
			return PrpdbpmMains.get(0);
		}
		return null;
	}
	
}
