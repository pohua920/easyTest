package com.sinosoft.undwrt.message.service.facade;

import java.util.ArrayList;
import com.sinosoft.undwrt.undwrtBase.model.WfLog;


/**
 * 即時通訊接口類.
 */
public interface MessageService {
	
	/**
	 *  獲取用戶代碼列表.
	 * 
	 * @param wfLog
	 *            工作流日誌
	 * @param wfLogOld
	 *            工作流日誌
	 * @return 滿足條件的用戶代碼集合
	 * @throws Exception
	 *             異常
	 */
	public ArrayList<String> getUsersOfNode(WfLog wfLog,WfLog wfLogOld)throws Exception;
	
	/**
	 * 發送即時訊息.
	 * 
	 * @param wfLog
	 *            工作流日誌
	 * @param wflogOld
	 *            工作流日誌
	 */
	public void send(WfLog wfLog,WfLog wflogOld);
}

