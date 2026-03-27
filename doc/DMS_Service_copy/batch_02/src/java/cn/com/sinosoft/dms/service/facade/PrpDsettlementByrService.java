package cn.com.sinosoft.dms.service.facade;

import ins.framework.common.Page;
import cn.com.sinosoft.dms.model.PrpDsettlementByr;

public interface PrpDsettlementByrService {

	public Page PrpDsettlementByrList(PrpDsettlementByr prpDsettlementByr, int pageNo,int pageSize);

	public PrpDsettlementByr findByPrimaryKey(String buyerUnitCode);

	public void updatePrpDsettlementByr(PrpDsettlementByr prpDsettlementByr,String userCode);

	public void insertPrpDsettlementByr(PrpDsettlementByr prpDsettlementByr,String userCode);

	public void prpdSettlementByrMessageProcess(
			PrpDsettlementByr prpDsettlementByr) throws Exception;

}
