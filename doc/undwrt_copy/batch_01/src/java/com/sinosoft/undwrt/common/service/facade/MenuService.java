package com.sinosoft.undwrt.common.service.facade;

// TODO: Auto-generated Javadoc
/**
 * 功能選單接口類.
 */
public interface MenuService {

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
	 */
	public String showMenu(String loginComCode, String userCode,
			String loginGradeCodes, String loginSystemCode, String menuStyle);

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
	 */
	public String showMenu(String loginComCode, String userCode,
			String loginGradeCodes, String loginSystemCode,
			String currentRiskCode, String menuStyle);

}
