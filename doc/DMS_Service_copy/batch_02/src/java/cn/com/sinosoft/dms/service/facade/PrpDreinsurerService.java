package cn.com.sinosoft.dms.service.facade;

import ins.framework.common.Page;
import cn.com.sinosoft.dms.model.PrpDreinsurer;

public interface PrpDreinsurerService {

	public Page PrpDreinsurerList(PrpDreinsurer prpDreinsurer, int pageNo, int pageSize);

	public void insertPrpDreinsurer(PrpDreinsurer prpDreinsurer, String userCode);

	public PrpDreinsurer findByPrimaryKey(String reinsCode);

	public void updatePrpDreinsurer(PrpDreinsurer prpDreinsurer, String userCode);

	public void prpdReinsurerMessageProcess(PrpDreinsurer prpDreinsurer) throws Exception;

}
