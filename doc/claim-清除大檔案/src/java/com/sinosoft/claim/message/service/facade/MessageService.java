package com.sinosoft.claim.message.service.facade;

import java.util.List;

import com.sinosoft.claim.schema.model.WfLog;


/**
 * <p>
 * Title: 即时消息
 * </p>
 * <p>
 * Description: 即时消息
 * </p>
 * @author 中科软
 * @version
 */
public interface MessageService {
	/**獲取用戶節點方法*/
	public List<String> getUsersOfNode(WfLog wfLog,WfLog wflogOld)throws Exception;
	/**消息發送方法*/
	public void send(WfLog wfLog,WfLog wflogOld);
}

