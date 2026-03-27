package cn.com.sinosoft.dms.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.sinosoft.dms.model.PrpDbank;
import cn.com.sinosoft.dms.service.facade.PrpDbankService;

public class PrpDbankAction extends Struts2Action {

    private static final long serialVersionUID = 1L;
    private static Log        logger           = LogFactory.getLog(PrpDbankAction.class);
    private PrpDbankService   prpDbankService;
    private PrpDbank          prpDbank;
    private String            bankCode;
    private String            editType;
    private String chkbox;


	public String getChkbox() {
		return chkbox;
	}

	public void setChkbox(String chkbox) {
		this.chkbox = chkbox;
	}

	public String getEditType() {
        return editType;
    }

    public void setEditType(String editType) {
        this.editType = editType;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public PrpDbank getPrpDbank() {
        return prpDbank;
    }

    public void setPrpDbank(PrpDbank prpDbank) {
        this.prpDbank = prpDbank;
    }

    public PrpDbankService getPrpDbankService() {
        return prpDbankService;
    }

    public void setPrpDbankService(PrpDbankService prpDbankService) {
        this.prpDbankService = prpDbankService;
    }

    public String prepareQueryPrpDbank() {
        // 此处填补权限控制逻辑
        return SUCCESS;
    }
    
    public String prepareInsertPrpDbank(){
    	return SUCCESS;
    }
    
    public String insertPrpDbank(){
    	logger.debug("【插入新的代码】");
    	String userCode = getSession().getAttribute("UserCode").toString();
    	prpDbankService.insertPrpDbank(prpDbank,userCode);
    	return SUCCESS;
    }

    public String queryPrpDbank() {
        logger.debug("【查询金融机构代码prpDbank开始】");
        try {
        	String userCode = getSession().getAttribute("UserCode").toString();
            Page page = prpDbankService.getPrpDbankList(prpDbank, userCode,this.pageNo, this.pageSize);
            logger.debug("【查询结果数：" + page.getTotalCount() + "】");
            this.writeJSONData(page, "bankCode", "bankName", "bankType", "comCode", "validStatus");
        } catch (Exception e) {
            e.printStackTrace();
            this.writeJSONMsg(e.getMessage());
        }
        return null;
    }

    public String prepareUpdatePrpDbank() {
        // TODO 编辑代码的权限校验
        logger.debug("【修改页面查询金融机构】");
        setPrpDbank(prpDbankService.findByPrimaryKey(getBankCode()));
        return SUCCESS;
    }
    
    public String updatePrpDbank(){
        logger.debug("【修改金融机构】");
        String userCode = getSession().getAttribute("UserCode").toString();
        prpDbankService.updatePrpDbank(prpDbank,userCode);
        setEditType("view");
        return SUCCESS;
    }
    
    public void deletePrpDbank(){
    	logger.debug("【删除金融机构】");
    	List list = new ArrayList();
    	String[] checkedValues = chkbox.split(",");
    	for(int i=0;i<checkedValues.length;i++){
    		String code = checkedValues[i];
    		 setPrpDbank(prpDbankService.findByPrimaryKey(code));
//        	 prpDbankService.deletePrpDbank(prpDbank);
        	 list.add(prpDbank);
    	}
    	prpDbankService.deleteAll(list);
    }
    
    public void changeValidStatus(){
    	String userCode = getSession().getAttribute("UserCode").toString();
		bankCode = prpDbank.getBankCode();
		prpDbank = prpDbankService.findByPrimaryKey(bankCode);
		String validStatus = prpDbank.getValidStatus();
		if("1".equals(validStatus)){
				prpDbank.setValidStatus("0");
				prpDbankService.updatePrpDbank(prpDbank,userCode);
		}else{
				prpDbank.setValidStatus("1");
				prpDbankService.updatePrpDbank(prpDbank,userCode);
		}
	}
    
}
