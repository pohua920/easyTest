package cn.com.sinosoft.dms.web;

import cn.com.sinosoft.dms.model.PrpDsettlementLkr;
import cn.com.sinosoft.dms.service.facade.PrpDsettlementLkrService;
import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

public class PrpDsettlementLkrAction extends Struts2Action{
	private static final long serialVersionUID = 1L;
	private PrpDsettlementLkr prpDsettlementLkr;
	private PrpDsettlementLkrService prpDsettlementLkrService;
	private String editType;
	
	public String getEditType() {
		return editType;
	}
	public void setEditType(String editType) {
		this.editType = editType;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public String prepareQueryPrpDsettlementLkr() { 
		// 此处填补权限控制逻辑
		return SUCCESS;
	}	
	public String prepareInsertPrpDsettlementLkr(){
    	return SUCCESS;
    }
	public String queryPrpDsettlementLkr() {
		try {
			String userCode = getSession().getAttribute("UserCode").toString();
		    Page page = prpDsettlementLkrService.PrpDsettlementLkrList(prpDsettlementLkr,userCode,this.pageNo,this.pageSize);
		    logger.debug("【查询结果数：" + page.getTotalCount() + "】");
		    this.writeJSONData(page,"linkerCode","linkerName","comCode","phoneNumber","mobile","faxNumber","validStatus","flag");
		    logger.debug("【writeJSONData over】");
		    } catch (Exception e) {
		        e.printStackTrace();
		        this.writeJSONMsg(e.getMessage());
		    }
		    return null;
		}
	 public String prepareUpdatePrpDsettlementLkr(){
	        setPrpDsettlementLkr(prpDsettlementLkrService.findByPrimaryKey(prpDsettlementLkr.getLinkerCode()));
	        if(prpDsettlementLkr.getFlag()!= null){
	        	prpDsettlementLkr.setFlag(prpDsettlementLkr.getFlag().trim());
	        }
	        else{
	        	prpDsettlementLkr.setFlag("");
	        }
	        return SUCCESS;
	  }
	public String updatePrpDsettlementLkr(){
		 String userCode = getSession().getAttribute("UserCode").toString();
		 prpDsettlementLkrService.updatePrpDsettlementLkr(prpDsettlementLkr,userCode);
		 return SUCCESS;
		
	}
	//注销功能
	public void changeValidStatus(){
		prpDsettlementLkr  = prpDsettlementLkrService.findByPrimaryKey(prpDsettlementLkr.getLinkerCode());
		String validStatus = prpDsettlementLkr.getValidStatus();
		String userCode = getSession().getAttribute("UserCode").toString();
		if("1".equals(validStatus)){
			prpDsettlementLkr.setValidStatus("0");
			prpDsettlementLkrService.updatePrpDsettlementLkr(prpDsettlementLkr, userCode);
		}else{
			prpDsettlementLkr.setValidStatus("1");
			prpDsettlementLkrService.updatePrpDsettlementLkr(prpDsettlementLkr, userCode);
		}
	}
	public String insertPrpdsettlementLkr() { 
		String userCode = getSession().getAttribute("UserCode").toString();
		prpDsettlementLkrService.insertPrpDsettlementLkr(prpDsettlementLkr,userCode);
		logger.debug("【插入新的代码】");
		return SUCCESS;
	}
	public PrpDsettlementLkr getPrpDsettlementLkr() {
		return prpDsettlementLkr;
	}
	public void setPrpDsettlementLkr(PrpDsettlementLkr prpDsettlementLkr) {
		this.prpDsettlementLkr = prpDsettlementLkr;
	}
	public PrpDsettlementLkrService getPrpDsettlementLkrService() {
		return prpDsettlementLkrService;
	}
	public void setPrpDsettlementLkrService(
			PrpDsettlementLkrService prpDsettlementLkrService) {
		this.prpDsettlementLkrService = prpDsettlementLkrService;
	}
}
