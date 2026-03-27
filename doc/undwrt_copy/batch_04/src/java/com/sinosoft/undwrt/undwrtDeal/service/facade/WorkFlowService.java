package com.sinosoft.undwrt.undwrtDeal.service.facade;

import java.sql.SQLException;

// TODO: Auto-generated Javadoc
/**
 * 工作流接口類.
 */
public interface WorkFlowService {

	/**
	 * 關閉工作流.
	 * 
	 * @param flowID
	 *            工作流號
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public void close(String flowID) throws SQLException, Exception;
}
