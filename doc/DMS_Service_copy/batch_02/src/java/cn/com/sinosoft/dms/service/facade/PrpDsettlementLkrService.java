package cn.com.sinosoft.dms.service.facade;

import ins.framework.common.Page;
import cn.com.sinosoft.dms.model.PrpDsettlementLkr;

public interface PrpDsettlementLkrService {

	public Page PrpDsettlementLkrList(PrpDsettlementLkr prpDsettlementLkr, String userCode,int pageNo,
			int pageSize)throws Exception;

	public PrpDsettlementLkr findByPrimaryKey(String linkerCode);

	public void updatePrpDsettlementLkr(PrpDsettlementLkr prpDsettlementLkr,String userCode);

	public void insertPrpDsettlementLkr(PrpDsettlementLkr prpDsettlementLkr,String userCode);

	public void prpdSettlementLkrMessageProcess(PrpDsettlementLkr prpDsettlementLkr) throws Exception;
	
	//addPower用来限制查询结果（允许机构之内的数据）
	public  String addPower(String userCode) throws Exception;

}
