package cn.com.sinosoft.ims.log.service.facade;

import java.util.List;

import cn.com.sinosoft.ims.log.model.UtiISyncLog;

public interface UtiISyncLogService {
	public void insertMethod(UtiISyncLog utiISyncLog);
	public void deleteMethod();
//	public Page getLogList(UtiISyncLog utiISyncLog,String userName, int pageNo,int pageSize);
	public List<UtiISyncLog> getLogList(UtiISyncLog utiISyncLog,String userName);
	
	public void insertAllUtiISyncLog(List<UtiISyncLog> utiISyncLogList);
}
