package cn.com.sinosoft.dms.service.facade;

import ins.framework.common.Page;
import cn.com.sinosoft.dms.model.PrpDnewCodeCom;
import cn.com.sinosoft.dms.model.PrpDnewCodeComId;

public interface PrpDcodeComService {

	public Page PrpDcodeComList(PrpDnewCodeCom prpDnewCodeCom, int pageNo, int pageSize);

	public void insertPrpDcodeCom(PrpDnewCodeCom prpDnewCodeCom, String userCode);

	public PrpDnewCodeCom findByPrimaryKey(PrpDnewCodeComId id);

	public void updatePrpDcodeCom(PrpDnewCodeCom prpDnewCodeCom, String userCode);

//	public void prpDcodeComMessageProcess(PrpDnewCodeCom prpDnewCodeCom) throws Exception;


}
