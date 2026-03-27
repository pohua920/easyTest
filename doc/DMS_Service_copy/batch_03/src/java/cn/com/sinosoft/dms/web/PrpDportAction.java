package cn.com.sinosoft.dms.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDport;
import cn.com.sinosoft.dms.service.facade.PrpDportService;

public class PrpDportAction extends Struts2Action{

	private static final long serialVersionUID = 1L;
	 private PrpDportService   prpDportService;
	 private PrpDport          prpDport;
	 private String             portCode;
	 private String             editType;
	 private String            chkbox;

	 public String getChkbox() {
		return chkbox;
	}
	public void setChkbox(String chkbox) {
		this.chkbox = chkbox;
	}
	public PrpDportService getPrpDportService() {
		return prpDportService;
	}
	public void setPrpDportService(PrpDportService prpDportService) {
		this.prpDportService = prpDportService;
	}
	public PrpDport getPrpDport() {
		return prpDport;
	}
	public void setPrpDport(PrpDport prpDport) {
		this.prpDport = prpDport;
	}

	public String getPortCode() {
		return portCode;
	}
	public void setPortCode(String portCode) {
		this.portCode = portCode;
	}
	public String getEditType() {
		return editType;
	}
	public void setEditType(String editType) {
		this.editType = editType;
	}
	
	 public String prepareQueryPrpDport() {
	        // 此处填补权限控制逻辑
	        return SUCCESS;
	 }
	 
	  
	    public String prepareInsertPrpDport(){
	    	return SUCCESS;
	    }
	    
	    public String insertPrpDport(){
	    	logger.debug("【插入新的代码】");
	    	String userCode = getSession().getAttribute("UserCode").toString();
	    	prpDportService.insertPrpDport(prpDport,userCode);
	    	return SUCCESS;
	    }

	    public String queryPrpDport() {
	        logger.debug("【查询金融机构代码prpDport开始】");
	        try {
	            Page page = prpDportService.getPrpDportList(prpDport, this.pageNo, this.pageSize);
	            logger.debug("【查询结果数：" + page.getTotalCount() + "】");
	            this.writeJSONData(page, "portCode", "portCName", "countryCode", "countryCName","validStatus");
	            logger.debug("【writeJSONData over】");
	        } catch (Exception e) {
	            e.printStackTrace();
	            this.writeJSONMsg(e.getMessage());
	        }
	        return null;
	    }

	    public String prepareUpdatePrpDport() {
	        // TODO 编辑代码的权限校验
	        logger.debug("【修改页面查询金融机构】");
	        logger.debug("【" + getPortCode() + "】");
	        logger.debug("【" + getEditType() + "】");
	        setPrpDport(prpDportService.findByPrimaryKey(getPortCode()));
	        return SUCCESS;
	    }

	    public String updatePrpDport(){
	    	String userCode = getSession().getAttribute("UserCode").toString();
	        prpDportService.updatePrpDport(prpDport,userCode);
	        setEditType("view");
	        return SUCCESS;
	    }
	    
	    public void deletePrpDport(){
	    	//以后在jsp页面增加多选框实现批量删除
	    	List list = new ArrayList();
	    	String[] checkedValues = chkbox.split(",");
	    	for(int i=0;i<checkedValues.length;i++){
	    		String code = checkedValues[i];
	    		setPrpDport(prpDportService.findByPrimaryKey(code));
//		    	 prpDportService.deletePrpDport(prpDport);
		    	 list.add(prpDport);
	    	}
	    	prpDportService.deleteAll(list);
	        
	    }
	    
	    public void changeValidStatus(){
	    	String userCode = getSession().getAttribute("UserCode").toString();
	    	portCode = prpDport.getPortCode();
			prpDport = prpDportService.findByPrimaryKey(portCode);
			String validStatus = prpDport.getValidStatus();
			if("1".equals(validStatus)){
					prpDport.setValidStatus("0");
					prpDportService.updatePrpDport(prpDport,userCode);
			}else{
					prpDport.setValidStatus("1");
					prpDportService.updatePrpDport(prpDport,userCode);
			}
		}

}
