package com.sinosoft.undwrt.undwrtDeal.service.facade;

import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * 再保服務接口類.
 */
public interface ReinsService {

	/**
	 * 是否強制分保試算計算.
	 * 
	 * @param RiskCode
	 *            險種代碼
	 * @param UwYear
	 *            業務年度
	 * @param BusinessNo
	 *            業務號
	 * @param BusinessType
	 *            業務類型
	 * @return 離線計算返回true, 否則返回false
	 * @throws Exception
	 *             異常
	 */
	public boolean ifOffLineCal(String RiskCode, String UwYear,
			String BusinessNo, String BusinessType) throws Exception;

	/**
	 * 獲取分保試算信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param dangerNo
	 *            危險單位號
	 * @param businessType
	 *            業務類型
	 * @return 分保試算信息類
	 * @throws Exception
	 *             異常
	 */
	public Collection getReinsTrialInfo(String businessNo, String dangerNo,
			String businessType) throws Exception;
}
