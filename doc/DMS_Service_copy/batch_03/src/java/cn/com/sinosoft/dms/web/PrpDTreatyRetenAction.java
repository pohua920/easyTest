package cn.com.sinosoft.dms.web;

import java.util.Map;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;
import cn.com.sinosoft.dms.model.PrpDtreatyReten;
import cn.com.sinosoft.dms.service.facade.PrpDTreatyRetenService;
import cn.com.sinosoft.dms.service.facade.PrpDcodeService;

public class PrpDTreatyRetenAction extends Struts2Action{
	private static final long serialVersionUID = 1L;
	private PrpDcodeService prpDcodeService;
	private String editType;
	private PrpDtreatyReten prpDtreatyReten;
	private String retentionValue;
	private PrpDTreatyRetenService prpDTreatyRetenService;
	private Map<String, String> riskCodeMap;
	
	public String prepareQueryPrpDTreatyReten() {
		// 此处填补权限控制逻辑
		return SUCCESS;
	}	
	public String prepareInsertPrpDTreatyReten(){
    	return SUCCESS;
    }
	public String queryPrpDTreatyReten() {
		try {
		    Page page = prpDTreatyRetenService.PrpDTreatyRetenList(prpDtreatyReten,this.pageNo,this.pageSize);
		    logger.debug("【查询结果数：" + page.getTotalCount() + "】");
		    this.writeJSONData(page,"id.uwYear","id.classCode","id.riskCode","id.serialNo","currency","grade","retentionValue","retentionRate","endDate");
		    logger.debug("【writeJSONData over】");
		    } catch (Exception e) {
		        e.printStackTrace();
		        this.writeJSONMsg(e.getMessage());
		    }
		    return null;
		}
	 public String prepareUpdatePrpDTreatyReten() throws Exception{
	        // TODO 编辑代码的权限校验
	        setPrpDtreatyReten(prpDTreatyRetenService.findByPrimaryKey(prpDtreatyReten.getId()));
	        return SUCCESS;
	  }
	public String updatePrpDTreatyReten(){
		 String userCode = getSession().getAttribute("UserCode").toString();
		 prpDTreatyRetenService.updatePrpDTreatyReten(prpDtreatyReten,userCode);
		 return SUCCESS;
		
	}
	public String insertPrpDTreatyReten() { 
		String userCode = getSession().getAttribute("UserCode").toString();
		prpDTreatyRetenService.insertPrpDTreatyReten(prpDtreatyReten,userCode);
		logger.debug("【插入新的代码】");
		return SUCCESS;
	}
	//注销功能
	public void changeValidStatus(){
		prpDtreatyReten=prpDTreatyRetenService.findByPrimaryKey(prpDtreatyReten.getId());
		String grade = prpDtreatyReten.getGrade();
		String userCode = getSession().getAttribute("UserCode").toString();
		if("1".equals(grade)){
			prpDtreatyReten.setGrade("0");
			prpDTreatyRetenService.updatePrpDTreatyReten(prpDtreatyReten,userCode);
		}else{
			prpDtreatyReten.setGrade("1");
			prpDTreatyRetenService.updatePrpDTreatyReten(prpDtreatyReten,userCode);
		}
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public PrpDtreatyReten getPrpDtreatyReten() {
		return prpDtreatyReten;
	}

	public void setPrpDtreatyReten(PrpDtreatyReten eatyReprpDtreatyRetenten) {
		this.prpDtreatyReten = eatyReprpDtreatyRetenten;
	}

	public PrpDTreatyRetenService getPrpDTreatyRetenService() {
		return prpDTreatyRetenService;
	}

	public void setPrpDTreatyRetenService(
			PrpDTreatyRetenService prpDTreatyRetenService) {
		this.prpDTreatyRetenService = prpDTreatyRetenService;
	}

	public String getEditType() {
		return editType;
	}
	public void setEditType(String editType) {
		this.editType = editType;
	}
	public String getRetentionValue() {
		return retentionValue;
	}
	public void setRetentionValue(String retentionValue) {
		this.retentionValue = retentionValue;
	}
	public Map<String, String> getRiskCodeMap() {
		return riskCodeMap;
	}
	public void setRiskCodeMap(Map<String, String> riskCodeMap) {
		this.riskCodeMap = riskCodeMap;
	}
	public PrpDcodeService getPrpDcodeService() {
		return prpDcodeService;
	}
	public void setPrpDcodeService(PrpDcodeService prpDcodeService) {
		this.prpDcodeService = prpDcodeService;
	}
}
