package cn.com.sinosoft.dms.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDagent;
import cn.com.sinosoft.dms.model.PrpDagentAll;
import cn.com.sinosoft.dms.service.facade.PrpDagentService;

public class PrpDagentAction extends Struts2Action{

	private static final long serialVersionUID = 1L;
	 private PrpDagentService   prpDagentService;
	 private PrpDagent          prpDagent;
	 private PrpDagentAll		prpDagentAll;
	 private String             agentCode;
	 private String             editType;
	 private String           chkbox;
	 
	public String getChkbox() {
		return chkbox;
	}
	public void setChkbox(String chkbox) {
		this.chkbox = chkbox;
	}
	public PrpDagentService getPrpDagentService() {
		return prpDagentService;
	}
	public void setPrpDagentService(PrpDagentService prpDagentService) {
		this.prpDagentService = prpDagentService;
	}

	public PrpDagent getPrpDagent() {
		return prpDagent;
	}
	public void setPrpDagent(PrpDagent prpDagent) {
		this.prpDagent = prpDagent;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getEditType() {
		return editType;
	}
	public void setEditType(String editType) {
		this.editType = editType;
	}
	
	
	public String prepareQueryPrpDagent() {
	        // 此处填补权限控制逻辑
	        return SUCCESS;
	 }
	 
	  
	    public String prepareInsertPrpDagent(){
	    	return SUCCESS;
	    }
	    
	    public String insertPrpDagent(){
	    	String userCode = getSession().getAttribute("UserCode").toString();
	    	prpDagentService.insertPrpDagent(prpDagent,userCode);
	    	return SUCCESS;
	    }

	    public String queryPrpDagent() {
	    	String userCode = getSession().getAttribute("UserCode").toString();
	        try {
	        	String deployCom = getSession().getAttribute("deployCom").toString();
	        	//modify by duanfa 20110726 start 总公司改为31000000
//	        	if(deployCom.equals("00000000")){        		
	        	if(deployCom.equals("31000000")){        		
	        		//modify by duanfa 20110726 end
	        		Page page = prpDagentService.getPrpDagentAllList(prpDagent, userCode,this.pageNo, this.pageSize);
		            logger.debug("【查询结果数：" + page.getTotalCount() + "】");
		            this.writeJSONData(page, "id.agentCode", "id.locateComCode","agentName", "addressName", "postCode", "agentType", "validStatus");
		            logger.debug("【writeJSONData over】");
	        	}  
	        	else{
	        		Page page = prpDagentService.getPrpDagentList(prpDagent, userCode,this.pageNo, this.pageSize);
		            logger.debug("【查询结果数：" + page.getTotalCount() + "】");
		            this.writeJSONData(page, "agentCode", "agentName", "addressName", "postCode", "agentType", "validStatus");
		            logger.debug("【writeJSONData over】");
	        	}
	        } catch (Exception e) {
	            e.printStackTrace();
	            this.writeJSONMsg(e.getMessage());
	        }
	        return null;
	    }
	    //进入分公司页面
	    public String prepareUpdatePrpDagent() {
	    	setPrpDagent(prpDagentService.findByPrimaryKey(getAgentCode()));    	
	        return SUCCESS;	 
	    	}
	    //进入总公司页面
	    public String prepareUpdatePrpDagentAll(){
	    	setPrpDagentAll(prpDagentService.findByPrimaryKey2(getAgentCode()));
	        return SUCCESS;
	    }

	    public String updatePrpDagent(){
	    	String userCode = getSession().getAttribute("UserCode").toString();
	        prpDagentService.updatePrpDagent(prpDagent,userCode);
	        setEditType("view");
	        return SUCCESS;
	    }
	    
	    public void deletePrpDagent(){
	    	//以后在jsp页面增加多选框实现批量删除
	    	List list = new ArrayList();
			String[] checkedValues = chkbox.split(",");
	    	for(int i=0;i<checkedValues.length;i++){
	    		String code = checkedValues[i];
	    		 setPrpDagent(prpDagentService.findByPrimaryKey(code));
//		    	 prpDagentService.deletePrpDagent(prpDagent);
		    	 list.add(prpDagent);
	    	}
	    	prpDagentService.deleteAll(list);
	    }
	    
	    public void changeValidStatus(){
	    	String userCode = getSession().getAttribute("UserCode").toString();
			agentCode = prpDagent.getAgentCode();
			prpDagent = prpDagentService.findByPrimaryKey(agentCode);
			String validStatus = prpDagent.getValidStatus();
			if("1".equals(validStatus)){
					prpDagent.setValidStatus("0");
					prpDagentService.updatePrpDagent(prpDagent,userCode);
			}else{
					prpDagent.setValidStatus("1");
					prpDagentService.updatePrpDagent(prpDagent,userCode);
			}
		}
		public PrpDagentAll getPrpDagentAll() {
			return prpDagentAll;
		}
		public void setPrpDagentAll(PrpDagentAll prpDagentAll) {
			this.prpDagentAll = prpDagentAll;
		}
}
