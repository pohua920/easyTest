package cn.com.sinosoft.dms.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDdriver;
import cn.com.sinosoft.dms.service.facade.PrpDdriverService;

public class PrpDdriverAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	private PrpDdriverService prpDdriverService;
	private PrpDdriver prpDdriver;
	private String drivingLicenseNo;
	private String editType;
	private String chkbox;



	public String getChkbox() {
		return chkbox;
	}

	public void setChkbox(String chkbox) {
		this.chkbox = chkbox;
	}

	public PrpDdriverService getPrpDdriverService() {
		return prpDdriverService;
	}

	public void setPrpDdriverService(PrpDdriverService prpDdriverService) {
		this.prpDdriverService = prpDdriverService;
	}

	public PrpDdriver getPrpDdriver() {
		return prpDdriver;
	}

	public void setPrpDdriver(PrpDdriver prpDdriver) {
		this.prpDdriver = prpDdriver;
	}

	public String getDrivingLicenseNo() {
		return drivingLicenseNo;
	}

	public void setDrivingLicenseNo(String drivingLicenseNo) {
		this.drivingLicenseNo = drivingLicenseNo;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public String prepareQueryPrpDdriver() {
		// 此处填补权限控制逻辑
		return SUCCESS;
	}

	public String prepareInsertPrpDdriver() {
		return SUCCESS;
	}

	public String insertPrpDdriver() {
		logger.debug("【插入新的代码】");
		prpDdriverService.insertPrpDdriver(prpDdriver);
		return SUCCESS;
	}

	public String queryPrpDdriver() {
		logger.debug("【查询金融机构代码prpDdriver开始】");
		try {
			Page page = prpDdriverService.getPrpDdriverList(prpDdriver,
					this.pageNo, this.pageSize);
			logger.debug("【查询结果数：" + page.getTotalCount() + "】");
			this.writeJSONData(page, "drivingLicenseNo", "driverName",
					"driverSex", "identifyNumber", "awardLicenseOrgan",
					"drivingCarType");
			logger.debug("【writeJSONData over】");
		} catch (Exception e) {
			e.printStackTrace();
			this.writeJSONMsg(e.getMessage());
		}
		return null;
	}

	public String prepareUpdatePrpDdriver() {
		// TODO 编辑代码的权限校验
		logger.debug("【修改页面查询金融机构】");
		logger.debug("【" + getDrivingLicenseNo() + "】");
		logger.debug("【" + getEditType() + "】");
		setPrpDdriver(prpDdriverService.findByPrimaryKey(getDrivingLicenseNo()));
		return SUCCESS;
	}

	public String updatePrpDdriver() {
		prpDdriverService.updatePrpDdriver(prpDdriver);
		setEditType("view");
		return SUCCESS;
	}

	public void deletePrpDdriver() {
		// 以后在jsp页面增加多选框实现批量删除
		List list = new ArrayList();
		String[] checkedValues = chkbox.split(",");
    	for(int i=0;i<checkedValues.length;i++){
    		String code = checkedValues[i];
			setPrpDdriver(prpDdriverService
					.findByPrimaryKey(code));
//			prpDdriverService.deletePrpDdriver(prpDdriver);
			list.add(prpDdriver);
		}
    	prpDdriverService.deleteAll(list);
	}

}
