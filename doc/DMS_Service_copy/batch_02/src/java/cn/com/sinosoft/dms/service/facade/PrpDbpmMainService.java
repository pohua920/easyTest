package cn.com.sinosoft.dms.service.facade;

import ins.framework.common.Page;
import cn.com.sinosoft.dms.model.PrpdbpmMain;

public interface PrpDbpmMainService {

	public Page prpdbpmMainList(PrpdbpmMain prpdbpmMain,int pageNo, int pageSize);

	public PrpdbpmMain findByPrimaryKey(String id);
	
	public PrpdbpmMain findByPropertyName(String PropertyName,String propertyValue);
	
	public void updatePrpdbpmMain(PrpdbpmMain prpdbpmMain);
	
	public void passPrpdbpmMain(String id);

}
