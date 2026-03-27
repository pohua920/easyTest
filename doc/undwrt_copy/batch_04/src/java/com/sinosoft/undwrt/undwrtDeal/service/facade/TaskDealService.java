package com.sinosoft.undwrt.undwrtDeal.service.facade;

import ins.framework.common.Page;

import java.util.List;

import com.sinosoft.common.schema.model.PrpDBankInfo;
import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.sysframework.common.datatype.PageRecord;
import com.sinosoft.undwrt.common.vo.NodeListVo;
import com.sinosoft.undwrt.common.vo.RiskCategoryCodeVo;

// TODO: Auto-generated Javadoc
/**
 * 核保處理任務服務接口類.
 */
public interface TaskDealService {

	/**
	 * 獲取通用代碼類型.
	 * 
	 * @return 通用代碼類型集合
	 * @throws Exception
	 *             異常
	 */
	public List findIdentifyTypeList() throws Exception;

	/**
	 * 查找險種大類.
	 * 
	 * @return 險種大類集合
	 * @throws Exception
	 *             異常
	 */
	public List<RiskCategoryCodeVo> findRiskCodeByRiskCategory() throws Exception;

	/**
	 * 根據條件查找工作流日誌.
	 * 
	 * @param statement
	 *            查詢條件
	 * @param pageNo
	 *            頁碼
	 * @param rowsPerPage
	 *            每頁的記錄條數
	 * @param blnView
	 *            是否走視圖
	 * @return page對象
	 * @throws Exception
	 *             異常
	 */
	public Page findByStatement(String statement, int pageNo, int rowsPerPage,
			boolean blnView) throws Exception;

	/**
	 * 根據條件查找工作流日誌.
	 * 
	 * @param statement
	 *            查詢條件
	 * @param pageNo
	 *            頁碼
	 * @param rowsPerPage
	 *            每頁的記錄條數
	 * @param bln
	 *            是否走視圖
	 * @return PageRecord對象
	 * @throws Exception
	 *             異常
	 */
	public PageRecord findByStatementPageRecord(String statement, int pageNo,
			int rowsPerPage, boolean bln) throws Exception;

	/**
	 * 根據條件查找報價單.
	 * 
	 * @param statement
	 *            查詢條件
	 * @param pageNo
	 *            頁碼
	 * @param rowsPerPage
	 *            每頁的記錄條數
	 * @param blnView
	 *            是否走視圖
	 * @return page對象
	 * @throws Exception
	 *             異常
	 */
	public Page findByStatementQta(String statement, int pageNo,
			int rowsPerPage, boolean blnView) throws Exception;

	/**
	 * 根據條件查找所有報價單.
	 * 
	 * @param statement
	 *            查詢條件
	 * @param pageNo
	 *            頁碼
	 * @param rowsPerPage
	 *            每頁的記錄條數
	 * @param blnView
	 *            是否走視圖
	 * @return page對象
	 * @throws Exception
	 *             異常
	 */
	public Page findAllByStatementQta(String statement, int pageNo,
			int rowsPerPage, boolean blnView) throws Exception;

	/**
	 * 根據條件查找報價單.
	 * 
	 * @param statement
	 *            查詢條件
	 * @param pageNo
	 *            頁碼
	 * @param rowsPerPage
	 *            每頁的記錄條數
	 * @param bln
	 *            是否走視圖
	 * @return PageRecord 對象
	 * @throws Exception
	 *             異常
	 */
	public PageRecord findByStatementQtaPageRecord(String statement,
			int pageNo, int rowsPerPage, boolean bln) throws Exception;

	/**
	 * 獲取選中的所有記錄的提交路徑.
	 * 
	 * @param checkboxSelectCollection
	 *            選中的所有記錄集合
	 * @return 提交路徑得集合
	 * @throws Exception
	 *             異常
	 */
	public List[] prepareBatchSubmitSuperior(List checkboxSelectCollection)
			throws Exception;

	/**
	 * 批量提交上級.
	 * 
	 * @param taskCollection
	 *            任務集合
	 * @param notionCollection
	 *            核保意見類集合
	 * @param prpDuserDto
	 *            用戶信息類
	 * @throws Exception
	 *             異常
	 */
	public void batchSubmitSuperior(List taskCollection, List notionCollection,
			PrpDuserDto prpDuserDto) throws Exception;

	/**
	 * 獲取選中的所有記錄的下發路徑.
	 * 
	 * @param checkboxSelectCollection
	 *            選中的所有記錄
	 * @return 下發路徑得集合
	 * @throws Exception
	 *             異常
	 */
	public List prepareBatchSubmitJunior(List checkboxSelectCollection)
			throws Exception;

	/**
	 * 批量下發修改.
	 * 
	 * @param taskCollection
	 *            任務集合
	 * @param notionCollection
	 *            核保意見類集合
	 * @param prpDuserDto
	 *            用戶信息類
	 * @throws Exception
	 *             異常
	 */
	public void batchSubmitJunior(List taskCollection, List notionCollection,
			PrpDuserDto prpDuserDto) throws Exception;

	/**
	 * 批量撤銷.
	 * 
	 * @param checkboxSelectCollection
	 *            選中的記錄集合
	 * @return 記錄集合
	 * @throws Exception
	 *             異常
	 */
	public List[] prepareBatchUndo(List checkboxSelectCollection)
			throws Exception;

	/**
	 * 批量撤銷.
	 * 
	 * @param taskCollection
	 *            任務集合
	 * @throws Exception
	 *             異常
	 */
	public void batchUndo(List taskCollection) throws Exception;

	/**
	 * 查詢核保級別.
	 * 
	 * @param userCode
	 *            用戶代碼
	 * @param comCode
	 *            機構代碼
	 * @return 核保級別集合
	 * @throws Exception
	 *             異常
	 */
	public List<NodeListVo> findNodeList(String userCode, String comCode) throws Exception;
	
	/**
	 * 查詢人工審核開關
	 * @return
	 * @throws Exception
	 */
	public String getRenGongKaiGuanStatu() throws Exception;
	
	/**
	 * 獲取作業狀態
	 * @param busiNo
	 * @param busiType
	 * @return
	 * @throws Exception
	 */
	public String getWorkStatusForBusiNo(String busiNo,String busiType) throws Exception;
	
	 /**
	  * songxin
	  * 獲取要保書的繳費信息和核保狀態
	  * @param busiNo
	  * @return
	  * @throws Exception
	  */
	public String getPayrefAndUnd(String busiNo) throws Exception;
	
	/**
	 * 银行信息查詢
	 * @param bankCode 銀行代碼
	 * @return PrpDBankInfo 銀行名稱
	 */
	public PrpDBankInfo queryBankInfo(String bankCode) throws Exception;	
	/**
	 * @author zhangruofei
	 * 更新介接表的實收狀態
	 * @param busiNo
	 * @return
	 */
	public void saveIntfPrpjpayrefrec(String[] busiNo,String certitype) throws Exception;
	/**
	 * 獲取虛擬編碼
	 * @param busiNo 業務號
	 * @return List 虛擬編碼
	 */
	public List queryPrpDprint(String busiNo) throws Exception;	
	/**
	 * 
	 * <p>功能描述:[需求167]</p>
	 * @param businessno
	 * @return
	 * @throws Exception
	 * @author:xuhuiling
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public String querySuperpay(String businessno) throws Exception;
}
