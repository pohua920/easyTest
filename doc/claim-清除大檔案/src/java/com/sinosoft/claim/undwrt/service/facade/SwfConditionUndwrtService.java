/*
 * @(#)BLSWfConditionAction.java	Feb 21, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.undwrt.service.facade;

import java.sql.SQLException;

import com.sinosoft.claim.schema.model.SwfCondition;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @Author <中科软>
 * @Date <Feb 21, 2013>
 * @description
 */
public interface SwfConditionUndwrtService {


	/**
	 * 构造函数
	 */

	/**
	 * 转换Dto
	 * @param wfConditionDto wfConditionDto
	 * @param mode 模式
	 * @throws Exception
	 */
	public void convertDto(SwfCondition swfCondition, String mode) throws Exception;

	/**
	 * 执行工作流系统发出的sql语句(针对简单描述和SQL描述)
	 * @param businessNo 业务号码
	 * @param wfConditionDto WfConditionDto
	 * @throws UserException
	 * @throws Exception
	 * @return boolean
	 */
	public boolean execute(String businessNo, int modelno, int nodeNo, SwfCondition swfCondition, String userCode) throws UserException, Exception;

	/**
	 * 删除路径下的所有条件
	 * @param modelNo int
	 * @param pathNo int
	 * @throws SQLException
	 * @throws Exception
	 * @author 中科软
	 */
	public void deleteAllCondition(int modelNo, int pathNo) throws Exception;

	/**
	 * 保存路径条件
	 * @param wfConditionDto WfConditionDto
	 * @throws SQLException
	 * @throws Exception
	 */
	public void saveWfCondition(SwfCondition swfCondition) throws Exception;

}
