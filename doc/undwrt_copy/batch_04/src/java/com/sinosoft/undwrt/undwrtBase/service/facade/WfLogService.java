package com.sinosoft.undwrt.undwrtBase.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpSession;

import com.sinosoft.prpall.dto.domain.PrpCmainDto;
import com.sinosoft.prpall.dto.domain.PrpTmainDto;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.undwrt.common.vo.WfLogVo;
import com.sinosoft.undwrt.undwrtBase.model.WfLog;

/**
 * 工作流日誌接口類.
 */
public interface WfLogService {

	/**
	 * 根據條件查詢工作流日誌.
	 * 
	 * @param conditions
	 *            條件
	 * @return 滿足條件的集合
	 * @throws Exception
	 *             異常
	 */
	public Collection findByConditions(String conditions) throws Exception;

	/**
	 * 根據條件查詢工作流日誌.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的集合
	 */
	public List<WfLog> findByQueryRuleList(QueryRule queryRule);

	/**
	 * 查詢滿足條件的記錄數.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的記錄數
	 */
	public int getCount(QueryRule queryRule);

	/**
	 * 根據條件查詢工作流日誌.
	 * 
	 * @param hql
	 *            查詢條件
	 * @return 滿足條件的集合
	 */
	public List<WfLog> findByHqlList(String hql);

	/**
	 * 根據條件查詢工作流日誌.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @param pageNo
	 *            頁碼
	 * @param pageSize
	 *            每頁的記錄條數
	 * @return page對象
	 */
	public Page findUserTaskList(QueryRule queryRule, int pageNo, int pageSize);

	/**
	 * 更新工作流日誌.
	 * 
	 * @param wfLog
	 *            工作流日誌對象
	 */
	public void update(WfLog wfLog);

	/**
	 * 保存工作流日誌.
	 * 
	 * @param wfLog
	 *            工作流日誌對象
	 */
	public void save(WfLog wfLog);

	/**
	 * 根據主鍵查找工作流日誌.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 工作流日誌對象
	 */
	public WfLog findByPrimaryKey(QueryRule queryRule);

	/**
	 * 根據條件查詢工作流日誌.
	 * 
	 * @param conditions
	 *            查詢條件
	 * @param pageNo
	 *            頁碼
	 * @param rowsPerPage
	 *            每頁的記錄條數
	 * @return 滿足條件的集合
	 * @throws Exception
	 *             異常
	 */
	public Collection findByConditions(String conditions, int pageNo, int rowsPerPage) throws Exception;

	/**
	 * 獲得合約號.
	 * 
	 * @param riskCategory
	 *            險種大類代碼
	 * @param businessNo
	 *            業務號
	 * @return 滿足查詢條件的合約號
	 * @throws Exception
	 *             異常
	 */
	public String getRelateContractNo(String riskCategory, String businessNo) throws Exception;

	/**
	 * 提交節點.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param userCode
	 *            用戶代碼
	 * @param iTaskCode
	 *            任務代碼
	 * @throws Exception
	 *             異常
	 * @throws UserException
	 *             自定義異常
	 */
	public void submitTaskQta(String businessNo, String userCode, String iTaskCode) throws Exception, UserException;

	/**
	 * 獲取回退節點列表.
	 * 
	 * @param FlowId
	 *            工作流號
	 * @param LogNo
	 *            序號
	 * @param nodeNo
	 *            節點號
	 * @return 回退節點的集合
	 * @throws Exception
	 *             異常
	 */
	public Collection getBackList(String FlowId, int LogNo, int nodeNo) throws Exception;

	/**
	 * 撤銷任務.
	 * 
	 * @param iFlowID
	 *            工作流號
	 * @param iLogNo
	 *            序號
	 * @return 成功返回true，失敗返回false
	 * @throws Exception
	 *             異常
	 */
	public boolean undo(String iFlowID, int iLogNo) throws Exception;

	/**
	 * 獲得組號列表.
	 * 
	 * @param conditions
	 *            查詢條件
	 * @return 滿足條件的集合
	 * @throws Exception
	 *             異常
	 */
	public Collection getPackageId(String conditions) throws Exception;

	/**
	 * 把wflog對象轉化為wflogvo對象.
	 * 
	 * @param sql
	 *            the sql
	 * @return 滿足條件的集合
	 * @throws Exception
	 *             異常
	 */
	public List<WfLogVo> findBySql(String sql) throws Exception;

	/**
	 * 獲取提交用戶列表.
	 * 
	 * @param modelNo
	 *            模板號
	 * @param nodeNo
	 *            節點號
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param flag
	 *            標誌
	 * @return 要提交的用戶列表集合
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public Collection getSubmitUserList(int modelNo, int nodeNo, String businessType, String businessNo, String flag) throws SQLException, Exception;

	/**
	 * 關閉工作流時把nodestatus置為“0”.
	 * 
	 * @param flowID
	 *            工作流號
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public void updateNodeStatusByFlowID(String flowID) throws SQLException, Exception;

	/**
	 * 檢查被保人曆史信息（曆史投保、曆史賠付）.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 被保人歷史信息
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public Vector checkHistoryInfo(String businessNo) throws SQLException, Exception;

	/**
	 * 獲得工作流查詢視圖.
	 * 
	 * @param sql
	 *            查詢的sql
	 * @return 工作流查詢視圖
	 * @throws Exception
	 *             異常
	 */
	public ArrayList getWorkFlowQueryView(String sql) throws Exception;

	/**
	 * 獲得工作流日誌表某工作流號所有記錄中的最大序號.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 最大序號
	 */
	public int getMaxLogNo(QueryRule queryRule);

	/**
	 * 交費計劃中的幣種信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 幣種信息
	 * @throws Exception
	 *             異常
	 */
	public Collection getPlanCurrencyType(String businessNo, String businessType) throws Exception;

	/**
	 * 自定義獲取標的主信息，標的地址，標的郵編.
	 * 
	 * @param proposalNo
	 *            要保單號
	 * @param riskCode
	 *            險種代碼
	 * @return the custom prp titem kind list
	 * @throws Exception
	 *             異常
	 */
	public Collection getCustomPrpTitemKindList(String proposalNo, String riskCode) throws Exception;

	/**
	 * 獲取用戶險別列表.
	 * 
	 * @param policyNo
	 *            保單號
	 * @param riskCode
	 *            險種代碼
	 * @return 滿足條件的集合
	 * @throws Exception
	 *             異常
	 */
	public Collection getCustomPrpCitemKindList(String policyNo, String riskCode) throws Exception;

	/**
	 * 獲取用戶險別列表.
	 * 
	 * @param endorseNo
	 *            批單號
	 * @param riskCode
	 *            險種代碼
	 * @return 滿足條件的列表集合
	 * @throws Exception
	 *             異常
	 */
	public Collection getCustomPrpPitemKindList(String endorseNo, String riskCode) throws Exception;

	/**
	 * 得到壹個危險單位的所用子信息.
	 * 
	 * @param businessNo
	 *            業務號碼
	 * @param itemNo
	 *            標的信息序號
	 * @param businessType
	 *            業務類型
	 * @return 滿足條件的列表集合
	 * @throws Exception
	 *             異常
	 */
	public Collection getDangerItemList(String businessNo, String itemNo, String businessType) throws Exception;

	/**
	 * 得到指定危險單位序號的危險單位信息(只適用于T,P,E).
	 * 
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param dangerNo
	 *            危險單位序號
	 * @return 符合條件的列表集合
	 * @throws Exception
	 *             異常
	 */
	public Collection getDangerDetail(String businessType, String businessNo, String dangerNo) throws Exception;

	/**
	 * 獲取投保單信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @return 投保單信息類
	 * @throws Exception
	 *             異常
	 */
	public PrpTmainDto getPrpTmain(String businessNo, String businessType) throws Exception;

	/**
	 * 獲取保單信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return 保單信息類
	 * @throws Exception
	 *             異常
	 */
	public PrpCmainDto getPrpCmain(String businessNo) throws Exception;

	/**
	 * 根據工作流號查找工作流日誌.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的集合
	 */
	List<WfLog> findByFlowId(QueryRule queryRule);

	/**
	 * 根據条件查找工作流日誌.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的集合
	 */
	public List<WfLog> findByQueryRule(QueryRule queryRule);
	
	/**
	 * 修改main表中的‘拒限保’，‘名單檢測’，‘風險評級’，‘作業狀態’ 四個狀態
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public int updateMainStatus(String sql) throws Exception;
	
	/**
	 * <p>功能描述:[获取当前的session用于获取用户信息]</p>
	 * @param session
	 * @author:xuhuiling
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
//	mantis： ???????，處理人員：Sam，需求單編號：??????? 正式區無此CODE
//	public void setHttpSession(HttpSession session);
}
