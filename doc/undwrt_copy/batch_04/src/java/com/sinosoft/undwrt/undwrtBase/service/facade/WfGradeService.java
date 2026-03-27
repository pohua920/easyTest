package com.sinosoft.undwrt.undwrtBase.service.facade;

import ins.framework.common.QueryRule;

import java.util.Collection;
import java.util.List;

import com.sinosoft.sysframework.common.datatype.PageRecord;
import com.sinosoft.undwrt.undwrtBase.model.WfGrade;
import com.sinosoft.utility.database.DbPool;

/**
 * 定級信息接口.
 */
public interface WfGradeService {

	/**
	 * 獲取屬性定級代碼.
	 * 
	 * @param flowID
	 *            工作流號
	 * @return 屬性定級代碼的值
	 * @throws Exception
	 *             異常
	 */
	String getPreGradeCode(String flowID) throws Exception;

	/**
	 * 根據條件查詢定級信息.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的定級信息集合
	 * @throws Exception
	 *             異常
	 */
	public List<WfGrade> findByConditions(QueryRule queryRule) throws Exception;

	/**
	 * 查詢符合條件的記錄條數.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的記錄條數
	 * @throws Exception
	 *             異常
	 */
	public int getCount(QueryRule queryRule) throws Exception;

	/**
	 * 保存定級信息.
	 * 
	 * @param wfLogService
	 *            工作流日誌接口
	 * @param iFlowID
	 *            工作流號
	 * @param iModelNo
	 *            模板號
	 * @param iNodeNo
	 *            節點號
	 * @param iBusinessType
	 *            業務類型
	 * @param iBusinessNo
	 *            業務號
	 * @param iUserCode
	 *            員工代碼
	 * @param iOpertorCode
	 *            操作員代碼
	 * @param iGradeCode
	 *            業務級別代碼
	 * @param iGradeValue
	 *            業務級別分值
	 * @param iMaxUsableRate
	 *            最大可用費用率
	 * @param iBrokerRate
	 *            經紀人傭金率
	 * @param iAgentRate
	 *            代理手續費用率
	 * @param iOrgRate
	 *            營銷組織利益率
	 * @param iBreakevenRate
	 *            盈虧平衡點利率
	 * @param iExtRate1
	 *            交換率1
	 * @param iExtRate2
	 *            交換率2
	 * @param iExtRate3
	 *            交換率3
	 * @throws Exception
	 *             異常
	 */
	public void saveWfGrade(WfLogService wfLogService, String iFlowID,
			int iModelNo, int iNodeNo, String iBusinessType,
			String iBusinessNo, String iUserCode, String iOpertorCode,
			String iGradeCode, String iGradeValue, String iMaxUsableRate,
			String iBrokerRate, String iAgentRate, String iOrgRate,
			String iBreakevenRate, String iExtRate1, String iExtRate2,
			String iExtRate3) throws Exception;

	/**
	 * 获取自动定级信息.
	 * 
	 * @param iFlowId
	 *            工作流號
	 * @param iLogNo
	 *            序號
	 * @param iModelNo
	 *            模板號
	 * @param iNodeNo
	 *            節點號
	 * @param iOperatorCode
	 *            操作員代碼
	 * @param iOperatorName
	 *            操作員名稱
	 * @param iBusinessType
	 *            業務類型
	 * @param iBusinessNo
	 *            業務號
	 * @return WfGradeDto 定級信息類
	 * @throws Exception
	 *             異常
	 */
	public WfGrade getAutoGrade(String iFlowId, int iLogNo, int iModelNo,
			int iNodeNo, String iOperatorCode, String iOperatorName,
			String iBusinessType, String iBusinessNo) throws Exception;

	/**
	 * 获取手工定级信息.
	 * 
	 * @param iFlowId
	 *            工作流號
	 * @param iLogNo
	 *            序號
	 * @param iModelNo
	 *            模板號
	 * @param iNodeNo
	 *            節點號
	 * @param iOperatorCode
	 *            操作員代碼
	 * @param iOperatorName
	 *            操作員名稱
	 * @param iBusinessType
	 *            業務類型
	 * @param iBusinessNo
	 *            業務號
	 * @param iGradeCode
	 *            業務級別代碼
	 * @param iGradeValue
	 *            業務級別分值
	 * @param iMaxUsableRate
	 *            最大可用費用率
	 * @param iBrokerRate
	 *            經紀人傭金率
	 * @param iAgentRate
	 *            代理手續費用率
	 * @param iOrgRate
	 *            營銷組織利益率
	 * @param iBreakevenRate
	 *            盈虧平衡點利率
	 * @param iExtRate1
	 *            交換率1
	 * @param iExtRate2
	 *            交換率2
	 * @param iExtRate3
	 *            交換率3
	 * @return WfGradeDto 定級信息類
	 * @throws Exception
	 *             異常
	 */
	public WfGrade getManualGrade(String iFlowId, int iLogNo, int iModelNo,
			int iNodeNo, String iOperatorCode, String iOperatorName,
			String iBusinessType, String iBusinessNo, String iGradeCode,
			String iGradeValue, String iMaxUsableRate, String iBrokerRate,
			String iAgentRate, String iOrgRate, String iBreakevenRate,
			String iExtRate1, String iExtRate2, String iExtRate3)
			throws Exception;

	/**
	 * 核保核批通過的對定級信息的後續處理.
	 * 
	 * @param iBusinessType
	 *            業務類型
	 * @param iBusinessNo
	 *            業務號
	 * @param iClassCode
	 *            險類代碼
	 * @param strRiskCode
	 *            險種
	 * @param iFlowId
	 *            工作流號
	 * @throws Exception
	 *             異常
	 */
	public void echoGrade(String iBusinessType, String iBusinessNo,
			String iClassCode, String strRiskCode, String iFlowId)
			throws Exception;

	/**
	 * 根據條件查找定級信息.
	 * 
	 * @param dbpool
	 *            數據管理對象
	 * @param conditions
	 *            條件
	 * @return 滿足查詢條件的集合
	 * @throws Exception
	 *             異常
	 */
	public Collection findByConditions(DbPool dbpool, String conditions)
			throws Exception;

	/**
	 * 根據條件查找定級信息.
	 * 
	 * @param queryRule
	 *            業務規則
	 * @return 滿足查詢條件的集合
	 * @throws Exception
	 *             異常
	 */
	// add by wangjun 20130307
	public List findListByQueryRule(QueryRule queryRule) throws Exception;

	/**
	 * 保存定級信息.
	 * 
	 * @param flowID
	 *            工作流號
	 * @param modelNo
	 *            模板號
	 * @param nodeNo
	 *            節點號
	 * @param certiType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param userCode
	 *            員工代碼
	 * @param strOperatorCode
	 *            操作員代碼
	 * @param strGradeCode
	 *            業務級別代碼
	 * @param strGradeValue
	 *            業務級別分值
	 * @param strMaxUsableRate
	 *            最大可用費用率
	 * @param strBrokerRate
	 *            經紀人傭金率
	 * @param strAgentRate
	 *            代理手續費用率
	 * @param strOrgRate
	 *            營銷組織利益率
	 * @param strBreakevenRate
	 *            盈虧平衡點利率
	 * @param strExtRate1
	 *            交換率1
	 * @param strExtRate2
	 *            交換率2
	 * @param strExtRate3
	 *            交換率3
	 */
	void saveWfGrade(String flowID, int modelNo, int nodeNo, String certiType,
			String businessNo, String userCode, String strOperatorCode,
			String strGradeCode, String strGradeValue, String strMaxUsableRate,
			String strBrokerRate, String strAgentRate, String strOrgRate,
			String strBreakevenRate, String strExtRate1, String strExtRate2,
			String strExtRate3);

}
