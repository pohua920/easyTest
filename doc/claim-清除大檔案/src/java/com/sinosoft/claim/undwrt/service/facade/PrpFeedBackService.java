package com.sinosoft.claim.undwrt.service.facade;

import java.sql.SQLException;
import java.util.Map;

import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.exceptionlog.UserException;
/**
 * 回写业务接口
 * @author 中科软
 *
 */

public interface PrpFeedBackService {
	/**
	 * 双核回写业务入口方法（核保通过/不通过後）
	 */
	public void echo(char certiType, String businessNo, String status, String underWriteCode, DateTime underWriteDate,Map<String,String> infoMap) throws UserException, SQLException, Exception;
	/**
	 * 理赔提交核赔时回写业务入口方法(）
	 */
	public void echoSubmit(char certiType, String businessNo) throws UserException, SQLException, Exception;

}
