package cn.com.sinosoft.dms.service.facade;

import ins.framework.common.Page;

import java.util.List;

import cn.com.sinosoft.dms.model.PrpDdriver;

public interface PrpDdriverService {
	public Page getPrpDdriverList(PrpDdriver prpDdriver, int pageNo, int pageSize);

    public PrpDdriver findByPrimaryKey(String drivingLicenseNo);
    

    public void updatePrpDdriver(PrpDdriver prpDdriver);
    
    public void deletePrpDdriver(PrpDdriver prpDdriver);

	public void insertPrpDdriver(PrpDdriver prpDdriver);
	
	public void deleteByPK(String PK);
	
	public void deleteAll(List list);
}
