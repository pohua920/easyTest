package cn.com.sinosoft.ims.log.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.sinosoft.sysframework.exception.BusinessException;

import cn.com.sinosoft.ims.log.model.UtiILoginLog;
import cn.com.sinosoft.ims.log.service.facade.UtiILoginLogService;
import cn.com.sinosoft.ims.log.vo.UtiILoginLogVO;
import cn.com.sinosoft.ims.svr.service.facade.UtiISvrService;

public class UtiILoginLogAction extends Struts2Action {
	
	private UtiILoginLogService utiILoginLogService;
	private UtiILoginLog utiILoginLog;
	private UtiISvrService utiISvrService ;
	private String userName;
	private List<UtiILoginLog> list = new ArrayList<UtiILoginLog>();
	
	// ----------------- getter & setter ---------------
	public UtiILoginLogService getUtiILoginLogService() {
		return utiILoginLogService;
	}

	public void setUtiILoginLogService(UtiILoginLogService utiILoginLogService) {
		this.utiILoginLogService = utiILoginLogService;
	}

	public UtiILoginLog getUtiILoginLog() {
		return utiILoginLog;
	}

	public void setUtiILoginLog(UtiILoginLog utiILoginLog) {
		this.utiILoginLog = utiILoginLog;
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
	
	public List<UtiILoginLog> getList() {
		return list;
	}

	public void setList(List<UtiILoginLog> list) {
		this.list = list;
	}

	//-----------------methods---------------------
	public void insertLoginLog(){
		
	}
	
	public void deleteLoginLog(){
		
	}
	public String prepareViewLoginLog(){
		
		return "success";
	}
	
	public String viewLoginLog(){
		Page page = utiILoginLogService.getLogList(utiILoginLog,this.pageNo,this.pageSize);
		list  = page.getResult();
		List<UtiILoginLogVO> voList = new ArrayList<UtiILoginLogVO>();
		for (int i = 0; i < list.size(); i++) {
			utiILoginLog = (UtiILoginLog) list.get(i);
			SimpleDateFormat sdf=new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss"); 
			String loginDate = sdf.format(utiILoginLog.getLoginTime());
			UtiILoginLogVO utiILoginLogVO = new UtiILoginLogVO();
			utiILoginLogVO.setUserCode(utiILoginLog.getUserCode());
			utiILoginLogVO.setLoginTime(loginDate);
			if(utiILoginLog.getExitTime()!=null){
				String exitDate = sdf.format(utiILoginLog.getExitTime());
				utiILoginLogVO.setExitTime(exitDate);
			}
			userName = utiISvrService.getUserNameByCode(utiILoginLog.getUserCode());
			utiILoginLogVO.setUserName(userName);
			voList.add(utiILoginLogVO);
		}
//		if(voList.size() <= 10){
//			page = new Page(1,voList.size(),voList.size(),voList);
//		}else{
//			int size = voList.size();
//			//取本页中的数据
//			if(size > pageSize*pageNo){
//				//如果数据总量大于 当前要取的数据的最大值
//				voList = voList.subList((pageNo-1)*10, pageSize*pageNo);
//			}else{
//				//小于，则为剩余的数据
//				voList = voList.subList((pageNo-1)*10, size);
//			}
//			if((size - pageNo*pageSize) > 10){
//				//如果剩余数据的数量大于10，则本页的数据数量为10
//				page = new Page(1,size,10,voList);
//			}else{
//				//小于10，则本页数据数量为余数
//				page = new Page(1,size,size%10,voList);
//			}
//		}	
		try {
			// utiISvr = (UtiISvr) getSession().getAttribute("utiISvr");
//			Page page = utiILoginLogService.getLogList(utiILoginLog,userName, this.pageNo,this.pageSize);
//			List pageList = page.getResult();
			// Object[] o = new Object[10];
//			List list = new ArrayList();
//			for (int i = 0; i < pageList.size(); i++) {
//				utiILoginLog = (UtiILoginLog) pageList.get(i);
//				SimpleDateFormat sdf=new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss"); 
//				String loginDate = sdf.format(utiILoginLog.getLoginTime());
//				UtiILoginLogVO utiILoginLogVO = new UtiILoginLogVO();
//				utiILoginLogVO.setUserCode(utiILoginLog.getUserCode());
//				utiILoginLogVO.setLoginTime(loginDate);
//				if(utiILoginLog.getExitTime()!=null){
//					String exitDate = sdf.format(utiILoginLog.getExitTime());
//					utiILoginLogVO.setExitTime(exitDate);
//				}
//				userName = utiISvrService.getUserNameByCode(utiILoginLog.getUserCode());
//				utiILoginLogVO.setUserName(userName);
//				list.add(utiILoginLogVO);
//			}
//			Page page1 = new Page(page.getStartOfPage(this.pageNo,
//					this.pageSize), page.getTotalCount(), this.pageSize, list);
			page = new Page(page.getStartOfPage(this.pageNo,
					this.pageSize), page.getTotalCount(), this.pageSize, voList);
			this.writeJSONData(page,"userCode","userName","loginTime","exitTime");
		} catch (Exception e) {
//			e.printStackTrace();
			this.writeJSONMsg(e.getMessage());
		}
		
		return null;
	}
}
