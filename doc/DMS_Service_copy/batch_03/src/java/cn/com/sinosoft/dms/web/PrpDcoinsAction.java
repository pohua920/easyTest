package cn.com.sinosoft.dms.web;

import cn.com.sinosoft.dms.model.PrpDcoins;
import cn.com.sinosoft.dms.service.facade.PrpDcoinsService;
import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

public class PrpDcoinsAction extends Struts2Action{
	private static final long serialVersionUID = 1L;
	private String editType;
	private PrpDcoins prpDcoins;
	private PrpDcoinsService prpDcoinsService;
	
	public String prepareQueryPrpDcoins() { 
		// 此处填补权限控制逻辑
		return SUCCESS;
	}	
	public String prepareInsertPrpDcoins(){
    	return SUCCESS;
    }
	public String queryPrpDcoins() {
		try {
			String userCode = getSession().getAttribute("UserCode").toString();
		    Page page = prpDcoinsService.PrpDcoinsList(prpDcoins,userCode,this.pageNo,this.pageSize);
		    logger.debug("【查询结果数：" + page.getTotalCount() + "】");
		    this.writeJSONData(page,"id.comCode","id.riskCode","id.period","coinsType","id.coinsComCode","coinsComName","coinsRate","validDate","validStatus");
		    logger.debug("【writeJSONData over】");
		    } catch (Exception e) {
		        e.printStackTrace();
		        this.writeJSONMsg(e.getMessage());
		    }
		    
		    return null;
		}
	 public String prepareUpdatePrpDcoins(){
	    setPrpDcoins(prpDcoinsService.findByPrimaryKey(prpDcoins.getId()));
	    return SUCCESS;
	  }
	public String updatePrpDcoins(){
		 String userCode = getSession().getAttribute("UserCode").toString();
		 prpDcoinsService.updatePrpDcoins(prpDcoins,userCode);
		 return SUCCESS;
		
	}
	//注销功能
	public void changeValidStatus(){
		prpDcoins  = prpDcoinsService.findByPrimaryKey(prpDcoins.getId());
		String validStatus = prpDcoins.getValidStatus();
		String userCode = getSession().getAttribute("UserCode").toString();
		if("1".equals(validStatus)){
			prpDcoins.setValidStatus("0");
			prpDcoinsService.updatePrpDcoins(prpDcoins, userCode);
		}else{
			prpDcoins.setValidStatus("1");
			prpDcoinsService.updatePrpDcoins(prpDcoins, userCode);
		}
	}
	public String insertPrpDcoins() { 
		String userCode = getSession().getAttribute("UserCode").toString();
		prpDcoinsService.insertPrpDcoins(prpDcoins,userCode);
		logger.debug("【插入新的代码】");
		return SUCCESS;
	}
	
	public String getEditType() {
		return editType;
	}
	public void setEditType(String editType) {
		this.editType = editType;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public PrpDcoins getPrpDcoins() {
		return prpDcoins;
	}
	public void setPrpDcoins(PrpDcoins prpDcoins) {
		this.prpDcoins = prpDcoins;
	}
	public PrpDcoinsService getPrpDcoinsService() {
		return prpDcoinsService;
	}
	public void setPrpDcoinsService(PrpDcoinsService prpDcoinsService) {
		this.prpDcoinsService = prpDcoinsService;
	}


}
