package cn.com.sinosoft.dms.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;
import cn.com.sinosoft.dms.model.PrpVersion;
import cn.com.sinosoft.dms.service.facade.PrpVersionService;

public class PrpVersionAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	 private PrpVersionService   prpVersionService;
	 private PrpVersion          prpVersion;
	 private String 			 productId;
	 private String                editType;
	public String prepareQueryPrpVersion(){
		
		return SUCCESS;
	}
	
	public String prepareUpdatePrpVersion(){
		setPrpVersion(prpVersionService.findByPrimaryKey(getProductId()));
		return SUCCESS;
	}
	
	public String queryPrpVersion() {
        logger.debug("【查询项目版本号projectVersion开始】");
        try {
            Page page = prpVersionService.getPrpVersionList(prpVersion, this.pageNo, this.pageSize);
            logger.debug("【查询结果数：" + page.getTotalCount() + "】");
            this.writeJSONData(page, "projectName", "id.projectVersion", "primaryVersion", "id.productId", "times", "updateDate");
            logger.debug("【writeJSONData over】");
        } catch (Exception e) {
            e.printStackTrace();
            this.writeJSONMsg(e.getMessage());
        }
        return null;
    }

	public PrpVersionService getPrpVersionService() {
		return prpVersionService;
	}

	public void setPrpVersionService(PrpVersionService prpVersionService) {
		this.prpVersionService = prpVersionService;
	}

	public PrpVersion getPrpVersion() {
		return prpVersion;
	}

	public void setPrpVersion(PrpVersion prpVersion) {
		this.prpVersion = prpVersion;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}
