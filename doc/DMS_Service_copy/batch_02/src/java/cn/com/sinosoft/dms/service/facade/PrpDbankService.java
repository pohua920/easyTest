package cn.com.sinosoft.dms.service.facade;

import ins.framework.common.Page;

import java.util.List;

import cn.com.sinosoft.dms.model.PrpDbank;

public interface PrpDbankService {

    public Page getPrpDbankList(PrpDbank prpDbank, String userCode,int pageNo, int pageSize)throws Exception;

    public PrpDbank findByPrimaryKey(String strBankCode);
    
    public PrpDbank findByPrimaryKey1(String strBankCode);

    public void updatePrpDbank(PrpDbank prpDbank,String userCode);
    
    public void deletePrpDbank(PrpDbank prpDbank);

	public void insertPrpDbank(PrpDbank prpDbank,String userCode);
	
	public void deleteByPK(String PK);
	
	public void deleteAll(List list);
	
	public void prpdBankMessageProcess(PrpDbank prpDbank)throws Exception;
	
	//addPower用来限制查询结果（允许机构之内的数据）
	public  String addPower(String userCode) throws Exception;
}
