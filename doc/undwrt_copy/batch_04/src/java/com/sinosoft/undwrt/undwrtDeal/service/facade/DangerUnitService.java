package com.sinosoft.undwrt.undwrtDeal.service.facade;

import java.util.Collection;


// TODO: Auto-generated Javadoc
/**
 * 獲取危險單位主信息接口類.
 */
public interface DangerUnitService {

	/**
	 * 獲取危險單位主信息.
	 * 
	 * @param businessNo
	 *            業務號
	 * @param dangerNo
	 *            危險單位號
	 * @param businessType
	 *            業務類型
	 * @return 符合條件的危險單位信息類集合
	 * @throws Exception
	 *             異常
	 */
	public Collection getDangerUnitItemInfo(String businessNo, String dangerNo,
			String businessType) throws Exception;
}
