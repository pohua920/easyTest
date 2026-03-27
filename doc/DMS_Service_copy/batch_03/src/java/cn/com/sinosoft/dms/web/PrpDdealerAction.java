package cn.com.sinosoft.dms.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDdealer;
import cn.com.sinosoft.dms.service.facade.PrpDdealerService;

public class PrpDdealerAction extends Struts2Action{

	private static final long serialVersionUID = 1L;
	 private PrpDdealerService   prpDdealerService;
	 private PrpDdealer          prpDdealer;
	 private String             dealerCode;
	 private String             editType;
	 private String           chkbox;
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getChkbox() {
		return chkbox;
	}
	public void setChkbox(String chkbox) {
		this.chkbox = chkbox;
	}
	public PrpDdealerService getPrpDdealerService() {
		return prpDdealerService;
	}
	public void setPrpDdealerService(PrpDdealerService prpDdealerService) {
		this.prpDdealerService = prpDdealerService;
	}
	public PrpDdealer getPrpDdealer() {
		return prpDdealer;
	}
	public void setPrpDdealer(PrpDdealer prpDdealer) {
		this.prpDdealer = prpDdealer;
	}
	public String getEditType() {
		return editType;
	}
	public void setEditType(String editType) {
		this.editType = editType;
	}
	
	 public String prepareQueryPrpDdealer() {
	        // 此处填补权限控制逻辑
	        return SUCCESS;
	 }
	 
	  
	    public String prepareInsertPrpDdealer(){
	    	return SUCCESS;
	    }
	    
	    public String insertPrpDdealer(){
	    	logger.debug("【插入新的代码】");
	    	String userCode = getSession().getAttribute("UserCode").toString();
	    	prpDdealerService.insertPrpDdealer(prpDdealer,userCode);
	    	return SUCCESS;
	    }

	    public String queryPrpDdealer() {
	        logger.debug("【查询金融机构代码prpDdealer开始】");
	        try {
	        	String userCode = getSession().getAttribute("UserCode").toString();
	            Page page = prpDdealerService.getPrpDdealerList(prpDdealer, userCode,this.pageNo, this.pageSize);
	            logger.debug("【查询结果数：" + page.getTotalCount() + "】");
	            this.writeJSONData(page, "dealerCode", "dealerName", "addressName", "capital","linkerName","phoneNumber","validStatus");
	            logger.debug("【writeJSONData over】");
	        } catch (Exception e) {
	            e.printStackTrace();
	            this.writeJSONMsg(e.getMessage());
	        }
	        return null;
	    }

	    public String prepareUpdatePrpDdealer() {
	        // TODO 编辑代码的权限校验
	        logger.debug("【修改页面查询金融机构】");
	        logger.debug("【" + getDealerCode() + "】");
	        logger.debug("【" + getEditType() + "】");
	        setPrpDdealer(prpDdealerService.findByPrimaryKey(getDealerCode()));
	        return SUCCESS;
	    }

	    public String updatePrpDdealer(){
	    	String userCode = getSession().getAttribute("UserCode").toString();
	        prpDdealerService.updatePrpDdealer(prpDdealer,userCode);
	        setEditType("view");
	        return SUCCESS;
	    }
	    
	    public void deletePrpDdealer(){
	    	//以后在jsp页面增加多选框实现批量删除
	    	List list = new ArrayList();
	    	String[] checkedValues = chkbox.split(",");
	    	for(int i=0;i<checkedValues.length;i++){
	    		String code = checkedValues[i];
	    		setPrpDdealer(prpDdealerService.findByPrimaryKey(code));
//		    	 prpDdealerService.deletePrpDdealer(prpDdealer);
	    		list.add(prpDdealer);
	    	}
	    	prpDdealerService.deleteAll(list);
	    }
	    
	    public void changeValidStatus(){
	    	String userCode = getSession().getAttribute("UserCode").toString();
			dealerCode = prpDdealer.getDealerCode();
			prpDdealer = prpDdealerService.findByPrimaryKey(dealerCode);
			String validStatus = prpDdealer.getValidStatus();
			if("1".equals(validStatus)){
					prpDdealer.setValidStatus("0");
					prpDdealerService.updatePrpDdealer(prpDdealer,userCode);
			}else{
					prpDdealer.setValidStatus("1");
					prpDdealerService.updatePrpDdealer(prpDdealer,userCode);
			}
		}
}
