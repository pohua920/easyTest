package com.sinosoft.undwrt.undwrtDeal.service.facade;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.undwrt.undwrtDeal.vo.ClaimInfoVo;

// TODO: Auto-generated Javadoc
/**
 * 核保系統幫助服務接口類.
 */
public interface WfLogHelperService {

	/**
	 * 根據頁面輸入條件拼寫Where字句.
	 * 
	 * @param req
	 *            請求對象
	 * @return 返回完整的查詢條件
	 * @throws Exception
	 *             異常
	 */
	public String getWherePart(HttpServletRequest req) throws Exception;

	/**
	 * 設置撤銷任務列表.
	 * 
	 * @param req
	 *            請求對象
	 * @throws Exception
	 *             異常
	 */
	public void setUndoQueryTaskList(HttpServletRequest req) throws Exception;

	/**
	 * 獲取批量核保列表.
	 * 
	 * @param req
	 *            請求對象
	 * @param flowID
	 *            工作流號
	 * @param logNo
	 *            序號
	 * @return 批量核保列表
	 * @throws Exception
	 *             異常
	 */
	public Collection setBatchTaskViewToDto(HttpServletRequest req,
			String[] flowID, String[] logNo) throws Exception;

	/**
	 * 設置批量核保提交列表.
	 * 
	 * @param req
	 *            請求對象
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public void setBatchTaskListDtoToView(HttpServletRequest req)
			throws UserException, Exception;

	/**
	 * 獲取 批量下發路徑.
	 * 
	 * @param req
	 *            請求對象
	 * @param iWfLogList
	 *            工作流日誌集合
	 * @return 批量下發路徑
	 * @throws Exception
	 *             異常
	 */
	public List getBackPathList(HttpServletRequest req, Collection iWfLogList)
			throws Exception;

	/**
	 * 設置提交指定人員列表.
	 * 
	 * @param req
	 *            請求對象
	 * @throws Exception
	 *             異常
	 */
	public void setSubmitUserrList(HttpServletRequest req) throws Exception;

	/**
	 * 設置任務列表.
	 * 
	 * @param req
	 *            請求對象
	 * @throws Exception
	 *             異常
	 */
	public void setTaskMessage(HttpServletRequest req) throws Exception;

	/**
	 * 檢查被保人曆史信息（曆史投保、曆史賠付）.
	 * 
	 * @param req
	 *            請求對象
	 * @throws Exception
	 *             異常
	 */
	public void checkHistoryInfo(HttpServletRequest req) throws Exception;

	/**
	 * 獲取危險單位信息到頁面.
	 * 
	 * @param req
	 *            請求對象
	 * @throws Exception
	 *             異常
	 */
	public void setDangerInfoToView(HttpServletRequest req) throws Exception;

	/**
	 * 單獨獲取標的信息(拆分危險單位時調用，非拆分危險單位部分禁止調用).
	 * 
	 * @param req
	 *            請求對象
	 * @return 標的信息
	 * @throws Exception
	 *             異常
	 */
	public void getItemInfoToView(HttpServletRequest req) throws Exception;

	/**
	 * 根據業務類型，業務號，危險單位序號來獲取指定序號危險單位的信息.
	 * 
	 * @param req
	 *            待設置的危險單位主信息 to view by danger no的值
	 * @throws Exception
	 *             異常
	 */
	public void setDangerDetailToViewByDangerNo(HttpServletRequest req)
			throws Exception;

	/**
	 * 獲取危險單位的子信息數據到頁面.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param dangerNo
	 *            危險單位號
	 * @param businessType
	 *            業務類型
	 * @param req
	 *            請求對象
	 * @return 危險單位的子信息
	 * @throws Exception
	 *             異常
	 */
	public void getDangerItemToView(String businessNo, String dangerNo,
			String businessType, HttpServletRequest req) throws Exception;

	/**
	 * 分保意向中取每個危險單位的相關信息.
	 * 
	 * @param certiNo
	 *            業務號
	 * @param certiType
	 *            業務類型
	 * @param req
	 *            請求對象
	 * @throws Exception
	 *             異常
	 */
	public void setDangerInfoToViewByReins(String certiNo, String certiType,
			HttpServletRequest req) throws Exception;

	/**
	 * 根據危險單位號生成分保訊息.
	 * 
	 * @param certiNo
	 *            業務號
	 * @param classCode
	 *            險類代碼
	 * @param certiType
	 *            業務類型
	 * @return "s"
	 * @throws Exception
	 *             異常
	 */
	public String simulateRepolicyByDangerNo(String certiNo, String classCode,
			String certiType) throws Exception;

	/**
	 * 獲取放棄任務列表到頁面.
	 * 
	 * @param flowID
	 *            工作流號
	 * @param logNo
	 *            序號
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public void setCancelTaskListDtoToView(String[] flowID, String[] logNo)
			throws UserException, Exception;

	/**
	 * 查詢該保單是續保業務則查詢續保保單是否存在 存在立案.
	 * 
	 * @param policyNo
	 *            保單號
	 * @return 存在返回true,不存在返回false
	 * @throws Exception
	 *             異常
	 */
	public boolean isExistPrplregis(String policyNo) throws Exception;
	/**
	 * 查詢被保險人近三年是否有賠案.
	 * 
	 * @param insuredCode
	 *            被保險人代碼
	 * @return 存在返回true,不存在返回false
	 * @throws Exception
	 *             異常
	 */
	public boolean isExistClaims(String insuredCode) throws Exception;

	/**
	 * 獲取查詢的聲明sql.
	 * 
	 * @param req
	 *            請求對象
	 * @return 查詢的聲明
	 */
	public String getQueryConditionStatement(HttpServletRequest req);
	
	/**
	 *查看與與該要保書要保人、被保險人、被保險財產坐落地址之一相同的資料庫中近五年保單的已決與未決的理賠記錄.
	 * 
	 * @param businessNo
	 *            業務號
	 * @return Page 對象
	 * @throws Exception
	 *             異常
	 */
	public List<ClaimInfoVo> similarClaimsInfo(String businessNo, int pageNo, int pageSize) throws Exception;

}
