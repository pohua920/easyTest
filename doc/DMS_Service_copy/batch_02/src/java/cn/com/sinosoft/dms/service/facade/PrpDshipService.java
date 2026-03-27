package cn.com.sinosoft.dms.service.facade;

import ins.framework.common.Page;

import java.util.List;

import cn.com.sinosoft.dms.model.PrpDship;

public interface PrpDshipService {
	public Page getPrpDshipList(PrpDship prpDship, int pageNo, int pageSize);

    public PrpDship findByPrimaryKey(String shipCode);
    
    public PrpDship findByPrimaryKey1(String shipCode);

    public void updatePrpDship(PrpDship prpDship,String userCode);
    
    public void deletePrpDship(PrpDship prpDship);

	public void insertPrpDship(PrpDship prpDship,String userCode);
	
	public void deleteByPK(String PK);
	
	public void deleteAll(List list);
	
	public void prpdShipMessageProcess(PrpDship prpdShip)throws Exception;
}
