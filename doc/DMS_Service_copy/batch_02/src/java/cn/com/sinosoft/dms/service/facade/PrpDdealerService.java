package cn.com.sinosoft.dms.service.facade;

import ins.framework.common.Page;

import java.util.List;

import cn.com.sinosoft.dms.model.PrpDdealer;

public interface PrpDdealerService {
	public Page getPrpDdealerList(PrpDdealer prpDdealer, String userCode,int pageNo, int pageSize)throws Exception;

    public PrpDdealer findByPrimaryKey(String dealerCode);

    public PrpDdealer findByPrimaryKey1(String dealerCode);
    
    public void updatePrpDdealer(PrpDdealer PrpDdealer,String userCode);
    
    public void deletePrpDdealer(PrpDdealer PrpDdealer);

	public void insertPrpDdealer(PrpDdealer PrpDdealer,String userCode);
	
	public void deleteByPK(String PK);
	
	public void deleteAll(List list);
	
	public void prpDdealerMessageProcess(PrpDdealer prpDdealer)throws Exception;
	
	public  String addPower(String userCode) throws Exception;
}
