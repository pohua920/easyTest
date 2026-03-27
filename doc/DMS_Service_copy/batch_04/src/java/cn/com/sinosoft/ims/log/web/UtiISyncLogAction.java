package cn.com.sinosoft.ims.log.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.ims.log.model.UtiISyncLog;
import cn.com.sinosoft.ims.log.service.facade.UtiISyncLogService;
import cn.com.sinosoft.ims.log.vo.UtiIOperateLogVO;
import cn.com.sinosoft.ims.log.vo.UtiISyncLogVO;
import cn.com.sinosoft.ims.svr.service.facade.UtiISvrService;

public class UtiISyncLogAction extends Struts2Action {
	
	private UtiISyncLogService utiISyncLogService;
	
	private UtiISyncLog utiISyncLog;
	
	private UtiISvrService utiISvrService;
	
	private String userName;
	
	private List<UtiISyncLog> list = new ArrayList<UtiISyncLog>();
//---------------getter & setter -------------
	public UtiISyncLogService getUtiISyncLogService() {
		return utiISyncLogService;
	}

	public void setUtiISyncLogService(UtiISyncLogService utiISyncLogService) {
		this.utiISyncLogService = utiISyncLogService;
	}

	public UtiISyncLog getUtiISyncLog() {
		return utiISyncLog;
	}

	public void setUtiISyncLog(UtiISyncLog utiISyncLog) {
		this.utiISyncLog = utiISyncLog;
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
	
	public List<UtiISyncLog> getList() {
		return list;
	}

	public void setList(List<UtiISyncLog> list) {
		this.list = list;
	}

	//-------------methods----------------
	public void insertSyncLog(){
		
	}
	
	public void deleteSyncLog(){
		
	}
	public String prepareViewSyncLog(){
		
		return "success";
	}
	
	public String viewSyncLog(){
		list = utiISyncLogService.getLogList(utiISyncLog,userName);
		Page page = null;
		List<UtiISyncLogVO> voList = new ArrayList<UtiISyncLogVO>();
		for (int i = 0; i < list.size(); i++) {
			utiISyncLog = (UtiISyncLog) list.get(i);
//			SimpleDateFormat sdf=new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss"); 
//			String date = sdf.format(utiISyncLog.getSynOccrTime());
//			UtiISyncLogVO utiISyncLogVO = new UtiISyncLogVO();
//			utiISyncLogVO.setUserCode(utiISyncLog.getUserCode());
//			utiISyncLogVO.setSynOccrTime(date);
//			userName = utiISvrService.getUserNameByCode(utiISyncLog.getUserCode());
//			utiISyncLogVO.setUserName(userName);
//			voList.add(utiISyncLogVO);
		}
		if(voList.size() <= 10){
			page = new Page(1,voList.size(),voList.size(),voList);
		}else{
			int size = voList.size();
			//取本页中的数据
			if(size > pageSize*pageNo){
				//如果数据总量大于 当前要取的数据的最大值
				voList = voList.subList((pageNo-1)*10, pageSize*pageNo);
			}else{
				//小于，则为剩余的数据
				voList = voList.subList((pageNo-1)*10, size);
			}
			if((size - pageNo*pageSize) > 10){
				//如果剩余数据的数量大于10，则本页的数据数量为10
				page = new Page(1,size,10,voList);
			}else{
				//小于10，则本页数据数量为余数
				page = new Page(1,size,size%10,voList);
			}
		}	
		try {
			// utiISvr = (UtiISvr) getSession().getAttribute("utiISvr");
//			Page page = utiISyncLogService.getLogList(utiISyncLog,userName, this.pageNo,
//					this.pageSize);
//			List pageList = page.getResult();
//			// Object[] o = new Object[10];
//			List list = new ArrayList();
//			for (int i = 0; i < pageList.size(); i++) {
//				utiISyncLog = (UtiISyncLog) pageList.get(i);
//				SimpleDateFormat sdf=new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss"); 
//				String date = sdf.format(utiISyncLog.getSynOccrTime());
//				UtiISyncLogVO utiISyncLogVO = new UtiISyncLogVO();
//				utiISyncLogVO.setUserCode(utiISyncLog.getUserCode());
//				utiISyncLogVO.setSynOccrTime(date);
//				userName = utiISvrService.getUserNameByCode(utiISyncLog.getUserCode());
//				utiISyncLogVO.setUserName(userName);
//				list.add(utiISyncLogVO);
//			}
//			Page page1 = new Page(page.getStartOfPage(this.pageNo,
//					this.pageSize), page.getTotalCount(), this.pageSize, list);
			this.writeJSONData(page,"userCode","userName","svrCode","sycSourceSvr","synDestSvr","synOccrTime");
		} catch (Exception e) {
			e.printStackTrace();
			this.writeJSONMsg(e.getMessage());
		}
		
		return null;
	}
}

