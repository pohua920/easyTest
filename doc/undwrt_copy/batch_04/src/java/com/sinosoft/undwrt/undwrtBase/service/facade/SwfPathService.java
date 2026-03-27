package com.sinosoft.undwrt.undwrtBase.service.facade;

import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.undwrt.undwrtBase.model.SwfPath;
import com.sinosoft.undwrt.undwrtBase.model.WfLog;

// TODO: Auto-generated Javadoc
/**
 * 工作流路徑定義接口類.
 */
public interface SwfPathService {

	/**
	 * 取得以某節點爲起始節點的所有滿足條件且優先級最高的路徑.
	 * 
	 * @param iModelNo
	 *            模板號
	 * @param iStartNodeNo
	 *            起始節點號
	 * @param iBusinessType
	 *            業務類型
	 * @param iBusinessNo
	 *            業務號
	 * @param iDefaultFlag
	 *            是否缺省值--*0:否 ':是
	 * @param iComCode
	 *            機構代碼
	 * @return 滿足條件的路徑類集合
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             the 異常
	 */
	public List<SwfPath> getPathes(int iModelNo, int iStartNodeNo, String iBusinessType, String iBusinessNo, String iDefaultFlag, String iComCode)
			throws UserException, Exception;

	/**
	 * 取得以某節點爲起始節點的所有滿足條件且優先級最高的路徑.
	 * 
	 * @param iModelNo
	 *            模板號
	 * @param iStartNodeNo
	 *            起始節點號
	 * @param endNodeNo
	 *            結束節點號
	 * @param iBusinessType
	 *            業務類型
	 * @param iBusinessNo
	 *            業務號
	 * @param iDefaultFlag
	 *            是否缺省值--*0:否 ':是
	 * @param iComCode
	 *            機構代碼
	 * @return 滿足條件的路徑類集合
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             the 異常
	 */
	public List<SwfPath> getPathes(int iModelNo, int iStartNodeNo, int endNodeNo, String iBusinessType, String iBusinessNo, String iDefaultFlag, String iComCode)
			throws UserException, Exception;

	/**
	 * 取得以某節點爲起始節點的所有滿足條件的路徑.
	 * 
	 * @param iModelNo
	 *            模板號
	 * @param iStartNodeNo
	 *            起始節點號
	 * @param iBusinessType
	 *            業務類型
	 * @param iBusinessNo
	 *            業務號
	 * @param iDefaultFlag
	 *            是否缺省值--*0:否 ':是
	 * @param iComCode
	 *            機構代碼
	 * @param batchFlag
	 *            標志
	 * @return 滿足條件的路徑類集合
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             the 異常
	 */
	public List<SwfPath> getPathes(int iModelNo, int iStartNodeNo, String iBusinessType, String iBusinessNo, String iDefaultFlag, String iComCode,
			String batchFlag) throws UserException, Exception;

	/**
	 * 取得以某節點爲起始節點的滿足條件的路徑.
	 * 
	 * @param wfLog
	 *            日誌工作流類
	 * @return 滿足條件的路徑
	 * @throws Exception
	 *             異常
	 */
	public SwfPath getPassPath(WfLog wfLog) throws Exception;

	/**
	 * 取得以某節點爲起始節點的所有滿足條件的路徑.
	 * 
	 * @param wfLog
	 *            日誌工作流類
	 * @return 滿足條件的路徑的集合
	 * @throws UserException
	 *             用戶自定義異常
	 * @throws Exception
	 *             異常
	 */
	public List<SwfPath> getPathes(WfLog wfLog) throws UserException, Exception;

	// 根据条件查询路径列表
	/**
	 * 取得以某節點爲起始節點的所有滿足條件的路徑.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的路徑的集合
	 * @throws UserException
	 *             用戶自定義異常
	 * @throws Exception
	 *             異常
	 */
	public List<SwfPath> getPathesByQueryRule(QueryRule queryRule) throws UserException, Exception;

	/**
	 * 取得以某節點爲起始節點的所有滿足條件的路徑.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的路徑的集合
	 */
	public List<SwfPath> getSwfPathList(QueryRule queryRule);
}
