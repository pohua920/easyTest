package cn.com.sinosoft.dms.service.facade;

import ins.framework.common.Page;
import cn.com.sinosoft.dms.model.PrpDcodeRisk;
import cn.com.sinosoft.dms.model.PrpDcodeRiskId;

public interface PrpDcodeRiskService {

	public Page PrpDcodeRiskList(PrpDcodeRisk prpDcodeRisk, int pageNo, int pageSize);

	public PrpDcodeRisk findByPrimaryKey(PrpDcodeRiskId id);

	public void updatePrpDcodeRisk(PrpDcodeRisk prpDcodeRisk, String userCode);

	public void insertPrpDcodeRisk(PrpDcodeRisk prpDcodeRisk, String userCode);

	public void prpdCodeRiskMessageProcess(PrpDcodeRisk prpDcodeRisk) throws Exception;

}
