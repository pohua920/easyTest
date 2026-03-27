package com.sinosoft.claim.common.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import com.sinosoft.platform.ui.model.UtiMenuShowMenuCommand;
import com.sinosoft.claim.common.service.facade.MenuService;

public class MenuServiceSpringImpl extends GenericDaoHibernate implements MenuService{
	UtiMenuShowMenuCommand command;
	public String showMenu(String loginComCode,String userCode,String loginGradeCodes,String loginSystemCode,String menuStyle){
		String value = "";
		try {
			command = new UtiMenuShowMenuCommand(loginComCode,userCode, loginGradeCodes,loginSystemCode,menuStyle);
			value = (String) command.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public String showMenu(String loginComCode,String userCode,String loginGradeCodes,String loginSystemCode,String currentRiskCode,String menuStyle){
		String value = "";
		try {
			command = new UtiMenuShowMenuCommand(loginComCode,userCode, loginGradeCodes,loginSystemCode,currentRiskCode,menuStyle);
			value = (String) command.execute();
		} catch (Exception e) {
		}
		return value;
	}
}
