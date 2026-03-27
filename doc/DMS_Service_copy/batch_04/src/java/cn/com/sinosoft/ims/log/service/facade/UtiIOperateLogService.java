package cn.com.sinosoft.ims.log.service.facade;

import ins.framework.common.Page;
import cn.com.sinosoft.ims.log.model.UtiIExceptionLog;
import cn.com.sinosoft.ims.log.model.UtiIOperateLog;

public interface UtiIOperateLogService {
	public void insertMethod(UtiIOperateLog utiIOperateLog);
	public void deleteMethod(UtiIExceptionLog utiIExceptionLog);
//	public Page getLogList(UtiIOperateLog log,String userName, int pageNo, int pageSize);
	public Page getLogList(UtiIOperateLog log,int pageNo,int pageSize);
	public String getCName(String actionType,String taskCode);
}
