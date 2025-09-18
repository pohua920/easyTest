package com.tlg.aps.bs.othBatchPassbookServerce;

import com.tlg.exception.SystemException;

/**
 * mantis：OTH0132，處理人員：BI086，需求單編號：OTH0132  保單存摺AS400資料寫入核心中介Table
 * @author bi086
 *
 */
public interface AS400ToPassbookDataService {

	
	public int insertOthBatchPassbookListFromAs400(String batchSerial, String exectutor) throws SystemException, Exception;
}
