package com.sinosoft.sys.platform.company.service.spring;

import com.sinosoft.app.common.util.MD5;
import com.sinosoft.sys.platform.company.service.facade.PwdService;
import com.sinosoft.sys.platform.power.model.SaaUser;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
 

public class PwdServiceSpringImpl extends
		GenericDaoHibernate<SaaUser, String> implements PwdService {

	public void updatePwd(SaaUser saaUser) {
		String md5PassWord = MD5.MD5Encode(saaUser.getPassword());
		saaUser.setPassword(md5PassWord);
		saaUser.setValidStatus("1");
		super.update(saaUser);
	}
	
	@SuppressWarnings("deprecation")
	public SaaUser findPwdByUserCode(String userCode) {
		SaaUser saaUser;
		QueryRule queryRule = QueryRule.getInstance();// QueryRule¿‡ µ¿˝
		queryRule.addEqual("userCode", userCode);
		saaUser = super.findUnique(queryRule);		
		return saaUser;
	}

}
