package cn.com.sinosoft.dms.service.facade;

import ins.framework.common.Page;

import java.util.List;

import cn.com.sinosoft.dms.model.PrpDcompanyCheck;
import cn.com.sinosoft.dms.model.PrpDcrossOrg;
import cn.com.sinosoft.dms.model.PrpDriskEngage;
import cn.com.sinosoft.dms.model.PrpDriskEngageId;

public interface PrpDcrossOrgService {
	
	//交叉销售PrpDcrossOrg清分
	public void prpDcrossOrgDataMessageProcess(PrpDcrossOrg prpDcrossOrg)throws Exception;
	
	//交叉销售PrpDcompanyCheck清分
	public void prpDcompanyCheckDataMessageProcess(PrpDcompanyCheck prpDcompanyCheck)throws Exception;
	
}
