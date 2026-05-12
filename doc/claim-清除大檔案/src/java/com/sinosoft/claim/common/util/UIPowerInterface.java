/*
 *
 *To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.sinosoft.claim.common.util;

import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.platform.dto.domain.PrpDuserDto;

/**
 * @author 中科软  To change the template for this generated type
 *         comment go to Window - Preferences - Java - Code Style - Code
 *         Templates
 */
public class UIPowerInterface {

	/**
	 * 添加新权限
	 * @param user 用户
	 * @param tableName 表名
	 * @param userCodeFields 用户字段
	 * @param comCodeFields 部门字段
	 * @return
	 * @throws Exception
	 */
	public String addPower(UserDto user, String tableName, String userCodeFields, String comCodeFields) throws Exception {
		PrpDuserDto userDto = user.convertToPlatFromPrpDuserDto();
		String condition = com.sinosoft.platform.ui.control.action.UIPowerAction.addPower(userDto, tableName, userCodeFields, comCodeFields);
		return condition;
	}

	/**
	 * 检查用户权限
	 * @param user 用户
	 * @param taskCode 任务
	 * @return
	 * @throws Exception
	 */
	public boolean checkPowerReturn(UserDto user, String taskCode) throws Exception {

		PrpDuserDto userDto = user.convertToPlatFromPrpDuserDto();
		boolean bln = com.sinosoft.platform.ui.control.action.UIPowerAction.checkPowerReturn(userDto, taskCode);
		return bln;

	}

	/**
	 * 检查
	 * @param user 用户
	 * @param dataUserCode 时间
	 * @param dataComCode 部门
	 * @throws Exception
	 */
	public void checkDataPower(UserDto user, String dataUserCode, String dataComCode) throws Exception {
		PrpDuserDto userDto = user.convertToPlatFromPrpDuserDto();
		com.sinosoft.platform.ui.control.action.UIPowerAction.checkDataPower(userDto, dataUserCode, dataUserCode);
	}

	/**
	 * 对险种限制
	 * @param user 用户
	 * @param tableName 表名
	 * @return
	 * @throws Exception
	 */
	public String addRiskPower(UserDto user, String tableName) throws Exception {
		PrpDuserDto userDto = user.convertToPlatFromPrpDuserDto();
		String condition = com.sinosoft.platform.ui.control.action.UIPowerAction.addRiskPower(userDto, tableName);
		return condition;
	}

	/**
	 * 对用户机构的限制
	 * @param user 用户
	 * @param tableName 表名
	 * @param userCodeFields 用户字段
	 * @param comCodeFields 部门字段
	 * @return
	 * @throws Exception
	 */
	public String addCustomerPower(UserDto user, String tableName, String userCodeFields, String comCodeFields) throws Exception {
		PrpDuserDto userDto = user.convertToPlatFromPrpDuserDto();
		String condition = com.sinosoft.platform.ui.control.action.UIPowerAction.addCustomerPower(userDto, tableName, userCodeFields, comCodeFields);
		return condition;
	}

}
