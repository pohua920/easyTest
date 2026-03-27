package cn.com.sinosoft.saa.service.facade;

import ins.framework.common.Page;

import java.util.List;

import cn.com.sinosoft.saa.model.SaaUser;
import cn.com.sinosoft.saa.model.SaaUserInstead;

public interface SaaUserInsteadService {

	public void editUserInstead(SaaUserInstead saaUserInstead);
	public SaaUserInstead getInstance();
	public String checkUserInstead(String userCode);
	public SaaUserInstead getUserInsteadByUserCode(String userCode);
	public void updateUserInstead(SaaUserInstead saaUserInstead);

	public Page getUserList(SaaUser saaUser, int pageNo,
			int pageSize);

}

