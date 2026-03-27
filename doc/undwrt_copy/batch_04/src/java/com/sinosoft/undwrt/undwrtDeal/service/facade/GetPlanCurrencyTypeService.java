package com.sinosoft.undwrt.undwrtDeal.service.facade;

import java.sql.SQLException;
import java.util.Collection;


// TODO: Auto-generated Javadoc
/**
 * 交費計劃中的幣種信息接口類.
 */
public interface GetPlanCurrencyTypeService {

	
	/**
	 * 交費計劃中的幣種信息.
	 * @param businessNo 業務號
	 * @param businessType 業務類型
	 * @return 幣種信息
	 * @throws SQLException sql異常
	 * @throws Exception 異常
	 */
	public Collection getPlanCurrencyType(String businessNo,
			String businessType) throws SQLException,
			Exception;
}
