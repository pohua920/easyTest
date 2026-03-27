package cn.com.sinosoft.dms.service.facade;

import ins.framework.common.Page;
import cn.com.sinosoft.dms.model.PrpDcoins;
import cn.com.sinosoft.dms.model.PrpDcoinsId;

public interface PrpDcoinsService {

	public  Page PrpDcoinsList(PrpDcoins prpDcoins, String userCode,int pageNo, int pageSize)throws Exception;

	public PrpDcoins findByPrimaryKey(PrpDcoinsId prpDcoinsId);

	public void updatePrpDcoins(PrpDcoins prpDcoins, String userCode);

	public void insertPrpDcoins(PrpDcoins prpDcoins, String userCode);

	public void prpdCoinsMessageProcess(PrpDcoins prpDcoins) throws Exception;
	
	//addPower用来限制查询结果（允许机构之内的数据）
	public  String addPower(String userCode) throws Exception;
}
