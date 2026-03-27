package com.sinosoft.undwrt.undwrtDeal.web;

import ins.framework.web.Struts2Action;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.platform.ui.control.action.LogUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.CommonDealSubmitService;
import com.sinosoft.undwrt.undwrtDeal.vo.TaskDealVo;

/**
 * 提交任務撤銷 .
 */
public class CommonRetractTaskAction extends Struts2Action {

	/** 屬性工作流號. */
	private String iFlowID;
	
	/** 屬性業務號. */
	private String businessNo;
	
	/** 屬性標題. */
	private String handTitle;
	
	/** 屬性跳轉頁面返回結果. */
	private String content;
	
	/** 屬性要請求的ip地址. */
	private String submitTip;
	
	/** 屬性序號. */
	private String iLogNo;
	
	/** 屬性錯誤信息. */
	private String errorMessage;
	
	/** 屬性標題. */
	private String title;
	
	/** 屬性處理類型. */
	private String handType;
	
	/** 屬性編輯類型. */
	private String editType;
	
	/** 屬性工作流日誌接口. */
	private WfLogService wfLogService;
	
	/** 屬性核保審核處理接口. */
	private CommonDealSubmitService commonDealSubmitService;

	/**
	 * 提交任務撤銷.
	 * 
	 * @return 跳轉頁面結果
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public String retractTask() throws UserException, Exception 
	{
	
    String forward = "";
    boolean blnReturn =false;
    PrpDuserDto prpDuserDto = (PrpDuserDto) this.getSession(true).getAttribute("user");
    //处理业务类型
    String handTitle = (String) this.getSession(false).getAttribute("HandTitle");
    String logMessage = handTitle + getText("undwrt.action.commonDealSubmit.task");
    String logModule = handTitle + getText("undwrt.action.commonCheckTask.dealWith");

    try {
      String flowId = StringUtils.trimToEmpty(iFlowID);
      HttpSession session = this.getSession();
      HashMap taskSubmitHashTable = (HashMap) session.getAttribute("TaskSubmitHashTable");
      TaskDealVo taskDealDto =null; 
    	  //(TaskDealVo) taskSubmitHashTable.get(flowId);
      if (taskDealDto != null) {
        session.setAttribute("taskDealDto", taskDealDto);
        forward="submittedError";
      }
      businessNo=businessNo.trim();//去除首尾的空格
      blnReturn = commonDealSubmitService.retract(businessNo, prpDuserDto);
      
      if(blnReturn){
    	  forward = "success";
          content=getText("undwrt.action.batchTaskSubmit.taskSubmitSuccess");
      }else{
          forward = "failure";
          content=getText("undwrt.action.commonRetactTask.taskWithdrawFail");
      }
      LogUtils.info(prpDuserDto, logModule,
                    prpDuserDto.getUserName() + " " + logMessage + getText("undwrt.action.commonDealSubmit.submitSucWorkflow") + iFlowID + getText("oaTaskDetail.serialNo")+"：" +
                    iLogNo);
      if (taskDealDto == null) {
        taskDealDto = new TaskDealVo();
        taskDealDto.setFlowId(flowId);
        taskDealDto.setBusinessNo(businessNo);
        taskDealDto.setSubmitted(true);
        taskDealDto.setSubmitTip(submitTip);
        //taskSubmitHashTable.put(flowId, taskDealDto);
      }
    }
    catch (UserException usee) {
      forward = "failure";
      errorMessage=usee.getErrorMessage();
      title = usee.getErrorModule();
    }
    catch (Exception e) {
      forward = "failure";
      try {
		LogUtils.info(prpDuserDto, logModule,
		                prpDuserDto.getUserName() + " " + logMessage + getText("undwrt.action.commonDealSubmit.submitFailWorkflow") + iFlowID + getText("oaTaskDetail.serialNo")+"：" +
		                iLogNo);
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

    }
    return forward;
  }
	

	/**
	 * 獲取屬性工作流號.
	 * 
	 * @return 屬性工作流號的值
	 */
	public String getiFlowID() {
		return iFlowID;
	}

	/**
	 * 設置屬性工作流號.
	 * 
	 * @param iFlowID
	 *            待設置的工作流號的值
	 */
	public void setiFlowID(String iFlowID) {
		this.iFlowID = iFlowID;
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
	 * 獲取屬性標題.
	 * 
	 * @return 屬性標題的值
	 */
	public String getHandTitle() {
		return handTitle;
	}

	/**
	 * 設置屬性標題.
	 * 
	 * @param handTitle
	 *            待設置的標題的值
	 */
	public void setHandTitle(String handTitle) {
		this.handTitle = handTitle;
	}

	/**
	 * 獲取屬性跳轉頁面返回結果.
	 * 
	 * @return 屬性跳轉頁面返回結果的值
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 設置屬性跳轉頁面返回結果.
	 * 
	 * @param content
	 *            待設置的跳轉頁面返回結果的值
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 獲取屬性要請求的ip地址.
	 * 
	 * @return 屬性要請求的ip地址的值
	 */
	public String getSubmitTip() {
		return submitTip;
	}

	/**
	 * 設置屬性要請求的ip地址.
	 * 
	 * @param submitTip
	 *            待設置的要請求的ip地址的值
	 */
	public void setSubmitTip(String submitTip) {
		this.submitTip = submitTip;
	}

	/**
	 * 獲取屬性序號.
	 * 
	 * @return 屬性序號的值
	 */
	public String getiLogNo() {
		return iLogNo;
	}

	/**
	 * 設置屬性序號.
	 * 
	 * @param iLogNo
	 *            待設置的序號的值
	 */
	public void setiLogNo(String iLogNo) {
		this.iLogNo = iLogNo;
	}

	/**
	 * 獲取屬性工作流日誌接口.
	 * 
	 * @return 屬性工作流日誌接口的值
	 */
	public WfLogService getWfLogService() {
		return wfLogService;
	}

	/**
	 * 設置屬性工作流日誌接口.
	 * 
	 * @param wfLogService
	 *            待設置的工作流日誌接口的值
	 */
	public void setWfLogService(WfLogService wfLogService) {
		this.wfLogService = wfLogService;
	}
	
	/**
	 * 獲取屬性錯誤信息.
	 * 
	 * @return 屬性錯誤信息的值
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	
	/**
	 * 設置屬性錯誤信息.
	 * 
	 * @param errorMessage
	 *            待設置的錯誤信息的值
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	/**
	 * 獲取屬性標題.
	 * 
	 * @return 屬性標題的值
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * 設置屬性標題.
	 * 
	 * @param title
	 *            待設置的標題的值
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 獲取屬性處理類型.
	 * 
	 * @return 屬性處理類型的值
	 */
	public String getHandType() {
		return handType;
	}

	/**
	 * 設置屬性處理類型.
	 * 
	 * @param handType
	 *            待設置的處理類型的值
	 */
	public void setHandType(String handType) {
		this.handType = handType;
	}

	/**
	 * 獲取屬性編輯類型.
	 * 
	 * @return 屬性編輯類型的值
	 */
	public String getEditType() {
		return editType;
	}

	/**
	 * 設置屬性編輯類型.
	 * 
	 * @param editType
	 *            待設置的編輯類型的值
	 */
	public void setEditType(String editType) {
		this.editType = editType;
	}
	
	/**
	 * 獲取屬性核保審核處理接口.
	 * 
	 * @return 屬性核保審核處理接口的值
	 */
	public CommonDealSubmitService getCommonDealSubmitService() {
		return commonDealSubmitService;
	}


	/**
	 * 設置屬性核保審核處理接口.
	 * 
	 * @param commonDealSubmitService
	 *            待設置的核保審核處理接口的值
	 */
	public void setCommonDealSubmitService(
			CommonDealSubmitService commonDealSubmitService) {
		this.commonDealSubmitService = commonDealSubmitService;
	}
}
