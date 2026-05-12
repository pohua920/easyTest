package com.sinosoft.claim.common.service.facade;

import com.sinosoft.claim.dto.custom.UserDto;
/**
 * 用户权限
 * @author 中科软
 *
 */
public interface PowerService {
	/**
	 * 添加用户的查询险种的权限。
	 * @param user 用户
	 * @param tableName 表名称
	 * @return
	 * @throws Exception
	 */
	public String addRiskPower(UserDto user,String tableName,String systemCode) throws Exception;
	/**
	 * 添加用户的查询险种的权限。
	 * @param userCode 用户名称
	 * @param comCode 登录机构
	 * @param gradeCodes 角色
	 * @param tableName 表名称
	 * @return
	 * @throws Exception
	 */
	public String addRiskPower(String userCode, String comCode, String gradeCodes, String tableName,String systemCode) throws Exception;
}
