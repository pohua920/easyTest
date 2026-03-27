package cn.com.sinosoft.ims.log.service.facade;

import ins.framework.common.Page;
import cn.com.sinosoft.ims.log.model.UtiIExceptionLog;

public interface UtiIExceptionLogService {
	public void insertMethod(UtiIExceptionLog utiIExceptionLog);
	public void deleteMethod(UtiIExceptionLog utiIExceptionLog);
//	public Page getLogList(UtiIExceptionLog log,String userName, int pageNo, int pageSize);
	public Page getLogList(UtiIExceptionLog log,int pageNo,int pageSize);
}
