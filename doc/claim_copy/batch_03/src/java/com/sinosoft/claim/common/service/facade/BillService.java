/**
 * <p>Title: BillService</p>
 * <p>Description:单号取号类 </p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: Sinosoft</p>
 * @author 中科软
 * @version 1.0
 */
package com.sinosoft.claim.common.service.facade;

import java.sql.SQLException;
import java.util.Map;
import java.util.Vector;

import com.sinosoft.sysframework.exceptionlog.UserException;

public interface BillService {
	/**
	 * 年度单号初始化（批量初始化）
	 * @param iRiskSchemas 险种代码数组
	 * @param iCompanySchemas 机构代码数组
	 * @param iTableSchemas 数据表数组
	 * @param iYear 4位年份
	 * @param iInitNo 初始化序号
	 * @return true/false 批量初始化成功/失败
	 * @throws SQLException,Exception,UserException
	 */
	public boolean batchInitNo(Vector<String> iRiskSchemas, Vector<String> iCompanySchemas, Vector<String> iTableSchemas, String iYear, String iInitNo) throws SQLException, Exception;

	/**
	 * 获取一个新号
	 * @param iTableName:单号数据表名
	 * @param iRiskCode:险种代码
	 * @param iComCode:出单部门
	 * @param iYear:业务年度
	 * @return 单号字符串
	 * @throws UserException
	 * @throws Exception
	 */
	public String getNo(String iTableName, String iRiskCode, String iComCode, int iYear) throws SQLException, Exception;
	
	/**
	 * 获取一个新号
	 * @param iTableName:单号数据表名
	 * @param iRiskCode:险种代码
	 * @param iComCode:出单部门
	 * @param iYear:业务年度
	 * @param damageCode:任意险出险原因	
	 * @return 单号字符串
	 * @throws UserException
	 * @throws Exception
	 */
	public String getNo(String iTableName, String iRiskCode, String iComCode, int iYear, String iSessionID,Map<String,Object>infoMap) throws SQLException, Exception;

	/**
	 * 拉长单号
	 * @param iTableName 数据表名
	 * @param iBillNo 单号
	 * @param iGroupNo 组号
	 * @param damageCode:任意险出险原因
	 * @return strBillNo 拉长後的单号
	 */
	public String pullNo(String iTableName, String iBillNo, String iGroupNo,String iRiskCode,Map<String,Object>infoMap) throws Exception;

	/**
	 * 单号检查
	 * @param iTableName 数据表
	 * @param iBillNo 单号
	 * @param iGroupNo 组号
	 * @param iCheckFlag 检查类型
	 * @param damageCode 任意险出险原因
	 * @return true/false 单号检查成功（合法）/单号检查失败（不合法）
	 * @throws Exception
	 */
	public boolean checkNo(String iTableName, String iBillNo, String iGroupNo, String iCheckFlag,String iRiskCode,Map<String,Object>infoMap) throws Exception;

	/**
	 * 放回新单号
	 * @param strTableName 数据表名
	 * @param strBillNo 单号
	 * @return true/false 放号成功/放号失败
	 * @throws Exception,SQLException,UserException
	 */
	public boolean putNo(String iTableName, String iBillNo,Map<String,Object> infoMap) throws SQLException, Exception;

	/**
	 * 成功後删除获取的单号
	 * @param strTableName 单号表名
	 * @param strBillNo 单证号
	 * @return true/false 删号成功/删号失败
	 * @throws Exception,SQLException,UserException
	 */
	public boolean deleteNo(String iTableName, String iBillNo,Map<String,Object> infoMap) throws SQLException, Exception;

	/**
	 * 手工占号
	 * @param iTableName 单号表名
	 * @param iBillNo 单证号
	 * @return true/false 占号成功/占号失败
	 * @throws UserException
	 * @throws Exception
	 */
	public boolean occupy(String iTableName, String iBillNo,Map<String,Object> infoMap) throws SQLException, Exception;

	/**
	 * 获取险种代码，交强，任意险种  AL-任意險/ BL-強制險/ TL-任意險失竊車
	 * @param riskCode 险种
	 * @param infoMap 条件包含保单号码，出险原因
	 * @return 返回单号险种
	 * @throws Exception
	 */
	public String getRiskCode(String riskCode, Map<String,Object>infoMap)throws Exception;

	/**
	 * 更具保单年份生成单号
	 * @param iTableName 表名
	 * @param iRiskCode 险别
	 * @param infoMap 条件，包含出险原因，保单号码
	 * @return 返回生成的单号
	 * @throws Exception
	 */
	public String getNoByPolciyYear(String iTableName, String iRiskCode, Map<String,Object>infoMap)throws Exception;
	/**生成立案号的组号
	 * @param policyNo
	 * @param iComCode
	 * @param iYear
	 * @param infoMap
	 * @return
	 * @throws Exception
	 */
	public String getPrpLclaimGroupNo(String policyNo, String iComCode, String iYear,Map<String,Object>infoMap)throws Exception;
}
