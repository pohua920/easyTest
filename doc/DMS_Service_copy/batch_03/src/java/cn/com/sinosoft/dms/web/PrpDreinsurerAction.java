package cn.com.sinosoft.dms.web;

import cn.com.sinosoft.dms.model.PrpDreinsurer;
import cn.com.sinosoft.dms.service.facade.PrpDreinsurerService;
import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

public class PrpDreinsurerAction extends Struts2Action{
	private static final long serialVersionUID = 1L;
	private String editType;
	private PrpDreinsurer prpDreinsurer;
	private PrpDreinsurerService prpDreinsurerService;
	public String prepareQueryPrpDreinsurer(){
		return SUCCESS;
	}
	public PrpDreinsurerService getPrpDreinsurerService() {
		return prpDreinsurerService;
	}
	public void setPrpDreinsurerService(PrpDreinsurerService prpDreinsurerService) {
		this.prpDreinsurerService = prpDreinsurerService;
	}
	public String prepareInsertPrpDreinsurer(){
		return SUCCESS;
	}
	public String queryPrpDreinsurer(){
		try {
		    Page page = prpDreinsurerService.PrpDreinsurerList(prpDreinsurer,this.pageNo,this.pageSize);
		    logger.debug("【查询结果数：" + page.getTotalCount() + "】");
		    this.writeJSONData(page,"reinsCode","longName","regionCode","countryName","validStatus");
		    logger.debug("【writeJSONData over】");
		    } catch (Exception e) {
		        e.printStackTrace();
		        this.writeJSONMsg(e.getMessage());
		    }
		return null;
		
	}
	
	public String insertPrpDreinsurer() { 
		String userCode = getSession().getAttribute("UserCode").toString();
		prpDreinsurerService.insertPrpDreinsurer(prpDreinsurer,userCode);
		logger.debug("【插入新的代码】");
		return SUCCESS;
	}
	 public String prepareUpdatePrpDreinsurer() throws Exception{
	        // TODO 编辑代码的权限校验
	        setPrpDreinsurer(prpDreinsurerService.findByPrimaryKey(prpDreinsurer.getReinsCode()));
	        return SUCCESS;
	  }
	public String updatePrpDreinsurer(){
		 String userCode = getSession().getAttribute("UserCode").toString();
		 prpDreinsurerService.updatePrpDreinsurer(prpDreinsurer,userCode);
		 return SUCCESS;
		
	}
	public void changeValidStatus(){
		prpDreinsurer = prpDreinsurerService.findByPrimaryKey(prpDreinsurer.getReinsCode());
		String grade = prpDreinsurer.getValidStatus();
		String userCode = getSession().getAttribute("UserCode").toString();
		if("1".equals(grade)){
			prpDreinsurer.setValidStatus("0");
			prpDreinsurerService.updatePrpDreinsurer(prpDreinsurer,userCode);
		}else{
			prpDreinsurer.setValidStatus("1");
			prpDreinsurerService.updatePrpDreinsurer(prpDreinsurer,userCode);
		}
	}
	public PrpDreinsurer getPrpDreinsurer() {
		return prpDreinsurer;
	}
	public void setPrpDreinsurer(PrpDreinsurer prpDreinsurer) {
		this.prpDreinsurer = prpDreinsurer;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public String getEditType() {
		return editType;
	}
	public void setEditType(String editType) {
		this.editType = editType;
	}

}
