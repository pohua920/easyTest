package com.sinosoft.sys.platform.company.web;

import ins.framework.web.Struts2Action;

import java.util.Date;

import com.sinosoft.sys.platform.company.service.facade.PwdService;
import com.sinosoft.sys.platform.power.model.SaaUser;


public class PwdAction extends Struts2Action {
	private PwdService pwdService;

	private SaaUser prpDuser;

	/* private String isNullFlag; */

	private static final long serialVersionUID = 1L;

	/* private static final int INITPAGESIZE = 10; */

	private String userCode;

	public SaaUser getPrpDuser() {
		return prpDuser;
	}

	public void setPrpDuser(SaaUser prpDuser) {
		this.prpDuser = prpDuser;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public PwdService getPwdService() {
		return pwdService;
	}

	public void setPwdService(PwdService pwdService) {
		this.pwdService = pwdService;
	}

	/*
	 * public String getIsNullFlag() { return isNullFlag; }
	 * 
	 * public void setIsNullFlag(String isNullFlag) { this.isNullFlag =
	 * isNullFlag; }
	 */

	/** *****************Action 开始 ********************************** */
	/*
	 * public String user() { return SUCCESS; }
	 * 
	 * public String edit() { if ("edit".equals(type)) { prpDuser =
	 * userService.getUserByUserCode(userCode); } return SUCCESS; }
	 */

	// 修改
	public String initPwd() {
		logger.debug("initPwd");
		prpDuser = pwdService.findPwdByUserCode(userCode);
		// pwdService.updatePwd(prpDuser);
		// prpDuser.setPasswdSetDate(new Date()); 需要放在update之前才是保存
		return SUCCESS;
	}

	public String editPwd() {
		logger.debug("editPwd");
		SaaUser prpDuser_ori = pwdService.findPwdByUserCode(prpDuser.getUserCode());
		prpDuser_ori.setPassword(prpDuser.getPassword());
		prpDuser_ori.setPasswdSetDate(new Date());
		pwdService.updatePwd(prpDuser_ori);
		return SUCCESS;
	}
}
