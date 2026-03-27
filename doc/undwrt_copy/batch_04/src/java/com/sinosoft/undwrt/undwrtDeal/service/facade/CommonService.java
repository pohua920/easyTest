package com.sinosoft.undwrt.undwrtDeal.service.facade;

/**
 * mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業
 * @author bi086
 *
 */
public interface CommonService {
	
	/**
	 * mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業
	 * 更新地址比對狀態
	 * 
	 * @param businessNo
	 * @throws Exception
	 */
	public void updateNomastatus(String type, String businessNo) throws Exception;

	/**
	 * mantis： CAR0670，處理人員：DP0713，需求單編號：支票收費註記卡控調整
	 * @param businessNo
	 * @throws Exception
	 */
	public String queryCheckPay(String businessNo) throws Exception;
}
