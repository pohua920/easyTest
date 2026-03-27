package cn.com.sinosoft.dms.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;
import cn.com.sinosoft.dms.model.PrpDcompany;
import cn.com.sinosoft.dms.model.PrpDresource;
import cn.com.sinosoft.dms.service.facade.PrpDresourceService;

public class PrpDresourceAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	 private PrpDresourceService   prpDresourceService;
	 private PrpDresource          prpDresource;
	 private String                resourceCode;
	 private PrpDcompany		   prpDcompany;
	 private String                editType;
	 private String                chkbox;
	public PrpDresourceService getPrpDresourceService() {
		return prpDresourceService;
	}
	public void setPrpDresourceService(PrpDresourceService prpDresourceService) {
		this.prpDresourceService = prpDresourceService;
	}
	public PrpDresource getPrpDresource() {
		return prpDresource;
	}
	public void setPrpDresource(PrpDresource prpDresource) {
		this.prpDresource = prpDresource;
	}
	public String getResourceCode() {
		return resourceCode;
	}
	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}
	public String getEditType() {
		return editType;
	}
	public void setEditType(String editType) {
		this.editType = editType;
	}
	public String getChkbox() {
		return chkbox;
	}
	public void setChkbox(String chkbox) {
		this.chkbox = chkbox;
	}
	
	public String prepareQueryPrpDresource(){
		
		return SUCCESS;
	}
	
	public String queryPrpDresource() {
        logger.debug("【查询专管专营代码prpDresource开始】");
        try {
        	String userCode = getSession().getAttribute("UserCode").toString();
            Page page = prpDresourceService.getPrpDresourceList(prpDresource, userCode,this.pageNo, this.pageSize);
            logger.debug("【查询结果数：" + page.getTotalCount() + "】");
            this.writeJSONData(page, "resourceCode", "resourceName", "projectCode", "comCode","validStatus");
            logger.debug("【writeJSONData over】");
        } catch (Exception e) {
            e.printStackTrace();
            this.writeJSONMsg(e.getMessage());
        }
        return null;
    }
	
	public String prepareInsertPrpDresource(){
		
		return SUCCESS;
	}
	
    public String insertPrpDresource(){
    	logger.debug("【插入新的代码】");
    	String userCode = getSession().getAttribute("UserCode").toString();
    	prpDresourceService.insertPrpDresource(prpDresource, userCode);
    	return SUCCESS;
    }
    
	 public String prepareUpdatePrpDresource() {
	        // TODO 编辑代码的权限校验
	        logger.debug("【修改页面查询专管专营机构】");
	        logger.debug("【" + getResourceCode()+ "】");
	        logger.debug("【" + getEditType() + "】");
	        setPrpDresource(prpDresourceService.findByPrimaryKey(getResourceCode()));
	        return SUCCESS;
	    }
	 
	 public String updatePrpDresource(){
	    	String userCode = getSession().getAttribute("UserCode").toString();
	    	prpDresourceService.updatePrpDresource(prpDresource, userCode);
	        setEditType("view");
	        return SUCCESS;
	    }
	 
	 public void changeValidStatus(){
	    	String userCode = getSession().getAttribute("UserCode").toString();
	    	resourceCode = prpDresource.getResourceCode();
	    	prpDresource = prpDresourceService.findByPrimaryKey(resourceCode);
			String validStatus = prpDresource.getValidStatus();
			if("1".equals(validStatus)){
					prpDresource.setValidStatus("0");
					prpDresourceService.updatePrpDresource(prpDresource, userCode);
			}else{
					prpDresource.setValidStatus("1");
					prpDresourceService.updatePrpDresource(prpDresource, userCode);
			}
		}
	 
}
