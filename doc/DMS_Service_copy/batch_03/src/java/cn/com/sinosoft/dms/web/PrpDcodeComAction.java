package cn.com.sinosoft.dms.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;
import cn.com.sinosoft.dms.service.facade.PrpDcodeComService;

public class PrpDcodeComAction extends Struts2Action{
	private static final long serialVersionUID = 1L;
	private cn.com.sinosoft.dms.model.PrpDnewCodeCom prpDnewCodeCom;
	private PrpDcodeComService prpDcodeComService;
	private String editType;
	
	public String prepareQueryPrpDcodeCom(){
		return SUCCESS;
	}
	public String prepareInsertPrpDcodeCom(){
		return SUCCESS;
	}
	public String queryPrpDcodeCom(){
		try {
		    Page page = prpDcodeComService.PrpDcodeComList(prpDnewCodeCom,this.pageNo,this.pageSize);
		    logger.debug("【查询结果数：" + page.getTotalCount() + "】");
		    this.writeJSONData(page,"id.comCode","id.codeType","id.codeCode","validStatus");
		    logger.debug("【writeJSONData over】");
		    } catch (Exception e) {
		        e.printStackTrace();
		        this.writeJSONMsg(e.getMessage());
		    }
		return null;
	}
	public String insertPrpDcodeCom() { 
		String userCode = getSession().getAttribute("UserCode").toString();
		prpDcodeComService.insertPrpDcodeCom(prpDnewCodeCom,userCode);
		logger.debug("【插入新的代码】");
		return SUCCESS;
	}
	 public String prepareUpdatePrpDcodeCom() throws Exception{
	        // TODO 编辑代码的权限校验
		  setPrpDnewCodeCom(prpDcodeComService.findByPrimaryKey(prpDnewCodeCom.getId()));
	        return SUCCESS;
	  }
	public String updatePrpDcodeCom(){
		 String userCode = getSession().getAttribute("UserCode").toString();
		 prpDcodeComService.updatePrpDcodeCom(prpDnewCodeCom,userCode);
		 return SUCCESS;
		
	}
	public void changeValidStatus(){
		prpDnewCodeCom = prpDcodeComService.findByPrimaryKey(prpDnewCodeCom.getId());
		String grade = prpDnewCodeCom.getValidStatus();
		String userCode = getSession().getAttribute("UserCode").toString();
		if("1".equals(grade)){
			prpDnewCodeCom.setValidStatus("0");
			prpDcodeComService.updatePrpDcodeCom(prpDnewCodeCom,userCode);
		}else{
			prpDnewCodeCom.setValidStatus("1");
			prpDcodeComService.updatePrpDcodeCom(prpDnewCodeCom,userCode);
		}
	}
	public String getEditType() {
		return editType;
	}
	public void setEditType(String editType) {
		this.editType = editType;
	}

	public PrpDcodeComService getPrpDcodeComService() {
		return prpDcodeComService;
	}
	public void setPrpDcodeComService(PrpDcodeComService prpDcodeComService) {
		this.prpDcodeComService = prpDcodeComService;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public cn.com.sinosoft.dms.model.PrpDnewCodeCom getPrpDnewCodeCom() {
		return prpDnewCodeCom;
	}
	public void setPrpDnewCodeCom(
			cn.com.sinosoft.dms.model.PrpDnewCodeCom prpDnewCodeCom) {
		this.prpDnewCodeCom = prpDnewCodeCom;
	}

}
