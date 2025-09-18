package com.tlg.aps.bs.carBrokenPolicyDataServerce;

import java.util.Date;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

/*mantis：CAR0417，處理人員：BJ085，需求單編號：CAR0417 機車強制車險重新投保發對接功能*/
public interface RunBrokenPolicyDataService {

	public Result readFileAndImportData(Date executeTime) throws Exception;
	
	public void checkTvbcmAndSendMail() throws SystemException, Exception;
}
