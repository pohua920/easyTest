package cn.com.sinosoft.dms.web;

import cn.com.sinosoft.dms.model.PrpDsettlementByr;
import cn.com.sinosoft.dms.service.facade.PrpDsettlementByrService;
import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

public class PrpDsettlementByrAction extends Struts2Action{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PrpDsettlementByr prpDsettlementByr;
	private PrpDsettlementByrService prpDsettlementByrService;
	private String editType;
	
	public String getEditType() {
		return editType;
	}
	public void setEditType(String editType) {
		this.editType = editType;
	}
	public String prepareQueryPrpDsettlementByr() {
		// 此处填补权限控制逻辑
		return SUCCESS;
	}	
	public String prepareInsertPrpDsettlementByr(){
    	return SUCCESS;
    }
	public String queryPrpDsettlementByr() {
		try {
		    Page page = prpDsettlementByrService.PrpDsettlementByrList(prpDsettlementByr,this.pageNo,this.pageSize);
		    logger.debug("【查询结果数：" + page.getTotalCount() + "】");
		    this.writeJSONData(page,"buyerUnitCode","buyerUnitName","buyerUnitAddress","validStatus","flag");
		    logger.debug("【writeJSONData over】");
		    } catch (Exception e) {
		        e.printStackTrace();
		        this.writeJSONMsg(e.getMessage());
		    }
		    return null;
		}
	 public String prepareUpdatePrpDsettlementByr() throws Exception{
	        setPrpDsettlementByr(prpDsettlementByrService.findByPrimaryKey(prpDsettlementByr.getBuyerUnitCode()));
	        if(prpDsettlementByr.getFlag()!= null){
	        	prpDsettlementByr.setFlag(prpDsettlementByr.getFlag().trim());
	        }
	        else{
	        	prpDsettlementByr.setFlag("");
	        }
	        return SUCCESS;
	  }
	public String updatePrpDsettlementByr(){
		 String userCode = getSession().getAttribute("UserCode").toString();
		 prpDsettlementByrService.updatePrpDsettlementByr(prpDsettlementByr,userCode);
		 return SUCCESS;
		
	}
	public String insertPrpDsettlementByr() { 
		String userCode = getSession().getAttribute("UserCode").toString();
		prpDsettlementByrService.insertPrpDsettlementByr(prpDsettlementByr,userCode);
		logger.debug("【插入新的代码】");
		return SUCCESS;
	}
	public void changeValidStatus(){
		prpDsettlementByr  = prpDsettlementByrService.findByPrimaryKey(prpDsettlementByr.getBuyerUnitCode());
		String validStatus = prpDsettlementByr.getValidStatus();
		String userCode = getSession().getAttribute("UserCode").toString();
		if("1".equals(validStatus)){
			prpDsettlementByr.setValidStatus("0");
			prpDsettlementByrService.updatePrpDsettlementByr(prpDsettlementByr, userCode);
		}else{
			prpDsettlementByr.setValidStatus("1");
			prpDsettlementByrService.updatePrpDsettlementByr(prpDsettlementByr, userCode);
		}
	}
	public PrpDsettlementByr getPrpDsettlementByr() {
		return prpDsettlementByr;
	}
	public void setPrpDsettlementByr(PrpDsettlementByr prpDsettlementByr) {
		this.prpDsettlementByr = prpDsettlementByr;
	}
	public PrpDsettlementByrService getPrpDsettlementByrService() {
		return prpDsettlementByrService;
	}
	public void setPrpDsettlementByrService(
			PrpDsettlementByrService prpDsettlementByrService) {
		this.prpDsettlementByrService = prpDsettlementByrService;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

}
