package cn.com.sinosoft.dms.web;

import cn.com.sinosoft.dms.model.PrpDcodeRisk;
import cn.com.sinosoft.dms.service.facade.PrpDcodeRiskService;
import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

public class PrpDcodeRiskAction extends Struts2Action{
	private static final long serialVersionUID = 1L;
	private PrpDcodeRisk prpDcodeRisk;
	private PrpDcodeRiskService prpDcodeRiskService;
	private String editType;
	
	public String prepareQueryPrpDcodeRisk() {
		// 此处填补权限控制逻辑
		return SUCCESS;
	}	
	public String prepareInsertPrpDcodeRisk(){
    	return SUCCESS;
    }
	public String queryPrpDcodeRisk() {
		try {
		    Page page = prpDcodeRiskService.PrpDcodeRiskList(prpDcodeRisk,this.pageNo,this.pageSize);
		    logger.debug("【查询结果数：" + page.getTotalCount() + "】");
		    this.writeJSONData(page,"id.riskCode","id.codeType","id.codeCode");
		    logger.debug("【writeJSONData over】");
		    } catch (Exception e) {
		        e.printStackTrace();
		        this.writeJSONMsg(e.getMessage());
		    }
		    return null;
		}
	 public String prepareUpdatePrpDcodeRisk() throws Exception{
	        // TODO 编辑代码的权限校验
	        setPrpDcodeRisk(prpDcodeRiskService.findByPrimaryKey(prpDcodeRisk.getId()));
	        return SUCCESS;
	  }
	 public String updatePrpDcodeRisk(){
		 String userCode = getSession().getAttribute("UserCode").toString();
		 prpDcodeRiskService.updatePrpDcodeRisk(prpDcodeRisk,userCode);
		 return SUCCESS;
	}
	 public String insertPrpDcodeRisk() { 
		String userCode = getSession().getAttribute("UserCode").toString();
		prpDcodeRiskService.insertPrpDcodeRisk(prpDcodeRisk,userCode);
		logger.debug("【插入新的代码】");
		return SUCCESS;
	}
	public PrpDcodeRisk getPrpDcodeRisk() {
		return prpDcodeRisk;
	}
	public void setPrpDcodeRisk(PrpDcodeRisk prpDcodeRisk) {
		this.prpDcodeRisk = prpDcodeRisk;
	}
	public PrpDcodeRiskService getPrpDcodeRiskService() {
		return prpDcodeRiskService;
	}
	public void setPrpDcodeRiskService(PrpDcodeRiskService prpDcodeRiskService) {
		this.prpDcodeRiskService = prpDcodeRiskService;
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


}
