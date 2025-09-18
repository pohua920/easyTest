package com.tlg.aps.bs.carFourthPostcardServerce;

import java.util.Date;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

/*mantis：CAR0427，處理人員：BJ085，需求單編號：CAR0427 機車強制車險重新投保發對接功能-第四次明信片*/
public interface RunFourthPostcardService {

	public Result readFileAndImportData(Date executeTime) throws Exception;
	
	public void checkTvmcqAndSendMail() throws SystemException, Exception;
}
