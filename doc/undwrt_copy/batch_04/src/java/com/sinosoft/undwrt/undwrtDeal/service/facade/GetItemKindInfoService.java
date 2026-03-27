package com.sinosoft.undwrt.undwrtDeal.service.facade;

import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * 查詢危險單位信息接口類.
 */
public interface GetItemKindInfoService {

	/**
	 * 獲取危險單位信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param riskCode
	 *            險種代碼
	 * @param businessType
	 *            業務類型
	 * @return 危險單位信息類
	 * @throws Exception
	 *             異常
	 */
	public Collection getItemInfoMain(String businessNo, String riskCode,
			String businessType) throws Exception;

	/**
	 * 要保書危險單位查詢聲明.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param riskCode
	 *            險種代碼
	 * @param businessType
	 *            業務類型
	 * @return 聲明的sql
	 * @throws Exception
	 *             異常
	 */
	public String getStatementT(String businessNo, String riskCode,
			String businessType) throws Exception;

	/**
	 * 保書危險單位查詢聲明.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param riskCode
	 *            險種代碼
	 * @param businessType
	 *            業務類型
	 * @return 聲明的sql
	 * @throws Exception
	 *             異常
	 */
	public String getStatementP(String businessNo, String riskCode,
			String businessType) throws Exception;

	/**
	 * 批單危險單位查詢聲明.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param riskCode
	 *            險種代碼
	 * @param businessType
	 *            業務類型
	 * @return 聲明的sql
	 * @throws Exception
	 *             異常
	 */
	public String getStatementE(String businessNo, String riskCode,
			String businessType) throws Exception;

	/**
	 * 獲取批單危險單位信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param riskCode
	 *            the 險種代碼
	 * @param businessType
	 *            業務類型
	 * @return 滿足條件的記錄集合
	 * @throws Exception
	 *             異常
	 */
	public Collection getItemInfoForE(String businessNo, String riskCode,
			String businessType) throws Exception;

	/**
	 * 獲取危險單位信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param riskCode
	 *            險種代碼
	 * @param businessType
	 *            業務類型
	 * @return 滿足條件的記錄集合
	 * @throws Exception
	 *             異常
	 */
	public Collection getItemInfo(String businessNo, String riskCode,
			String businessType) throws Exception;

}
