package com.sinosoft.undwrt.undwrtDeal.service.facade;

import java.sql.SQLException;

import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.DBManager;

// TODO: Auto-generated Javadoc
/**
 * 核保回寫數據服務接口類.
 */
public interface PrpFeedBackService {

	/**
	 * 回寫授權數據.
	 * 
	 * @param iBussinessType
	 *            業務類型
	 * @param iBussinessNo
	 *            業務號
	 * @return 返回授權信息
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public String echoAuthorizePrp(String iBussinessType, String iBussinessNo)
			throws UserException, Exception;

	/**
	 * 補錄保單單證狀態回寫（保單）.
	 * 
	 * @param dbManager
	 *            數據管理對象
	 * @param policyNo
	 *            保單號
	 * @throws Exception
	 *             異常
	 */
	public void echoVisa(DBManager dbManager, String policyNo) throws Exception;

	/**
	 * 回寫報價單信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param status
	 *            狀態
	 * @param underwriteCode
	 *            最終核保人代碼
	 * @param underwriteDate
	 *            核保完成日期
	 * @throws UserException
	 *             自定義異常
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public void echoQta(String businessNo, String status,
			String underwriteCode, DateTime underwriteDate)
			throws UserException, SQLException, Exception;

	/**
	 * 回寫相關數據信息.
	 * 
	 * @param dbManager
	 *            數據管理對象
	 * @param certiType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param status
	 *            狀態
	 * @param underWriteCode
	 *            最終核保人代碼
	 * @param underWriteDate
	 *            核保完成日期
	 * @param flag
	 *            核保標誌位
	 * @param businessSource
	 *            業務來源
	 * @return 成功返回true,失敗返回false
	 * @throws UserException
	 *             自定義異常
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public boolean echo(DBManager dbManager, char certiType, String businessNo,
			String status, String underWriteCode, DateTime underWriteDate,
			String flag, String businessSource) throws UserException,
			SQLException, Exception;

	/**
	 * 回寫相關數據信息.
	 * 
	 * @param dbManager
	 *            數據管理對象
	 * @param certiType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param status
	 *            狀態
	 * @param underWriteCode
	 *            最終核保人代碼
	 * @param underWriteDate
	 *            the 核保完成日期
	 * @param flag
	 *            核保標誌位
	 * @param businessSource
	 *            業務來源
	 * @param currendNodeNo
	 *            當前節點號
	 * @return 成功返回true,失敗返回false
	 * @throws UserException
	 *             自定義異常
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public boolean echo(DBManager dbManager, char certiType, String businessNo,
			String status, String underWriteCode, DateTime underWriteDate,
			String flag, String businessSource, int currendNodeNo)
			throws UserException, SQLException, Exception;

	/**
	 * 核保回寫業務入口方法(提交核保）.
	 * 
	 * @param certiType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param flag
	 *            核保標誌位
	 * @param businessSource
	 *            業務來源
	 * @return 成功返回true，失敗返回false
	 * @throws UserException
	 *             自定義異常
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public boolean echoSubmit(char certiType, String businessNo, String flag,
			String businessSource) throws UserException, SQLException,
			Exception;

	/**
	 * 雙核預審核回寫業務強三平台結果方法..
	 * 
	 * @param dbManager
	 *            數據管理對象
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public void echoCISubmit(DBManager dbManager, String businessNo,
			String businessType) throws UserException, Exception;

	/**
	 * 雙核預審核回寫業務強三平台結果方法.
	 * 
	 * @param dbManager
	 *            the db manager
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public void echoCISubmitJF(DBManager dbManager, String businessNo,
			String businessType) throws UserException, Exception;

	/**
	 * 核保回寫業務入口方法（核保通過/不通過後）.
	 * 
	 * @param dbManager
	 *            數據管理對象
	 * @param certiType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param status
	 *            狀態
	 * @param underWriteCode
	 *            最終核保人代碼
	 * @param underWriteDate
	 *            核保完成日期
	 * @param flag
	 *            核保標誌位
	 * @param businessSource
	 *            業務來源
	 * @param nodeNo
	 *            節點號
	 * @return 成功返回true，失敗返回false
	 * @throws UserException
	 *             自定義異常
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public boolean echoJF(DBManager dbManager, char certiType,
			String businessNo, String status, String underWriteCode,
			DateTime underWriteDate, String flag, String businessSource,
			int nodeNo) throws UserException, SQLException, Exception;

	/**
	 * 回寫要保書子信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @throws Exception
	 *             異常
	 */
	public void echoMainSub(String businessNo) throws Exception;
	
	/**
	 * 删除指定投保单号(批单号)，危险单位号的相关主信息和子信息.
	 * 
	 * @param businessType
	 *            业务类型
	 * @param businessNo
	 *            业务号
	 * @param dangerNo
	 *            危险单位号
	 * @throws Exception
	 *             the exception
	 */
	public void deletePrpDangerUnitAndItem(String businessType, String businessNo, String dangerNo) throws Exception;
	
	
	/**
	 * 調用承保的AML系統
	 * @param businessType
	 *        業務類型
	 * @param businessNo
	 *        業務號
	 * @throws Exception
	 * 
	 * @author xuhuiling
	 */
	public boolean callPrpinsAml(String businessType,String businessNo,String userCode)throws Exception;

}
