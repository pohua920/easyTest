package cn.com.sinosoft.dms.service.facade;

import ins.framework.common.Page;

import java.util.List;

import cn.com.sinosoft.dms.model.PrpDplane;

public interface PrpDplaneService {
	public Page getPrpDplaneList(PrpDplane prpDplane, int pageNo, int pageSize);

    public PrpDplane findByPrimaryKey(String licenceNo);
    
    public PrpDplane findByPrimaryKey1(String licenceNo);

    public void updatePrpDplane(PrpDplane prpDplane,String userCode);
    
    public void deletePrpDplane(PrpDplane prpDplane);

	public void insertPrpDplane(PrpDplane prpDplane,String userCode);
	
	public void deleteByPK(String PK);
	
	public void delsteAll(List list);
	
	public void prpDplaneMessageProcess(PrpDplane prpDplane)throws Exception;
}
