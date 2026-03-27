package com.sinosoft.claim.common.service.facade;


public interface MenuService {
	
	public String showMenu(String loginComCode,String userCode,String loginGradeCodes,String loginSystemCode,String menuStyle);
	public String showMenu(String loginComCode,String userCode,String loginGradeCodes,String loginSystemCode,String currentRiskCode,String menuStyle);

}
