package cn.com.sinosoft.dms.service.facade;

import ins.framework.common.Page;

import cn.com.sinosoft.dms.model.PrpDtype;
import cn.com.sinosoft.dms.model.PrpVersion;

public interface PrpVersionService {

	//获得所有申请
	public Page getPrpVersionList(PrpVersion prpVersion, int pageNo,
			int pageSize);	
	
    public PrpVersion findByPrimaryKey(String productId);
}
