package cn.com.sinosoft.dms.service.spring;

import ins.framework.dao.GenericDaoHibernate;
import cn.com.sinosoft.dms.model.PrpDcompanyCheck;
import cn.com.sinosoft.dms.model.PrpDcrossOrg;
import cn.com.sinosoft.dms.model.PrpDcrossOrgId;
import cn.com.sinosoft.dms.service.facade.PrpDcrossOrgService;

public class PrpDcrossOrgServiceSpringImpl extends
GenericDaoHibernate<PrpDcrossOrg, PrpDcrossOrgId>implements PrpDcrossOrgService {

	//交叉销售PrpDcrossOrg清分
	public void prpDcrossOrgDataMessageProcess(PrpDcrossOrg prpDcrossOrg)
	throws Exception {
		if (prpDcrossOrg != null) {
			try {
				super.save(prpDcrossOrg);				
			} catch (Exception e) {
				e.printStackTrace();
				throw new  Exception(e.getMessage());
			}
		}
	}
	
	//交叉销售PrpDcompanyCheck清分
	public void prpDcompanyCheckDataMessageProcess(PrpDcompanyCheck prpDcompanyCheck)
	throws Exception {
		if (prpDcompanyCheck != null) {
			try {
				super.save(prpDcompanyCheck);				
			} catch (Exception e) {
				e.printStackTrace();
				throw new  Exception(e.getMessage());
			}
		}
	}
}


