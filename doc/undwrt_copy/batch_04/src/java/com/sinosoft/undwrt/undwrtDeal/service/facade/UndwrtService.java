package com.sinosoft.undwrt.undwrtDeal.service.facade;

import java.sql.SQLException;

/**
 * 核保系統接口類.
 */
public interface UndwrtService {

	/**
	 * 獲取模板號.
	 * 
	 * @param modelType
	 *            模板類型
	 * @param riskCode
	 *            險種代碼
	 * @param comCode
	 *            機構代碼
	 * @return 模板號
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public int getModelNo(String modelType, String classCode,String riskCode, String comCode)
			throws SQLException, Exception;
}
