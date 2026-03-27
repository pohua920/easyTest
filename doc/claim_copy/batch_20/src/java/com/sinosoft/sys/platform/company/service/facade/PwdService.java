package com.sinosoft.sys.platform.company.service.facade;

import com.sinosoft.sys.platform.power.model.SaaUser;

public interface PwdService {

	public void updatePwd(SaaUser prpDuser);
	
	public SaaUser findPwdByUserCode(String userCode);

}
