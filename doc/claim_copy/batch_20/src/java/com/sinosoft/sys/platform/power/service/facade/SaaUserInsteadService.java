package com.sinosoft.sys.platform.power.service.facade;

import com.sinosoft.sys.platform.power.model.SaaUser;
import com.sinosoft.sys.platform.power.model.SaaUserInstead;

import ins.framework.common.Page;

public interface SaaUserInsteadService {

	public void editUserInstead(SaaUserInstead saaUserInstead);
	public SaaUserInstead getInstance();
	public String checkUserInstead(String userCode);
	public SaaUserInstead getUserInsteadByUserCode(String userCode);
	public void updateUserInstead(SaaUserInstead saaUserInstead);

	public Page getUserList(SaaUser saaUser, int pageNo,
			int pageSize);

}

