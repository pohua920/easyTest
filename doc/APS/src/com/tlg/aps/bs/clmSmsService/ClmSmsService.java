package com.tlg.aps.bs.clmSmsService;

import com.tlg.exception.SystemException;

public interface ClmSmsService {

	public void createPdf() throws SystemException, Exception;
	
	public void sendSms() throws SystemException, Exception;
}
