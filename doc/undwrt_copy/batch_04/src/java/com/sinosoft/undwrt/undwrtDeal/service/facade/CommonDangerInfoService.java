package com.sinosoft.undwrt.undwrtDeal.service.facade;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

// TODO: Auto-generated Javadoc
/**
 * 危險單位信息服務接口類.
 */
public interface CommonDangerInfoService {

	/**
	 * 取除外標的訊息.
	 * 
	 * @param riskCode
	 *            險種代碼
	 * @return the danger ex item kind
	 * @throws Exception
	 *             異常
	 */
	public Collection getDangerExItemKind(String riskCode) throws Exception;

	/**
	 * 取自留額訊息.
	 * 
	 * @param strConditon
	 *            查詢條件
	 * @return 自留額訊息
	 * @throws Exception
	 *             異常
	 */
	public Collection getRetenValue(String strConditon) throws Exception;

	/**
	 * 獲取危險單位訊息.
	 * 
	 * @param req
	 *            請求對象
	 * @return 危險單位訊息
	 * @throws Exception
	 *             異常
	 */
	public Collection getDangerUnit(HttpServletRequest req) throws Exception;

	/**
	 * 獲取險別訊息.
	 * 
	 * @param req
	 *            請求對象
	 * @return 滿足條件的險別訊息
	 * @throws Exception
	 *             異常
	 */
	public ArrayList getDangerItemList(HttpServletRequest req) throws Exception;

	/**
	 * 獲取投保單危險單位金額合計資訊.
	 * 
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param prpDangerUnitDtoList
	 *            危險單位類集合
	 * @return 投保單危險單位金額類集合
	 * @throws Exception
	 *             異常
	 */
	public Collection getDangerTotList(String businessType, String businessNo,
			Collection prpDangerUnitDtoList) throws Exception;

	/**
	 * 獲取投保單危險單位交費計畫.
	 * 
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param prpDangerUnitDtoList
	 *            危險單位類集合
	 * @return 投保單危險單位交費計畫類集合
	 * @throws Exception
	 *             異常
	 */
	public Collection getDangerPlanList(String businessType, String businessNo,
			Collection prpDangerUnitDtoList) throws Exception;

	/**
	 * 獲取投保單危險單位共保資訊.
	 * 
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param policyNo
	 *            保單號
	 * @param prpDangerUnitDtoList
	 *            危險單位類集合
	 * @return 保單危險單位共保資訊類集合
	 * @throws Exception
	 *             異常
	 */
	public Collection getDangerCoinsList(String businessType,
			String businessNo, String policyNo, Collection prpDangerUnitDtoList)
			throws Exception;

	/**
	 * 更新危險單位共保等保額訊息.
	 * 
	 * @param businessType
	 *            業務類型
	 * @param sumAmount
	 *            總保額
	 * @param chgAmount
	 *            變化保額
	 * @param prpDangerUnitDtoList
	 *            危險單位類集合
	 * @param prpDangerTotDtoList
	 *            投保單危險單位金額合計資訊類集合
	 * @param prpDangerCoinsDtoList
	 *            投保單危險單位共保資訊類集合
	 * @throws Exception
	 *             異常
	 */
	public void updateAmountFor1903(String businessType, double sumAmount,
			double chgAmount, Collection prpDangerUnitDtoList,
			Collection prpDangerTotDtoList, Collection prpDangerCoinsDtoList)
			throws Exception;

	/**
	 * 獲取危險單位訊息.
	 * 
	 * @param certiNo
	 *            業務號
	 * @param req
	 *            請求對象
	 * @return 危險單位訊息類
	 * @throws Exception
	 *             異常
	 */
	public void getDangerUnit(String certiNo, HttpServletRequest req)
			throws Exception;

	/**
	 * 根據合約號查詢合約主信息.
	 * 
	 * @param treatyNo
	 *            合約號
	 * @return 合約簡稱
	 * @throws Exception
	 *             異常
	 */
	public String findByPrimaryKey(String treatyNo) throws Exception;

	/**
	 * 分保訊息轉成請求對象.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param businessType
	 *            業務類型
	 * @param req
	 *            請求對象
	 * @throws Exception
	 *             異常
	 */
	public void reinsTrialInfoToRequest(String businessNo, String businessType,
			HttpServletRequest req) throws Exception;
}
