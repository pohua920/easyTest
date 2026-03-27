package cn.com.sinosoft.ims.log.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.ims.log.model.UtiIOperateLog;
import cn.com.sinosoft.ims.log.service.facade.UtiIOperateLogService;
import cn.com.sinosoft.ims.log.vo.UtiIOperateLogVO;
import cn.com.sinosoft.ims.svr.service.facade.UtiISvrService;

public class UtiIOperateLogAction extends Struts2Action {
	
	private UtiIOperateLogService utiIOperateLogService;
	private UtiIOperateLog utiIOperateLog;
	private UtiISvrService utiISvrService ;
	private String userName;
	private List<UtiIOperateLog> list = new ArrayList<UtiIOperateLog>();
	
	
//-------------------	getter & setter ---------------
	
	public UtiIOperateLogService getUtiIOperateLogService() {
		return utiIOperateLogService;
	}

	public void setUtiIOperateLogService(UtiIOperateLogService utiIOperateLogService) {
		this.utiIOperateLogService = utiIOperateLogService;
	}

	public UtiIOperateLog getUtiIOperateLog() {
		return utiIOperateLog;
	}

	public void setUtiIOperateLog(UtiIOperateLog utiIOperateLog) {
		this.utiIOperateLog = utiIOperateLog;
	}

	public UtiISvrService getUtiISvrService() {
		return utiISvrService;
	}

	public void setUtiISvrService(UtiISvrService utiISvrService) {
		this.utiISvrService = utiISvrService;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<UtiIOperateLog> getList() {
		return list;
	}

	public void setList(List<UtiIOperateLog> list) {
		this.list = list;
	}

	//	-------------  methods------------
	public void insertOperateLog(){
		
	}
	
	public void deleteOperateLog(){
		
	}
	public String prepareViewOperateLog(){
		
		return "success";
	}
	
	public String viewOperateLog(){
		Page page = utiIOperateLogService.getLogList(utiIOperateLog,this.pageNo,this.pageSize);
		try {
			// utiISvr = (UtiISvr) getSession().getAttribute("utiISvr");
//			Page page = utiIOperateLogService.getLogList(utiIOperateLog,userName, this.pageNo,this.pageSize);
			List pageList = page.getResult();
			// Object[] o = new Object[10];
			UtiIOperateLogVO utiIOperateLogVO = null;
			List list = new ArrayList();
			for (int i = 0; i < pageList.size(); i++) {
				utiIOperateLog = (UtiIOperateLog) pageList.get(i);
				SimpleDateFormat sdf=new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss"); 
				String date = sdf.format(utiIOperateLog.getLoginTime());
				utiIOperateLogVO = new UtiIOperateLogVO();
				utiIOperateLogVO.setUserCode(utiIOperateLog.getUserCode());
				utiIOperateLogVO.setLoginTime(date);
				if (userName == null || "".equals(userName)) {
					userName = utiISvrService.getUserNameByCode(utiIOperateLog.getUserCode());
				}
				utiIOperateLogVO.setUserName(userName);
				utiIOperateLogVO.setDescription(utiIOperateLog.getDescription());
				list.add(utiIOperateLogVO);
			}
			Page page1 = new Page(page.getStartOfPage(this.pageNo,
					this.pageSize), page.getTotalCount(), this.pageSize, list);
			this.writeJSONData(page1,"userCode","userName","loginTime","description");
		} catch (Exception e) {
			e.printStackTrace();
			this.writeJSONMsg(e.getMessage());
		}
		return null;
	}
	
}
