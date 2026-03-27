package cn.com.sinosoft.ims.log.service.facade;
import ins.framework.common.Page;
import cn.com.sinosoft.ims.log.model.UtiILoginLog;

public interface UtiILoginLogService {
	public void insertMethod(UtiILoginLog utiILoginLog);
	public void deleteMethod();
//	public List<UtiILoginLog> getLogList(UtiILoginLog log,String userName, int pageNo, int pageSize);
	public Page getLogList(UtiILoginLog log,int pageNo,int pageSize);
	public void updateMethod(UtiILoginLog utiILoginLog);
}
