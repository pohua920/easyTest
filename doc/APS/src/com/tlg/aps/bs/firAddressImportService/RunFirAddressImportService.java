package com.tlg.aps.bs.firAddressImportService;

import java.util.Date;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;
import com.tlg.util.UserInfo;

public interface RunFirAddressImportService {
	/* mantis：FIR0183，處理人員：BJ085，需求單編號：FIR0183 火險地址資料匯入 start */
	public Result AddressDataImport(UserInfo userInfo,Date excuteTime,String programId) throws SystemException, Exception;
	
}
