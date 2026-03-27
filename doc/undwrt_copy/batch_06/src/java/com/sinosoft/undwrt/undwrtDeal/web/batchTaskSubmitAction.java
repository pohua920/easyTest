package com.sinosoft.undwrt.undwrtDeal.web;

import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.undwrt.undwrtBase.model.UwNotion;
import com.sinosoft.undwrt.undwrtBase.model.UwNotionId;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.CommonDealSubmitService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.CommonDealTaskService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.WfLogHelperService;

/**
 * 提交或放棄任務 .
 */
public class batchTaskSubmitAction extends Struts2Action {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 屬性處理類型. */
	private String dealType;

	/** 屬性跳轉頁面返回結果. */
	private String content;

	/** 屬性工作流日誌接口. */
	private WfLogService wfLogService;

	/** 屬性核保系統提交任務服務接口. */
	private CommonDealTaskService commonDealTaskService;

	/** 屬性處理意見. */
	private String handleText;

	/** 屬性核保處理意見. */
	private Collection uwNotionList = new ArrayList();

	/** 屬性操作標誌位. */
	private String[] operateFlag;

	/** 屬性工作流號. */
	private String[] flowID;

	/** 屬性序號. */
	private String[] logNo;

	/** 屬性標題. */
	private String handTitle;

	/** 屬性處理類型. */
	private String handType;

	/** 屬性編輯類型. */
	private String editType;

	/** 屬性險種代碼. */
	private String riskCode;

	/** 屬性核保系統幫助服務接口. */
	private WfLogHelperService wfLogHelperService;

	/** 屬性核保審核處理接口. */
	private CommonDealSubmitService commonDealSubmitService;

	/**
	 * 處理任務.
	 * 
	 * @return 跳轉頁面結果
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public String handleTask() throws UserException, Exception {
		HttpSession session = this.getSession();
		handTitle = (String) session.getAttribute("HandTitle");
		String forward = "";
		String[] HandType = handType.split(",");
		handType = HandType[0];
		String[] EditType = editType.split(",");
		editType = EditType[0];
		handleText = handleText.replace("'", "''");
		// 处理类型 submitBefore--提交类表 saveNotion--保存任务
		if (handleText == null) {
			handleText = "";
		}
		UwNotion uwNotionDto = null;
		for (int i = 0; i < operateFlag.length; i++) {
			if (operateFlag[i].equals("Y") && !logNo[i].equals("0")) {
				uwNotionDto = new UwNotion();
				UwNotionId id = new UwNotionId();
				id.setFlowId(flowID[i]);
				id.setLogNo(Integer.parseInt(logNo[i]));
				uwNotionDto.setId(id);
				uwNotionDto.setHandleText(handleText);
				uwNotionList.add(uwNotionDto);
			}

		}
		HttpServletRequest req = ServletActionContext.getRequest();
		if (null == req) {
			req = this.getRequest();
		}
		PrpDuserDto prpDuserDto = new PrpDuserDto();
		prpDuserDto.setUserCode((String) req.getSession(false).getAttribute(
				"myUserCode"));
		prpDuserDto.setUserName((String) req.getSession(false).getAttribute(
				"myUserName"));
		prpDuserDto.setComCode((String) req.getSession(false).getAttribute(
				"myComCode"));
		// 将HandleText拆分成多条 变成多个uwNotionDto对象批量插入uwNotion表
		// WfLogQueryViewHelper wfLogQueryViewHelper = new
		// WfLogQueryViewHelper();
		try {
			// 保存审批意见
			if (dealType.equals("saveNotion")) {
				// 保存审批意见
				commonDealTaskService.saveBatchTask(uwNotionList, prpDuserDto);
				forward = "saveNotion";
				content = getText("undwrt.action.batchTaskSubmit.appOpinionSaveSuccess");
			}
			// 获取提交列表
			else if (dealType.equals("submitBefore")) {
				// 保存审批意见
				commonDealTaskService.saveBatchTask(uwNotionList, prpDuserDto);
				// 提交列表
				wfLogHelperService.setBatchTaskListDtoToView(req);
				forward = "submitBefore";
			}
			// 提交节点
			else if (dealType.equals("submit")) {
				// 保存审批意见
				commonDealTaskService.saveBatchTask(uwNotionList, prpDuserDto);
				// 保存任务
				Collection wfLogList = wfLogHelperService.setBatchTaskViewToDto(req, flowID, logNo);
				commonDealSubmitService.submitBatchTask(wfLogList);
				forward = "success";
				content = getText("undwrt.action.batchTaskSubmit.taskSubmitSuccess");
			}
			// 放弃任务 addby yanglibo 20090827 begin
			else if (dealType.equals("cancel")) {
				// 更新节点
				wfLogHelperService.setCancelTaskListDtoToView(flowID, logNo);
				forward = "cancel";
				content = getText("undwrt.action.batchTaskSubmit.taskDropSuccess");
			}
		} catch (UserException usee) {
			forward = "failure";
			throw usee;
		} catch (Exception e) {
			forward = "failure";
			throw e;
		}
		return forward;
	}

	/**
	 * 獲取屬性處理類型.
	 * 
	 * @return 屬性處理類型的值
	 */
	public String getDealType() {
		return dealType;
	}

	/**
	 * 設置屬性處理類型.
	 * 
	 * @param dealType
	 *            待設置的處理類型的值
	 */
	public void setDealType(String dealType) {
		this.dealType = dealType;
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
	 * 獲取屬性核保系統提交任務服務接口.
	 * 
	 * @return 屬性核保系統提交任務服務接口的值
	 */
	public CommonDealTaskService getCommonDealTaskService() {
		return commonDealTaskService;
	}

	/**
	 * 設置屬性核保系統提交任務服務接口.
	 * 
	 * @param commonDealTaskService
	 *            待設置的核保系統提交任務服務接口的值
	 */
	public void setCommonDealTaskService(
			CommonDealTaskService commonDealTaskService) {
		this.commonDealTaskService = commonDealTaskService;
	}

	/**
	 * 獲取屬性處理意見.
	 * 
	 * @return 屬性處理意見的值
	 */
	public String getHandleText() {
		return handleText;
	}

	/**
	 * 設置屬性處理意見.
	 * 
	 * @param handleText
	 *            待設置的處理意見的值
	 */
	public void setHandleText(String handleText) {
		this.handleText = handleText;
	}

	/**
	 * 獲取屬性核保處理意見.
	 * 
	 * @return 屬性核保處理意見的值
	 */
	public Collection getUwNotionList() {
		return uwNotionList;
	}

	/**
	 * 設置屬性核保處理意見.
	 * 
	 * @param uwNotionList
	 *            待設置的核保處理意見的值
	 */
	public void setUwNotionList(Collection uwNotionList) {
		this.uwNotionList = uwNotionList;
	}

	/**
	 * 獲取屬性操作標誌位.
	 * 
	 * @return 屬性操作標誌位的值
	 */
	public String[] getOperateFlag() {
		return operateFlag;
	}

	/**
	 * 設置屬性操作標誌位.
	 * 
	 * @param operateFlag
	 *            待設置的操作標誌位的值
	 */
	public void setOperateFlag(String[] operateFlag) {
		this.operateFlag = operateFlag;
	}

	/**
	 * 獲取屬性工作流號.
	 * 
	 * @return 屬性工作流號的值
	 */
	public String[] getFlowID() {
		return flowID;
	}

	/**
	 * 設置屬性工作流號.
	 * 
	 * @param flowID
	 *            待設置的工作流號的值
	 */
	public void setFlowID(String[] flowID) {
		this.flowID = flowID;
	}

	/**
	 * 獲取屬性序號.
	 * 
	 * @return 屬性序號的值
	 */
	public String[] getLogNo() {
		return logNo;
	}

	/**
	 * 設置屬性序號.
	 * 
	 * @param logNo
	 *            待設置的序號的值
	 */
	public void setLogNo(String[] logNo) {
		this.logNo = logNo;
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
	 * 獲取屬性險種代碼.
	 * 
	 * @return 屬性險種代碼的值
	 */
	public String getRiskCode() {
		return riskCode;
	}

	/**
	 * 設置屬性險種代碼.
	 * 
	 * @param riskCode
	 *            待設置的險種代碼的值
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 獲取屬性核保系統幫助服務接口.
	 * 
	 * @return 屬性核保系統幫助服務接口的值
	 */
	public WfLogHelperService getWfLogHelperService() {
		return wfLogHelperService;
	}

	/**
	 * 設置屬性核保系統幫助服務接口.
	 * 
	 * @param wfLogHelperService
	 *            待設置的核保系統幫助服務接口的值
	 */
	public void setWfLogHelperService(WfLogHelperService wfLogHelperService) {
		this.wfLogHelperService = wfLogHelperService;
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
