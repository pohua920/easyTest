package cn.com.sinosoft.ims.log.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.ims.log.model.UtiIExceptionLog;
import cn.com.sinosoft.ims.log.model.UtiILoginLog;
import cn.com.sinosoft.ims.log.service.facade.UtiIExceptionLogService;
import cn.com.sinosoft.ims.log.vo.UtiIExceptionLogVO;
import cn.com.sinosoft.ims.log.vo.UtiILoginLogVO;
import cn.com.sinosoft.ims.svr.service.facade.UtiISvrService;

public class UtiIExceptionLogAction extends Struts2Action {
	
	private UtiIExceptionLogService utiIExceptionLogService ;
	private UtiISvrService utiISvrService;
	private UtiIExceptionLog utiIExceptionLog;
	private String userName;
	private List<UtiIExceptionLog> list = new ArrayList<UtiIExceptionLog>();
	
//----------------getter  and  setter----------
	
	public UtiIExceptionLogService getUtiIExceptionLogService() {
		return utiIExceptionLogService;
	}
	public void setUtiIExceptionLogService(
			UtiIExceptionLogService utiIExceptionLogService) {
		this.utiIExceptionLogService = utiIExceptionLogService;
	}
	public UtiISvrService getUtiISvrService() {
		return utiISvrService;
	}
	public void setUtiISvrService(UtiISvrService utiISvrService) {
		this.utiISvrService = utiISvrService;
	}
	public UtiIExceptionLog getUtiIExceptionLog() {
		return utiIExceptionLog;
	}
	public void setUtiIExceptionLog(UtiIExceptionLog utiIExceptionLog) {
		this.utiIExceptionLog = utiIExceptionLog;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<UtiIExceptionLog> getList() {
		return list;
	}
	public void setList(List<UtiIExceptionLog> list) {
		this.list = list;
	}
	//--------------------method--------------------
	public void insertExceptionLog(UtiIExceptionLog utiIExceptionLog){
		
		utiIExceptionLogService.insertMethod(utiIExceptionLog);
	}
	
	public String prepareViewExceptionLog(){
		
		return "success";
	}
	
	public String viewExceptionLog(){
		Page page = utiIExceptionLogService.getLogList(utiIExceptionLog,this.pageNo,this.pageSize);
		List<UtiIExceptionLogVO> voList = new ArrayList<UtiIExceptionLogVO>();
		list = page.getResult();
		for (int i = 0; i < list.size(); i++) {
			//String date = new SimpleDateFormat("yyyy-MM-dd").format(utiIExceptionLog.getOccurTime());    
		//	equpForm.setPioDate(pioDate);   
			utiIExceptionLog = (UtiIExceptionLog) list.get(i);
			SimpleDateFormat sdf=new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss"); 
			String date = sdf.format(utiIExceptionLog.getOccurTime());
			UtiIExceptionLogVO utiIExceptionLogVO = new UtiIExceptionLogVO();
			utiIExceptionLogVO.setUserCode(utiIExceptionLog.getUserCode());
			utiIExceptionLogVO.setOccurTime(date);
			if(utiIExceptionLog.getErrorMsg().indexOf(":")>0){
				String[] args = (utiIExceptionLog.getErrorMsg()).split(":");
				StringBuffer sb = new StringBuffer();
				for(int j=1;j<args.length;j++){
					sb.append(args[j]);
				}
				utiIExceptionLogVO.setDescription(sb.toString());
			}else{
				utiIExceptionLogVO.setDescription(utiIExceptionLog.getErrorMsg());
			}
//			utiIExceptionLogVO.setDescription(utiIExceptionLog.getErrorMsg());
			if(utiIExceptionLog.getUserCode()!=null){
				userName = utiISvrService.getUserNameByCode(utiIExceptionLog.getUserCode());
			}else{
				userName = " ";
			}
			utiIExceptionLogVO.setUserName(userName);
			voList.add(utiIExceptionLogVO);
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
//			Page page = utiIExceptionLogService.getLogList(utiIExceptionLog,userName, this.pageNo,
//					this.pageSize);
//			List pageList = page.getResult();
			// Object[] o = new Object[10];
//			List list = new ArrayList();
			Page page1 = new Page(page.getStartOfPage(this.pageNo,
					this.pageSize), page.getTotalCount(), this.pageSize, voList);
			this.writeJSONData(page1,"userCode","userName","occurTime","description");
		} catch (Exception e) {
			e.printStackTrace();
			this.writeJSONMsg(e.getMessage());
		}
		return null;
	}
}
