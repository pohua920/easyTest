package com.sinosoft.undwrt.common.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import com.sinosoft.platform.ui.model.UtiMenuShowMenuCommand;
import com.sinosoft.undwrt.common.service.facade.MenuService;

/**
 * 功能選單實現類.
 */
public class MenuServiceSpringImpl extends GenericDaoHibernate implements
		MenuService {

	/** 屬性功能選單展示Command. */
	UtiMenuShowMenuCommand command;

	/**
	 * 展示功能選單.
	 * 
	 * @param loginComCode
	 *            登錄的機構號
	 * @param userCode
	 *            人員工號
	 * @param loginGradeCodes
	 *            登錄的職位代碼
	 * @param loginSystemCode
	 *            登錄的系統代碼
	 * @param menuStyle
	 *            功能選單樣式
	 * @return 系統代碼
	 * @see com.sinosoft.undwrt.common.service.facade.MenuService#showMenu(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public String showMenu(String loginComCode, String userCode,
			String loginGradeCodes, String loginSystemCode, String menuStyle) {
		String value = "";
		try {
			command = new UtiMenuShowMenuCommand(loginComCode, userCode,
					loginGradeCodes, loginSystemCode, menuStyle);
			value = (String) command.execute();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return value;
	}

	/**
	 * 展示功能選單.
	 * 
	 * @param loginComCode
	 *            登錄的機構號
	 * @param userCode
	 *            人員工號
	 * @param loginGradeCodes
	 *            登錄的職位代碼
	 * @param loginSystemCode
	 *            登錄的系統代碼
	 * @param currentRiskCode
	 *            當前險種代碼
	 * @param menuStyle
	 *            功能選單樣式
	 * @return 系統代碼
	 * @see com.sinosoft.undwrt.common.service.facade.MenuService#showMenu(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public String showMenu(String loginComCode, String userCode,
			String loginGradeCodes, String loginSystemCode,
			String currentRiskCode, String menuStyle) {
		String value = "";
		try {
			command = new UtiMenuShowMenuCommand(loginComCode, userCode,
					loginGradeCodes, loginSystemCode, currentRiskCode,
					menuStyle);
			value = (String) command.execute();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return value;
	}
}
