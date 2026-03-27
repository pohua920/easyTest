package cn.com.sinosoft.dms.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;
import cn.com.sinosoft.dms.model.PrpDriskEngage;
import cn.com.sinosoft.dms.service.facade.PrpDriskEngageService;

public class PrpDriskEngageAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	 private PrpDriskEngageService   prpDriskEngageService;
	 private PrpDriskEngage         prpDriskEngage;
	 private String                riskCode;
	 private String                editType;
	 private String                chkbox;

		public PrpDriskEngageService getPrpDriskEngageService() {
			return prpDriskEngageService;
		}

		public void setPrpDriskEngageService(PrpDriskEngageService prpDriskEngageService) {
			this.prpDriskEngageService = prpDriskEngageService;
		}

		public PrpDriskEngage getPrpDriskEngage() {
			return prpDriskEngage;
		}

		public void setPrpDriskEngage(PrpDriskEngage prpDriskEngage) {
			this.prpDriskEngage = prpDriskEngage;
		}

		public String getRiskCode() {
			return riskCode;
		}

		public void setRiskCode(String riskCode) {
			this.riskCode = riskCode;
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
	
	public String prepareQueryPrpDriskEngage(){
		return SUCCESS;
	}
	
	public String queryPrpDriskEngage() {
        logger.debug("【查询专管专营代码prpDresource开始】");
        try {
        	String userCode = getSession().getAttribute("UserCode").toString();
            Page page = prpDriskEngageService.getPrpDriskEngageList(prpDriskEngage, userCode,pageNo, pageSize);
            logger.debug("【查询结果数：" + page.getTotalCount() + "】");
            this.writeJSONData(page, "id.riskCode", "id.clauseCode", "id.engageCode", "oldEngageCode","validInd");
            logger.debug("【writeJSONData over】");
        } catch (Exception e) {
            e.printStackTrace();
            this.writeJSONMsg(e.getMessage());
        }
        return null;
    }
	
	public String prepareInsertPrpDriskEngage(){
		
		return SUCCESS;
	}
	
    public String insertPrpDriskEngage(){
    	logger.debug("【插入新的代码】");
    	String userCode = getSession().getAttribute("UserCode").toString();
    	prpDriskEngageService.insertPrpDriskEngage(prpDriskEngage, userCode);
    	return SUCCESS;
    }
    
	 public String prepareUpdatePrpDriskEngage() {
	        // TODO 编辑代码的权限校验
	        setPrpDriskEngage(prpDriskEngageService.findByPrimaryKey(prpDriskEngage.getId()));
	        return SUCCESS;
	    }
	 
	 public String updatePrpDriskEngage(){
	    	String userCode = getSession().getAttribute("UserCode").toString();
	    	prpDriskEngageService.updatePrpDriskEngage(prpDriskEngage, userCode);
	        setEditType("view");
	        return SUCCESS;
	    }
	 
	 public void changeValidStatus(){
	    	String userCode = getSession().getAttribute("UserCode").toString();
	    	prpDriskEngage = prpDriskEngageService.findByPrimaryKey(prpDriskEngage.getId());
			String validInd = prpDriskEngage.getValidInd();
			if("1".equals(validInd)){
					prpDriskEngage.setValidInd("0");
					prpDriskEngageService.updatePrpDriskEngage(prpDriskEngage, userCode);
			}else{
				prpDriskEngage.setValidInd("1");
				prpDriskEngageService.updatePrpDriskEngage(prpDriskEngage, userCode);
			}
		} 
}
