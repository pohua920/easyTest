package cn.com.sinosoft.dms.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDtype;
import cn.com.sinosoft.dms.service.facade.PrpDtypeService;

public class PrpDtypeAction extends Struts2Action{

	/**
	 * 代码类型
	 */
	private static final long serialVersionUID = 1L;
	private PrpDtypeService prpDtypeService;
	private PrpDtype prpDtype;
	private String codeType;
	private String editType;
	private String chkbox;
 

	public PrpDtypeService getPrpDtypeService() {
		return prpDtypeService;
	}


	public void setPrpDtypeService(PrpDtypeService prpDtypeService) {
		this.prpDtypeService = prpDtypeService;
	}


	public PrpDtype getPrpDtype() {
		return prpDtype;
	}


	public void setPrpDtype(PrpDtype prpDtype) {
		this.prpDtype = prpDtype;
	}


	public String getCodeType() {
		return codeType;
	}


	public void setCodeType(String codeType) {
		this.codeType = codeType;
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
//----------------------------------------------------
	
	/**
	 *主菜单到prpDtype菜单的页面跳转
	 * */
	public String initframe(){
		
		return SUCCESS;
	}
		public String prepareQueryPrpDtype() {
		        // 此处填补权限控制逻辑
		        return SUCCESS;
		 }
		 
		 public String prepareInsertPrpDtype(){
			 return SUCCESS;
		 }
	 
	    public String prepareUpdatePrpDtype() {
	    	String[] checkedValues = chkbox.split(",");
	    	for(int i=0;i<checkedValues.length;i++){
	    		 codeType = checkedValues[i];
	    	}
	        setPrpDtype(prpDtypeService.findByPrimaryKey(codeType));
	        return SUCCESS;
	    }

	    
	    public String insertPrpDtype(){
	    	String userCode = getSession().getAttribute("UserCode").toString();
	    	//add by duanfa 2011-06-22 添加页面去掉了newcodetype的输入框，默认和codetype相同
	    	prpDtype.setNewCodeType(prpDtype.getCodeType());
	    	prpDtypeService.insertPrpDtype(prpDtype,userCode);
	    	return SUCCESS;
	    }

	    public String queryPrpDtype() {
	        try {
	            Page page = prpDtypeService.getPrpDtypeList(prpDtype, this.pageNo, this.pageSize);
	            this.writeJSONData(page, "codeType", "codeTypeDesc");
	        } catch (Exception e) {
	            e.printStackTrace();
	            this.writeJSONMsg(e.getMessage());
	        }
	        return null;
	    }


	    public String updatePrpDtype(){
	    	String userCode = getSession().getAttribute("UserCode").toString();
	    	prpDtypeService.updatePrpDtype(prpDtype,userCode);
	        setEditType("view");
	        return SUCCESS;
	    }
	    
	    public void deletePrpDtype(){
	    	//以后在jsp页面增加多选框实现批量删除
	    	List list = new ArrayList();
			String[] checkedValues = chkbox.split(",");
	    	for(int i=0;i<checkedValues.length;i++){
	    		String code = checkedValues[i];
	    		 setPrpDtype(prpDtypeService.findByPrimaryKey(code));
//		    	 prpDtypeService.deletePrpDtype(prpDtype);
		    	 list.add(prpDtype);
	    	}
	    	prpDtypeService.deleteAll(list);
	    }
	    public boolean checkSameKey(){
	    	
	    	return false;
	    }
}
