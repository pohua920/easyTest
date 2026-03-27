package cn.com.sinosoft.dms.service.facade;

import ins.framework.common.Page;

import java.util.List;

import cn.com.sinosoft.dms.model.PrpDtype;

public interface PrpDtypeService {
	public Page getPrpDtypeList(PrpDtype prpDtype, int pageNo, int pageSize);

    public PrpDtype findByPrimaryKey(String prpDtype);
    
    public PrpDtype findByPrimaryKey1(String prpDtype);

    public void updatePrpDtype(PrpDtype prpDtype,String userCode);
    
    public void deletePrpDtype(PrpDtype prpDtype);

	public void insertPrpDtype(PrpDtype prpDtype,String userCode);
	
	public void deleteByPK(String PK);

	public PrpDtype get(String codeType);
	
	public void deleteAll(List list);
	
	public void prpdTypeMessageProcess(PrpDtype prpDtype)throws Exception;
}
