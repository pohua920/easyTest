package com.sinosoft.undwrt.undwrtInterface.web;

import ins.framework.web.Struts2Action;

import com.sinosoft.prpall.blsvr.pg.BLPrpPhead;
import com.sinosoft.prpall.blsvr.tb.BLPrpTmain;
import com.sinosoft.prpall.schema.PrpPheadSchema;
import com.sinosoft.prpall.schema.PrpTmainSchema;
import com.sinosoft.undwrt.undwrtInterface.service.facade.TaskService;

/**
 * 提交核保處理類.
 */
public class UnderwriteSubmitAction extends Struts2Action {

	/** 請求類型. */
	private String UIAction;
	
	/** 屬性業務號. */
	private String businessNo;
	
	/** 屬性承保系統提交核保的業務接口. */
	private TaskService taskService;

	/**
	 * 提交核保.
	 * 
	 * @return 結果訊息
	 * @throws Exception
	 *             異常
	 */
	public String underwriteSubmit() throws Exception {

		String strRiskCode = "", strComCode = "", strMakeCom = "", strClassCode = "", strHandlerCode = "", strHandler1Code = "", contractNo = "", strOperatorCode = "";
		String certiType = "";
		String strUserCode = "0000000000";

		BLPrpTmain blPrpTmain = new BLPrpTmain();
		BLPrpPhead blPrpPhead = new BLPrpPhead();

		blPrpTmain.getData(businessNo);
		if (blPrpTmain.getSize() > 0) {
			certiType = "T";
			PrpTmainSchema prpTmainSchema = blPrpTmain.getArr(0);
			strRiskCode = prpTmainSchema.getRiskCode();
			strComCode = prpTmainSchema.getComCode();
			strMakeCom = prpTmainSchema.getMakeCom();
			strClassCode = prpTmainSchema.getClassCode();
			strHandlerCode = prpTmainSchema.getHandlerCode();
			strHandler1Code = prpTmainSchema.getHandler1Code();
			contractNo = prpTmainSchema.getContractNo();
			strOperatorCode = prpTmainSchema.getOperatorCode();
		}

		blPrpPhead.getData(businessNo);
		if (blPrpPhead.getSize() > 0) {
			certiType = "E";
			PrpPheadSchema prpPheadSchema = blPrpPhead.getArr(0);
			strRiskCode = prpPheadSchema.getRiskCode();
			strComCode = prpPheadSchema.getComCode();
			strClassCode = prpPheadSchema.getClassCode();
			strMakeCom = prpPheadSchema.getMakeCom();
			strHandlerCode = prpPheadSchema.getHandlerCode();
			strHandler1Code = prpPheadSchema.getHandler1Code();
			strOperatorCode = prpPheadSchema.getOperatorCode();
		}

		if("T".equals(certiType) || "E".equals(certiType)){
			/*taskService.start("11", certiType, businessNo, strRiskCode,
					strClassCode, strComCode, strMakeCom, strUserCode,
					strHandlerCode, strHandler1Code, contractNo, strOperatorCode);*/
		}else{
			taskService.startQta(businessNo,certiType,"12");
		}
		return SUCCESS;
	}

	/**
	 * 公共處理請求操作
	 * 
	 * @return 請求結果
	 */
	public String commonInput() {
		return UIAction;
	}

	/**
	 * 獲取請求類型.
	 * 
	 * @return 請求類型
	 */
	public String getUIAction() {
		return UIAction;
	}

	/**
	 * 設置請求類型.
	 * 
	 * @param uIAction
	 *            請求類型
	 */
	public void setUIAction(String uIAction) {
		UIAction = uIAction;
	}
	
	/**
	 * 獲取屬性業務號.
	 * 
	 * @return 屬性業務號的值
	 */
	public String getBusinessNo() {
		return businessNo;
	}

	/**
	 * 設置屬性業務號.
	 * 
	 * @param businessNo
	 *            待設置的業務號的值
	 */
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	/**
	 * 獲取屬性承保系統提交核保的業務接口.
	 * 
	 * @return 屬性承保系統提交核保的業務接口的值
	 */
	public TaskService getTaskService() {
		return taskService;
	}

	/**
	 * 設置屬性承保系統提交核保的業務接口.
	 * 
	 * @param taskService
	 *            待設置的承保系統提交核保的業務接口的值
	 */
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
}
