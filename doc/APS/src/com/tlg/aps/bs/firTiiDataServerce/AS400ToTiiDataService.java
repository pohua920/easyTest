package com.tlg.aps.bs.firTiiDataServerce;

import com.tlg.exception.SystemException;

/**
 * @author bi086
 *
 */
public interface AS400ToTiiDataService {
	
	public int insertFirBatchTiiListFromAs400(String batchSerial, String exectutor) throws SystemException, Exception;
	
}
