package com.sinosoft.undwrt.undwrtDeal.service.facade;

import java.sql.SQLException;
import java.util.Collection;

import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.undwrt.undwrtBase.model.UwNotion;

// TODO: Auto-generated Javadoc
/**
 * 核保系統提交任務服務接口類.
 */
public interface CommonDealTaskService {
	
	/**
	 * 保存任務.
	 * 
	 * @param uwNotionDto
	 *            核保意見類
	 * @param prpDuserDto
	 *            用戶信息類
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public void saveTask(UwNotion uwNotionDto, PrpDuserDto prpDuserDto)
			throws SQLException, Exception;

	/**
	 * 批量保存任務.
	 * @param uwNotionList 核保意見類集合
	 * @param prpDuserDto 用戶信息類
	 * @throws SQLException sql異常
	 * @throws Exception 異常
	 */
	public void saveBatchTask(Collection uwNotionList, PrpDuserDto prpDuserDto)
			throws SQLException, Exception;

}
