/*
 * @(#)BLConfigAction.java	Feb 21, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.undwrt.service.facade;

import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @Author  中科软
 * @Date    <Feb 21, 2013>
 * @description 
 */
public interface ConfigUndwrtService {
	
	/**
	 *执行工作流系统发出的sql语句(针对简单描述和SQL描述)
	 *@param iBusinessNo 业务号码
	 *@param iStrSQL     路径条件拼成的SQL语句
	 *@return 执行结果(TRUE:成功/FALSE:失败)
	 *@throws UserException,Exception
	 */
	public boolean executeSql(String iBusinessNo,String iStrSQL) throws Exception;
	
	/**
	 *执行工作流系统发出的高级条件消息语句(针对高级条件)
	 *为了简化，目前的高级条件设置没有弄成反射的方式，而是沿袭了以前的方式，采用直接写方法名
	 *@param iBusinessNo 业务号码
	 *@param iFuncName   高级条件接口名称
	 *@return 执行结果(TRUE:成功/FALSE:失败)
	 *@throws UserException,Exception
	 */
	public boolean executeFunc(String iBusinessNo,int iModelNo,int iNodeNo,String iFuncNameAndBusinessType,String userCode) throws Exception;

}
