package com.sinosoft.undwrt.common.service.facade;

/**
 * 紀錄使用者登錄相關資訊
 * mantis： OTH0126，處理人員：DP0706，需求單編號：OTH0126 核心系統增加登入紀錄
 * @author dp0706
 *
 */
public interface CoreService {

	public void insertCoreLoginRecord(String system, String account, String loginDate, String status);
}
