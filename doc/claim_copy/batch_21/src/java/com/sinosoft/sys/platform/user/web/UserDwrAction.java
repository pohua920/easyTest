package com.sinosoft.sys.platform.user.web;

import ins.platform.sui.ac.DwrCodeAction;
import ins.platform.sui.ac.DwrCodeActionInterface;
import ins.platform.sui.ac.vo.CodeCondition;

import java.util.List;

import com.sinosoft.sys.platform.user.service.facade.UserService;

public class UserDwrAction implements DwrCodeActionInterface {

	private UserService userService;

	public UserDwrAction() {
		DwrCodeAction.registCodeType("UserCodeTree", this);
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List findDwrCode(CodeCondition cond) {
		return userService.listUserCodeSelect(cond);
	}

	public Object[] findCodeByName(String codeType, Object name) {
		return null;
	}

	public Object[] findNameByCode(String codeType, Object code) {
		return null;
	}
}
