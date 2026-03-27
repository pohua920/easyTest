package com.sinosoft.undwrt.undwrtBase.service.facade;

import ins.framework.common.QueryRule;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import com.sinosoft.undwrt.undwrtBase.model.UwNotion;
import com.sinosoft.undwrt.undwrtBase.model.WfLog;

// TODO: Auto-generated Javadoc
/**
 * 核保處理意見接口類.
 */
public interface UwNotionService {

	/**
	 * 批量插入.
	 * 
	 * @param uwNotionList
	 *            核保意見列表
	 * @throws Exception
	 *             異常
	 */
	public void insertAll(List<UwNotion> uwNotionList) throws Exception;

	/**
	 * 保存審核意見.
	 * 
	 * @param uwNotionDto
	 *            核保意見類
	 * @throws Exception
	 *             異常
	 */
	public void saveNotion(UwNotion uwNotionDto) throws Exception;

	/**
	 * 將制單員的說明，插入到UwNotion表中。如果是出單員，則插入出單員意見.
	 * 
	 * @param wfLog
	 *            工作流日誌類
	 * @param iCertiType
	 *            業務類型
	 * @throws Exception
	 *             異常
	 */
	public void insertUwNotionByMakeUser(WfLog wfLog, String iCertiType)
			throws Exception;

	/**
	 * 按條件查詢多條數據.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的記錄集合
	 * @throws Exception
	 *             異常
	 */
	public List<UwNotion> findByConditions(QueryRule queryRule)
			throws Exception;

	/**
	 * 刪除多條記錄.
	 * 
	 * @param list
	 *            要刪除的集合
	 * @throws Exception
	 *             異常
	 */
	public void deleteList(List list) throws Exception;

	/**
	 * 查詢處理意見.
	 * 
	 * @param flowID
	 *            工作流號
	 * @return 處理意見
	 * @throws Exception
	 *             異常
	 */
	public String getPreHandleText(String flowID) throws Exception;

	/**
	 * 拆分審批意見.
	 * 
	 * @param uwNotionDto
	 *            處理意見類
	 * @return 拆分后的集合
	 */
	public Collection ungroup(UwNotion uwNotionDto);

}
