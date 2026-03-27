package cn.com.sinosoft.dms.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;
import cn.com.sinosoft.dms.model.PrpDproject;
import cn.com.sinosoft.dms.service.facade.PrpDprojectService;

public class PrpDprojectAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	 private PrpDprojectService   prpDprojectService;
	 private PrpDproject         prpDproject;
	 private String                projectCode;
	 private String                editType;
	 private String                chkbox;

		public PrpDprojectService getPrpDprojectService() {
			return prpDprojectService;
		}

		public void setPrpDprojectService(PrpDprojectService prpDprojectService) {
			this.prpDprojectService = prpDprojectService;
		}

		public PrpDproject getPrpDproject() {
			return prpDproject;
		}

		public void setPrpDproject(PrpDproject prpDproject) {
			this.prpDproject = prpDproject;
		}

		public String getProjectCode() {
			return projectCode;
		}

		public void setProjectCode(String projectCode) {
			this.projectCode = projectCode;
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

		public static long getSerialVersionUID() {
			return serialVersionUID;
		}	
	
	public String prepareQueryPrpDproject(){
		
		return SUCCESS;
	}
	
	public String queryPrpDproject() {
        logger.debug("【查询专管专营代码prpDresource开始】");
        try {
        	String userCode = getSession().getAttribute("UserCode").toString();
            Page page = prpDprojectService.getPrpDprojectList(prpDproject, userCode,pageNo, pageSize);
            logger.debug("【查询结果数：" + page.getTotalCount() + "】");
            this.writeJSONData(page, "projectCode", "projectCName", "creatorCode", "comCode","validInd");
            logger.debug("【writeJSONData over】");
        } catch (Exception e) {
            e.printStackTrace();
            this.writeJSONMsg(e.getMessage());
        }
        return null;
    }
	
	public String prepareInsertPrpDproject(){
		
		return SUCCESS;
	}
	
    public String insertPrpDproject(){
    	String userCode = getSession().getAttribute("UserCode").toString();
    	prpDprojectService.insertPrpDproject(prpDproject, userCode);
    	return SUCCESS;
    }
    
	 public String prepareUpdatePrpDproject() {
	        setPrpDproject(prpDprojectService.findByPrimaryKey(getProjectCode()));
	        return SUCCESS;
	    }
	 
	 public String updatePrpDproject(){
	    	String userCode = getSession().getAttribute("UserCode").toString();
	    	prpDprojectService.updatePrpDproject(prpDproject, userCode);
	        setEditType("view");
	        return SUCCESS;
	    }
	 
	 public void changeValidStatus(){
	    	String userCode = getSession().getAttribute("UserCode").toString();
	    	projectCode = prpDproject.getProjectCode();
	    	prpDproject = prpDprojectService.findByPrimaryKey(projectCode);
			String validInd = prpDproject.getValidInd();
			if("1".equals(validInd)){
					prpDproject.setValidInd("0");
					prpDprojectService.updatePrpDproject(prpDproject, userCode);
			}else{
				prpDproject.setValidInd("1");
				prpDprojectService.updatePrpDproject(prpDproject, userCode);
			}
		}

 
}
