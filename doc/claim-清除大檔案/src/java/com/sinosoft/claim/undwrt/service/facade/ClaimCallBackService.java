package com.sinosoft.claim.undwrt.service.facade;


public interface ClaimCallBackService {
	/**
	 * 双核回调处理
	 * @param businessType
	 * @param businessNo
	 * @throws Exception
	 */
	public void callBack(String businessType,String businessNo) throws Exception;

}
