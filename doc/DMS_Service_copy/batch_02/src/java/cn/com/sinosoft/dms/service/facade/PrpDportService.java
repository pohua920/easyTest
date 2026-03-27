package cn.com.sinosoft.dms.service.facade;

import ins.framework.common.Page;

import java.util.List;

import cn.com.sinosoft.dms.model.PrpDport;

public interface PrpDportService {
	public Page getPrpDportList(PrpDport prpDport, int pageNo, int pageSize);

	public PrpDport findByPrimaryKey(String portCode);
	
	public PrpDport findByPrimaryKey1(String portCode);

	public void updatePrpDport(PrpDport prpDport,String userCode);

	public void deletePrpDport(PrpDport prpDport);

	public void insertPrpDport(PrpDport prpDport,String userCode);
	
	public void deleteByPK(String PK);
	
	public void deleteAll(List list);
	
	public void prpDportMessageProcess(PrpDport prpPort)throws Exception;
}
