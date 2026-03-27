package com.sinosoft.undwrt.common.service.facade;

import ins.framework.common.QueryRule;

import java.util.Collection;
import java.util.List;

import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.undwrt.undwrtBase.model.WfMessage;

// TODO: Auto-generated Javadoc
/**
 * 備注訊息接口類.
 */
public interface WfMessageService {
	
	/**
	 * 根據條件查詢備註信息.
	 *
	 * @param conditions 查詢條件
	 * @return 滿足條件的集合
	 * @throws Exception 異常
	 */
	public List<WfMessage> findByConditions(String conditions) throws Exception;

	/**
	 * 得到某條記錄最大的留言序號.
	 *
	 * @param queryRule 查詢規則
	 * @return 某條記錄的所有留言集合
	 * @throws Exception 異常
	 */
	public Collection getMaxSerialNo(QueryRule queryRule) throws Exception;
	
	 /**
 	 * 保存備註訊息.
 	 *
 	 * @param wfMessageDto 備註訊息類
 	 * @param dbManager 數據管理對象
 	 * @throws Exception 異常
 	 */
 	public void saveMessage(WfMessage wfMessageDto,DBManager dbManager) throws Exception;
 	
 	/**
 	 * 根據主鍵獲取唯一備註記錄.
 	 *
 	 * @param queryRule 查詢規則
 	 * @return 某條記錄的首條留言記錄
 	 * @throws Exception 異常
 	 */
 	public WfMessage getUniqueMessage(QueryRule queryRule) throws Exception;
}
